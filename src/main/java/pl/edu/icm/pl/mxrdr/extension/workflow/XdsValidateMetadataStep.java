package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.persistence.workflow.WorkflowExecution;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.Success;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class XdsValidateMetadataStep implements WorkflowStep {
    public static final String STEP_ID = "xds-validate-metadata";

    private DatasetVersionServiceBean versionService;

    // -------------------- CONSTRUCTORS --------------------

    public XdsValidateMetadataStep(DatasetVersionServiceBean versionService) {
        this.versionService = versionService;
    }

    // -------------------- LOGIC --------------------

    @Override
    public WorkflowStepResult run(WorkflowExecutionContext workflowExecutionContext) {
        long dataCollectionFieldsCount = countDataCollectionFields(workflowExecutionContext);
        return dataCollectionFieldsCount == 1L
                ? new Success()
                : new Failure("Dataset version should have exactly one dataCollection field. " +
                "Current version has: " + dataCollectionFieldsCount + ".");
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionContext workflowExecutionContext, Map<String, String> map, String s) {
        throw new UnsupportedOperationException("This step does not pause");
    }

    @Override
    public void rollback(WorkflowExecutionContext workflowExecutionContext, Failure failure) { }

    // -------------------- PRIVATE --------------------

    private long countDataCollectionFields(WorkflowExecutionContext executionContext) {
        WorkflowExecution execution = executionContext.getExecution();
        DatasetVersion version = versionService.findByVersionNumber(execution.getDatasetId(),
                execution.getMajorVersionNumber(), execution.getMinorVersionNumber());
        return Optional.ofNullable(version)
                .map(v -> v.getDatasetFieldsByTypeName(MxrdrMetadataField.DATA_COLLECTION.getValue()))
                .orElse(Collections.emptyList())
                .size();
    }
}
