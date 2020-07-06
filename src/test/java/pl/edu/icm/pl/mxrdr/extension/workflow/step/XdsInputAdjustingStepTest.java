package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsInputAdjustingStep;
import pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsInputAdjustingStep.ResolutionParameterExtractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputFileProcessor.XDS_INPUT_FILE_NAME;
import static pl.edu.icm.pl.mxrdr.extension.xds.output.XdsOutputFileParser.XDS_OUTPUT_FILE_NAME;

public class XdsInputAdjustingStepTest {

    static final String JOB_LIST = "TEST_JOB_LIST";

    Path workDir;

    @BeforeEach
    public void setUp() throws IOException {
        workDir = Files.createTempDirectory("xds-test-temp");
        String correctionFileSourcePath = getFileFromResources(XDS_OUTPUT_FILE_NAME);
        String inputFileSourcePath = getFileFromResources(XDS_INPUT_FILE_NAME);
        Files.copy(Paths.get(correctionFileSourcePath), workDir.resolve(XDS_OUTPUT_FILE_NAME));
        Files.copy(Paths.get(inputFileSourcePath), workDir.resolve(XDS_INPUT_FILE_NAME));
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
        input.put(XdsInputAdjustingStep.JOBS_PARAM_NAME, JOB_LIST);
        input.put(XdsInputAdjustingStep.ADJUST_RESOLUTION_PARAM_NAME, "true");
        XdsInputAdjustingStep step = new XdsInputAdjustingStep(new WorkflowStepParams(input));

        // when
        step.runInternal(null, workDir);

        // then
        List<String> lines = Files.readAllLines(workDir.resolve(XDS_INPUT_FILE_NAME));

        assertThat(lines).anyMatch(s -> s.contains(JOB_LIST));
        assertThat(lines).anyMatch(s -> s.contains("INCLUDE_RESOLUTION_RANGE=50"))
                .noneMatch(s -> s.contains("INCLUDE_RESOLUTION_RANGE=50 0")); // here we care only whether the parameter is changed
    }

    @Test
    @DisplayName("Should correctly extract value for INCLUDE_RESOLUTION_RANGE")
    public void shouldExtractProperValueForResolution() throws IOException {
        // given
        ResolutionParameterExtractor extractor = new ResolutionParameterExtractor(workDir);

        // when
        String value = extractor.extract();

        // then
        assertThat(value).isEqualTo("2.11");
    }

    // -------------------- PRIVATE --------------------

    private String getFileFromResources(String name) {
        return getClass().getClassLoader().getResource("xds/xar-test-" + name).getPath();
    }
}
