package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.DatasetFieldServiceBean;
import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.workflow.WorkflowStepRegistry;
import edu.harvard.iq.dataverse.workflow.WorkflowStepSPI;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStep;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Map;

@Startup
@Singleton
public class MxrdrWorkflowStepSPI implements WorkflowStepSPI {

    private static final String PROVIDER_ID = "mxrdr";

    private final WorkflowStepRegistry stepRegistry;
    private final DatasetVersionServiceBean datasetVersions;
    private final DatasetFieldServiceBean datasetFields;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrWorkflowStepSPI(WorkflowStepRegistry stepRegistry, DatasetVersionServiceBean datasetVersions,
                                DatasetFieldServiceBean datasetFields) {
        this.stepRegistry = stepRegistry;
        this.datasetVersions = datasetVersions;
        this.datasetFields = datasetFields;
    }

    @PostConstruct
    public void init() {
        stepRegistry.register(PROVIDER_ID, this);
    }

    // -------------------- LOGIC --------------------

    @Override
    public WorkflowStep getStep(String stepType, Map<String, String> stepParameters) {
        switch (stepType) {
            case XdsImagesFetchingStep.STEP_ID:
                return new XdsImagesFetchingStep(stepParameters, datasetVersions);
            case XdsImagesPatternStep.STEP_ID:
                return new XdsImagesPatternStep(stepParameters);
            case XdsAdjustResultStep.STEP_ID:
                return new XdsAdjustResultStep(stepParameters);
            case XdsOutputImportStep.STEP_ID:
                return new XdsOutputImportStep(stepParameters, datasetVersions, datasetFields);
            case XdsSupplyMissingValuesStep.STEP_ID:
                return new XdsSupplyMissingValuesStep(stepParameters, datasetVersions);
            default:
                throw new IllegalArgumentException("Unsupported step type: '" + stepType + "'.");
        }
    }
}
