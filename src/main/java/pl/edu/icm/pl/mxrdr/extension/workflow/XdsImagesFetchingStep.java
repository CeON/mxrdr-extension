package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.datafile.FileMetadata;
import edu.harvard.iq.dataverse.workflow.WorkflowContext;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static edu.harvard.iq.dataverse.dataaccess.DataAccess.dataAccess;
import static pl.edu.icm.pl.mxrdr.extension.workflow.XdsImagesPatternStep.FILE_NAMES_PARAM_NAME;
import static pl.edu.icm.pl.mxrdr.extension.workflow.XdsImagesPatternStep.FILE_NAMES_SEPARATOR;

/**
 * Fetches dataset version files into local directory filtering images only.
 */
class XdsImagesFetchingStep extends XdsWorkflowStep {

    static final String STEP_ID = "xds-images-fetching";

    // -------------------- CONSTRUCTORS --------------------

    public XdsImagesFetchingStep(DatasetVersionServiceBean datasetVersions) {
        super(datasetVersions);
    }

    // -------------------- LOGIC --------------------

    @Override
    protected WorkflowStepResult runInternal(WorkflowContext context, Path workDir) throws Exception {
        Path imgDir = imagesDir(workDir);

        List<String> fileNames = new ArrayList<>();
        for (FileMetadata metadata : datasetVersion.getFileMetadatas()) {
            fileNames.addAll(new ImageFetcher(metadata).fetchInto(imgDir));
        }

        Map<String, String> outputParams = new HashMap<>();
        outputParams.put(FILE_NAMES_PARAM_NAME, String.join(FILE_NAMES_SEPARATOR, fileNames));
        // FIXME: pass on outputParams
        return WorkflowStepResult.OK;
    }

    // -------------------- INNER CLASSES --------------------

    static class ImageFetcher {

        static final Predicate<String> IMAGES_PATTERNS =
                Stream.of(".+\\d+\\.cbf", ".+\\d+\\.img", ".+\\d+\\.mccd", ".+\\d+\\.h5", ".+master\\.h5")
                        .map(Pattern::compile)
                        .map(Pattern::asPredicate)
                        .reduce(x -> false, Predicate::or);

        private final FileMetadata metadata;

        ImageFetcher(FileMetadata metadata) {
            this.metadata = metadata;
        }

        List<String> fetchInto(Path dir) throws IOException {
            if (isZip(metadata)) {
                return fetchZipContents(this::fileStorage, dir);
            } else {
                return fetchImage(metadata.getLabel(), this::fileStorage, dir)
                        .map(Collections::singletonList)
                        .orElseGet(Collections::emptyList);
            }
        }

        private InputStream fileStorage() throws IOException {
            return dataAccess().getStorageIO(metadata.getDataFile()).getInputStream();
        }

        private boolean isZip(FileMetadata metadata) {
            return metadata.getLabel()
                    .endsWith(".zip"); // FIXME: anything else?
        }

        private List<String> fetchZipContents(Storage storage, Path dir) throws IOException {
            try (ZipInputStream zip = new ZipInputStream(storage.getInputStream())) {
                List<String> names = new ArrayList<>();
                ZipEntry entry;
                while ((entry = zip.getNextEntry()) != null) {
                    fetchImage(entry.getName(), () -> zip, dir)
                            .ifPresent(names::add);
                    zip.closeEntry();
                }
                return names;
            }
        }

        private Optional<String> fetchImage(String fileName, Storage storage, Path dir) throws IOException {
            if (isImage(fileName)) {
                Path filePath = dir.resolve(fileName);
                if (Files.exists(filePath)) {
                    throw new IllegalArgumentException("Duplicate file: " + fileName);
                }
                try (InputStream in = storage.getInputStream()) {
                    Files.copy(in, filePath);
                }
                return Optional.of(fileName);
            } else {
                return Optional.empty();
            }
        }

        private boolean isImage(String fileName) {
            return IMAGES_PATTERNS.test(fileName);
        }
    }

    @FunctionalInterface
    interface Storage {
        InputStream getInputStream() throws IOException;
    }
}
