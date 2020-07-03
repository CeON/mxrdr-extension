package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.persistence.workflow.WorkflowMother;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowContextMother;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.Success;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static edu.harvard.iq.dataverse.persistence.dataset.DatasetMother.createField;
import static edu.harvard.iq.dataverse.persistence.dataset.DatasetMother.givenDatasetVersion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;

class XdsValidateMetadataStepTest {

    private WorkflowExecutionContext context = WorkflowContextMother.givenWorkflowExecutionContext(
            1L, WorkflowMother.givenWorkflow(1L));
    private DatasetVersionServiceBean versionService = Mockito.mock(DatasetVersionServiceBean.class);

    @ParameterizedTest
    @DisplayName("Should return Success when there is exactly one dataCollection field in dataset version")
    @MethodSource("provideTestArguments")
    public void shouldReturnSuccessWhenExactlyOneDataCollectionField(Integer size, Class<? extends WorkflowStepResult> expected) {
        // given
        DatasetVersion version = givenDatasetVersion(createFields(size));

        Mockito.when(versionService.findByVersionNumber(anyLong(), anyLong(), anyLong())).thenReturn(version);
        XdsValidateMetadataStep step = new XdsValidateMetadataStep(versionService);

        // when
        WorkflowStepResult result = step.run(context);

        // then
        assertThat(result).isInstanceOf(expected);
    }

    // -------------------- PRIVATE --------------------

    private static Stream<Arguments> provideTestArguments() {
        return Stream.of(
                Arguments.of(0, Failure.class),
                Arguments.of(1, Success.class),
                Arguments.of(3, Failure.class),
                Arguments.of(10, Failure.class));
    }

    private static DatasetField[] createFields(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> createField(MxrdrMetadataField.DATA_COLLECTION.getValue(), ""))
                .toArray(DatasetField[]::new);
    }
}