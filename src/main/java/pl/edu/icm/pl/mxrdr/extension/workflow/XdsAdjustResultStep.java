package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.workflow.WorkflowExecutionContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.Success;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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
public class XdsAdjustResultStep extends FilesystemAccessingWorkflowStep {

    private static final Logger logger = LoggerFactory.getLogger(XdsAdjustResultStep.class);

    public static final String STEP_ID = "xds-adjust-result";

    static final String XDS_JOB_LIST_PARAM = "xdsJobList";
    static final String COMPUTE_AND_UPDATE_RESOLUTION_RANGE_PARAM = "computeAndUpdateResolutionRange";

    static final String XDS_INP = "XDS.INP";
    static final String CORRECT_LP = "CORRECT.LP";

    private static final String DELIMITERS_REGEX = "[;,]";
    private static final String JOB_PARAM_REGEX = "^.*JOB=.*$";
    private static final String JOB_PARAM_REPLACEMENT = "JOB= ";
    private static final String RESOLUTION_PARAM_REGEX = "^.*INCLUDE_RESOLUTION_RANGE=50.*$";
    private static final String RESOLUTION_PARAM_REPLACEMENT = "INCLUDE_RESOLUTION_RANGE=50 ";

    private String jobs;
    private boolean shouldProcessResolutionRange;

    // -------------------- CONSTRUCTORS --------------------

    public XdsAdjustResultStep(Map<String, String> inputParams) {
        super(inputParams);
        this.jobs = inputParams.getOrDefault(XDS_JOB_LIST_PARAM, StringUtils.EMPTY);
        this.shouldProcessResolutionRange
                = Boolean.TRUE.toString()
                .equalsIgnoreCase(inputParams.getOrDefault(COMPUTE_AND_UPDATE_RESOLUTION_RANGE_PARAM, Boolean.FALSE.toString()));
    }

    // -------------------- LOGIC --------------------

    @Override
    protected WorkflowStepResult.Source runInternal(WorkflowExecutionContext workflowExecutionContext, Path workDir) throws Exception {
        try {
            FileContentReplacer.of(XDS_INP, workDir)
                    .add(PatternAndReplacement.of(
                            JOB_PARAM_REGEX, JOB_PARAM_REPLACEMENT + sanitizeJobList(jobs)))
                    .add(shouldProcessResolutionRange
                            ? PatternAndReplacement.of(RESOLUTION_PARAM_REGEX,
                            RESOLUTION_PARAM_REPLACEMENT + ResolutionParameterExtractor.of(workDir).extract())
                            : PatternAndReplacement.NONE)
                    .replace();
            return Success::new;
        } catch (RuntimeException re) {
            logger.warn("Exception during step execution: ", re);
            return output -> new Failure(re.getMessage());
        }
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionContext context, Map<String, String> internalData, String externalData) {
        throw new UnsupportedOperationException("This step does not pause");
    }

    @Override
    public void rollback(WorkflowExecutionContext workflowExecutionContext, Failure reason) { }

    // -------------------- PRIVATE --------------------

    private String sanitizeJobList(String jobList) {
        return jobList.replaceAll(DELIMITERS_REGEX, " ")
                .replaceAll("\\s+", " ");
    }

    // -------------------- INNER CLASSES --------------------

    static class FileContentReplacer {
        private String fileName;
        private Path workDir;
        private Set<PatternAndReplacement> actions = new HashSet<>();

        // -------------------- CONSTRUCTORS --------------------

        private FileContentReplacer(String fileName, Path workDir) {
            this.fileName = fileName;
            this.workDir = workDir;
        }

        // -------------------- LOGIC --------------------

        public static FileContentReplacer of(String fileName, Path workDir) {
            return new FileContentReplacer(fileName, workDir);
        }

        public FileContentReplacer add(PatternAndReplacement action) {
            if (action != PatternAndReplacement.NONE) {
                actions.add(action);
            }
            return this;
        }

        public void replace() {
            File file = workDir.resolve(fileName).toFile();
            File modifiedFile = workDir.resolve(fileName + RandomStringUtils.randomAlphanumeric(8)).toFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(modifiedFile))) {
                readReplaceAndWrite(reader, writer);
                file.delete();
                modifiedFile.renameTo(workDir.resolve(fileName).toFile());
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }

        // -------------------- PRIVATE --------------------

        private void readReplaceAndWrite(BufferedReader reader, BufferedWriter writer) throws IOException {
            String line;
            while ((line = reader.readLine()) != null) {
                for (PatternAndReplacement action : actions) {
                    line = line.replaceAll(action.pattern, action.replacement);
                }
                writer.write(line);
                writer.newLine();
            }
        }
    }

    static class ResolutionParameterExtractor {
        public static final String NUMBER_REGEX = "[0-9.\\-+Ee]+";
        public static final String NON_NUMERIC_CHARACTERS_REGEX = "[^0-9.\\-+Ee]";

        private Path workDir;

        // -------------------- CONSTRUCTORS --------------------

        private ResolutionParameterExtractor(Path workDir) {
            this.workDir = workDir;
        }

        // -------------------- LOGIC --------------------

        public static ResolutionParameterExtractor of(Path workDir) {
            return new ResolutionParameterExtractor(workDir);
        }

        public String extract() {
            File correctionFile = workDir.resolve(CORRECT_LP).toFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(correctionFile))) {
                return extractResolutionParameter(reader.lines());
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
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

    private static class PatternAndReplacement {
        public static final PatternAndReplacement NONE = new PatternAndReplacement(StringUtils.EMPTY, StringUtils.EMPTY);
        public final String pattern;
        public final String replacement;

        // -------------------- CONSTRUCTORS --------------------

        private PatternAndReplacement(String pattern, String replacement) {
            this.pattern = pattern;
            this.replacement = replacement;
        }

        // -------------------- LOGIC --------------------

        public static PatternAndReplacement of(String pattern, String replacement) {
            return new PatternAndReplacement(pattern, replacement);
        }
    }
}
