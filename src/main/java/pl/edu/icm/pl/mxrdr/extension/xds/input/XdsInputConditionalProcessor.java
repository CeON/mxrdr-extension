package pl.edu.icm.pl.mxrdr.extension.xds.input;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * Wrapper for {@link XdsInputLineProcessor} allowing to add a condition to check and decide
 * if actual processing should be applied.
 */
public class XdsInputConditionalProcessor implements XdsInputLineProcessor {

    private final Supplier<XdsInputLineProcessor> delegate;
    private final Supplier<Boolean> condition;

    public XdsInputConditionalProcessor(boolean condition, XdsInputLineProcessor delegate) {
        this(() -> condition, delegate);
    }

    public XdsInputConditionalProcessor(Supplier<Boolean> condition, XdsInputLineProcessor delegate) {
        this(condition, () -> delegate);
    }

    public XdsInputConditionalProcessor(Supplier<Boolean> condition, Supplier<XdsInputLineProcessor> delegate) {
        this.delegate = delegate;
        this.condition = condition;
    }

    @Override
    public String process(String line) throws IOException {
        if (Boolean.TRUE.equals(condition.get())) {
            return delegate.get().process(line);
        } else {
            return line;
        }
    }
}
