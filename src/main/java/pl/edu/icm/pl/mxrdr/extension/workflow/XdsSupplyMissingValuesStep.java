package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.persistence.workflow.WorkflowExecution;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.Success;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.workflow.utils.FileContentReplacer;
import pl.edu.icm.pl.mxrdr.extension.workflow.utils.FileContentReplacer.PatternAndReplacement;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This step checks whether some values in XDS.INP were not set (these are: DETECTOR_DISTANCE,
 * OSCILLATION_RANGE, STARTING_ANGLE, X-RAY_WAVELENGTH, ORGX, ORGY – in case there are not set
 * their values are XXX or 0 (single zero, without dot)) and tries to supply their values
 * from dataset metadata.
 */
public class XdsSupplyMissingValuesStep extends FilesystemAccessingWorkflowStep {

    static final String STEP_ID = "xds-supply-missing-values";

    private static final Map<String, PatternAndReplacement> PATTERNS = Initializer.initializePatternMap();

    private DatasetVersionServiceBean versionService;

    public XdsSupplyMissingValuesStep(Map<String, String> inputParams, DatasetVersionServiceBean versionService) {
        super(inputParams);
        this.versionService = versionService;
    }

    @Override
    protected WorkflowStepResult.Source runInternal(WorkflowExecutionContext workflowExecutionContext, Path path) throws Exception {
        List<PatternAndReplacement> actions = prepareReplacementActions(workflowExecutionContext);
        FileContentReplacer.of(XdsAdjustResultStep.XDS_INP, path)
                .add(actions)
                .replace();
        return Success::new;
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionContext workflowExecutionContext, Map<String, String> map, String s) {
        throw new UnsupportedOperationException("This step does not pause");
    }

    @Override
    public void rollback(WorkflowExecutionContext workflowExecutionContext, Failure failure) { }

    // -------------------- PRIVATE --------------------

    private List<PatternAndReplacement> prepareReplacementActions(WorkflowExecutionContext workflowExecutionContext) {
        Map<String, String> replacementValues = readReplacementValues(workflowExecutionContext);
        return PATTERNS.entrySet().stream()
                .filter(e -> replacementValues.get(e.getKey()) != null)
                .map(e -> PatternAndReplacement.of(e.getValue().pattern,
                        String.format(e.getValue().replacement, replacementValues.get(e.getKey()))))
                .collect(Collectors.toList());
    }

    private Map<String, String> readReplacementValues(WorkflowExecutionContext executionContext) {
        return findVersion(executionContext)
                .map(v -> v.getDatasetFieldsByTypeName(MxrdrMetadataField.DATA_COLLECTION.getValue()))
                .orElse(Collections.emptyList()).stream()
                .findFirst()
                .map(DatasetField::getDatasetFieldsChildren)
                .orElse(Collections.emptyList()).stream()
                .filter(f -> PATTERNS.containsKey(extractFieldName(f)) && f.getValue() != null)
                .collect(Collectors.toMap(this::extractFieldName, DatasetField::getValue));
    }

    private String extractFieldName(DatasetField field) {
        return field.getDatasetFieldType().getName();
    }

    private Optional<DatasetVersion> findVersion(WorkflowExecutionContext executionContext) {
        WorkflowExecution execution = executionContext.getExecution();
        DatasetVersion version = versionService.findByVersionNumber(execution.getDatasetId(),
                execution.getMajorVersionNumber(), execution.getMinorVersionNumber());
        return Optional.ofNullable(version);
    }

    // -------------------- INNER CLASSES --------------------

    private static class Initializer {
        private static final String UNDEFINED_REGEX = "(XXX|0[^.]?)";
        private static final String COMMENT = " ! value supplied by " + STEP_ID + " step.";

        // -------------------- LOGIC --------------------

        static Map<String, PatternAndReplacement> initializePatternMap() {
            Map<String, PatternAndReplacement> patterns = new HashMap<>();
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_DISTANCE.getValue(),
                    createPatternAndReplacement("DETECTOR_DISTANCE"));
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_OSCILLATION_STEP_SIZE.getValue(),
                    createPatternAndReplacement("OSCILLATION_RANGE"));
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_STARTING_ANGLE.getValue(),
                    createPatternAndReplacement("STARTING_ANGLE"));
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_WAVE_LENGTH.getValue(),
                    createPatternAndReplacement("X-RAY_WAVELENGTH"));
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_ORG_X.getValue(),
                    PatternAndReplacement.of(createPattern("ORGX"), "ORGX= %s"));
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_ORG_Y.getValue(),
                    createPatternAndReplacement("ORGY"));
            return patterns;
        }

        // -------------------- PRIVATE --------------------

        private static String createPattern(String parameter) {
            return parameter + "=\\s*" + UNDEFINED_REGEX;
        }

        private static PatternAndReplacement createPatternAndReplacement(String parameter) {
            return PatternAndReplacement.of(
                    createPattern(parameter),
                    parameter + "= %s" + COMMENT);
        }
    }
}
