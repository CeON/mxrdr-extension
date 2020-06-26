package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.DatasetFieldServiceBean;
import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.workflow.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.Success;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult.Source;
import pl.edu.icm.pl.mxrdr.extension.importer.xds.XdsFileParser;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class XdsOutputImportStep extends FilesystemAccessingWorkflowStep {

    static final String STEP_ID = "xds-output-import";

    static final String XDS_OUTPUT_FILE_NAME = "CORRECT.LP";

    static final String XDS_DATASET_FIELD_SOURCE = "XDS";

    private final DatasetVersionServiceBean datasetVersions;

    private final DatasetFieldServiceBean datasetFields;

    // -------------------- CONSTRUCTORS --------------------

    public XdsOutputImportStep(Map<String, String> inputParams, DatasetVersionServiceBean datasetVersions,
                               DatasetFieldServiceBean datasetFields) {
        super(inputParams);
        this.datasetFields = datasetFields;
        this.datasetVersions = datasetVersions;
    }

    // -------------------- LOGIC --------------------

    @Override
    protected Source runInternal(WorkflowExecutionContext context, Path workdirPath) {
        DatasetVersion editVersion = context.getDataset().getEditVersion();
        addXdsDatasetFields(workdirPath, editVersion);
        datasetVersions.updateDatasetVersion(editVersion, true);
        return Success::new;
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionContext context, Map<String, String> arg1, String arg2) {
        throw new UnsupportedOperationException("This step does not pause");
    }

    @Override
    public void rollback(WorkflowExecutionContext context, Failure failure) {
        DatasetVersion editVersion = context.getDataset().getEditVersion();
        removeXdsDatasetFields(editVersion);
        datasetVersions.updateDatasetVersion(editVersion, true);
    }

    // -------------------- PRIVATE --------------------

    private void addXdsDatasetFields(Path workdirPath, DatasetVersion editVersion) {
        List<DatasetField> editDatasetFields = editVersion.getDatasetFields();

        parseXdsOutputFrom(workdirPath)
                .map(this::asDatasetField)
                .forEach(editDatasetFields::add);
    }

    private Stream<ResultField> parseXdsOutputFrom(Path workdirPath) {
        File correctionFile = workdirPath.resolve(XDS_OUTPUT_FILE_NAME).toFile();
        return new XdsFileParser()
                .parse(correctionFile)
                .stream()
                .flatMap(ResultField::stream);
    }

    private DatasetField asDatasetField(ResultField resultField) {
        DatasetField datasetField = new DatasetField();
        datasetField.setDatasetFieldType(datasetFields.findByName(resultField.getName()));
        datasetField.setSource(XDS_DATASET_FIELD_SOURCE);
        datasetField.setFieldValue(resultField.getValue());
        return datasetField;
    }

    private void removeXdsDatasetFields(DatasetVersion editVersion) {
        editVersion.getDatasetFields()
                .removeIf(this::isXdsDatasetField);
    }

    private boolean isXdsDatasetField(DatasetField field) {
        return XDS_DATASET_FIELD_SOURCE.equals(field.getSource());
    }
}
