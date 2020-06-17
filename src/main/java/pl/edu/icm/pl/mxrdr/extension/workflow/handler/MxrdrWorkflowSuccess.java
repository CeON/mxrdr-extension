package pl.edu.icm.pl.mxrdr.extension.workflow.handler;

import edu.harvard.iq.dataverse.workflow.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.handler.WorkflowSuccessHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MxrdrWorkflowSuccess implements WorkflowSuccessHandler {

    private MxrdrNotificationSender notificationSender;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrWorkflowSuccess(MxrdrNotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }


    // -------------------- LOGIC --------------------

    @Override
    public void handleSuccess(WorkflowExecutionContext workflowExecutionContext) {
        notificationSender.sendNotification(workflowExecutionContext, );
    }
}
