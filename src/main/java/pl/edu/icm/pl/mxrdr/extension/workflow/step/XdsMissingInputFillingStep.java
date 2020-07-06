package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import com.google.common.io.InputSupplier;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.Success;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputConditionalProcessor;
import pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputFileProcessor;
import pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputLineProcessor;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputParameterProcessor.replaceUndefinedValue;

/**
 * This step checks whether some values in XDS.INP were not set (these are: DETECTOR_DISTANCE,
 * OSCILLATION_RANGE, STARTING_ANGLE, X-RAY_WAVELENGTH, ORGX, ORGY – in case there are not set
 * their values are XXX or 0 (single zero, without dot)) and tries to supply their values
 * from dataset metadata.
 */
public class XdsMissingInputFillingStep extends FilesystemAccessingWorkflowStep {

    private static final Logger log = LoggerFactory.getLogger(XdsMissingInputFillingStep.class);

    public static final String STEP_ID = "xds-fill-missing-input";

    private static final Map<String, XdsInputLineProcessorFactory> FACTORIES = new HashMap<String, XdsInputLineProcessorFactory>() {{
        put(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_DISTANCE.getValue(), value ->
                replaceUndefinedValue("DETECTOR_DISTANCE", value));
        put(MxrdrMetadataField.DATA_COLLECTION_OSCILLATION_STEP_SIZE.getValue(), value ->
                replaceUndefinedValue("OSCILLATION_RANGE", value));
        put(MxrdrMetadataField.DATA_COLLECTION_STARTING_ANGLE.getValue(), value ->
                replaceUndefinedValue("STARTING_ANGLE", value));
        put(MxrdrMetadataField.DATA_COLLECTION_WAVE_LENGTH.getValue(), value ->
                replaceUndefinedValue("X-RAY_WAVELENGTH", value));
        put(MxrdrMetadataField.DATA_COLLECTION_ORG_X.getValue(), value ->
                replaceUndefinedValue("ORGX", value));
        put(MxrdrMetadataField.DATA_COLLECTION_ORG_Y.getValue(), value ->
                replaceUndefinedValue("ORGY", value));
    }};

    public XdsMissingInputFillingStep(WorkflowStepParams inputParams) {
        super(inputParams);
    }

    @Override
    protected WorkflowStepResult.Source runInternal(WorkflowExecutionContext context, Path workDir) throws Exception {
        List<XdsInputLineProcessor> processors = prepareProcessors(context);
        log.trace("Potentially adjusting values for {} XDS input parameters", processors.size());
        new XdsInputFileProcessor(workDir)
                .withAll(processors)
                .process();
        return Success::new;
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionContext context, Map<String, String> internalData, String externalData) {
        throw new UnsupportedOperationException("This step does not pause");
    }

    @Override
    public void rollback(WorkflowExecutionContext workflowExecutionContext, Failure failure) { }

    // -------------------- PRIVATE --------------------

    private List<XdsInputLineProcessor> prepareProcessors(WorkflowExecutionContext context) {
        return context.getDatasetVersion()
                .getDatasetFieldByTypeName(MxrdrMetadataField.DATA_COLLECTION.getValue())
                .map(DatasetField::getDatasetFieldsChildren)
                .orElseGet(Collections::emptyList).stream()
                .map(this::getProcessor)
                .collect(toList());
    }

    private XdsInputLineProcessor getProcessor(DatasetField field) {
        final String fieldName = field.getDatasetFieldType().getName();
        final String fieldValue = field.getValue();
        return new XdsInputConditionalProcessor(
                () -> FACTORIES.containsKey(fieldName) && nonNull(fieldValue),
                () -> FACTORIES.get(fieldName).apply(() -> fieldValue)
        );
    }

    // -------------------- INNER CLASSES --------------------

    private interface XdsInputLineProcessorFactory extends Function<InputSupplier<String>, XdsInputLineProcessor> { }
}
