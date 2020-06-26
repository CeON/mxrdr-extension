package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.datafile.FileMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.workflow.XdsImagesFetchingStep.Storage;
import pl.edu.icm.pl.mxrdr.extension.workflow.XdsImagesFetchingStep.StorageSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class XdsImagesFetchingStepTest {

    private static final String[] IMAGES_NAMES = {
            "match001.cbf",
            "match002.cbf",
            "match020.cbf",
            "match920.cbf",
            "match5555.img",
            "sth-12.h5",
            "sth-master.h5"
    };

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    Map<String, String> inputParams = new HashMap<>();
    DatasetVersionServiceBean datasetVersions = mock(DatasetVersionServiceBean.class);
    StorageSource storageSource = new ClasspathStorageSource(classLoader);

    XdsImagesFetchingStep step = new XdsImagesFetchingStep(inputParams, datasetVersions, storageSource);

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
        List<String> listedNames = Files.list(tmpDir).map(Path::getFileName).map(Path::toString).collect(toList());
        // then
        assertThat(fetchedNames).containsOnly(IMAGES_NAMES);
        assertThat(listedNames).containsOnly(IMAGES_NAMES);
    }

    private List<FileMetadata> filesMetadataFromPath(String dirPath) throws IOException, URISyntaxException {
        return Files.list(Paths.get(classLoader.getResource(dirPath).toURI()))
                    .map(filePath -> {
                        FileMetadata metadata = new FileMetadata();
                        metadata.setLabel(filePath.getFileName().toString());
                        return metadata;
                    })
                    .collect(toList());
    }

    static class ClasspathStorageSource implements StorageSource {

        private final ClassLoader classLoader;

        ClasspathStorageSource(ClassLoader classLoader) {
            this.classLoader = classLoader;
        }

        @Override
        public Storage getStorage(FileMetadata metadata) {
            return () -> {
                String fileName = metadata.getLabel();
                return classLoader.getResourceAsStream("xds/" + fileName);
            };
        }
    }
}