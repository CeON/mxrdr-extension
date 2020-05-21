package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.workflow.WorkflowContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

class SampleWorkflowStep implements WorkflowStep {

    private static final Logger log = LoggerFactory.getLogger(SampleWorkflowStep.class);

    static final String STEP_ID = "sample";

    private final Map<String, String> parameters;

    SampleWorkflowStep(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public WorkflowStepResult run(WorkflowContext context) {
        log.info("Sample run with params: {}", parameters);
        return WorkflowStepResult.OK;
    }

    @Override
    public WorkflowStepResult resume(WorkflowContext context, Map<String, String> internalData, String externalData) {
        log.info("Sample resumed with data: {}", internalData);
        return WorkflowStepResult.OK;
    }

    @Override
    public void rollback(WorkflowContext context, Failure reason) {

    }
}
