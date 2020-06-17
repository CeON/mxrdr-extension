package pl.edu.icm.pl.mxrdr.extension.workflow.handler;

import edu.harvard.iq.dataverse.mail.EmailContent;
import edu.harvard.iq.dataverse.mail.MailService;
import edu.harvard.iq.dataverse.persistence.user.AuthenticatedUser;
import edu.harvard.iq.dataverse.persistence.user.UserNotification;
import edu.harvard.iq.dataverse.persistence.user.UserNotificationDao;
import edu.harvard.iq.dataverse.workflow.WorkflowExecutionContext;

import javax.inject.Singleton;
import java.sql.Timestamp;
import java.util.Date;

@Singleton
public class MxrdrNotificationSender {

    private UserNotificationDao userNotificationDao;
    private MailService mailService;

    void sendNotification(WorkflowExecutionContext workflowExecutionContext, EmailContent emailContent) {

        AuthenticatedUser userSender = workflowExecutionContext
                .getRequest()
                .getAuthenticatedUser();

        UserNotification notification = createNotification(userSender,
                                                           new Timestamp(new Date().getTime()),
                                                           "",
                                                           workflowExecutionContext
                                                                   .getExecution()
                                                                   .getDatasetId());
        userNotificationDao.save(notification);
        userNotificationDao.flush();

        boolean emailSent = mailService.sendMail(userSender.getDisplayInfo().getEmailAddress(), emailContent);

        if (emailSent) {
            userNotificationDao.updateEmailSent(notification.getId());
        }

    }

    // -------------------- PRIVATE --------------------

    private UserNotification createNotification(AuthenticatedUser dataverseUser, Timestamp sendDate, String type, Long dvObjectId) {

        UserNotification userNotification = new UserNotification();
        userNotification.setUser(dataverseUser);
        userNotification.setSendDate(sendDate);
        userNotification.setType(type);
        userNotification.setObjectId(dvObjectId);

        return userNotification;
    }
}
