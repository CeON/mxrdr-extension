package pl.edu.icm.pl.mxrdr.extension.workflow;

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

    @Inject
    public MxrdrWorkflowStepSPI(WorkflowStepRegistry stepRegistry) {
        this.stepRegistry = stepRegistry;
    }

    @PostConstruct
    public void init() {
        stepRegistry.register(PROVIDER_ID, this);
    }

    @Override
    public WorkflowStep getStep(String stepType, Map<String, String> stepParameters) {
        switch (stepType) {
            case SampleWorkflowStep.STEP_ID:
                return new SampleWorkflowStep(stepParameters);
            default:
                throw new IllegalArgumentException("Unsupported step type: '" + stepType + "'.");
        }
    }
}
