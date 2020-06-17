package pl.edu.icm.pl.mxrdr.extension.workflow.handler;

import edu.harvard.iq.dataverse.workflow.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.handler.WorkflowFailureHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MxrdrWorkflowFailure implements WorkflowFailureHandler {

    private MxrdrNotificationSender notificationSender;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrWorkflowFailure(MxrdrNotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    // -------------------- LOGIC --------------------


    @Override
    public void handleFailure(WorkflowExecutionContext workflowExecutionContext) {
        notificationSender.sendNotification(workflowExecutionContext, );
    }
}
