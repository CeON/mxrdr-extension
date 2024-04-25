package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldTypeRepository;
import edu.harvard.iq.dataverse.persistence.dataset.FieldType;
import edu.harvard.iq.dataverse.persistence.workflow.Workflow;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionStepContext;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionTestBase;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.xds.output.XdsOutputFileParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static edu.harvard.iq.dataverse.persistence.dataset.DatasetMother.givenDataset;
import static edu.harvard.iq.dataverse.persistence.workflow.WorkflowMother.givenWorkflow;
import static edu.harvard.iq.dataverse.persistence.workflow.WorkflowMother.givenWorkflowStep;
import static edu.harvard.iq.dataverse.workflow.execution.WorkflowContextMother.givenWorkflowExecutionContext;
import static edu.harvard.iq.dataverse.workflow.execution.WorkflowContextMother.nextStepContextToExecute;
import static edu.harvard.iq.dataverse.workflow.execution.WorkflowContextMother.nextStepContextToRollback;
import static edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep.WORK_DIR_PARAM_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsOutputImportingStep.XDS_DATASET_FIELD_SOURCE;

public class XdsOutputImportingStepTest extends WorkflowExecutionTestBase {

    DatasetFieldTypeRepository fieldTypes = persistence.stub(DatasetFieldTypeRepository.class);
    XdsOutputImportingStep step;

    Dataset dataset = givenDataset(1L);
    Workflow workflow = givenWorkflow(1L, givenWorkflowStep(XdsOutputImportingStep.STEP_ID));
    WorkflowExecutionContext context = givenWorkflowExecutionContext(dataset.getId(), workflow);
    Path workDir;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        workDir = Files.createTempDirectory("xds-test-import");
        WorkflowStepParams stepParams = new WorkflowStepParams(WORK_DIR_PARAM_NAME, workDir.toString());
        context.getExecution().start("test", "127.0.1.1", clock);
        step = new XdsOutputImportingStep(stepParams, versionsService, fieldTypes);

        File xdsFile = new File(getClass().getClassLoader().getResource("xds/CORRECT.LP").toURI());
        Files.copy(Paths.get(xdsFile.getPath()), workDir.resolve(XdsOutputFileParser.XDS_OUTPUT_FILE_NAME));

        Stream.of(MxrdrMetadataField.values())
                .map(field -> new DatasetFieldType(field.getValue(), FieldType.TEXT, false))
                .forEach(fieldTypes::save);
        doAnswer(invocation -> persistence.of(DatasetFieldType.class)
                .findOne(f -> f.getName().equals(invocation.getArgument(0))))
                .when(fieldTypes).findByName(anyString());

        datasets.save(dataset);
        datasetVersions.save(dataset.getLatestVersion());
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.list(workDir).forEach(f -> f.toFile().delete());
        workDir.toFile().delete();
    }

    @Test
    public void shouldImportFile() {
        // when
        WorkflowExecutionStepContext stepContext = nextStepContextToExecute(context);
        step.run(stepContext);
        // then
        List<DatasetField> fields = dataset.getLatestVersion().getDatasetFields();
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
        assertThat(fields).anyMatch(xdsField("overallNumberOfPossibleReflections", "93791"));
        assertThat(fields).anyMatch(xdsField("overallAnomalousCorrelation", "5"));
        assertThat(fields).anyMatch(xdsField("overallAnomalousSignal", "0.755"));
        // and
        assertThat(fields).anyMatch(xdsField("hrsCompleteness", "97.1"));
        assertThat(fields).anyMatch(xdsField("hrsISigma", "1.84"));
        assertThat(fields).anyMatch(xdsField("hrsCc", "80.2"));
        assertThat(fields).anyMatch(xdsField("hrsRMerge", "97.2"));
        assertThat(fields).anyMatch(xdsField("hrsRMeas", "106.1"));
        assertThat(fields).anyMatch(xdsField("hrsDataResolutionRangeLow", "2.23"));
        assertThat(fields).anyMatch(xdsField("hrsDataResolutionRangeHigh", "2.11"));
        assertThat(fields).anyMatch(xdsField("hrsNumberOfObservedReflections", "92851"));
        assertThat(fields).anyMatch(xdsField("hrsNumberOfUniqueReflections", "14747"));
        assertThat(fields).anyMatch(xdsField("hrsNumberOfPossibleReflections", "15187"));
        assertThat(fields).anyMatch(xdsField("hrsAnomalousCorrelation", "-3"));
        assertThat(fields).anyMatch(xdsField("hrsAnomalousSignal", "0.639"));
        
        assertThat(fields).anyMatch(xdsField("processingSoftware", "XDS"));
    }

    @Test
    public void shouldRollbackImport() {
        // given
        List<DatasetField> fields = dataset.getLatestVersion().getDatasetFields();
        fields.add(primaryField("unitCellParameterA", "90.00"));
        WorkflowExecutionStepContext stepContext = nextStepContextToExecute(context);
        stepContext.getStepExecution().start(Collections.emptyMap(), clock);
        // when
        step.run(stepContext);
        // then
        assertThat(fields).hasSize(42);
        // when
        step.rollback(nextStepContextToRollback(context), new Failure("test"));
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
