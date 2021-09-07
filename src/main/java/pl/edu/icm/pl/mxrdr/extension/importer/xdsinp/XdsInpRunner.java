package pl.edu.icm.pl.mxrdr.extension.importer.xdsinp;

import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.settings.SettingsServiceBean;
import edu.harvard.iq.dataverse.util.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class XdsInpRunner {
    private static final Logger logger = LoggerFactory.getLogger(XdsInpRunner.class);

    private static final String GENERATE_XDS_INP_FULL_PATH = ":GenerateXdsInpFullPath";
    private SettingsServiceBean settingsService;

    // -------------------- CONSTRUCTORS --------------------

    public XdsInpRunner(SettingsServiceBean settingsService) {
        this.settingsService = settingsService;
    }

    // -------------------- LOGIC --------------------

    /**
     * Creates temporary working directory and moves input file there.
     */
    ProducedPaths prepareFiles(Map<ImporterFieldKey, Object> importerInput) {
        File inputFile = (File) importerInput.get(XdsInpFormKeys.INPUT_FILE);
        Path tempDirectory = null;
        Path movedInput = null;
        try {
            Path filesDirectory = Paths.get(SystemConfig.getFilesDirectoryStatic());
            if (!filesDirectory.toFile().exists()) {
                Files.createDirectories(filesDirectory);
            }
            tempDirectory = Files.createTempDirectory(filesDirectory, inputFile.getName());
            movedInput = Files.move(inputFile.toPath(), tempDirectory.resolve(inputFile.getName()));
        } catch (IOException ioe) {
            logger.error("Exception while preparing files to generate XDS.INP", ioe);
        }
        return new ProducedPaths(movedInput, tempDirectory);
    }

    /**
     * Starts generate_XDS.INP script which should, on successful run, produce
     * XDS.INP file for further processing.
     */
    void runGenerateXdsInp(ProducedPaths producedPaths) {
        String generateXdsInpPath = settingsService.get(GENERATE_XDS_INP_FULL_PATH);
        try {
            new ProcessBuilder(generateXdsInpPath, producedPaths.getInputFile().getFileName().toString())
                    .directory(producedPaths.getTempDirectory().toFile())
                    .start()
                    .waitFor();
        } catch (IOException ioe) {
            logger.error("Exception while creating XDS.INP", ioe);
        } catch (InterruptedException ie) {
            logger.info("Thread was interrupted", ie);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Removes input file, generated XDS.INP file and working directory.
     */
    void cleanUp(ProducedPaths producedPaths) {
        Stream.of(producedPaths.getInputFile(), producedPaths.resolveXdsInp(), producedPaths.getTempDirectory())
                .filter(Objects::nonNull)
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException ioe) {
                        logger.error(String.format("Error while removing temporary file (%s)", p.toString()), ioe);
                    }
                });
    }

    // -------------------- INNER CLASSES --------------------

    public static class ProducedPaths {
        private Path inputFile;
        private Path tempDirectory;

        // -------------------- CONSTRUCTORS --------------------

        public ProducedPaths(Path inputFile, Path tempDirectory) {
            this.inputFile = inputFile;
            this.tempDirectory = tempDirectory;
        }

        // -------------------- GETTERS --------------------

        public Path getInputFile() {
            return inputFile;
        }

        public Path getTempDirectory() {
            return tempDirectory;
        }

        // -------------------- LOGIC --------------------

        public Path resolveXdsInp() {
            return tempDirectory.resolve("XDS.INP");
        }
    }
}
