package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.Success;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.Map;

public class XdsValidateMetadataStep implements WorkflowStep {

    public static final String STEP_ID = "xds-validate-metadata";

    private final DatasetVersionServiceBean versionsService;

    // -------------------- CONSTRUCTORS --------------------

    public XdsValidateMetadataStep(DatasetVersionServiceBean versionsService) {
        this.versionsService = versionsService;
    }

    // -------------------- LOGIC --------------------

    @Override
    public WorkflowStepResult run(WorkflowExecutionContext context) {
        long dataCollectionFieldsCount = versionsService
                .withDatasetVersion(context, this::countDataCollectionFields)
                .orElse(0L);
        if (dataCollectionFieldsCount == 1L) {
            return new Success();
        } else {
            return new Failure("Dataset version should have exactly one dataCollection field. " +
                    "Current version has: " + dataCollectionFieldsCount + ".",
                    "The analysis was aborted due to multiple \"Data Collection\" metadata groups. " +
                            "The repository can perform the analysis only on dataset versions having exactly " +
                            "one \"Data Collection\" metadata group.");
        }
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionContext context, Map<String, String> internalData, String externalData) {
        throw new UnsupportedOperationException("This step does not pause");
    }

    @Override
    public void rollback(WorkflowExecutionContext context, Failure failure) { }

    // -------------------- PRIVATE --------------------

    private long countDataCollectionFields(DatasetVersion datasetVersion) {
        return datasetVersion
                .getDatasetFieldsByTypeName(MxrdrMetadataField.DATA_COLLECTION.getValue())
                .size();
    }
}
