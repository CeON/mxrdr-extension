package pl.edu.icm.pl.mxrdr.extension.workflow.listener;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
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

    private final DatasetVersionServiceBean versionsService;
    private final MxrdrNotificationSender notificationSender;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrWorkflowListener(DatasetVersionServiceBean versionsService, MxrdrNotificationSender notificationSender) {
        this.versionsService = versionsService;
        this.notificationSender = notificationSender;
    }

    // -------------------- LOGIC --------------------

    @Override
    public void onSuccess(WorkflowExecutionContext context) {
        versionsService.withDatasetVersion(context,
                datasetVersion -> notificationSender.sendNotification(MXRDR_WORKFLOW_SUCCESS,
                        datasetVersion.getDataset(),
                        context.getRequest().getAuthenticatedUser())
        );
    }

    @Override
    public void onFailure(WorkflowExecutionContext context, Failure failure) {
        versionsService.withDatasetVersion(context,
                datasetVersion -> notificationSender.sendNotification(MXRDR_WORKFLOW_FAIL,
                        datasetVersion.getDataset(),
                        context.getRequest().getAuthenticatedUser())
        );
    }
}
