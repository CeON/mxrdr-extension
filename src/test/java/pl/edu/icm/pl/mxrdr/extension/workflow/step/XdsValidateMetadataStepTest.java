package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetMother;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionTestBase;
import edu.harvard.iq.dataverse.workflow.step.Success;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static edu.harvard.iq.dataverse.persistence.dataset.DatasetMother.givenDataset;
import static edu.harvard.iq.dataverse.persistence.workflow.WorkflowMother.givenWorkflow;
import static edu.harvard.iq.dataverse.workflow.execution.WorkflowContextMother.givenWorkflowExecutionContext;
import static org.assertj.core.api.Assertions.assertThat;

class XdsValidateMetadataStepTest extends WorkflowExecutionTestBase implements ArgumentsProvider {

    Dataset dataset = givenDataset(1L);
    WorkflowExecutionContext context = givenWorkflowExecutionContext(dataset.getId(), givenWorkflow(1L));

    @ParameterizedTest(name = "[{index}] When {0} fields, then success is {1}")
    @ArgumentsSource(XdsValidateMetadataStepTest.class)
    public void shouldReturnSuccessWhenExactlyOneDataCollectionField(Integer size, boolean isSuccess) {
        // given
        dataset.getLatestVersion().getDatasetFields().addAll(createFields(size));
        datasetVersions.save(dataset.getLatestVersion());

        XdsValidateMetadataStep step = new XdsValidateMetadataStep(versionsService);

        // when
        WorkflowStepResult result = step.run(context);

        // then
        assertThat(result instanceof Success).isEqualTo(isSuccess);
    }

    // -------------------- PRIVATE --------------------

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(1, true),
                Arguments.of(3, false),
                Arguments.of(10, false));
    }

    private static List<DatasetField> createFields(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> DatasetMother.givenDatasetFiled(MxrdrMetadataField.DATA_COLLECTION.getValue(), ""))
                .collect(Collectors.toList());
    }
}
