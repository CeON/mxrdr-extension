package pl.edu.icm.pl.mxrdr.extension.notification;

import edu.harvard.iq.dataverse.persistence.user.UserNotification;

/**
 * Class that holds result after sending notification with email.
 */
public class NotificationSentResult {

    private final UserNotification userNotification;
    private final boolean emailSent;

    // -------------------- CONSTRUCTORS --------------------

    public NotificationSentResult(UserNotification userNotification, boolean emailSent) {
        this.userNotification = userNotification;
        this.emailSent = emailSent;
    }

    // -------------------- GETTERS --------------------

    public UserNotification getUserNotification() {
        return userNotification;
    }

    public boolean isEmailSent() {
        return emailSent;
    }
}
