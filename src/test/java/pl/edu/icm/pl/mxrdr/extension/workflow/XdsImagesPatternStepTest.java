package pl.edu.icm.pl.mxrdr.extension.workflow;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.edu.icm.pl.mxrdr.extension.workflow.XdsImagesPatternStep.FILE_NAMES_PARAM_NAME;
import static pl.edu.icm.pl.mxrdr.extension.workflow.XdsImagesPatternStep.FILE_NAMES_SEPARATOR;

class XdsImagesPatternStepTest implements ArgumentsProvider {

    @ParameterizedTest(name = "[{index}] \"{1}\" pattern")
    @ArgumentsSource(XdsImagesPatternStepTest.class)
    void shouldCalculatePatterns(String names, String expected) {
        // given
        XdsImagesPatternStep step = new XdsImagesPatternStep(singletonMap(FILE_NAMES_PARAM_NAME, names));
        // when
        String actual = step.calculatePattern();
        // then
        assertEquals(expected, actual);
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(listOfNames("sample1.img", "test1.img", "test2.img", "test3.img"), "test?.img"),
                Arguments.of(listOfNames("abc-master.h5", "abc-1.h5", "abc-2.h5", "abc-3.h5"), "abc-master.h5"),
                Arguments.of(listOfNames("short1.cbf", "short2.cbf", "longer1.img", "longer2.img"), "longer?.img")
        );
    }

    private static String listOfNames(String...names) {
        return String.join(FILE_NAMES_SEPARATOR, names);
    }
}
