package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.persistence.workflow.WorkflowMother;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionTestBase;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static edu.harvard.iq.dataverse.persistence.dataset.DatasetMother.givenDatasetFiled;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputFileProcessor.XDS_INPUT_FILE_NAME;

class XdsMissingInputFillingStepTest extends WorkflowExecutionTestBase {

    Path workDir;

    WorkflowExecutionContext context = givenWorkflowExecutionContext(
            1L, WorkflowMother.givenWorkflow(1L));

    @BeforeEach
    public void setUp() throws IOException {
        workDir = Files.createTempDirectory("xds-test-temp");
        String inputFileSourcePath = getFileFromResources("xds/xsmv-test-" + XDS_INPUT_FILE_NAME);
        Files.copy(Paths.get(inputFileSourcePath), workDir.resolve(XDS_INPUT_FILE_NAME));
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.list(workDir).forEach(f -> f.toFile().delete());
        workDir.toFile().delete();
    }

    @Test
    @DisplayName("Should supply missing values in XDS.INP with available metadata")
    public void shouldSupplyMissingValuesFromMetadata() throws Exception {
        // given
        context.getDatasetVersion().getDatasetFields().add(givenDatasetFiled(MxrdrMetadataField.DATA_COLLECTION.getValue(),
                givenDatasetFiled(MxrdrMetadataField.DATA_COLLECTION_ORG_X.getValue(), "12.3"),
                givenDatasetFiled(MxrdrMetadataField.DATA_COLLECTION_ORG_Y.getValue(), "45.6"),
                givenDatasetFiled(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_DISTANCE.getValue(), "78.9"),
                givenDatasetFiled(MxrdrMetadataField.DATA_COLLECTION_STARTING_ANGLE.getValue(), "98.7"),
                givenDatasetFiled(MxrdrMetadataField.DATA_COLLECTION_WAVE_LENGTH.getValue(), "65.4")));

        XdsMissingInputFillingStep step = new XdsMissingInputFillingStep(new WorkflowStepParams());

        // when
        step.runInternal(context, workDir);

        // then
        List<String> lines = Files.readAllLines(workDir.resolve(XDS_INPUT_FILE_NAME));

        assertThat(lines.get(0)).startsWith("ORGX=12.3");
        assertThat(lines.get(0)).contains("ORGY=45.6");
        assertThat(lines.get(2)).startsWith("DETECTOR_DISTANCE=78.9");
        assertThat(lines.get(3)).startsWith("OSCILLATION_RANGE= 2345.0");
        assertThat(lines.get(4)).startsWith("STARTING_ANGLE=98.7");
        assertThat(lines.get(5)).startsWith("X-RAY_WAVELENGTH=65.4");
    }

    @Test
    @DisplayName("Should do nothing if no metadata values")
    public void shouldSupplyNoneIfNoMetadata() throws Exception {
        // given
        context.getDatasetVersion().getDatasetFields().add(givenDatasetFiled(MxrdrMetadataField.DATA_COLLECTION.getValue()));

        XdsMissingInputFillingStep step = new XdsMissingInputFillingStep(new WorkflowStepParams());

        // when
        step.runInternal(context, workDir);

        // then
        List<String> lines = Files.readAllLines(workDir.resolve(XDS_INPUT_FILE_NAME));

        assertThat(lines.get(0)).startsWith("ORGX= XXX");
        assertThat(lines.get(0)).contains("ORGY= XXX");
        assertThat(lines.get(2)).startsWith("DETECTOR_DISTANCE= XXX");
        assertThat(lines.get(3)).startsWith("OSCILLATION_RANGE= 2345.0");
        assertThat(lines.get(4)).startsWith("STARTING_ANGLE= 0");
        assertThat(lines.get(5)).startsWith("X-RAY_WAVELENGTH= XXX");
    }

    @Test
    @DisplayName("Should do nothing if value already present")
    public void shouldSupplyNoneIfValueAlreadyPresent() throws Exception {
        // given
        context.getDatasetVersion().getDatasetFields().add(
                givenDatasetFiled(MxrdrMetadataField.DATA_COLLECTION.getValue(),
                        givenDatasetFiled(MxrdrMetadataField.DATA_COLLECTION_OSCILLATION_STEP_SIZE.getValue(), "12.3")));

        XdsMissingInputFillingStep step = new XdsMissingInputFillingStep(new WorkflowStepParams());

        // when
        step.runInternal(context, workDir);

        // then
        List<String> lines = Files.readAllLines(workDir.resolve(XDS_INPUT_FILE_NAME));

        assertThat(lines.get(0)).startsWith("ORGX= XXX");
        assertThat(lines.get(0)).contains("ORGY= XXX");
        assertThat(lines.get(2)).startsWith("DETECTOR_DISTANCE= XXX");
        assertThat(lines.get(3)).startsWith("OSCILLATION_RANGE= 2345.0");
        assertThat(lines.get(4)).startsWith("STARTING_ANGLE= 0");
        assertThat(lines.get(5)).startsWith("X-RAY_WAVELENGTH= XXX");
    }

    // -------------------- PRIVATE --------------------

    private String getFileFromResources(String name) {
        return getClass().getClassLoader().getResource(name).getPath();
    }
}
