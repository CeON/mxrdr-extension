package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionStepContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.FilesystemAccessingWorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static edu.harvard.iq.dataverse.workflow.internalspi.SystemProcessStep.ARGUMENTS_PARAM_NAME;
import static edu.harvard.iq.dataverse.workflow.step.Success.successWith;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsImagesFetchingStep.IMAGES_DIR_PARAM_DEFAULT;
import static pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsImagesFetchingStep.IMAGES_DIR_PARAM_NAME;
import static pl.edu.icm.pl.mxrdr.extension.xds.input.XdsInputFileProcessor.XDS_INPUT_FILE_NAME;

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
public class XdsImagesPatternCalculatingStep extends FilesystemAccessingWorkflowStep {

    private static final Logger log = LoggerFactory.getLogger(XdsImagesPatternCalculatingStep.class);

    public static final String STEP_ID = "xds-calculate-images-pattern";

    private final String imgDirName;

    // -------------------- CONSTRUCTORS --------------------

    public XdsImagesPatternCalculatingStep(WorkflowStepParams inputParams) {
        super(inputParams);
        this.imgDirName = inputParams.getOrDefault(IMAGES_DIR_PARAM_NAME, IMAGES_DIR_PARAM_DEFAULT);
    }

    // -------------------- LOGIC --------------------

    @Override
    protected WorkflowStepResult.Source runInternal(WorkflowExecutionStepContext context, Path workDir) throws IOException {
        List<String> fileNames = readFileNamesIn(workDir);
        log.trace("Calculating XDS images name pattern for {} files", fileNames.size());
        String namePattern = calculatePattern(fileNames);
        log.trace("Calculated XDS images name pattern: {}", namePattern);
        return successWith(data -> {
            data.put(ARGUMENTS_PARAM_NAME, namePattern);
            data.put(FAILURE_ARTIFACTS_PARAM_NAME, XDS_INPUT_FILE_NAME);
        });
    }

    @Override
    public WorkflowStepResult resume(WorkflowExecutionStepContext context, Map<String, String> internalData, String externalData) {
        throw new UnsupportedOperationException("This step des not pause");
    }

    @Override
    public void rollback(WorkflowExecutionStepContext context, Failure reason) {
    }

    // -------------------- PRIVATE --------------------

    List<String> readFileNamesIn(Path workDir) throws IOException {
        Path imgDir = workDir.resolve(imgDirName);
        if (!Files.exists(imgDir)) {
            return emptyList();
        }
        return Files.list(imgDir)
                .map(workDir::relativize)
                .map(Path::toString)
                .collect(toList());
    }

    String calculatePattern(List<String> fileNames) {
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
            String prefixString = removeLastDigits(prefix.substring);
            String suffixString = removeFirstDigits(suffix.substring);
            int questionMarkCount = suffix.elements.get(0).length() - prefixString.length() - suffixString.length();
            String questionMarks = StringUtils.repeat('?', questionMarkCount);
            return prefixString + questionMarks + suffixString;
        }
    }
    
    private String removeLastDigits(String input) {
        while (StringUtils.isNotEmpty(input) && Character.isDigit(input.charAt(input.length() - 1))) {
            input = input.substring(0, input.length() - 1);
        }
        return input;
    }

    private String removeFirstDigits(String input) {
        while (StringUtils.isNotEmpty(input) && Character.isDigit(input.charAt(0))) {
            input = input.substring(1);
        }
        return input;
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
