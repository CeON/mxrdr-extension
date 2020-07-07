package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.Success;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.Map;

public class XdsValidateMetadataStep implements WorkflowStep {

    public static final String STEP_ID = "xds-validate-metadata";

    // -------------------- LOGIC --------------------

    @Override
    public WorkflowStepResult run(WorkflowExecutionContext context) {
        long dataCollectionFieldsCount = countDataCollectionFields(context);
        if (dataCollectionFieldsCount == 1L) {
            return new Success();
        } else {
            return new Failure("Dataset version should have exactly one dataCollection field. " +
                    "Current version has: " + dataCollectionFieldsCount + ".");
        }
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionContext context, Map<String, String> internalData, String externalData) {
        throw new UnsupportedOperationException("This step does not pause");
    }

    @Override
    public void rollback(WorkflowExecutionContext context, Failure failure) { }

    // -------------------- PRIVATE --------------------

    private long countDataCollectionFields(WorkflowExecutionContext context) {
        return context.getDatasetVersion()
                .getDatasetFieldsByTypeName(MxrdrMetadataField.DATA_COLLECTION.getValue())
                .size();
    }
}
