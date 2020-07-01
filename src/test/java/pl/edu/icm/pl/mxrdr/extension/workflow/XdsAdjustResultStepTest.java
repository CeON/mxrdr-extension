package pl.edu.icm.pl.mxrdr.extension.workflow;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class XdsAdjustResultStepTest {
    private static final String JOB_LIST = "TEST_JOB_LIST";
    private static final String TEST_PATH_PREFIX = "xds/xar-test-";
    private Path workDir;

    @BeforeEach
    public void setUp() throws IOException {
        workDir = Files.createTempDirectory("xds-test-temp");
        String correctionFileSourcePath = getFileFromResources(XdsAdjustResultStep.CORRECT_LP);
        String inputFileSourcePath = getFileFromResources(XdsAdjustResultStep.XDS_INP);
        Files.copy(Paths.get(correctionFileSourcePath), workDir.resolve(XdsAdjustResultStep.CORRECT_LP));
        Files.copy(Paths.get(inputFileSourcePath), workDir.resolve(XdsAdjustResultStep.XDS_INP));
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.list(workDir).forEach(f -> f.toFile().delete());
        workDir.toFile().delete();
    }

    @Test
    @DisplayName("Should update parameters in XDS.INP file")
    public void shouldUpdateParametersInFile() throws Exception {
        // given
        Map<String, String> input = new HashMap<>();
        input.put(XdsAdjustResultStep.XDS_JOB_LIST_PARAM, JOB_LIST);
        input.put(XdsAdjustResultStep.COMPUTE_AND_UPDATE_RESOLUTION_RANGE_PARAM, "true");
        XdsAdjustResultStep step = new XdsAdjustResultStep(input);

        // when
        step.runInternal(null, workDir);

        // then
        List<String> lines = Files.readAllLines(workDir.resolve(XdsAdjustResultStep.XDS_INP));

        assertThat(lines).anyMatch(s -> s.contains(JOB_LIST));
        assertThat(lines).anyMatch(s -> s.contains("INCLUDE_RESOLUTION_RANGE=50"))
                .noneMatch(s -> s.contains("INCLUDE_RESOLUTION_RANGE=50 0")); // here we care only whether the parameter is changed
    }

    @Test
    @DisplayName("Should correctly extract value for INCLUDE_RESOLUTION_RANGE")
    public void shouldExtractProperValueForResolution() {
        // given
        XdsAdjustResultStep.ResolutionParameterExtractor extractor = XdsAdjustResultStep.ResolutionParameterExtractor.of(workDir);

        // when
        String value = extractor.extract();

        // then
        assertThat(value).isEqualTo("2.11");
    }

    // -------------------- PRIVATE --------------------

    private String getFileFromResources(String name) {
        return getClass().getClassLoader().getResource(TEST_PATH_PREFIX + name).getPath();
    }
}