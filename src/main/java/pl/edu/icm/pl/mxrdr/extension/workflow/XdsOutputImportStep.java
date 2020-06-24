package pl.edu.icm.pl.mxrdr.extension.workflow;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import edu.harvard.iq.dataverse.DatasetFieldServiceBean;
import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.workflow.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.Success;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult.Source;
import pl.edu.icm.pl.mxrdr.extension.importer.xds.XdsFileParser;

public class XdsOutputImportStep extends FilesystemAccessingWorkflowStep {

    private static final String WORKFLOW = "WORKFLOW";

    static final String CORRECT_LP = "CORRECT.LP";
    
    private DatasetFieldServiceBean datasetFieldService;

    private DatasetVersionServiceBean datasetVersionService;


    public XdsOutputImportStep(Map<String, String> inputParams, DatasetFieldServiceBean datasetFieldService, DatasetVersionServiceBean datasetVersionService) {
        super(inputParams);
        this.datasetFieldService = datasetFieldService;
        this.datasetVersionService = datasetVersionService;
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionContext context, Map<String, String> arg1, String arg2) {
        throw new UnsupportedOperationException("This step does not pause");
    }

    @Override
    public void rollback(WorkflowExecutionContext context, Failure failure) {
        Dataset dataset = context.getDataset();
        DatasetVersion editVersion = dataset.getEditVersion();
        editVersion.getDatasetFields().removeIf(e -> e.getSource().equals(WORKFLOW));
    }

    @Override
    protected Source runInternal(WorkflowExecutionContext context, Path workdirPath) throws Exception {
        
        File correctionFile = workdirPath.resolve(CORRECT_LP).toFile();
        Dataset dataset = context.getDataset();
        XdsFileParser parser = new XdsFileParser();
        List<ResultField> resultFields = parser.parse(correctionFile);
        DatasetVersion editVersion = dataset.getEditVersion();
                
        resultFields
                .stream()
                .flatMap(resultField -> resultField.getChildren().isEmpty() ? Stream.of(resultField) : resultField.getChildren().stream())
                .map(resultField -> {
                    DatasetField datasetField = new DatasetField();
                    datasetField.setDatasetFieldType(datasetFieldService.findByName(resultField.getName()));
                    datasetField.setSource(WORKFLOW);
                    datasetField.setFieldValue(resultField.getValue());
                    return datasetField;
                })
                .forEach(datasetField -> editVersion.getDatasetFields().add(datasetField));
        
        datasetVersionService.updateDatasetVersion(editVersion, true);

        return Success.successWith(output -> Collections.emptyMap());

    }

}
