package pl.edu.icm.pl.mxrdr.extension.notification;

import edu.harvard.iq.dataverse.mail.EmailContent;
import edu.harvard.iq.dataverse.mail.MailService;
import edu.harvard.iq.dataverse.persistence.DvObject;
import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.persistence.dataset.FieldType;
import edu.harvard.iq.dataverse.persistence.user.AuthenticatedUser;
import edu.harvard.iq.dataverse.persistence.user.UserNotification;
import edu.harvard.iq.dataverse.persistence.user.UserNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static pl.edu.icm.pl.mxrdr.extension.notification.MxrdrNotificationType.MXRDR_WORKFLOW_SUCCESS;

public class MxrdrNotificationSenderTest {

    UserNotificationRepository notifications = Mockito.mock(UserNotificationRepository.class);
    MailService mailService = Mockito.mock(MailService.class);
    MxrdrEmailFactory mailFactory = Mockito.mock(MxrdrEmailFactory.class);
    Clock clock = Clock.fixed(Instant.parse("2020-06-01T09:10:20.00Z"), UTC);

    MxrdrNotificationSender sender = new MxrdrNotificationSender(notifications, mailService, mailFactory, clock);

    AuthenticatedUser user = new AuthenticatedUser();

    @BeforeEach
    void setUp() {
        user.setEmail("testemail@test.com");
        doAnswer(invocation -> {
            ((UserNotification) invocation.getArgument(0)).setId(1L);
            return null;
        }).when(notifications).saveAndFlush(any(UserNotification.class));
    }

    @Test
    public void shouldSendNotificationWithEmail() {
        // given
        String notificationType = MXRDR_WORKFLOW_SUCCESS;
        Dataset dataset = makeDataset();
        when(mailFactory.getEmailTemplate(eq(notificationType), any(DvObject.class)))
                .thenReturn(Optional.of(new EmailContent("", "", "")));
        when(mailService.sendMail(eq(user.getEmail()), isNull(), any(EmailContent.class)))
                .thenReturn(true);

        // when
        NotificationSentResult result = sender.sendNotification(notificationType, dataset, user);

        // then
        assertThat(result.isEmailSent()).isTrue();
        assertThat(result.getUserNotification().getObjectId()).isEqualTo(dataset.getId());
        assertThat(result.getUserNotification().getUser()).isEqualTo(user);
        assertThat(result.getUserNotification().getType()).isEqualTo(notificationType);
        assertThat(result.getUserNotification().getSendDate()).isEqualTo(Timestamp.from(clock.instant()));
    }

    @Test
    public void shouldSendNotificationWithoutEmail() {
        // given
        String notificationType = MXRDR_WORKFLOW_SUCCESS;
        Dataset dataset = makeDataset();
        when(mailFactory.getEmailTemplate(eq(notificationType), any(DvObject.class)))
                .thenReturn(Optional.empty());

        // when
        NotificationSentResult result = sender.sendNotification(notificationType, dataset, user);

        // then
        assertThat(result.isEmailSent()).isFalse();
        assertThat(result.getUserNotification().getObjectId()).isEqualTo(dataset.getId());
        assertThat(result.getUserNotification().getUser()).isEqualTo(user);
        assertThat(result.getUserNotification().getType()).isEqualTo(notificationType);
        assertThat(result.getUserNotification().getSendDate()).isEqualTo(Timestamp.from(clock.instant()));
    }

    // -------------------- TESTS --------------------

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
}