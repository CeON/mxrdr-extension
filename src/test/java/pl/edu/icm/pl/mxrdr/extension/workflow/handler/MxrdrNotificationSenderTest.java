package pl.edu.icm.pl.mxrdr.extension.workflow.handler;

import edu.harvard.iq.dataverse.engine.command.DataverseRequest;
import edu.harvard.iq.dataverse.mail.EmailContent;
import edu.harvard.iq.dataverse.mail.MailService;
import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.persistence.dataset.FieldType;
import edu.harvard.iq.dataverse.persistence.group.IpAddress;
import edu.harvard.iq.dataverse.persistence.user.ApiToken;
import edu.harvard.iq.dataverse.persistence.user.AuthenticatedUser;
import edu.harvard.iq.dataverse.persistence.user.UserNotification;
import edu.harvard.iq.dataverse.persistence.user.UserNotificationDao;
import edu.harvard.iq.dataverse.persistence.workflow.Workflow;
import edu.harvard.iq.dataverse.persistence.workflow.WorkflowExecution;
import edu.harvard.iq.dataverse.workflow.WorkflowContext;
import edu.harvard.iq.dataverse.workflow.WorkflowExecutionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.icm.pl.mxrdr.extension.notification.MxrdrNotificationType;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.harvard.iq.dataverse.workflow.WorkflowContext.TriggerType.PostPublishDataset;
import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class MxrdrNotificationSenderTest {

    private MxrdrNotificationSender mxrdrNotificationSender;

    private UserNotificationDao userNotificationDao;

    private MailService mailService;

    private AuthenticatedUser user;
    private final Instant timer = Instant.ofEpochSecond(1592570489);

    @BeforeEach
    void setUp() {
        mailService = Mockito.mock(MailService.class);
        userNotificationDao = Mockito.mock(UserNotificationDao.class);
        user = new AuthenticatedUser();
        user.setEmail("testemail@test.com");

        mxrdrNotificationSender = new MxrdrNotificationSender(userNotificationDao, mailService, Clock.fixed(timer, ZoneId.systemDefault()));
    }

    @Test
    public void sendNotification() {
        //given
        EmailContent emailContent = new EmailContent("", "", "");
        Dataset dataset = makeDataset();
        Workflow workflow = new Workflow();
        workflow.setId(2L);
        WorkflowExecutionContext execContext = givenWorkflowExecutionContext(workflow, dataset);
        String mxrdrWorkflowSuccess = MxrdrNotificationType.MXRDR_WORKFLOW_SUCCESS;

        //when
        Mockito.when(mailService.sendMail(Mockito.eq(user.getEmail()), Mockito.eq(emailContent))).thenReturn(false);
        NotificationSentResult notificationSentResult = mxrdrNotificationSender.sendNotification(execContext, emailContent, mxrdrWorkflowSuccess);

        //then
        assertFalse(notificationSentResult.isEmailSent());

        UserNotification createdNotification = notificationSentResult.getUserNotification();
        assertEquals(dataset.getId(), createdNotification.getObjectId());
        assertEquals(user, createdNotification.getUser());
        assertEquals(mxrdrWorkflowSuccess, createdNotification.getType());
        assertEquals(new SimpleDateFormat("MMMM d, yyyy h:mm a z").format(Timestamp.from(timer)), createdNotification.getSendDate());

    }

    @Test
    public void sendNotificationWithoutEmail() {
        //given
        Dataset dataset = makeDataset();
        Workflow workflow = new Workflow();
        workflow.setId(2L);
        WorkflowExecutionContext execContext = givenWorkflowExecutionContext(workflow, dataset);
        String mxrdrWorkflowSuccess = MxrdrNotificationType.MXRDR_WORKFLOW_SUCCESS;

        //when
        UserNotification notificationSentResult = mxrdrNotificationSender.sendNotificationWithoutEmail(execContext, mxrdrWorkflowSuccess);

        //then
        assertEquals(dataset.getId(), notificationSentResult.getObjectId());
        assertEquals(user, notificationSentResult.getUser());
        assertEquals(mxrdrWorkflowSuccess, notificationSentResult.getType());
        assertEquals(new SimpleDateFormat("MMMM d, yyyy h:mm a z").format(Timestamp.from(timer)), notificationSentResult.getSendDate());
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

    private DataverseRequest givenDataverseRequest() {
        return new DataverseRequest(user, IpAddress.valueOf("127.0.0.1"));
    }

    private WorkflowContext givenWorkflowContext(Dataset dataset) {
        return new WorkflowContext(PostPublishDataset, dataset, 1L, 0L, givenDataverseRequest(), false);
    }

    private WorkflowExecutionContext givenWorkflowExecutionContext(Workflow workflow, Dataset dataset) {
        return givenWorkflowExecutionContext(workflow, givenWorkflowExecution(dataset, workflow.getId()), dataset);
    }

    private WorkflowExecutionContext givenWorkflowExecutionContext(Workflow workflow, WorkflowExecution execution, Dataset dataset) {
        WorkflowContext workflowContext = givenWorkflowContext(dataset);
        return new WorkflowExecutionContext(workflow, workflowContext, execution, new ApiToken(), emptyMap());
    }

    private WorkflowExecution givenWorkflowExecution(Dataset dataset, long workflowId) {
        return givenWorkflowExecution(workflowId, dataset, 1L, 0L);
    }

    private WorkflowExecution givenWorkflowExecution(long workflowId, Dataset dataset, long majorVersionNumber, long minorVersionNumber) {
        return new WorkflowExecution(workflowId, "PostPublishDataset", dataset.getId(), majorVersionNumber,
                                     minorVersionNumber, false, "test workflow execution");
    }
}