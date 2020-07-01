package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetField;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import javax.enterprise.inject.Vetoed;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class XdsSupplyMissingValuesStepTest {

    private static final String TEST_PATH_PREFIX = "xds/xsmv-test-";
    private static final String VALUE = "1234.5";

    private Path workDir;

    private WorkflowExecutionContext context = Mockito.mock(WorkflowExecutionContext.class, Mockito.RETURNS_DEEP_STUBS);

    @BeforeEach
    public void setUp() throws IOException {
        workDir = Files.createTempDirectory("xds-test-temp");
        String inputFileSourcePath = getFileFromResources(XdsAdjustResultStep.XDS_INP);
        Files.copy(Paths.get(inputFileSourcePath), workDir.resolve(XdsAdjustResultStep.XDS_INP));
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.list(workDir).forEach(f -> f.toFile().delete());
        workDir.toFile().delete();
    }

    @Test
    @DisplayName("Should supply missing values in XDS.INP with available metadata")
    public void shouldSupplyMissingValuesFromMetadata() throws Exception {
        // given
        DatasetVersion version = createVersionWithFields(createField(MxrdrMetadataField.DATA_COLLECTION,
                createField(MxrdrMetadataField.DATA_COLLECTION_ORG_X, VALUE),
                createField(MxrdrMetadataField.DATA_COLLECTION_ORG_Y, VALUE),
                createField(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_DISTANCE, VALUE),
                createField(MxrdrMetadataField.DATA_COLLECTION_OSCILLATION_STEP_SIZE, VALUE),
                createField(MxrdrMetadataField.DATA_COLLECTION_STARTING_ANGLE, VALUE)));
        TestDatasetVersionService versionService = new TestDatasetVersionService(version);
        XdsSupplyMissingValuesStep step = new XdsSupplyMissingValuesStep(Collections.emptyMap(), versionService);

        // when
        step.runInternal(context, workDir);

        // then
        List<String> lines = Files.readAllLines(workDir.resolve(XdsAdjustResultStep.XDS_INP));

        //  – values supplied and updated
        assertThat(lines).anyMatch(keyValMatcher("ORGX", VALUE));
        assertThat(lines).anyMatch(keyValMatcher("STARTING_ANGLE", VALUE));
        assertThat(lines).anyMatch(keyValMatcher("DETECTOR_DISTANCE", VALUE));

        //  – values supplied, but other present in file already
        assertThat(lines).noneMatch(keyValMatcher("OSCILLATION_RANGE", VALUE));
        assertThat(lines).noneMatch(keyValMatcher("ORGY", VALUE));

        //  – value not supplied nor present in file
        assertThat(lines).anyMatch(keyValMatcher("X-RAY_WAVELENGTH", "XXX"));
    }

    // -------------------- PRIVATE --------------------

    private Predicate<String> keyValMatcher(String key, String value) {
        return s -> s.matches(".*" + key + "=\\s*" + value + ".*");
    }

    private String getFileFromResources(String name) {
        return getClass().getClassLoader().getResource(TEST_PATH_PREFIX + name).getPath();
    }

    private DatasetField createField(MxrdrMetadataField type, String value) {
        DatasetField field = new DatasetField();
        DatasetFieldType fieldType = new DatasetFieldType();
        fieldType.setName(type.getValue());
        field.setDatasetFieldType(fieldType);
        field.setValue(value);
        return field;
    }

    private DatasetField createField(MxrdrMetadataField type, DatasetField... children) {
        DatasetField field = createField(type, (String) null);
        field.setDatasetFieldsChildren(Arrays.asList(children));
        return field;
    }

    private DatasetVersion createVersionWithFields(DatasetField... fields) {
        DatasetVersion version = new DatasetVersion();
        version.setDatasetFields(Arrays.asList(fields));
        return version;
    }

    // -------------------- INNER CLASSES --------------------

    @Vetoed
    static class TestDatasetVersionService extends DatasetVersionServiceBean {
        private DatasetVersion version;

        // -------------------- CONSTRUCTORS --------------------

        public TestDatasetVersionService(DatasetVersion version) {
            this.version = version;
        }

        // -------------------- LOGIC --------------------

        @Override
        public DatasetVersion findByVersionNumber(Long datasetId, Long majorVersionNumber, Long minorVersionNumber) {
            return version;
        }
    }
}