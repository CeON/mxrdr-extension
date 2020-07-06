package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.RoleAssigneeServiceBean;
import edu.harvard.iq.dataverse.authorization.AuthenticationServiceBean;
import edu.harvard.iq.dataverse.engine.TestSettingsServiceBean;
import edu.harvard.iq.dataverse.mocks.MockAuthenticatedUser;
import edu.harvard.iq.dataverse.mocks.MockAuthenticationServiceBean;
import edu.harvard.iq.dataverse.mocks.MockRoleAssigneeServiceBean;
import edu.harvard.iq.dataverse.persistence.StubJpaPersistence;
import edu.harvard.iq.dataverse.persistence.datafile.FileMetadata;
import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldTypeRepository;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetLock;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetRepository;
import edu.harvard.iq.dataverse.persistence.dataset.FieldType;
import edu.harvard.iq.dataverse.persistence.user.AuthenticatedUser;
import edu.harvard.iq.dataverse.persistence.workflow.Workflow;
import edu.harvard.iq.dataverse.persistence.workflow.WorkflowExecutionRepository;
import edu.harvard.iq.dataverse.persistence.workflow.WorkflowRepository;
import edu.harvard.iq.dataverse.settings.SettingsServiceBean;
import edu.harvard.iq.dataverse.test.WithTestClock;
import edu.harvard.iq.dataverse.workflow.WorkflowStepRegistry;
import edu.harvard.iq.dataverse.workflow.WorkflowStepSPI;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowContext;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContextFactory;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionScheduler;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionServiceBean;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionStepRunner;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionWorker;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowJMSTestBase;
import edu.harvard.iq.dataverse.workflow.internalspi.SystemProcessStep;
import edu.harvard.iq.dataverse.workflow.listener.WorkflowExecutionListener;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.workflow.MxrdrWorkflowStepSPI;
import pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsImagesFetchingStep.StorageSource;

import javax.enterprise.inject.Instance;
import javax.naming.NamingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static edu.harvard.iq.dataverse.persistence.dataset.DatasetMother.givenDataset;
import static edu.harvard.iq.dataverse.persistence.dataset.DatasetMother.givenDatasetFiled;
import static edu.harvard.iq.dataverse.persistence.workflow.WorkflowMother.givenWorkflow;
import static edu.harvard.iq.dataverse.persistence.workflow.WorkflowMother.givenWorkflowStep;
import static edu.harvard.iq.dataverse.workflow.execution.WorkflowContextMother.givenWorkflowExecutionContext;
import static edu.harvard.iq.dataverse.workflow.internalspi.InternalWorkflowStepSPI.INTERNAL_PROVIDER_ID;
import static edu.harvard.iq.dataverse.workflow.internalspi.SystemProcessStep.COMMAND_PARAM_NAME;
import static java.nio.file.StandardOpenOption.READ;
import static java.time.Duration.ofMinutes;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static pl.edu.icm.pl.mxrdr.extension.workflow.MxrdrWorkflowStepSPI.MXRDR_PROVIDER_ID;
import static pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsInputAdjustingStep.ADJUST_RESOLUTION_PARAM_NAME;
import static pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsInputAdjustingStep.JOBS_PARAM_NAME;

public class XdsAnalysisWorkflowTest extends WorkflowJMSTestBase implements WorkflowStepSPI, WithTestClock {

    static final Logger log = LoggerFactory.getLogger(XdsAnalysisWorkflowTest.class);

    static final Duration TIMEOUT = ofMinutes(5);
    static final String FILES_PATH = "/home/darek/Downloads/Maciupkin/raw";

    SettingsServiceBean settings = new TestSettingsServiceBean();
    LocalDirStorageSource storageSource = new LocalDirStorageSource(FILES_PATH);

    StubJpaPersistence persistence = new StubJpaPersistence();
    DatasetRepository datasets = persistence.stub(DatasetRepository.class);
    DatasetFieldTypeRepository fieldTypes = persistence.stub(DatasetFieldTypeRepository.class);
    WorkflowRepository workflows = persistence.stub(WorkflowRepository.class);
    WorkflowExecutionRepository executions = persistence.stub(WorkflowExecutionRepository.class);

    WorkflowStepRegistry steps = new WorkflowStepRegistry() {{ init(); }};
    MxrdrWorkflowStepSPI mxrdrSteps = new MxrdrWorkflowStepSPI(steps, fieldTypes);
    RoleAssigneeServiceBean roleAssignees = new MockRoleAssigneeServiceBean() {{ add(new MockAuthenticatedUser()); }};
    AuthenticationServiceBean authentication = new MockAuthenticationServiceBean(clock);
    Instance<WorkflowExecutionListener> executionListeners = mock(Instance.class);

    WorkflowExecutionContextFactory contextFactory = new WorkflowExecutionContextFactory(
            settings, datasets, workflows, executions, roleAssignees, authentication);
    WorkflowExecutionScheduler scheduler = new WorkflowExecutionScheduler() {{
        setQueue(queue); setFactory(factory); }};
    WorkflowExecutionStepRunner runner = new WorkflowExecutionStepRunner(steps, clock);

    WorkflowExecutionServiceBean service = new WorkflowExecutionServiceBean(
            datasets, executions, contextFactory, scheduler, clock);
    WorkflowExecutionWorker worker = new WorkflowExecutionWorker(
            datasets, executions, contextFactory, scheduler, runner, executionListeners, clock);

