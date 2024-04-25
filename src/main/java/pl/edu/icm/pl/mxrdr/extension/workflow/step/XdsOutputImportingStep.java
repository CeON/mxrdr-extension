package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import edu.harvard.iq.dataverse.persistence.dataset.ControlledVocabularyValue;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldTypeRepository;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionStepContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.icm.pl.mxrdr.extension.xds.output.XdsOutputFileParser;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static edu.harvard.iq.dataverse.workflow.step.Success.successWith;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputFileProcessor.XDS_INPUT_FILE_NAME;
import static pl.edu.icm.pl.mxrdr.extension.xds.output.XdsOutputFileParser.XDS_OUTPUT_FILE_CHARSET;
import static pl.edu.icm.pl.mxrdr.extension.xds.output.XdsOutputFileParser.XDS_OUTPUT_FILE_NAME;

public class XdsOutputImportingStep extends FilesystemAccessingWorkflowStep {

    private static final Logger log = LoggerFactory.getLogger(XdsOutputImportingStep.class);

    public static final String STEP_ID = "xds-output-import";

    public static final String XDS_DATASET_FIELD_SOURCE = "XDS";

    private final DatasetVersionServiceBean versionsService;

    private final DatasetFieldTypeRepository fieldTypes;

    // -------------------- CONSTRUCTORS --------------------

    public XdsOutputImportingStep(WorkflowStepParams inputParams,
                                  DatasetVersionServiceBean versionsService,
                                  DatasetFieldTypeRepository fieldTypes) {
        super(inputParams);
        this.versionsService = versionsService;
        this.fieldTypes = fieldTypes;
    }

    // -------------------- LOGIC --------------------

    @Override
    protected WorkflowStepResult.Source runInternal(WorkflowExecutionStepContext context, Path workDir) {
        addFailureArtifacts(XDS_INPUT_FILE_NAME, XDS_OUTPUT_FILE_NAME);

        versionsService.withDatasetVersion(context,
                datasetVersion -> addXdsMetadata(datasetVersion, workDir)
        );

        return successWith(
                workDirArtifacts(asList(XDS_INPUT_FILE_NAME, XDS_OUTPUT_FILE_NAME), XDS_OUTPUT_FILE_CHARSET)
        );
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionStepContext context, Map<String, String> internalData, String externalData) {
        throw new UnsupportedOperationException("This step does not pause");
    }

    @Override
    public void rollback(WorkflowExecutionStepContext context, Failure failure) {
        versionsService.withDatasetVersion(context,
                datasetVersion -> datasetVersion.getDatasetFields()
                        .removeIf(XdsOutputImportingStep::isXdsDatasetField)
        );
    }

    // -------------------- PRIVATE --------------------

    private List<DatasetField> addXdsMetadata(DatasetVersion datasetVersion, Path workDir) {
        log.trace("Reading {} file", XDS_OUTPUT_FILE_NAME);
        List<DatasetField> datasetFields = parseXdsOutputFrom(workDir)
                .flatMap(resultField -> asDatasetFields(datasetVersion, resultField))
                .collect(toList());

        log.trace("Adding {} read metadata fields", datasetFields.size());
        datasetVersion.getDatasetFields().addAll(datasetFields);
        return datasetFields;
    }

    private Stream<ResultField> parseXdsOutputFrom(Path workDir) {
        File dataFile = workDir.resolve(XDS_OUTPUT_FILE_NAME).toFile();
        return new XdsOutputFileParser(dataFile)
                .asResultFields().stream();
    }

    private Stream<DatasetField> asDatasetFields(DatasetVersion datasetVersion, ResultField resultField) {
        return fieldTypes.findByName(resultField.getName())
                .map(fieldType -> resultField.stream().map(field ->
                        asDatasetField(datasetVersion, field, getFieldTypeOrDefault(field, fieldType))))
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Unknown field '%s'", resultField)));
    }

    private DatasetFieldType getFieldTypeOrDefault(ResultField resultField, DatasetFieldType fallbackFieldType) {
        return Optional.of(resultField.getName())
                .filter(StringUtils::isNotEmpty)
                .flatMap(fieldTypes::findByName)
                .orElse(fallbackFieldType);
    }

    private DatasetField asDatasetField(DatasetVersion datasetVersion, ResultField resultField, DatasetFieldType fieldType) {
        DatasetField datasetField = new DatasetField();
        datasetField.setDatasetVersion(datasetVersion);
        datasetField.setDatasetFieldType(fieldType);
        datasetField.setSource(XDS_DATASET_FIELD_SOURCE);
        if (fieldType.isControlledVocabulary()) {
            List<ControlledVocabularyValue> targetVocabularyValues = fieldType.getControlledVocabularyValues().stream()
                    .filter(vocabValue -> vocabValue.getStrValue().equals(resultField.getValue()))
                    .collect(Collectors.toList());
            datasetField.setControlledVocabularyValues(targetVocabularyValues);
        } else {
            datasetField.setFieldValue(resultField.getValue());
        }
        return datasetField;
    }

    static boolean isXdsDatasetField(DatasetField field) {
        return XDS_DATASET_FIELD_SOURCE.equals(field.getSource());
    }
}
