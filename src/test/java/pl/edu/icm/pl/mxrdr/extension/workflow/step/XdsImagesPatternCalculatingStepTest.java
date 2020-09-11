package pl.edu.icm.pl.mxrdr.extension.workflow.step;

import edu.harvard.iq.dataverse.workflow.step.WorkflowStepParams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.Files.createTempFile;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.edu.icm.pl.mxrdr.extension.workflow.step.XdsImagesFetchingStep.IMAGES_DIR_PARAM_DEFAULT;

public class XdsImagesPatternCalculatingStepTest implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(asList("sample1.img", "test1.img", "test2.img", "test3.img"), "test?.img"),
                Arguments.of(asList("abc-master.h5", "abc-1.h5", "abc-2.h5", "abc-3.h5"), "abc-master.h5"),
                Arguments.of(asList("short1.cbf", "short2.cbf", "longer1.img", "longer2.img"), "longer?.img"),
                Arguments.of(asList("pomiar08_1_00001.cbf", 
                                    "pomiar08_1_00251.cbf", 
                                    "pomiar08_1_00501.cbf", 
                                    "pomiar08_1_00751.cbf", 
                                    "pomiar08_1_01001.cbf", 
                                    "pomiar08_1_01251.cbf", 
                                    "pomiar08_1_01501.cbf", 
                                    "pomiar08_1_01751.cbf"), "pomiar08_1_?????.cbf"),
                Arguments.of(asList("ZD-3_Pn7.0001",
                                    "ZD-3_Pn7.0021",
                                    "ZD-3_Pn7.0031",
                                    "ZD-3_Pn7.0401",
                                    "ZD-3_Pn7.0451",
                                    "ZD-3_Pn7.0491",
                                    "ZD-3_Pn7.0501",
                                    "ZD-3_Pn7.0661",
                                    "ZD-3_Pn7.0771",
                                    "ZD-3_Pn7.0891"), "ZD-3_Pn7.????") 
        );
    }

    @ParameterizedTest(name = "[{index}] \"{1}\" pattern")
    @ArgumentsSource(XdsImagesPatternCalculatingStepTest.class)
    void shouldCalculatePatterns(List<String> fileNames, String expected) {
        // given
        XdsImagesPatternCalculatingStep step = new XdsImagesPatternCalculatingStep(new WorkflowStepParams());
        // when
        String actual = step.calculatePattern(fileNames);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void shouldListFileNames() throws IOException {
        // given
        int imagesCount = 10;
        Path tmpDir = givenTempDirWithImages(imagesCount);
        XdsImagesPatternCalculatingStep step = new XdsImagesPatternCalculatingStep(new WorkflowStepParams());
        // when
        List<String> fileNames = step.readFileNamesIn(tmpDir);
        // then
        assertThat(fileNames).hasSize(imagesCount);
        assertThat(fileNames).allMatch(name -> name.startsWith(IMAGES_DIR_PARAM_DEFAULT +"/test"));
        assertThat(fileNames).allMatch(name -> name.endsWith(".img"));
    }

    private static Path givenTempDirWithImages(int imagesCount) throws IOException {
        Path tmpDir = createTempDirectory("test");
        tmpDir.toFile().deleteOnExit();
        Path imgDir = createDirectories(tmpDir.resolve(IMAGES_DIR_PARAM_DEFAULT));
        imgDir.toFile().deleteOnExit();
        for (int i = 0; i < imagesCount; i++) {
            Path tmpFile = createTempFile(imgDir, "test" + i, ".img");
            tmpFile.toFile().deleteOnExit();
        }
        return tmpDir;
    }
}
