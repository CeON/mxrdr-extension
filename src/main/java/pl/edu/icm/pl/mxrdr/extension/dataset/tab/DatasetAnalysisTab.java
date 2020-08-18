package pl.edu.icm.pl.mxrdr.extension.dataset.tab;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import edu.harvard.iq.dataverse.DatasetFieldServiceBean;
import edu.harvard.iq.dataverse.DataverseRequestServiceBean;
import edu.harvard.iq.dataverse.PermissionsWrapper;
import edu.harvard.iq.dataverse.persistence.dataset.ControlledVocabularyValue;
import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.persistence.workflow.WorkflowArtifact;
import edu.harvard.iq.dataverse.persistence.workflow.WorkflowExecution;
import edu.harvard.iq.dataverse.settings.SettingsServiceBean;
import edu.harvard.iq.dataverse.workflow.artifacts.WorkflowArtifactServiceBean;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowContext.TriggerType;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionService;
import edu.harvard.iq.dataverse.workflow.step.Failure;

@ViewScoped
@Named("datasetAnalysisTab")
public class DatasetAnalysisTab implements Serializable {
    
    private static final Logger logger = LoggerFactory.getLogger(DatasetAnalysisTab.class);

    private DatasetFieldServiceBean datasetFields;
    
    private SettingsServiceBean settingsService;
    
    private PermissionsWrapper permissionsWrapper;

    private DataverseRequestServiceBean dvRequestService;
    
    private WorkflowExecutionService workflowServiceBean;
    
    private WorkflowArtifactServiceBean workflowArfifactService;

    @Inject
    public DatasetAnalysisTab(SettingsServiceBean settingsService, PermissionsWrapper permissionsWrapper,
            DataverseRequestServiceBean dvRequestService, WorkflowExecutionService workflowServiceBean,
            WorkflowArtifactServiceBean workflowArtifactService, DatasetFieldServiceBean datasetFields) {
        this.settingsService = settingsService;
        this.permissionsWrapper = permissionsWrapper;
        this.dvRequestService = dvRequestService;
        this.workflowServiceBean = workflowServiceBean;
        this.workflowArfifactService = workflowArtifactService;
        this.datasetFields = datasetFields;

    }
    
    // -------------------- GETTERS --------------------

    
    public List<AnalysisResultDTO> getAnalysisFields(DatasetVersion datasetVersion) {
        List<AnalysisResultDTO> resultList = new ArrayList<AnalysisResultDTO>();
        for (XdsAnalysisField field:XdsAnalysisField.values()) {
            AnalysisResultDTO res = new AnalysisResultDTO();
            DatasetFieldType type = datasetFields.findByName(field.getName());
            res.setFieldLabel(type != null ? type.getDisplayName() : field.getName());

            datasetVersion.getDatasetFieldByTypeName(field.getName())
            .map(this::getSimpleValue)
            .ifPresent(res::setPrimaryValue);
            
            for (DatasetField dsf : datasetVersion.getDatasetFieldsOptional()) {
                if (dsf.getDatasetFieldType().getName().equals(field.getName())) {
                    res.setSecondaryValue(getSimpleValue(dsf));
                }
            }
            
            resultList.add(res);
        }
        
        return resultList;
    }
    
    private String getSimpleValue(DatasetField datasetField) {
        StringBuilder returnString = new StringBuilder();
        List<String> valuesList = new ArrayList<>();
        if (!datasetField.getFieldValue().isEmpty()) {
            valuesList.add(datasetField.getFieldValue().get());
        } else {
            for (ControlledVocabularyValue cvv : datasetField.getControlledVocabularyValues()) {
                if (cvv != null && cvv.getLocaleStrValue() != null) {
                    valuesList.add(cvv.getLocaleStrValue());
                }
            }
        }
        for (String value : valuesList) {
            if (value == null) {
                value = "";
            }
            returnString.append((returnString.length() == 0) ? "" : "; ").append(value.trim());
        }
        return returnString.toString();

    }
    
    // -------------------- LOGIC --------------------

    
    public boolean isDatasetUnderEmbargo(Dataset dataset) {
        return dataset.hasActiveEmbargo();
    }

    public boolean isDatasetInDraft(DatasetVersion datasetVersion) {
        return datasetVersion.isDraft();
    }

