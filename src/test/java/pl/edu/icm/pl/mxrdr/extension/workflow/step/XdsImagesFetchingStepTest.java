package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.persistence.datafile.FileMetadata;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsImagesFetchingStep.Storage;
import pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsImagesFetchingStep.StorageSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Thread.currentThread;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsImagesFetchingStep.IMAGES_DIR_PARAM_DEFAULT;

public class XdsImagesFetchingStepTest {

    static final String[] IMAGES_NAMES = {
            "match001.cbf",
            "match002.cbf",
            "match005.cbf",
            "match010.cbf",
            "match020.cbf",
            "match920.cbf",
            "match5555.img",
            "sth-12.h5",
            "sth-master.h5",
            "ZD-3_Pn7.0001"
    };

    private static final String[] EXPECTED_PATHS = Stream.of(IMAGES_NAMES)
            .map(name -> "img/" + name).toArray(String[]::new);

    StorageSource storageSource = new ClasspathStorageSource();

    XdsImagesFetchingStep step = new XdsImagesFetchingStep(new WorkflowStepParams(), null, storageSource);

    Path tmpDir;

    @BeforeEach
    void setUp() throws IOException {
        tmpDir = Files.createTempDirectory("test");
        tmpDir.toFile().deleteOnExit();
    }

    @Test
    void shouldFilterAndFetchFiles() throws Exception {
        // given
        List<FileMetadata> metadatas = filesMetadataFromPath("xds");
        // when
        List<String> fetchedNames = step.fetchInto(metadatas, tmpDir);
        // then
        assertThat(fetchedNames).containsOnly(EXPECTED_PATHS);
        // when
        List<String> listedNames = Files.list(tmpDir.resolve(IMAGES_DIR_PARAM_DEFAULT))
                .map(Path::getFileName).map(Path::toString).collect(toList());
        // then
        assertThat(listedNames).containsOnly(IMAGES_NAMES);
    }

    private List<FileMetadata> filesMetadataFromPath(String dirPath) throws IOException, URISyntaxException {
        return Files.list(Paths.get(currentThread().getContextClassLoader().getResource(dirPath).toURI()))
                    .map(filePath -> {
                        FileMetadata metadata = new FileMetadata();
                        metadata.setLabel(filePath.getFileName().toString());
                        return metadata;
                    })
                    .collect(toList());
    }

    static class ClasspathStorageSource implements StorageSource {

        @Override
        public Storage getStorage(FileMetadata metadata) {
            return () -> {
                String fileName = metadata.getLabel();
                return currentThread().getContextClassLoader().getResourceAsStream("xds/" + fileName);
            };
        }
    }
}