package pl.edu.icm.pl.mxrdr.extension.notification;

import edu.harvard.iq.dataverse.common.BundleUtil;
import edu.harvard.iq.dataverse.mail.EmailContent;
import edu.harvard.iq.dataverse.mail.MailService;
import edu.harvard.iq.dataverse.persistence.DvObject;
import edu.harvard.iq.dataverse.settings.SettingsServiceBean;
import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Locale;

/**
 * Class that manages email templates.
 */
public class MxrdrEmailFactory {

    private Locale repoLocale;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String BUNDLE_NAME = "MxrdrBundle";

    private MailService mailService;
    private SettingsServiceBean settings;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrEmailFactory(MailService mailService, SettingsServiceBean settings) {
        repoLocale = Locale.ENGLISH;
        this.mailService = mailService;
        this.settings = settings;
    }

    // -------------------- LOGIC --------------------

    public Option<EmailContent> getEmailTemplate(String notificationType, DvObject dvObject) {

        if (notificationType.equals(MxrdrNotificationType.MXRDR_WORKFLOW_SUCCESS)) {
            return Option.of(getWorkflowSuccessTemplate(dvObject));
        }

        if (notificationType.equals(MxrdrNotificationType.MXRDR_WORKFLOW_FAIL)) {
            return Option.of(getWorkflowFailureTemplate(dvObject));
        }

        logger.warn("No email template found for notification type: " + notificationType);
        return Option.none();
    }

    // -------------------- PRIVATE --------------------

    private EmailContent getWorkflowSuccessTemplate(DvObject dvObject) {

        return new EmailContent(BundleUtil.getStringFromBundle("mail.subject.workflow.success.xds", BUNDLE_NAME),
                         BundleUtil.getStringFromBundle("mail.content.workflow.success.xds", BUNDLE_NAME,
                                                        dvObject.getDisplayName(),
                                                        constructDatasetLink(dvObject.getGlobalId().asString()),
                                                        mailService.getSystemAddress()),
                         mailService.getFooterMailMessage(repoLocale));
    }

    private EmailContent getWorkflowFailureTemplate(DvObject dvObject) {

        return new EmailContent(BundleUtil.getStringFromBundle("mail.subject.workflow.fail.xds", BUNDLE_NAME),
                         BundleUtil.getStringFromBundle("mail.content.workflow.fail.xds", BUNDLE_NAME,
                                                        dvObject.getDisplayName(),
                                                        constructDatasetLink(dvObject.getGlobalId().asString()),
                                                        mailService.getSystemAddress()),
                         mailService.getFooterMailMessage(repoLocale));
    }

    private String constructDatasetLink(String datasetId) {
        return settings.getValueForKey(SettingsServiceBean.Key.SiteUrl) + "/dataset.xhtml?persistentId=" + datasetId;
    }
}
