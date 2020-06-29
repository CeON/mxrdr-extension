package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.DatasetFieldServiceBean;
import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.FieldType;
import edu.harvard.iq.dataverse.test.WithTestClock;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

import static edu.harvard.iq.dataverse.persistence.dataset.DatasetMother.givenDataset;
import static edu.harvard.iq.dataverse.persistence.workflow.WorkflowMother.givenWorkflow;
import static edu.harvard.iq.dataverse.workflow.execution.WorkflowContextMother.givenWorkflowExecutionContext;
import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static pl.edu.icm.pl.mxrdr.extension.workflow.XdsOutputImportStep.XDS_DATASET_FIELD_SOURCE;

public class XdsOutputImportStepTest implements WithTestClock {

    DatasetVersionServiceBean datasetVersions = mock(DatasetVersionServiceBean.class);
    DatasetFieldServiceBean datasetFieldService = mock(DatasetFieldServiceBean.class);
    XdsOutputImportStep step = new XdsOutputImportStep(emptyMap(), datasetVersions, datasetFieldService);

    Dataset dataset = givenDataset(1L);
    WorkflowExecutionContext context = givenWorkflowExecutionContext(dataset, givenWorkflow(1L));
    Path workDir;

    @BeforeEach
    public void setUp() throws Exception {
        workDir = Files.createTempDirectory("xds-test-import");
        File xdsFile = new File(getClass().getClassLoader().getResource("xds/CORRECT.LP").toURI());
        Files.copy(Paths.get(xdsFile.getPath()), workDir.resolve(XdsOutputImportStep.XDS_OUTPUT_FILE_NAME));

        doAnswer(invocation -> new DatasetFieldType(invocation.getArgument(0), FieldType.TEXT, false))
                .when(datasetFieldService).findByName(any(String.class));
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.list(workDir).forEach(f -> f.toFile().delete());
        workDir.toFile().delete();
    }

    @Test
    public void shouldImportFile() {
        // when
        step.runInternal(context, workDir);
        // then
        List<DatasetField> fields = dataset.getEditVersion().getDatasetFields();
        assertThat(fields).hasSize(41);
        // and
        assertThat(fields).anyMatch(xdsField("unitCellParameterA", "78.45"));
        assertThat(fields).anyMatch(xdsField("unitCellParameterB", "91.20"));
        assertThat(fields).anyMatch(xdsField("unitCellParameterC", "116.82"));
        assertThat(fields).anyMatch(xdsField("unitCellParameterAlpha", "90.000"));
        assertThat(fields).anyMatch(xdsField("unitCellParameterBeta", "90.000"));
        assertThat(fields).anyMatch(xdsField("unitCellParameterGamma", "90.000"));
        // and
        assertThat(fields).anyMatch(xdsField("dataCollectionOscillationStepSize", "0.200000"));
        assertThat(fields).anyMatch(xdsField("dataCollectionStartingAngle", "1.010"));
        assertThat(fields).anyMatch(xdsField("dataCollectionOrgX", "1221.81"));
        assertThat(fields).anyMatch(xdsField("dataCollectionOrgY", "1257.43"));
        assertThat(fields).anyMatch(xdsField("dataCollectionDetectorDistance", "216.250"));
        assertThat(fields).anyMatch(xdsField("dataCollectionWavelength", "1.278650"));
        assertThat(fields).anyMatch(xdsField("dataCollectionDetectorOverload", "1048500"));
        assertThat(fields).anyMatch(xdsField("dataCollectionDetectorThickness", "0.450000"));
        assertThat(fields).anyMatch(xdsField("dataCollectionStartingAngle", "1.010"));
        // and
        assertThat(fields).anyMatch(xdsField("overallCompleteness", "99.5"));
        assertThat(fields).anyMatch(xdsField("overallISigma", "9.09"));
        assertThat(fields).anyMatch(xdsField("overallCc", "99.7"));
        assertThat(fields).anyMatch(xdsField("overallRMerge", "14.8"));
        assertThat(fields).anyMatch(xdsField("overallRMeas", "16.0"));
        assertThat(fields).anyMatch(xdsField("overallDataResolutionRangeLow", "49.186"));
        assertThat(fields).anyMatch(xdsField("overallDataResolutionRangeHigh", "2.11"));
        assertThat(fields).anyMatch(xdsField("overallNumberOfObservedReflections", "632938"));
        assertThat(fields).anyMatch(xdsField("overallNumberOfUniqueReflections", "93289"));
        assertThat(fields).anyMatch(xdsField("overallNumberOfNumberOfPossibleReflections", "93791"));
        assertThat(fields).anyMatch(xdsField("overallAnomalousCorrelation", "5"));
        assertThat(fields).anyMatch(xdsField("overallAnomalousSignal", "0.755"));
        // and
        assertThat(fields).anyMatch(xdsField("hrsCompleteness", "97.1"));
        assertThat(fields).anyMatch(xdsField("hrsSigma", "1.84"));
        assertThat(fields).anyMatch(xdsField("hrsCc", "80.2"));
        assertThat(fields).anyMatch(xdsField("hrsRMerge", "97.2"));
        assertThat(fields).anyMatch(xdsField("hrsRMeas", "106.1"));
        assertThat(fields).anyMatch(xdsField("hrsDataResolutionRangeLow", "2.23"));
        assertThat(fields).anyMatch(xdsField("hrsDataResolutionRangeHigh", "2.11"));
        assertThat(fields).anyMatch(xdsField("hrsNumberOfObservedReflections", "92851"));
        assertThat(fields).anyMatch(xdsField("hrsNumberOfUniqueReflections", "14747"));
        assertThat(fields).anyMatch(xdsField("hrsNumberOfNumberOfPossibleReflections", "15187"));
        assertThat(fields).anyMatch(xdsField("hrsAnomalousCorrelation", "-3"));
        assertThat(fields).anyMatch(xdsField("hrsAnomalousSignal", "0.639"));
    }

    @Test
    public void shouldRollbackImport() {
        // given
        List<DatasetField> fields = dataset.getEditVersion().getDatasetFields();
        fields.add(primaryField("unitCellParameterA", "90.00"));
        // when
        step.runInternal(context, workDir);
        // then
        assertThat(fields).hasSize(42);
        // when
        step.rollback(context, new Failure("test"));
        // then
        assertThat(fields).hasSize(1);
    }

    private static Predicate<DatasetField> xdsField(String name, String value) {
        return field -> field.getDatasetFieldType().getName().equals(name)
                && field.getValue().equals(value)
                && field.getSource().contentEquals(XDS_DATASET_FIELD_SOURCE);
    }

    private DatasetField primaryField(String name, String value) {
        DatasetField field = new DatasetField();
        field.setDatasetFieldType( new DatasetFieldType(name, FieldType.TEXT, false));
        field.setSource("PRIMARY");
        field.setFieldValue(value);
        return field;
    }
}
