package pl.edu.icm.pl.mxrdr.extension.notification;

import edu.harvard.iq.dataverse.mail.MailService;
import edu.harvard.iq.dataverse.persistence.DvObject;
import edu.harvard.iq.dataverse.persistence.user.AuthenticatedUser;
import edu.harvard.iq.dataverse.persistence.user.UserNotification;
import edu.harvard.iq.dataverse.persistence.user.UserNotificationDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Clock;

@Singleton
public class MxrdrNotificationSender {

    private final UserNotificationDao notifications;
    private final MailService mailService;
    private final MxrdrEmailFactory mailFactory;
    private final Clock clock;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrNotificationSender(UserNotificationDao notifications, MailService mailService,
                                   MxrdrEmailFactory mailFactory) {
        this(notifications, mailService, mailFactory, Clock.systemUTC());
    }

    public MxrdrNotificationSender(UserNotificationDao notifications, MailService mailService,
                                   MxrdrEmailFactory mailFactory, Clock clock) {
        this.notifications = notifications;
        this.mailFactory = mailFactory;
        this.mailService = mailService;
        this.clock = clock;
    }

    // -------------------- LOGIC --------------------

    public NotificationSentResult sendNotification(String type, DvObject dvObject, AuthenticatedUser sender) {

        UserNotification notification = createNotification(type, dvObject, sender);
        notifications.save(notification);
        notifications.flush();

        boolean emailSent = mailFactory.getEmailTemplate(type, dvObject)
                .map(content -> mailService.sendMail(sender.getDisplayInfo().getEmailAddress(), content))
                .orElse(false);

        if (emailSent) {
            notifications.updateEmailSent(notification.getId());
        }

        return new NotificationSentResult(notification, emailSent);
    }

    // -------------------- PRIVATE --------------------

    private UserNotification createNotification(String type, DvObject dvObject, AuthenticatedUser sender) {
        UserNotification userNotification = new UserNotification();
        userNotification.setType(type);
        userNotification.setObjectId(dvObject.getId());
        userNotification.setUser(sender);
        userNotification.setSendDate(Timestamp.from(clock.instant()));
        return userNotification;
    }
}
