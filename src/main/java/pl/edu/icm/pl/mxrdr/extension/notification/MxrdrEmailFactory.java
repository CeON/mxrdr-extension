package pl.edu.icm.pl.mxrdr.extension.notification;

import edu.harvard.iq.dataverse.common.BundleUtil;
import edu.harvard.iq.dataverse.mail.EmailContent;
import edu.harvard.iq.dataverse.mail.MailService;
import edu.harvard.iq.dataverse.persistence.DvObject;
import edu.harvard.iq.dataverse.settings.SettingsServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Optional;

/**
 * Class that manages email templates.
 */
public class MxrdrEmailFactory {

    private static final Logger log = LoggerFactory.getLogger(MxrdrEmailFactory.class);

    static final String BUNDLE_NAME = "MxrdrBundle";

    private final MailService mailService;
    private final SettingsServiceBean settings;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrEmailFactory(MailService mailService, SettingsServiceBean settings) {
        this.mailService = mailService;
        this.settings = settings;
    }

    // -------------------- LOGIC --------------------

    public Optional<EmailContent> getEmailTemplate(String notificationType, DvObject dvObject) {

        if (notificationType.equals(MxrdrNotificationType.MXRDR_WORKFLOW_SUCCESS)) {
            return Optional.of(getWorkflowSuccessTemplate(dvObject));
        }

        if (notificationType.equals(MxrdrNotificationType.MXRDR_WORKFLOW_FAIL)) {
            return Optional.of(getWorkflowFailureTemplate(dvObject));
        }

        log.warn("No email template found for notification type: " + notificationType);
        return Optional.empty();
    }

    // -------------------- PRIVATE --------------------

    private EmailContent getWorkflowSuccessTemplate(DvObject dvObject) {

        return new EmailContent(BundleUtil.getStringFromClasspathBundle("mail.subject.workflow.success.xds", BUNDLE_NAME),
                         BundleUtil.getStringFromClasspathBundle("mail.content.workflow.success.xds", BUNDLE_NAME,
                                                        dvObject.getDisplayName(),
                                                        constructDatasetLink(dvObject.getGlobalId().asString()),
                                                        mailService.getSystemAddress()),
                         mailService.getFooterMailMessage(BundleUtil.getCurrentLocale()));
    }

    private EmailContent getWorkflowFailureTemplate(DvObject dvObject) {

        return new EmailContent(BundleUtil.getStringFromClasspathBundle("mail.subject.workflow.fail.xds", BUNDLE_NAME),
                         BundleUtil.getStringFromClasspathBundle("mail.content.workflow.fail.xds", BUNDLE_NAME,
                                                        dvObject.getDisplayName(),
                                                        constructDatasetLink(dvObject.getGlobalId().asString()),
                                                        mailService.getSystemAddress()),
                         mailService.getFooterMailMessage(BundleUtil.getCurrentLocale()));
    }

    private String constructDatasetLink(String datasetId) {
        return settings.getValueForKey(SettingsServiceBean.Key.SiteUrl) + "/dataset.xhtml?persistentId=" + datasetId;
    }
}
