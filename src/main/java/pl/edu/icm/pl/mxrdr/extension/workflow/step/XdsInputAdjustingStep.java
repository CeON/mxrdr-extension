package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import com.google.common.io.InputSupplier;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputFileProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static edu.harvard.iq.dataverse.workflow.step.Success.successWith;
import static java.util.Collections.singletonList;
import static pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputFileProcessor.XDS_INPUT_FILE_NAME;
import static pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputParameterProcessor.replaceAnyValue;
import static pl.edu.icm.pl.mxrdr.extension.xds.output.XdsOutputFileParser.XDS_OUTPUT_FILE_NAME;

/**
 * This step allows to replace JOBS and INCLUDE_RESOLUTION_RANGE parameter values in XDS.INP file.
 * <p><u>Usage</u> It can be run with following input parameters:
 * <ul>
 * <li><b>xdsJobList</b> – list of jobs that should be overwritten in XDS.INP file. The items could be
 * separated by spaces, commas or semicolons.
 * <li><b>computeAndUpdateResolutionRange</b> – whether should extract one of the range limits for INCLUDE_RESOLUTION_RANGE
 * parameter in XDS.INP and overwrite it. Requires that CORRECT.LP file is present in the working directory.
 * Should be set to {@code true} or {@code false} or not included at all.
 * </ul>
 */
public class XdsInputAdjustingStep extends FilesystemAccessingWorkflowStep {

    private static final Logger log = LoggerFactory.getLogger(XdsInputAdjustingStep.class);

    public static final String STEP_ID = "xds-adjust-input";

    /**
     * Input parameter containing semicolon (;) separated list of XDS jobs to configure.
     * Default value is a single job of <code>CORRECT</code>.
     */
    static final String JOBS_PARAM_NAME = "jobs";
    /**
     * Boolean input parameter deciding if <code>INCLUDE_RESOLUTION_RANGE</code> parameter should be adjusted
     * based on results of previous execution from <code>CORRECT.LP</code> file present in the working directory.
     * Default value is <code>false</code>.
     */
    static final String ADJUST_RESOLUTION_PARAM_NAME = "adjustResolution";

    private final List<String> jobs;
    private final boolean adjustResolution;

    // -------------------- CONSTRUCTORS --------------------

    public XdsInputAdjustingStep(WorkflowStepParams inputParams) {
        super(inputParams);
        jobs = inputParams.getListOrDefault(JOBS_PARAM_NAME, ";", singletonList("CORRECT"));
        adjustResolution = inputParams.getBoolean(ADJUST_RESOLUTION_PARAM_NAME);
    }

    // -------------------- LOGIC --------------------

    @Override
    protected WorkflowStepResult.Source runInternal(WorkflowExecutionContext context, Path workDir) throws Exception {
        log.trace("Adjusting {} with JOB={}", XDS_INPUT_FILE_NAME, jobsValue());
        addFailureArtifacts(XDS_INPUT_FILE_NAME);
        new XdsInputFileProcessor(workDir)
                .with(replaceAnyValue("JOB", this::jobsValue)
                        .matchingWholeLine())
                .with(replaceAnyValue("INCLUDE_RESOLUTION_RANGE", includeResolutionRangeValue(workDir))
                        .matchingWholeLine()
                        .processWhen(adjustResolution))
                .process();
        return successWith(data ->
                data.put(FAILURE_ARTIFACTS_PARAM_NAME, XDS_INPUT_FILE_NAME)
        );
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionContext context, Map<String, String> internalData, String externalData) {
        throw new UnsupportedOperationException("This step does not pause");
    }

    @Override
    public void rollback(WorkflowExecutionContext workflowExecutionContext, Failure reason) { }

    // -------------------- PRIVATE --------------------

    private String jobsValue() {
        return String.join(" ", jobs);
    }

    private InputSupplier<String> includeResolutionRangeValue(Path workDir) {
        return () -> "50 " + new ResolutionParameterExtractor(workDir).extract();
    }

    // -------------------- INNER CLASSES --------------------

    static class ResolutionParameterExtractor {

        public static final String NUMBER_REGEX = "[0-9.\\-+Ee]+";
        public static final String NON_NUMERIC_CHARACTERS_REGEX = "[^0-9.\\-+Ee]";

        private final Path workDir;

        // -------------------- CONSTRUCTORS --------------------

        public ResolutionParameterExtractor(Path workDir) {
            this.workDir = workDir;
        }

        // -------------------- LOGIC --------------------

        public String extract() throws IOException {
            File correctionFile = workDir.resolve(XDS_OUTPUT_FILE_NAME).toFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(correctionFile))) {
                return extractResolutionParameter(reader.lines());
            }
        }

        // -------------------- PRIVATE --------------------

        /**
         * Realizes line:
         * {@code awk '{ if ($1>=0.3 && $1<=5 && $9>=1.2 && $9<=100 && $11>=50 && $5~"%" && $6~"%" && $7~"%") { print $1 } }' CORRECT.LP | tail -n 1}
         * from the script.
         */
        private String extractResolutionParameter(Stream<String> lines) {
            return lines.map(String::trim)
                    .map(s -> s.split("\\s+"))
                    .filter(ResolutionParameterExtractor::recognizeRowWithResolution)
                    .map(ResolutionParameterExtractor::removeUnusedColumnsAndMapToDoubleArray)
                    .filter(d -> isBetween(d[0], 0.3, 5.0) && isBetween(d[1], 1.2, 100.0) && d[2] >= 50.0)
                    .map(d -> d[0])
                    .map(Object::toString)
                    .reduce((result, element) -> element)
                    .orElseThrow(() -> new IllegalArgumentException("Incorrect data in CORRECT.LP file."));
        }

        private static boolean recognizeRowWithResolution(String[] a) {
            return a.length >= 14
                    && a[0].matches(NUMBER_REGEX)
                    && containPercentSign(a[4], a[5], a[6]);
        }

        public static boolean containPercentSign(String... strings) {
            return Arrays.stream(strings)
                    .allMatch(e -> e.contains("%"));
        }

        private static Double[] removeUnusedColumnsAndMapToDoubleArray(String[] a) {
            return Stream.of(a[0], a[8], a[10])
                    .map(e -> e.replaceAll(NON_NUMERIC_CHARACTERS_REGEX, StringUtils.EMPTY))
                    .map(Double::valueOf)
                    .toArray(Double[]::new);
        }

        private boolean isBetween(double checked, double lowerBound, double upperBound) {
            return checked >= lowerBound && checked <= upperBound;
        }
    }
}
