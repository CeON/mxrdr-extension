package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldTypeRepository;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.Success;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult.Source;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.icm.pl.mxrdr.extension.xds.output.XdsOutputFileParser;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static pl.edu.icm.pl.mxrdr.extension.xds.output.XdsOutputFileParser.XDS_OUTPUT_FILE_NAME;

public class XdsOutputImportingStep extends FilesystemAccessingWorkflowStep {

    private static final Logger log = LoggerFactory.getLogger(XdsOutputImportingStep.class);

    public static final String STEP_ID = "xds-output-import";

    public static final String XDS_DATASET_FIELD_SOURCE = "XDS";

    private final DatasetFieldTypeRepository fieldTypes;

    // -------------------- CONSTRUCTORS --------------------

    public XdsOutputImportingStep(WorkflowStepParams inputParams, DatasetFieldTypeRepository fieldTypes) {
        super(inputParams);
        this.fieldTypes = fieldTypes;
    }

    // -------------------- LOGIC --------------------

    @Override
    protected Source runInternal(WorkflowExecutionContext context, Path workDir) {
        log.trace("Reading {} file", XDS_OUTPUT_FILE_NAME);
        List<DatasetField> datasetFields = context.getDatasetVersion().getDatasetFields();
        log.trace("Adding {} read metadata fields", datasetFields.size());
        parseXdsOutputFrom(workDir)
                .flatMap(this::asDatasetFields)
                .forEach(datasetFields::add);
        return Success::new;
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionContext context, Map<String, String> internalData, String externalData) {
        throw new UnsupportedOperationException("This step does not pause");
    }

    @Override
    public void rollback(WorkflowExecutionContext context, Failure failure) {
        context.getDatasetVersion().getDatasetFields()
                .removeIf(XdsOutputImportingStep::isXdsDatasetField);
    }

    // -------------------- PRIVATE --------------------

    private Stream<ResultField> parseXdsOutputFrom(Path workDir) {
        File dataFile = workDir.resolve(XDS_OUTPUT_FILE_NAME).toFile();
        return new XdsOutputFileParser(dataFile)
                .asResultFields().stream();
    }

    private Stream<DatasetField> asDatasetFields(ResultField resultField) {
        return fieldTypes.findByName(resultField.getName())
                .map(fieldType -> resultField.stream().map(field ->
                        asDatasetField(field, getFieldTypeOrDefault(field, fieldType))))
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Unknown field '%s'", resultField)));
    }

    private DatasetFieldType getFieldTypeOrDefault(ResultField resultField, DatasetFieldType fallbackFieldType) {
        return Optional.of(resultField.getName())
                .filter(StringUtils::isNotEmpty)
                .flatMap(fieldTypes::findByName)
                .orElse(fallbackFieldType);
    }

    private DatasetField asDatasetField(ResultField resultField, DatasetFieldType fieldType) {
        DatasetField datasetField = new DatasetField();
        datasetField.setDatasetFieldType(fieldType);
        datasetField.setSource(XDS_DATASET_FIELD_SOURCE);
        datasetField.setFieldValue(resultField.getValue());
        return datasetField;
    }

    static boolean isXdsDatasetField(DatasetField field) {
        return XDS_DATASET_FIELD_SOURCE.equals(field.getSource());
    }
}
