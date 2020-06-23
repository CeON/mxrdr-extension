package pl.edu.icm.pl.mxrdr.extension.workflow.listener;

import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.listener.WorkflowExecutionListener;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import pl.edu.icm.pl.mxrdr.extension.notification.MxrdrNotificationSender;

import javax.inject.Inject;
import javax.inject.Singleton;

import static pl.edu.icm.pl.mxrdr.extension.notification.MxrdrNotificationType.MXRDR_WORKFLOW_FAIL;
import static pl.edu.icm.pl.mxrdr.extension.notification.MxrdrNotificationType.MXRDR_WORKFLOW_SUCCESS;

@Singleton
public class MxrdrWorkflowListener implements WorkflowExecutionListener {

    private final MxrdrNotificationSender notificationSender;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrWorkflowListener(MxrdrNotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    // -------------------- LOGIC --------------------

    @Override
    public void onSuccess(WorkflowExecutionContext executionContext) {
        notificationSender.sendNotification(MXRDR_WORKFLOW_SUCCESS,
                executionContext.getDataset(),
                executionContext.getRequest().getAuthenticatedUser());
    }

    @Override
    public void onFailure(WorkflowExecutionContext executionContext, Failure failure) {
        notificationSender.sendNotification(MXRDR_WORKFLOW_FAIL,
                executionContext.getDataset(),
                executionContext.getRequest().getAuthenticatedUser());
    }
}
