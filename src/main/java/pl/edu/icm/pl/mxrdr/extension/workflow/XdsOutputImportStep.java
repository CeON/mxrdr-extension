package pl.edu.icm.pl.mxrdr.extension.workflow;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.harvard.iq.dataverse.DatasetFieldServiceBean;
import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
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
import io.vavr.control.Try;
import pl.edu.icm.pl.mxrdr.extension.importer.xds.XdsImporter;
import pl.edu.icm.pl.mxrdr.extension.importer.xds.XdsImporterForm;

public class XdsOutputImportStep extends FilesystemAccessingWorkflowStep {

    private static final String WORKFLOW = "WORKFLOW";

    static final String CORRECT_LP = "CORRECT.LP";
    
    ImporterRegistry importers;

    private DatasetFieldServiceBean datasetFieldService;

    private DatasetVersionServiceBean datasetVersionService;


    public XdsOutputImportStep(Map<String, String> inputParams, DatasetFieldServiceBean datasetFieldService, DatasetVersionServiceBean datasetVersionService, ImporterRegistry importers) {
        super(inputParams);
        this.datasetFieldService = datasetFieldService;
        this.datasetVersionService = datasetVersionService;
        this.importers = importers;
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionContext context, Map<String, String> arg1, String arg2) {
        return null;
    }

    @Override
    public void rollback(WorkflowExecutionContext context, Failure failure) {
    }

    @Override
    protected Source runInternal(WorkflowExecutionContext context, Path workdirPath) throws Exception {
        
        File correctionFile = workdirPath.resolve(CORRECT_LP).toFile();
        Dataset dataset = context.getDataset();
        for (MetadataImporter importer:importers.getImporters().values()) {
            if (importer instanceof XdsImporter) {
                Map<ImporterFieldKey, Object> inputParametersMap = new HashMap<>();
                inputParametersMap.put(XdsImporterForm.XDS_FILE, correctionFile);
                List<ResultField> resultFields = importer.fetchMetadata(inputParametersMap);
                DatasetVersion editVersion = dataset.getEditVersion();
                
                for (ResultField resultField:resultFields) {
                    if (resultField.getChildren().isEmpty()) {
                        addField(editVersion, resultField);
                    } else {
                        for (ResultField childField:resultField.getChildren()) {
                            addField(editVersion, childField);
                        }
                    }
                }
                
                Try<Dataset> updateDataset = Try.of(() -> datasetVersionService.updateDatasetVersion(editVersion, true));

                if (updateDataset.isSuccess()){
                    return Success.successWith(output -> Collections.emptyMap());
                }

            }
        }
        
        return outputParams -> new Failure("No data processed", "No data processed");
        }

    private void addField(DatasetVersion editVersion, ResultField resultField) {
        DatasetField datasetField = new DatasetField();
        datasetField.setDatasetFieldType(datasetFieldService.findByName(resultField.getName()));
        datasetField.setSource(WORKFLOW);
        datasetField.setFieldValue(resultField.getValue());
        editVersion.getDatasetFields().add(datasetField);
    }

}
