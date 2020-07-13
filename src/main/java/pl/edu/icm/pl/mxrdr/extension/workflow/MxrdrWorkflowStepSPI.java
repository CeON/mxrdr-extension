package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldTypeRepository;
import edu.harvard.iq.dataverse.workflow.WorkflowStepRegistry;
import edu.harvard.iq.dataverse.workflow.WorkflowStepSPI;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsImagesFetchingStep;
import pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsImagesPatternCalculatingStep;
import pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsInputAdjustingStep;
import pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsMissingInputFillingStep;
import pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsOutputImportingStep;
import pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsValidateMetadataStep;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Startup
@Singleton
public class MxrdrWorkflowStepSPI implements WorkflowStepSPI {

    public static final String MXRDR_PROVIDER_ID = "mxrdr";

    private final WorkflowStepRegistry stepRegistry;
    private final DatasetVersionServiceBean datasetVersions;
    private final DatasetFieldTypeRepository fieldTypes;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public MxrdrWorkflowStepSPI(WorkflowStepRegistry stepRegistry,
                                DatasetVersionServiceBean datasetVersions,
                                DatasetFieldTypeRepository fieldTypes) {
        this.stepRegistry = stepRegistry;
        this.datasetVersions = datasetVersions;
        this.fieldTypes = fieldTypes;
    }

    @PostConstruct
    public void init() {
        stepRegistry.register(MXRDR_PROVIDER_ID, this);
    }

    // -------------------- LOGIC --------------------

    @Override
    public WorkflowStep getStep(String stepType, WorkflowStepParams stepParameters) {
        switch (stepType) {
            case XdsValidateMetadataStep.STEP_ID:
                return new XdsValidateMetadataStep(datasetVersions);
            case XdsImagesFetchingStep.STEP_ID:
                return new XdsImagesFetchingStep(stepParameters, datasetVersions);
            case XdsImagesPatternCalculatingStep.STEP_ID:
                return new XdsImagesPatternCalculatingStep(stepParameters);
            case XdsMissingInputFillingStep.STEP_ID:
                return new XdsMissingInputFillingStep(stepParameters, datasetVersions);
            case XdsInputAdjustingStep.STEP_ID:
                return new XdsInputAdjustingStep(stepParameters);
            case XdsOutputImportingStep.STEP_ID:
                return new XdsOutputImportingStep(stepParameters, datasetVersions, fieldTypes);
            default:
                throw new IllegalArgumentException("Unsupported step type: '" + stepType + "'.");
        }
    }
}
