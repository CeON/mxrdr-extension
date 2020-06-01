package pl.edu.icm.pl.mxrdr.extension.workflow;

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

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrWorkflowStepSPI(WorkflowStepRegistry stepRegistry, DatasetVersionServiceBean datasetVersions) {
        this.stepRegistry = stepRegistry;
        this.datasetVersions = datasetVersions;
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
                return new XdsImagesFetchingStep(datasetVersions);
            case XdsImagesPatternStep.STEP_ID:
                return new XdsImagesPatternStep(datasetVersions, stepParameters);
            default:
                throw new IllegalArgumentException("Unsupported step type: '" + stepType + "'.");
        }
    }
}
