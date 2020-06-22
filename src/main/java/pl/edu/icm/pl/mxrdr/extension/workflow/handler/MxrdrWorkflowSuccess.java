package pl.edu.icm.pl.mxrdr.extension.workflow.handler;

import edu.harvard.iq.dataverse.mail.EmailContent;
import edu.harvard.iq.dataverse.workflow.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.handler.WorkflowSuccessHandler;
import io.vavr.control.Option;
import pl.edu.icm.pl.mxrdr.extension.notification.MxrdrEmailFactory;
import pl.edu.icm.pl.mxrdr.extension.notification.MxrdrNotificationType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MxrdrWorkflowSuccess implements WorkflowSuccessHandler {

    private MxrdrNotificationSender notificationSender;
    private MxrdrEmailFactory emailFactory;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrWorkflowSuccess(MxrdrNotificationSender notificationSender, MxrdrEmailFactory emailFactory) {
        this.notificationSender = notificationSender;
        this.emailFactory = emailFactory;
    }

    // -------------------- LOGIC --------------------

    @Override
    public void handleSuccess(WorkflowExecutionContext workflowExecutionContext) {
        Option<EmailContent> emailTemplate = emailFactory.getEmailTemplate(MxrdrNotificationType.MXRDR_WORKFLOW_SUCCESS,
                                                                           workflowExecutionContext.getDataset());

        emailTemplate
                .peek(emailContent -> notificationSender.sendNotification(workflowExecutionContext, emailContent, MxrdrNotificationType.MXRDR_WORKFLOW_SUCCESS))
                .onEmpty(() -> notificationSender.sendNotificationWithoutEmail(workflowExecutionContext, MxrdrNotificationType.MXRDR_WORKFLOW_SUCCESS));
    }
}
