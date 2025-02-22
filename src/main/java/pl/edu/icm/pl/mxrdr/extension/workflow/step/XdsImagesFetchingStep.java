package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.dataaccess.DataAccessOption;
import edu.harvard.iq.dataverse.dataaccess.StorageIO;
import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.datafile.DataFile;
import edu.harvard.iq.dataverse.persistence.datafile.FileMetadata;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionStepContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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

/**
 * Fetches dataset version files into local directory filtering images only.
 */
public class XdsImagesFetchingStep extends FilesystemAccessingWorkflowStep {

    private static final Logger log = LoggerFactory.getLogger(XdsImagesFetchingStep.class);

    public static final String STEP_ID = "xds-fetch-images";

    /**
     * Input parameter containing the name of images directory within current working directory.
     * Defaults to {@value IMAGES_DIR_PARAM_DEFAULT}.
     */
    static final String IMAGES_DIR_PARAM_NAME = "imgDir";

    /**
     * Default value for {@value IMAGES_DIR_PARAM_NAME} input parameter.
     */
    static final String IMAGES_DIR_PARAM_DEFAULT = "img";

    /**
     * Default files storage source using {@link StorageIO} API.
     */
    static final StorageSource DEFAULT_STORAGE_SOURCE = metadata -> () -> {
        StorageIO<DataFile> storage = dataAccess().getStorageIO(metadata.getDataFile());
        storage.open(DataAccessOption.READ_ACCESS);
        return storage.getInputStream();
    };

    private final String imgDirName;
    private final DatasetVersionServiceBean versionsService;
    private final StorageSource storageSource;

    // -------------------- CONSTRUCTORS --------------------

    public XdsImagesFetchingStep(WorkflowStepParams inputParams, DatasetVersionServiceBean versionsService) {
        this(inputParams, versionsService, DEFAULT_STORAGE_SOURCE);
    }

    public XdsImagesFetchingStep(WorkflowStepParams inputParams, DatasetVersionServiceBean versionsService, StorageSource storageSource) {
        super(inputParams);
        this.imgDirName = inputParams.getOrDefault(IMAGES_DIR_PARAM_NAME, IMAGES_DIR_PARAM_DEFAULT);
        this.versionsService = versionsService;
        this.storageSource = storageSource;
    }

    // -------------------- LOGIC --------------------

    @Override
    protected WorkflowStepResult.Source runInternal(WorkflowExecutionStepContext context, Path workDir) throws Exception {
        List<FileMetadata> fileMetadata = versionsService
                .withDatasetVersion(context, DatasetVersion::getFileMetadatas)
                .orElseGet(Collections::emptyList);
        fetchInto(fileMetadata, workDir);
        return successWith(data ->
                data.put(IMAGES_DIR_PARAM_NAME, imgDirName)
        );
    }

    List<String> fetchInto(List<FileMetadata> metadatas, Path workDir) throws IOException {
        Path imgDir = Files.createDirectories(workDir.resolve(imgDirName));
        log.trace("Fetching images into {}", imgDir);
        Path relativeImgDir = workDir.relativize(imgDir);
        List<String> fileNames = new ArrayList<>();
        for (FileMetadata metadata : metadatas) {
            new ImageFetcher(metadata, storageSource).fetchInto(imgDir).stream()
                    .map(fileName -> relativeImgDir.resolve(fileName).toString())
                    .forEach(fileNames::add);
        }
        log.trace("Fetched {} images into {}", fileNames.size(), imgDir);
        return fileNames;
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionStepContext context, Map<String, String> internalData, String externalData) {
        throw new UnsupportedOperationException("This step des not pause");
    }

    @Override
    public void rollback(WorkflowExecutionStepContext context, Failure reason) {
        String imgDir = context.getStepExecution().getOutputParams().get(IMAGES_DIR_PARAM_NAME);
        if (imgDir == null) {
            log.warn("Output directory missing in outputParams.");
            return;
        }

        resolveWorkDirFromOutputParams(context.getStepExecution())
                .map(workdir -> workdir.resolve(imgDir))
                .ifPresent(imgPath -> {
                    try {
                        FileUtils.deleteDirectory(imgPath.toFile());
                    } catch (IOException e) {
                        log.warn("Unable to remove temporary directory for xds images on step rollback: " + imgPath.toString());
                    }
                });
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
                Stream.of(".+\\d+\\.cbf", ".+\\d+\\.img", ".+\\d+\\.mccd", ".+\\d+\\.h5", 
                          ".+master\\.h5", ".+\\.\\d+", ".+\\.bz2", ".+\\.gz", ".+\\.xz")
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
                    .endsWith(".zip");
        }

        private List<String> fetchZipContents(Storage storage, Path dir) throws IOException {
            try (ZipInputStream zip = new ZipInputStream(storage.getInputStream())) {
                List<String> names = new ArrayList<>();
                Storage zipStorage = () -> new NonClosableInputStream(zip);
                ZipEntry entry;
                while ((entry = zip.getNextEntry()) != null) {
                    if (!entry.isDirectory()) {
                        fetchImage(entry.getName(), zipStorage, dir)
                            .ifPresent(names::add);
                    }
                    zip.closeEntry();
                }
                return names;
            }
        }

        private Optional<String> fetchImage(String fileName, Storage storage, Path dir) throws IOException {
            if (isImage(fileName)) {
                if (fileName.contains(File.separator)) {
                    fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.length());
                }
                Path filePath = dir.resolve(fileName);
                if (Files.exists(filePath)) {
                    String newSum = DigestUtils.md5Hex(storage.getInputStream());
                    String existingSum = DigestUtils.md5Hex(Files.newInputStream(filePath));
                    if (!existingSum.equals(newSum)) {
                        throw new IllegalArgumentException("Duplicate file: " + fileName);
                    } else {
                        return Optional.empty();
                    }
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
            // suppress & do nothing
        }
    }
}
