package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.datafile.FileMetadata;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.workflow.WorkflowContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static edu.harvard.iq.dataverse.dataaccess.DataAccess.dataAccess;
import static edu.harvard.iq.dataverse.workflow.step.Success.successWith;
import static pl.edu.icm.pl.mxrdr.extension.workflow.XdsImagesPatternStep.FILE_NAMES_PARAM_NAME;
import static pl.edu.icm.pl.mxrdr.extension.workflow.XdsImagesPatternStep.FILE_NAMES_SEPARATOR;

/**
 * Fetches dataset version files into local directory filtering images only.
 */
class XdsImagesFetchingStep extends FilesystemAccessingWorkflowStep {

    static final String STEP_ID = "xds-images-fetching";

    static final StorageSource DEFAULT_STORAGE_SOURCE = metadata ->
            () -> dataAccess().getStorageIO(metadata.getDataFile()).getInputStream();

    private final DatasetVersionServiceBean datasetVersions;
    private final StorageSource storageSource;

    // -------------------- CONSTRUCTORS --------------------

    public XdsImagesFetchingStep(Map<String, String> inputParams, DatasetVersionServiceBean datasetVersions) {
        this(inputParams, datasetVersions, DEFAULT_STORAGE_SOURCE);
    }

    public XdsImagesFetchingStep(Map<String, String> inputParams, DatasetVersionServiceBean datasetVersions,
                                 StorageSource storageSource) {
        super(inputParams);
        this.datasetVersions = datasetVersions;
        this.storageSource = storageSource;
    }

    // -------------------- LOGIC --------------------

    @Override
    protected WorkflowStepResult.Source runInternal(WorkflowContext context, Path workDir) throws Exception {
        Path imgDir = imagesDir(workDir);

        List<String> fileNames = fetchInto(getDatasetVersion(context).getFileMetadatas(), imgDir);

        return successWith(outputParams ->
                outputParams.put(FILE_NAMES_PARAM_NAME, String.join(FILE_NAMES_SEPARATOR, fileNames))
        );
    }

    List<String> fetchInto(List<FileMetadata> metadatas, Path imgDir) throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (FileMetadata metadata : metadatas) {
            fileNames.addAll(new ImageFetcher(metadata, storageSource).fetchInto(imgDir));
        }
        return fileNames;
    }

    @Override
    public WorkflowStepResult resume(WorkflowContext context, Map<String, String> internalData, String externalData) {
        throw new UnsupportedOperationException("This step des not pause");
    }

    @Override
    public void rollback(WorkflowContext context, Failure reason) {
    }

    // -------------------- PRIVATE --------------------

    private Path imagesDir(Path workDir) throws IOException {
        return Files.createDirectories(workDir.resolve("img"));
    }

    private DatasetVersion getDatasetVersion(WorkflowContext context) {
        return datasetVersions.findByVersionNumber(
                context.getDataset().getId(), context.getNextVersionNumber(), context.getNextMinorVersionNumber());
    }

    // -------------------- INNER CLASSES --------------------

    /**
     * Represents source of binary data to read from.
     */
    @FunctionalInterface
    interface Storage {
        InputStream getInputStream() throws IOException;
    }

    /**
     * Represents {@link Storage} supplier for given {@link FileMetadata}.
     */
    @FunctionalInterface
    interface StorageSource {
        Storage getStorage(FileMetadata metadata);
    }

    /**
     * Fetches given file from remote {@link Storage} into local directory.
     * Fetching is done only if the file confirms to defined image naming patterns.
     * If given file is a ZIP archive, it's contents are unpacked and taken.
     */
    static class ImageFetcher {

        static final Predicate<String> IMAGES_PATTERNS =
                Stream.of(".+\\d+\\.cbf", ".+\\d+\\.img", ".+\\d+\\.mccd", ".+\\d+\\.h5", ".+master\\.h5")
                        .map(Pattern::compile)
                        .map(Pattern::asPredicate)
                        .reduce(x -> false, Predicate::or);

        private final FileMetadata metadata;
        private final StorageSource storageSource;

        ImageFetcher(FileMetadata metadata, StorageSource storageSource) {
            this.metadata = metadata;
            this.storageSource = storageSource;
        }

        List<String> fetchInto(Path dir) throws IOException {
            Storage fileStorage = storageSource.getStorage(metadata);
            if (isZip(metadata)) {
                return fetchZipContents(fileStorage, dir);
            } else {
                return fetchImage(metadata.getLabel(), fileStorage, dir)
                        .map(Collections::singletonList)
                        .orElseGet(Collections::emptyList);
            }
        }

        private boolean isZip(FileMetadata metadata) {
            return metadata.getLabel()
                    .endsWith(".zip"); // FIXME: anything else?
        }

        private List<String> fetchZipContents(Storage storage, Path dir) throws IOException {
            try (ZipInputStream zip = new ZipInputStream(storage.getInputStream())) {
                List<String> names = new ArrayList<>();
                Storage zipStorage = () -> new NonClosableInputStream(zip);
                ZipEntry entry;
                while ((entry = zip.getNextEntry()) != null) {
                    fetchImage(entry.getName(), zipStorage, dir)
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

    /**
     * Prevents stream from closing on {@link java.io.Closeable} call.
     */
    static class NonClosableInputStream extends InputStream {

        private final InputStream stream;

        NonClosableInputStream(InputStream stream) {
            this.stream = stream;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public void close() {
            // do nothing
        }
    }
}
