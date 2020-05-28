package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.workflow.WorkflowContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static edu.harvard.iq.dataverse.workflow.internalspi.SystemProcessStep.ARGUMENTS_PARAM_NAME;
import static edu.harvard.iq.dataverse.workflow.step.Success.successWith;
import static java.util.Arrays.asList;

/**
 * Computes file name pattern to pass to <code>generate_XDS.INP</code> script.
 * <p>
 * There should be a set of images following a given naming pattern:
 * <ul>
 *     <li>all names in that set will have equal length</li>
 *     <li>each name will start with some common prefix</li>
 *     <li>each name will then contain a constant width ordinal number filled up with leading zeros</li>
 *     <li>each name will then end with a common file extension</li>
 *     <li>there will be many more names following such pattern (thousands), than any other names (tens)</li>
 * </ul>
 * The aim of this class is to identify the subset of files following such pattern and identify that exact pattern.
 * <p>
 * The idea is based on a heuristic described above and expectation that a set of files will distinguish itself
 * from the rest. There is an expectation that datasets will be properly prepared and that the files will follow
 * expected file naming convention. This is by no means a perfect algorithm and knowingly does not cover all possible
 * edge cases, as those are pretty vast and unpredictable given that any files can be present in a dataset. We try
 * our best to figure out what images we should analyse and come up with a pattern that is later put into generated
 * input file. That file is later accessible to the author of the dataset to see what wi were able to deduce. In case
 * the dataset did not conform with our assumptions, we expect the author to correct the dataset in accordance to them
 * and retry the process on the next dataset version.
 */
class XdsImagesPatternStep extends FilesystemAccessingWorkflowStep {

    static final String STEP_ID = "xds-images-pattern";

    /**
     * Input parameter containing {@value #FILE_NAMES_SEPARATOR} separated list of file names to compute pattern for.
     */
    static final String FILE_NAMES_PARAM_NAME = "fileNames";

    /**
     * Values separator for {@value #FILE_NAMES_PARAM_NAME} input parameter.
     */
    static final String FILE_NAMES_SEPARATOR = ";";

    private final List<String> fileNames;

    // -------------------- CONSTRUCTORS --------------------

    public XdsImagesPatternStep(Map<String, String> inputParams) {
        super(inputParams);
        this.fileNames = asList(inputParams.getOrDefault(FILE_NAMES_PARAM_NAME, "").split(FILE_NAMES_SEPARATOR));
    }

    // -------------------- LOGIC --------------------

    @Override
    protected WorkflowStepResult.Source runInternal(WorkflowContext context, Path workDir) {
        String namePattern = calculatePattern();

        return successWith(outputParams ->
                outputParams.put(ARGUMENTS_PARAM_NAME, "\"" + namePattern + "\"")
        );
    }

    @Override
    public WorkflowStepResult resume(WorkflowContext context, Map<String, String> internalData, String externalData) {
        throw new UnsupportedOperationException("This step des not pause");
    }

    @Override
    public void rollback(WorkflowContext context, Failure reason) {
    }

    // -------------------- PRIVATE --------------------

    String calculatePattern() {
        WithCommonSubstring prefix = new MostCommonLongestSubstringFinder(this::prefixOf)
                .findIn(fileNames);

        WithCommonSubstring suffix = new MostCommonLongestSubstringFinder(this::suffixOf)
                .findIn(prefix.elements);

        return patternWith(prefix, suffix);
    }

    private String prefixOf(String value, int prefixLength) {
        return value.substring(0, prefixLength);
    }

    private String suffixOf(String value, int suffixLength) {
        return value.substring(value.length() - suffixLength);
    }

    private String patternWith(WithCommonSubstring prefix, WithCommonSubstring suffix) {
        if (".h5".equals(suffix.substring)) {
            return prefix.substring + "master.h5";
        } else {
            int questionMarkCount = suffix.elements.get(0).length() - prefix.length() - suffix.length();
            String questionMarks = StringUtils.repeat('?', questionMarkCount);
            return prefix.substring + questionMarks + suffix.substring;
        }
    }

    // -------------------- INNER CLASSES --------------------

    /**
     * Finder class for most common and longest substring.
     * Consult assumptions documented at the outer class level.
     */
    private static class MostCommonLongestSubstringFinder {

        private final Map<String, WithCommonSubstring> cache = new HashMap<>();
        private final BiFunction<String, Integer, String> substringSupplier;

        private WithCommonSubstring found = new WithCommonSubstring("");
        private int checkedSubstringLength = 0;
        private boolean longerSubstringPossible = true;

        MostCommonLongestSubstringFinder(BiFunction<String, Integer, String> substringSupplier) {
            this.substringSupplier = substringSupplier;
        }

        WithCommonSubstring findIn(List<String> values) {
            while (isLongerSubstringPossible()) {
                for (String value : values) {
                    cacheAndCompare(value);
                }
            }
            return found;
        }

        private boolean isLongerSubstringPossible() {
            if (longerSubstringPossible) {
                checkedSubstringLength++;
                longerSubstringPossible = false;
                return true;
            } else {
                return false;
            }
        }

        private void cacheAndCompare(String value) {
            if (checkedSubstringLength > value.length()) {
                return;
            }

            WithCommonSubstring candidate = computeAndCache(value);

            if (candidate.isMoreCommonOrLonger(found)) {
                found = candidate;
                longerSubstringPossible = true;
            }
        }

        private WithCommonSubstring computeAndCache(String value) {
            String substring = substringSupplier.apply(value, checkedSubstringLength);
            return cache.computeIfAbsent(substring, WithCommonSubstring::new)
                    .withElement(value);
        }
    }

    /**
     * Holder class for a common substring and all elements sharing it.
     */
    private static class WithCommonSubstring {

        private final String substring;
        private final List<String> elements = new ArrayList<>();

        WithCommonSubstring(String substring) {
            this.substring = substring;
        }

        int length() {
            return substring.length();
        }

        WithCommonSubstring withElement(String element) {
            elements.add(element);
            return this;
        }

        boolean isMoreCommonOrLonger(WithCommonSubstring other) {
            return isMoreCommon(other) || isAsCommonAndLonger(other);
        }

        private boolean isMoreCommon(WithCommonSubstring other) {
            return elements.size() > other.elements.size();
        }

        private boolean isAsCommonAndLonger(WithCommonSubstring other) {
            return elements.size() == other.elements.size() && substring.length() > other.substring.length();
        }
    }
}