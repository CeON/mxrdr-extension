package pl.edu.icm.pl.mxrdr.extension.importer;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.cbf.CbfFileParser;
import pl.edu.icm.pl.mxrdr.extension.importer.cbf.CbfImporter;
import pl.edu.icm.pl.mxrdr.extension.importer.cbf.CbfImporterForm;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class CbfImporterTest {

    private final CbfImporter cbfImporter = new CbfImporter(new ImporterRegistry(), new CbfFileParser());

    // -------------------- TESTS --------------------

    @Test
    public void getMetadataBlockName() {
        //given & when
        String metadataBlockName = cbfImporter.getMetadataBlockName();

        //then
        assertEquals("macromolecularcrystallography", metadataBlockName);
    }

    @Test
    public void getBundle() {
        //given & when
        ResourceBundle bundle = cbfImporter.getBundle(Locale.ENGLISH);

        //then
        assertEquals("CbfImporterBundle", bundle.getBaseBundleName());
    }

    @Test
    public void getImporterData() {
        //given & when
        ImporterData importerData = cbfImporter.getImporterData();

        //then
        assertAll(() -> assertEquals(2, importerData.getImporterFormSchema().size()),
                  () -> assertEquals(CbfImporterForm.CBF_FILE, importerData.getImporterFormSchema().get(1).fieldKey),
                  () -> assertEquals(ImporterFieldType.UPLOAD_TEMP_FILE, importerData.getImporterFormSchema().get(1).fieldType));

    }

    @Test
    public void fetchMetadata_forCbfData() throws URISyntaxException {
        //given
        File cbfFile = new File(getClass().getClassLoader().getResource("cbf/testFile.cbf").toURI());
        Map<ImporterFieldKey, Object> cbfData = new HashMap<>();
        cbfData.put(CbfImporterForm.CBF_FILE, cbfFile);

        //when
        List<ResultField> resultFields = cbfImporter.fetchMetadata(cbfData);

        //then
        assertEquals(2, resultFields.size());
        assertEquals("detectorType", resultFields.get(0).getName());
        assertEquals("PILATUS 6M-F", resultFields.get(0).getValue());

        ResultField parentOfMultiple = resultFields.get(1);
        assertTrue(containsName("dataCollectionOscillationStepSize", parentOfMultiple));
        assertTrue(containsValue("0.1000", parentOfMultiple));
        assertTrue(containsName("dataCollectionStartingAngle", parentOfMultiple));
        assertTrue(containsValue("303.5800", parentOfMultiple));
        assertTrue(containsName("dataCollectionOrgX", parentOfMultiple));
        assertTrue(containsValue("1221.62", parentOfMultiple));
        assertTrue(containsName("dataCollectionOrgY", parentOfMultiple));
        assertTrue(containsValue("1257.86", parentOfMultiple));
        assertTrue(containsName("dataCollectionDetectorDistance", parentOfMultiple));
        assertTrue(containsValue("190.5", parentOfMultiple));
        assertTrue(containsName("dataCollectionWavelength", parentOfMultiple));
        assertTrue(containsValue("0.72932", parentOfMultiple));
        assertTrue(containsName("dataCollectionDetectorOverload", parentOfMultiple));
        assertTrue(containsValue("152194", parentOfMultiple));
        assertTrue(containsName("dataCollectionDetectorThickness", parentOfMultiple));
        assertTrue(containsValue("0.45", parentOfMultiple));
    }

    @Test
    public void validate_withCorrectFile() throws URISyntaxException {
        //given
        File cbfFile = new File(getClass().getClassLoader().getResource("cbf/testFile.cbf").toURI());
        Map<ImporterFieldKey, Object> cbfData = new HashMap<>();
        cbfData.put(CbfImporterForm.CBF_FILE, cbfFile);

        //when
        Map<ImporterFieldKey, String> validate = cbfImporter.validate(cbfData);

        //then
        assertTrue(validate.isEmpty());
    }

    @Test
    public void validate_withFileMissing() throws URISyntaxException {
        //given
        File txtFile = new File(getClass().getClassLoader().getResource("txt/blank.txt").toURI());
        Map<ImporterFieldKey, Object> txtData = new HashMap<>();
        txtData.put(CbfImporterForm.CBF_FILE, txtFile);

        //when
        Map<ImporterFieldKey, String> validate = cbfImporter.validate(txtData);

        //then
        assertEquals(1, validate.size());
        assertEquals(ResourceBundle.getBundle("CbfImporterBundle",Locale.ENGLISH).getString("cbf.error.wrongFile"),
                     validate.get(CbfImporterForm.CBF_FILE));
    }

    // -------------------- PRIVATE --------------------

    private boolean containsName(String name, ResultField parent) {
        return parent.getChildren().stream().anyMatch(field -> field.getName().equals(name));
    }

    private boolean containsValue(String value, ResultField parent) {
        return parent.getChildren().stream().anyMatch(field -> field.getValue().equals(value));
    }
}