    public boolean isPermissionToViewFiles(Dataset dataset) {
        return permissionsWrapper.canViewUnpublishedDataset(dvRequestService.getDataverseRequest(), dataset);
    }

    public boolean isAnalysisInProgress(WorkflowExecution workflowExecution) {
        return workflowExecution != null && !workflowExecution.isFinished();
    }

    public boolean isAnalysisNotPerformed(WorkflowExecution workflowExecution) {
        return workflowExecution == null;
    }

    public boolean isAnalysisSucceeded(WorkflowExecution workflowExecution) {
        return workflowExecution != null && workflowExecution.isFinished() && workflowExecution.getLastStep().getFinishedSuccessfully();
    }

    public boolean isAnalysisFailure(WorkflowExecution workflowExecution) {
        return workflowExecution != null && workflowExecution.isFinished() && !workflowExecution.getLastStep().getFinishedSuccessfully();
    }

    public String getEmbargoDateForDisplay(Dataset dataset) {
        SimpleDateFormat format = new SimpleDateFormat(settingsService.getValueForKey(SettingsServiceBean.Key.DefaultDateFormat));
        return dataset.getEmbargoDate().isEmpty() ? "" : format.format(dataset.getEmbargoDate().getOrNull());
    }
    
    
    public WorkflowExecution getWorkflowExecution(DatasetVersion datasetVersion) {
        if (!datasetVersion.isDraft()) {
            Optional<WorkflowExecution> result = workflowServiceBean.findLatestByTriggerTypeAndDatasetVersion(TriggerType.PostPublishDataset, datasetVersion.getDataset().getId(), datasetVersion.getVersionNumber(), datasetVersion.getMinorVersionNumber());
            if (result.isPresent()) {
                return result.get();
            }
        }
        return null;
    }
    
    public String getWorkflowFailureReasonDetail(DatasetVersion datasetVersion) {
        if (!datasetVersion.isDraft()) {
            return workflowServiceBean.findLatestByTriggerTypeAndDatasetVersion(TriggerType.PostPublishDataset, datasetVersion.getDataset().getId(), datasetVersion.getVersionNumber(), datasetVersion.getMinorVersionNumber())
                    .map(execution -> execution.getLastStep())
                    .map(stepExecution -> stepExecution.getOutputParams())
                    .map(outputParams -> outputParams.getOrDefault(Failure.REASON_PARAM_NAME, StringUtils.EMPTY))
                    .orElse(StringUtils.EMPTY);
        }
        return StringUtils.EMPTY;
    }
    
    public List<WorkflowArtifact> getArtifacts(DatasetVersion datasetVersion, WorkflowExecution workflowExecution) {
        return workflowExecution != null ? workflowArfifactService.findAll(workflowExecution.getId()) : Lists.newArrayList();
    }

    public void downloadArtifact(String name, String location) {
        try {
            Faces.sendFile(workflowArfifactService.readAsStream(location).get().getInput(), name, true);
        } catch (IOException e) {
            String error = "Problem getting stream from " + location + ": " + e;
            logger.warn(error);
        }
    }
    
    private boolean canViewDataset(DatasetVersion datasetVersion) {
        return !datasetVersion.getDataset().hasActiveEmbargo() 
                || (datasetVersion.getDataset().hasActiveEmbargo() && isPermissionToViewFiles(datasetVersion.getDataset()));
    }
    
    public String getAnalysisFailureReason(DatasetVersion datasetVersion, WorkflowExecution workflowExecution) {
        if (datasetVersion.isDraft()) {
            return "dataset.analysisTab.dataset.in.draft.message";
        } else if (datasetVersion.getDataset().hasActiveEmbargo() && !isPermissionToViewFiles(datasetVersion.getDataset())) {
            return "dataset.analysisTab.embargo.message";
        } else if (canViewDataset(datasetVersion)
                    && isAnalysisInProgress(workflowExecution)) {
            return "dataset.analysisTab.analysis.in.progress.message";
        } else if (canViewDataset(datasetVersion)
                && isAnalysisNotPerformed(workflowExecution)) {
            return "dataset.analysisTab.analysis.not.performed.message";
        } else if (canViewDataset(datasetVersion)
                && isAnalysisFailure(workflowExecution)) {
            return "dataset.analysisTab.analysis.failed.message";
        }
        
        return null;
    }
}
