package pl.edu.icm.pl.mxrdr.extension.importer.xdsinp;

import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class XdsInpRunnerTest {

    private XdsInpRunner runner = new XdsInpRunner(null);

    @Test
    void prepareFiles() throws IOException {
        // given
        File inputFile = Files.createTempFile("temp-test", "").toFile();

        // when
        XdsInpRunner.ProducedPaths producedPaths = runner.prepareFiles(inputFile);

        // then
        assertThat(producedPaths.getTempDirectory()).exists();
        assertThat(producedPaths.getInputFile()).exists();
        assertThat(producedPaths.getInputFile().getParent()).isEqualTo(producedPaths.getTempDirectory());

        Files.deleteIfExists(producedPaths.getInputFile());
        Files.deleteIfExists(producedPaths.getTempDirectory());
    }

    @Test
    void cleanUp() throws IOException {
        // given
        File inputFile = Files.createTempFile("temp-test", "").toFile();
        XdsInpRunner.ProducedPaths producedPaths = runner.prepareFiles(inputFile);

        // when
        runner.cleanUp(producedPaths);

        // then
        assertThat(producedPaths.getTempDirectory()).doesNotExist();
        assertThat(producedPaths.getInputFile()).doesNotExist();
    }
}