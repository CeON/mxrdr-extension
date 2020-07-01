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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XdsSupplyMissingValuesStep extends FilesystemAccessingWorkflowStep {

    static final String STEP_ID = "xds-supply-missing-values";

    private static final Set<String> SOURCE_FIELDS_SET = Initializer.initializeSourceFieldsSet();
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
        return PATTERNS.keySet().stream()
                .filter(k -> replacementValues.get(k) != null)
                .map(k -> {
                    PatternAndReplacement patterns = PATTERNS.get(k);
                    return FileContentReplacer.PatternAndReplacement.of(patterns.pattern,
                            String.format(patterns.replacement, replacementValues.get(k)));
                })
                .collect(Collectors.toList());
    }

    private Map<String, String> readReplacementValues(WorkflowExecutionContext executionContext) {
        return findVersion(executionContext)
                .map(DatasetVersion::getDatasetFields)
                .orElse(Collections.emptyList()).stream()
                .filter(f -> MxrdrMetadataField.DATA_COLLECTION.getValue()
                        .equals(extractFieldName(f)))
                .findFirst()
                .map(DatasetField::getDatasetFieldsChildren)
                .orElse(Collections.emptyList()).stream()
                .filter(f -> SOURCE_FIELDS_SET.contains(extractFieldName(f)))
                .collect(HashMap::new,
                        (m, f) -> m.put(extractFieldName(f), f.getValue()),
                        HashMap::putAll);
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

        static Set<String> initializeSourceFieldsSet() {
            return Stream.of(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_DISTANCE,
                    MxrdrMetadataField.DATA_COLLECTION_OSCILLATION_STEP_SIZE,
                    MxrdrMetadataField.DATA_COLLECTION_STARTING_ANGLE,
                    MxrdrMetadataField.DATA_COLLECTION_WAVE_LENGTH,
                    MxrdrMetadataField.DATA_COLLECTION_ORG_X,
                    MxrdrMetadataField.DATA_COLLECTION_ORG_Y)
                    .map(MxrdrMetadataField::getValue)
                    .collect(Collectors.toSet());
        }

        static Map<String, PatternAndReplacement> initializePatternMap() {
            Map<String, PatternAndReplacement> patterns = new HashMap<>();
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_DISTANCE.getValue(),
                    FileContentReplacer.PatternAndReplacement.of("^.*DETECTOR_DISTANCE=\\s*" + UNDEFINED_REGEX,
                            "DETECTOR_DISTANCE= %s" + COMMENT));
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_OSCILLATION_STEP_SIZE.getValue(),
                    FileContentReplacer.PatternAndReplacement.of("^.*OSCILLATION_RANGE=\\s*" + UNDEFINED_REGEX,
                            "OSCILLATION_RANGE= %s" + COMMENT));
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_STARTING_ANGLE.getValue(),
                    FileContentReplacer.PatternAndReplacement.of("^.*STARTING_ANGLE=\\s*" + UNDEFINED_REGEX,
                            "STARTING_ANGLE= %s" + COMMENT));
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_WAVE_LENGTH.getValue(),
                    FileContentReplacer.PatternAndReplacement.of("^.*X-RAY_WAVELENGTH=\\s*" + UNDEFINED_REGEX,
                            "X-RAY_WAVELENGTH= %s" + COMMENT));
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_ORG_X.getValue(),
                    FileContentReplacer.PatternAndReplacement.of("^.*ORGX=\\s*" + UNDEFINED_REGEX,
                            "ORGX= %s"));
            patterns.put(MxrdrMetadataField.DATA_COLLECTION_ORG_Y.getValue(),
                    FileContentReplacer.PatternAndReplacement.of("ORGY=\\s*" + UNDEFINED_REGEX,
                            "ORGY= %s" + COMMENT));
            return patterns;
        }
    }
}
