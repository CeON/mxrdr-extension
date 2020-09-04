package pl.edu.icm.pl.mxrdr.extension.xds.input;

import com.google.common.io.InputSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Implementation of XDS input processor that allows changing values of XDS input parameters.
 */
public class XdsInputParameterProcessor implements XdsInputLineProcessor {

    private static final Logger log = LoggerFactory.getLogger(XdsInputParameterProcessor.class);

    public final String pattern;
    public final InputSupplier<String> replacement;

    // -------------------- CONSTRUCTORS --------------------

    private XdsInputParameterProcessor(String pattern, InputSupplier<String> replacement) {
        this.pattern = pattern;
        this.replacement = replacement;
    }

    /**
     * Creates a processor that will replace value for given parameter regardless of it's actual value.
     * @param paramName parameter name to change value for.
     * @param paramValue supplier of value to be put as a replacement of original parameter value.
     * @return line processor for given XDS input parameter.
     */
    public static XdsInputParameterProcessor replaceAnyValue(String paramName, InputSupplier<String> paramValue) {
        return new XdsInputParameterProcessor(anyValueOf(paramName), valueReplacementOf(paramName, paramValue));
    }

    /**
     * Creates a processor that will replace value for given parameter only if actual value is undefined.
     * @param paramName parameter name to change value for.
     * @param paramValue supplier of value to be put as a replacement of original parameter value.
     * @return line processor for given XDS input parameter.
     */
    public static XdsInputParameterProcessor replaceUndefinedValue(String paramName, InputSupplier<String> paramValue) {
        return new XdsInputParameterProcessor(undefinedValueOf(paramName), valueReplacementOf(paramName, paramValue));
    }

    // -------------------- LOGIC --------------------

    /**
     * Extends the matching pattern for the whole line.
     * @return processor with matching pattern extended.
     */
    public XdsInputParameterProcessor matchingWholeLine() {
        return new XdsInputParameterProcessor("^" + pattern + "$", replacement);
    }

    /**
     * Wraps current processor into a conditional one.
     * @param condition the condition to deciding if processing should be applied.
     * @return conditional processor.
     */
    public XdsInputConditionalProcessor processWhen(boolean condition) {
        return new XdsInputConditionalProcessor(condition, this);
    }

    /**
     * Performs the line replacement.
     * @param line line to process.
     * @return replaced line.
     * @throws IOException in case of I/O errors.
     */
    @Override
    public String process(String line) throws IOException {
        String newValue = replacement.getInput();
        log.trace("Set XDS input param {}", newValue);
        return line.replaceAll(pattern, newValue);
    }

    // -------------------- PRIVATE --------------------

    private static String anyValueOf(String paramName) {
        return paramName + "\\s*=\\s*.*";
    }

    private static String undefinedValueOf(String paramName) {
        return paramName + "\\s*=\\s*XXX";
    }

    private static InputSupplier<String> valueReplacementOf(String paramName, InputSupplier<String> paramValue) {
        return () -> paramName + "=" + paramValue.getInput();
    }
}
