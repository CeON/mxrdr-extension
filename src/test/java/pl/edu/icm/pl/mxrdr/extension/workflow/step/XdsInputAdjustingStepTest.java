package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import org.apache.commons.io.FileUtils;
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
    }

    @AfterEach
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(workDir.toFile());
    }

    @Test
    @DisplayName("Should update parameters in XDS.INP file")
    public void shouldUpdateParametersInFile() throws Exception {
        // given
        copyCorrectLpFileToWorkDir("xds/xar-test-CORRECT.LP");
        copyInputXdsFileToWorkDir("xds/xar-test-XDS.INP");
        
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
    @DisplayName("Should not update INCLUDE_RESOLUTION_RANGE parameter in XDS.INP file")
    public void shouldNotUpdateIncludeResolutionRange() throws Exception {
        // given
        copyCorrectLpFileToWorkDir("xds/no-replace-resolution-range-CORRECT.LP");
        copyInputXdsFileToWorkDir("xds/xar-test-XDS.INP");
        
        Map<String, String> input = new HashMap<>();
        input.put(XdsInputAdjustingStep.JOBS_PARAM_NAME, JOB_LIST);
        input.put(XdsInputAdjustingStep.ADJUST_RESOLUTION_PARAM_NAME, "true");
        XdsInputAdjustingStep step = new XdsInputAdjustingStep(new WorkflowStepParams(input));

        // when
        step.runInternal(null, workDir);

        // then
        List<String> lines = Files.readAllLines(workDir.resolve(XDS_INPUT_FILE_NAME));

        assertThat(lines).anyMatch(s -> s.equals("INCLUDE_RESOLUTION_RANGE=50 0  ! after CORRECT, insert high resol limit; re-run CORRECT"));
    }

    @Test
    @DisplayName("Should correctly extract value for INCLUDE_RESOLUTION_RANGE")
    public void shouldExtractProperValueForResolution() throws IOException {
        // given
        copyCorrectLpFileToWorkDir("xds/xar-test-CORRECT.LP");
        ResolutionParameterExtractor extractor = new ResolutionParameterExtractor(workDir);

        // when
        String value = extractor.extract();

        // then
        assertThat(value).isEqualTo("2.11");
    }

    @Test
    @DisplayName("Should not extract invalid value for INCLUDE_RESOLUTION_RANGE")
    public void shouldNotExtractProperValueForResolution() throws IOException {
        // given
        copyCorrectLpFileToWorkDir("xds/no-replace-resolution-range-CORRECT.LP");
        ResolutionParameterExtractor extractor = new ResolutionParameterExtractor(workDir);

        // when
        String value = extractor.extract();

        // then
        assertThat(value).isEmpty();
    }

    // -------------------- PRIVATE --------------------

    private void copyInputXdsFileToWorkDir(String classpath) throws IOException {
        String path = getClass().getClassLoader().getResource(classpath).getPath();
        Files.copy(Paths.get(path), workDir.resolve(XDS_INPUT_FILE_NAME));
    }
    private void copyCorrectLpFileToWorkDir(String classpath) throws IOException {
        String path = getClass().getClassLoader().getResource(classpath).getPath();
        Files.copy(Paths.get(path), workDir.resolve(XDS_OUTPUT_FILE_NAME));
    }
}
