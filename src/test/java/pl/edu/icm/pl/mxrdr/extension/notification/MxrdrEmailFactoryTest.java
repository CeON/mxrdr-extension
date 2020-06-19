package pl.edu.icm.pl.mxrdr.extension.notification;

import edu.harvard.iq.dataverse.common.BundleUtil;
import edu.harvard.iq.dataverse.mail.EmailContent;
import edu.harvard.iq.dataverse.mail.MailService;
import edu.harvard.iq.dataverse.persistence.GlobalId;
import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.persistence.dataset.FieldType;
import edu.harvard.iq.dataverse.settings.SettingsServiceBean;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MxrdrEmailFactoryTest {

    @InjectMocks
    private MxrdrEmailFactory emailFactory;

    @Mock
    private MailService mailService;

    @Mock
    private SettingsServiceBean settings;

    private static final String BUNDLE_NAME = "MxrdrBundle";

    // -------------------- TESTS --------------------

    @Test
    public void getEmailTemplate_withSuccessTemplate() throws AddressException {
        //given
        Dataset dataset = makeDataset();
        GlobalId pid = new GlobalId("doi", "auth", dataset.getIdentifier());
        dataset.setGlobalId(pid);
        String mxrdrWorkflowSuccess = MxrdrNotificationType.MXRDR_WORKFLOW_SUCCESS;
        InternetAddress testAddress = new InternetAddress("testAddress");
        String repoURL = "http://localhost:8080";

        //when
        Mockito.when(mailService.getSystemAddress()).thenReturn(testAddress);
        Mockito.when(settings.getValueForKey(SettingsServiceBean.Key.SiteUrl)).thenReturn(repoURL);
        Option<EmailContent> emailTemplate = emailFactory.getEmailTemplate(mxrdrWorkflowSuccess, dataset);

        //then
        assertTrue(emailTemplate.isDefined());
        assertEquals(BundleUtil.getStringFromPropertyFile("mail.subject.workflow.success.xds", BUNDLE_NAME), emailTemplate.get().getSubject());
        assertEquals(BundleUtil.getStringFromBundle("mail.content.workflow.success.xds",BUNDLE_NAME,
                                                               dataset.getDisplayName(),
                                                               expectedURL(repoURL, pid.asString()),
                                                               testAddress), emailTemplate.get().getMessageText());


    }

    @Test
    public void getEmailTemplate_withFailTemplate() throws AddressException {
        //given
        Dataset dataset = makeDataset();
        GlobalId pid = new GlobalId("doi", "auth", dataset.getIdentifier());
        dataset.setGlobalId(pid);
        String mxrdrWorkflowSuccess = MxrdrNotificationType.MXRDR_WORKFLOW_FAIL;
        InternetAddress testAddress = new InternetAddress("testAddress");
        String repoURL = "http://localhost:8080";

        //when
        Mockito.when(mailService.getSystemAddress()).thenReturn(testAddress);
        Mockito.when(settings.getValueForKey(SettingsServiceBean.Key.SiteUrl)).thenReturn(repoURL);
        Option<EmailContent> emailTemplate = emailFactory.getEmailTemplate(mxrdrWorkflowSuccess, dataset);

        //then
        assertTrue(emailTemplate.isDefined());
        assertEquals(BundleUtil.getStringFromPropertyFile("mail.subject.workflow.fail.xds", BUNDLE_NAME), emailTemplate.get().getSubject());
        assertEquals(BundleUtil.getStringFromBundle("mail.content.workflow.fail.xds",BUNDLE_NAME,
                                                               dataset.getDisplayName(),
                                                               expectedURL(repoURL, pid.asString()),
                                                               testAddress), emailTemplate.get().getMessageText());

    }

    @Test
    public void getEmailTemplate_withInvalidNotification() throws AddressException {
        //given
        Dataset dataset = makeDataset();

        //when
        Option<EmailContent> emailTemplate = emailFactory.getEmailTemplate("", dataset);

        //then
        assertTrue(emailTemplate.isEmpty());

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