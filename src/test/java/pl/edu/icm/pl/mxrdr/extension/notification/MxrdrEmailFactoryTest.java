package pl.edu.icm.pl.mxrdr.extension.notification;

import edu.harvard.iq.dataverse.mail.EmailContent;
import edu.harvard.iq.dataverse.mail.MailService;
import edu.harvard.iq.dataverse.persistence.GlobalId;
import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.persistence.dataset.FieldType;
import edu.harvard.iq.dataverse.settings.SettingsServiceBean;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static edu.harvard.iq.dataverse.common.BundleUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static pl.edu.icm.pl.mxrdr.extension.notification.MxrdrEmailFactory.BUNDLE_NAME;
import static pl.edu.icm.pl.mxrdr.extension.notification.MxrdrNotificationType.MXRDR_WORKFLOW_FAIL;
import static pl.edu.icm.pl.mxrdr.extension.notification.MxrdrNotificationType.MXRDR_WORKFLOW_SUCCESS;

public class MxrdrEmailFactoryTest {

    MailService mailService = Mockito.mock(MailService.class);
    SettingsServiceBean settings = Mockito.mock(SettingsServiceBean.class);

    MxrdrEmailFactory emailFactory = new MxrdrEmailFactory(mailService, settings);

    // -------------------- TESTS --------------------

    @Test
    public void getEmailTemplate_withSuccessTemplate() throws AddressException {
        // given
        Dataset dataset = makeDataset();
        GlobalId pid = new GlobalId("doi", "auth", dataset.getIdentifier());
        dataset.setGlobalId(pid);
        InternetAddress testAddress = new InternetAddress("testAddress");
        String repoURL = "http://localhost:8080";
        // and
        when(mailService.getSystemAddress())
                .thenReturn(testAddress);
        when(settings.getValueForKey(SettingsServiceBean.Key.SiteUrl))
                .thenReturn(repoURL);
        // when
        Optional<EmailContent> emailTemplate = emailFactory.getEmailTemplate(MXRDR_WORKFLOW_SUCCESS, dataset);
        // then
        assertThat(emailTemplate).isPresent();
        assertThat(emailTemplate.get().getSubject())
                .isEqualTo(getStringFromClasspathBundle("mail.subject.workflow.success.xds", BUNDLE_NAME));
        assertThat(emailTemplate.get().getMessageText())
                .isEqualTo(getStringFromClasspathBundle("mail.content.workflow.success.xds",BUNDLE_NAME,
                        dataset.getDisplayName(), expectedURL(repoURL, pid.asString()), testAddress));
    }

    @Test
    public void getEmailTemplate_withFailTemplate() throws AddressException {
        //given
        Dataset dataset = makeDataset();
        GlobalId pid = new GlobalId("doi", "auth", dataset.getIdentifier());
        dataset.setGlobalId(pid);
        InternetAddress testAddress = new InternetAddress("testAddress");
        String repoURL = "http://localhost:8080";
        // and
        when(mailService.getSystemAddress())
                .thenReturn(testAddress);
        when(settings.getValueForKey(SettingsServiceBean.Key.SiteUrl))
                .thenReturn(repoURL);
        // when
        Optional<EmailContent> emailTemplate = emailFactory.getEmailTemplate(MXRDR_WORKFLOW_FAIL, dataset);
        // then
        assertThat(emailTemplate).isPresent();
        assertThat(emailTemplate.get().getSubject())
                .isEqualTo(getStringFromClasspathBundle("mail.subject.workflow.fail.xds", BUNDLE_NAME));
        assertThat(emailTemplate.get().getMessageText())
                .isEqualTo(getStringFromClasspathBundle("mail.content.workflow.fail.xds",BUNDLE_NAME,
                        dataset.getDisplayName(), expectedURL(repoURL, pid.asString()), testAddress));
    }

    @Test
    public void getEmailTemplate_withInvalidNotification() {
        // given
        Dataset dataset = makeDataset();
        // when
        Optional<EmailContent> emailTemplate = emailFactory.getEmailTemplate("", dataset);
        // then
        assertThat(emailTemplate).isNotPresent();
    }

    // -------------------- PRIVATE --------------------

    private Dataset makeDataset() {
        Dataset ds = new Dataset();
        ds.setId(1L);
        ds.setIdentifier("sample-ds-" + ds.getId());
        ds.setCategoriesByName(Arrays.asList("CatOne", "CatTwo", "CatThree"));

        final DatasetVersion initialVersion = ds.getVersions().get(0);

        List<DatasetField> fields = new ArrayList<>();
        DatasetField field = new DatasetField();
        field.setId(1L);
        field.setFieldValue("Sample Field Value");
        field.setDatasetFieldType(makeDatasetFieldType());
        fields.add(field);
        initialVersion.setDatasetFields(fields);

        return ds;
    }

    private DatasetFieldType makeDatasetFieldType() {
        final Long id = 1L;
        DatasetFieldType retVal = new DatasetFieldType("SampleType-" + id, FieldType.TEXT, false);
        retVal.setId(id);
        return retVal;
    }

    private String expectedURL(String repoUrl, String datasetId) {
        return repoUrl + "/dataset.xhtml?persistentId=" + datasetId;
    }
}