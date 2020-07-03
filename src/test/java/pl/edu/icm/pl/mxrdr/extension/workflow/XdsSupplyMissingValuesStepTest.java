package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.persistence.workflow.WorkflowMother;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowContextMother;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static edu.harvard.iq.dataverse.persistence.dataset.DatasetMother.createField;
import static edu.harvard.iq.dataverse.persistence.dataset.DatasetMother.givenDatasetVersion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;

class XdsSupplyMissingValuesStepTest {

    private static final String TEST_PATH_PREFIX = "xds/xsmv-test-";
    private static final String VALUE = "1234.5";

    private Path workDir;

    private WorkflowExecutionContext context = WorkflowContextMother.givenWorkflowExecutionContext(
            1L, WorkflowMother.givenWorkflow(1L));
    private DatasetVersionServiceBean versionService = Mockito.mock(DatasetVersionServiceBean.class);

    @BeforeEach
    public void setUp() throws IOException {
        workDir = Files.createTempDirectory("xds-test-temp");
        String inputFileSourcePath = getFileFromResources(TEST_PATH_PREFIX + XdsAdjustResultStep.XDS_INP);
        Files.copy(Paths.get(inputFileSourcePath), workDir.resolve(XdsAdjustResultStep.XDS_INP));
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
        DatasetVersion version = givenDatasetVersion(createField(MxrdrMetadataField.DATA_COLLECTION.getValue(),
                createField(MxrdrMetadataField.DATA_COLLECTION_ORG_X.getValue(), VALUE),
                createField(MxrdrMetadataField.DATA_COLLECTION_ORG_Y.getValue(), VALUE),
                createField(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_DISTANCE.getValue(), VALUE),
                createField(MxrdrMetadataField.DATA_COLLECTION_STARTING_ANGLE.getValue(), VALUE),
                createField(MxrdrMetadataField.DATA_COLLECTION_WAVE_LENGTH.getValue(), VALUE)));

        Mockito.when(versionService.findByVersionNumber(anyLong(), anyLong(), anyLong())).thenReturn(version);
        XdsSupplyMissingValuesStep step = new XdsSupplyMissingValuesStep(Collections.emptyMap(), versionService);

        // when
        step.runInternal(context, workDir);

        // then
        List<String> lines = Files.readAllLines(workDir.resolve(XdsAdjustResultStep.XDS_INP));

        assertThat(lines).anyMatch(contains("ORGX", VALUE));
        assertThat(lines).anyMatch(contains("ORGY", VALUE));
        assertThat(lines).anyMatch(contains("STARTING_ANGLE", VALUE));
        assertThat(lines).anyMatch(contains("DETECTOR_DISTANCE", VALUE));
        assertThat(lines).anyMatch(contains("X-RAY_WAVELENGTH", VALUE));
    }

    @Test
    @DisplayName("Should do nothing if no metadata values")
    public void shouldSupplyNoneIfNoMetadata() throws Exception {
        // given
        DatasetVersion version = givenDatasetVersion(createField(MxrdrMetadataField.DATA_COLLECTION.getValue()));

        Mockito.when(versionService.findByVersionNumber(anyLong(), anyLong(), anyLong())).thenReturn(version);
        XdsSupplyMissingValuesStep step = new XdsSupplyMissingValuesStep(Collections.emptyMap(), versionService);

        // when
        step.runInternal(context, workDir);

        // then
        List<String> lines = Files.readAllLines(workDir.resolve(XdsAdjustResultStep.XDS_INP));

        assertThat(lines).anyMatch(contains("ORGX", "XXX"));
        assertThat(lines).anyMatch(contains("ORGY", "XXX"));
        assertThat(lines).anyMatch(contains("STARTING_ANGLE", "0"));
        assertThat(lines).anyMatch(contains("DETECTOR_DISTANCE", "XXX"));
        assertThat(lines).anyMatch(contains("X-RAY_WAVELENGTH", "XXX"));
    }

    @Test
    @DisplayName("Should do nothing if value already present")
    public void shouldSupplyNoneIfValueAlreadyPresent() throws Exception {
        // given
        DatasetVersion version = givenDatasetVersion(
                createField(MxrdrMetadataField.DATA_COLLECTION.getValue(),
                        createField(MxrdrMetadataField.DATA_COLLECTION_OSCILLATION_STEP_SIZE.getValue(), VALUE)));

        Mockito.when(versionService.findByVersionNumber(anyLong(), anyLong(), anyLong())).thenReturn(version);
        XdsSupplyMissingValuesStep step = new XdsSupplyMissingValuesStep(Collections.emptyMap(), versionService);

        // when
        step.runInternal(context, workDir);

        // then
        List<String> lines = Files.readAllLines(workDir.resolve(XdsAdjustResultStep.XDS_INP));

        assertThat(lines).anyMatch(s -> s.contains("OSCILLATION_RANGE"));
        assertThat(lines).noneMatch(contains("OSCILLATION_RANGE", VALUE));
    }

    // -------------------- PRIVATE --------------------

    private Predicate<String> contains(String key, String value) {
        return s -> s.contains(key + "= " + value);
    }

    private String getFileFromResources(String name) {
        return getClass().getClassLoader().getResource(name).getPath();
    }
}