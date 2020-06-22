package pl.edu.icm.pl.mxrdr.extension.workflow.handler;

import edu.harvard.iq.dataverse.mail.EmailContent;
import edu.harvard.iq.dataverse.mail.MailService;
import edu.harvard.iq.dataverse.persistence.user.AuthenticatedUser;
import edu.harvard.iq.dataverse.persistence.user.UserNotification;
import edu.harvard.iq.dataverse.persistence.user.UserNotificationDao;
import edu.harvard.iq.dataverse.workflow.WorkflowExecutionContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;

@Singleton
public class MxrdrNotificationSender {

    private Clock clock;

    private UserNotificationDao userNotificationDao;
    private MailService mailService;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrNotificationSender(UserNotificationDao userNotificationDao, MailService mailService) {
        this.clock = Clock.systemDefaultZone();
        this.userNotificationDao = userNotificationDao;
        this.mailService = mailService;
    }

    public MxrdrNotificationSender(UserNotificationDao userNotificationDao, MailService mailService, Clock clock) {
        this.clock = clock;
        this.userNotificationDao = userNotificationDao;
        this.mailService = mailService;
    }

    // -------------------- LOGIC --------------------

    NotificationSentResult sendNotification(WorkflowExecutionContext workflowExecutionContext, EmailContent emailContent, String notificationType) {

        AuthenticatedUser userSender = workflowExecutionContext
                .getRequest()
                .getAuthenticatedUser();

        UserNotification notification = createNotification(userSender,
                                                           Timestamp.from(Instant.now(clock)),
                                                           notificationType,
                                                           workflowExecutionContext
                                                                   .getExecution()
                                                                   .getDatasetId());
        userNotificationDao.save(notification);
        userNotificationDao.flush();

        boolean emailSent = mailService.sendMail(userSender.getDisplayInfo().getEmailAddress(), emailContent);

        if (emailSent) {
            userNotificationDao.updateEmailSent(notification.getId());
        }

        return new NotificationSentResult(notification, emailSent);
    }

    UserNotification sendNotificationWithoutEmail(WorkflowExecutionContext workflowExecutionContext, String notificationType) {

        AuthenticatedUser userSender = workflowExecutionContext
                .getRequest()
                .getAuthenticatedUser();

        UserNotification notification = createNotification(userSender,
                                                           Timestamp.from(Instant.now(clock)),
                                                           notificationType,
                                                           workflowExecutionContext
                                                                   .getExecution()
                                                                   .getDatasetId());
        userNotificationDao.save(notification);
        userNotificationDao.flush();

        return notification;
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