    Dataset dataset = givenDataset();

    Workflow workflow = givenWorkflow(
            givenWorkflowStep(MXRDR_PROVIDER_ID, XdsValidateMetadataStep.STEP_ID, emptyMap()),
            givenWorkflowStep(MXRDR_PROVIDER_ID, XdsImagesFetchingStep.STEP_ID, emptyMap()),
            givenWorkflowStep(MXRDR_PROVIDER_ID, XdsImagesPatternCalculatingStep.STEP_ID, emptyMap()),
            givenWorkflowStep(INTERNAL_PROVIDER_ID, SystemProcessStep.STEP_ID,
                    singletonMap(COMMAND_PARAM_NAME, "generate_XDS.INP")),
            givenWorkflowStep(MXRDR_PROVIDER_ID, XdsMissingInputFillingStep.STEP_ID, emptyMap()),
            givenWorkflowStep(MXRDR_PROVIDER_ID, XdsInputAdjustingStep.STEP_ID,
                    singletonMap(JOBS_PARAM_NAME, "XYCORR;INIT;COLSPOT;IDXREF")),
            givenWorkflowStep(INTERNAL_PROVIDER_ID, SystemProcessStep.STEP_ID,
                    singletonMap(COMMAND_PARAM_NAME, "xds_par")),
            givenWorkflowStep(MXRDR_PROVIDER_ID, XdsInputAdjustingStep.STEP_ID,
                    singletonMap(JOBS_PARAM_NAME, "DEFPIX;INTEGRATE;CORRECT")),
            givenWorkflowStep(INTERNAL_PROVIDER_ID, SystemProcessStep.STEP_ID,
                    singletonMap(COMMAND_PARAM_NAME, "xds_par")),
            givenWorkflowStep(MXRDR_PROVIDER_ID, XdsInputAdjustingStep.STEP_ID,
                    singletonMap(ADJUST_RESOLUTION_PARAM_NAME, "true")),
            givenWorkflowStep(INTERNAL_PROVIDER_ID, SystemProcessStep.STEP_ID,
                    singletonMap(COMMAND_PARAM_NAME, "xds_par")),
            givenWorkflowStep(MXRDR_PROVIDER_ID, XdsOutputImportingStep.STEP_ID, emptyMap())
    );

    public XdsAnalysisWorkflowTest() throws NamingException {}

    @BeforeEach
    void setUp() throws Exception {
        steps.register(MXRDR_PROVIDER_ID, this);

        dataset.getLatestVersion().getDatasetFields().add(
                givenDatasetFiled(MxrdrMetadataField.DATA_COLLECTION.getValue(), StringUtils.EMPTY));
        dataset.getLatestVersion().setFileMetadatas(storageSource.loadFilesMetadata());
        datasets.save(dataset);

        workflows.save(workflow);

        Stream.of(MxrdrMetadataField.values())
                .map(field -> new DatasetFieldType(field.getValue(), FieldType.TEXT, false))
                .forEach(fieldTypes::save);
        doAnswer(invocation -> persistence.of(DatasetFieldType.class)
                .findOne(f -> f.getName().equals(invocation.getArgument(0))))
                .when(fieldTypes).findByName(anyString());

        doNothing().when(datasets)
                .lockDataset(any(Dataset.class), any(AuthenticatedUser.class), any(DatasetLock.Reason.class));
        doNothing().when(datasets)
                .unlockDataset(any(Dataset.class), any(DatasetLock.Reason.class));
    }

    @Test
    @Disabled("for local testing only")
    void shouldPassXdsAnalysis() throws Exception {
        // given
        WorkflowContext context = givenWorkflowExecutionContext(dataset.getId(), workflow);
        // when
        givenMessageConsumer(worker)
                .callProducer(() -> service.start(workflow, context))
                .andAwaitMessages(workflow.getSteps().size() + 1, TIMEOUT);
        // then
        List<DatasetField> fields = dataset.getLatestVersion().getDatasetFields()
                .stream().filter(XdsOutputImportingStep::isXdsDatasetField)
                .collect(toList());

        assertThat(fields).isNotEmpty();

        log.info("XDS analysis results:\n{}", fields.stream()
                .map(field -> field.getDatasetFieldType().getName() + ": " + field.getValue())
                .collect(joining("\n")));
    }

    @Override
    public WorkflowStep getStep(String stepType, WorkflowStepParams stepParameters) {
        switch (stepType) {
            case XdsImagesFetchingStep.STEP_ID:
                return new XdsImagesFetchingStep(stepParameters, storageSource);
            default:
                return mxrdrSteps.getStep(stepType, stepParameters);
        }
    }

    static class LocalDirStorageSource implements StorageSource {

        private final Path dir;

        public LocalDirStorageSource(String dir) {
            this.dir = Paths.get(dir);
        }

        @Override
        public XdsImagesFetchingStep.Storage getStorage(FileMetadata metadata) {
            return () -> {
                String fileName = metadata.getLabel();
                return Files.newInputStream(dir.resolve(fileName), READ);
            };
        }

        List<FileMetadata> loadFilesMetadata() throws IOException {
            return Files.list(dir)
                    .map(filePath -> {
                        FileMetadata metadata = new FileMetadata();
                        metadata.setLabel(filePath.getFileName().toString());
                        return metadata;
                    })
                    .collect(toList());
        }
    }
}
