package pl.edu.icm.pl.mxrdr.extension.importer;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class CbfImporterTest {

    private final CbfImporter cbfImporter = new CbfImporter(new ImporterRegistry());

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
        assertEquals("dataCollectionOscillationStepSize", parentOfMultiple.getChildren().get(0).getName());
        assertEquals("0.1000", parentOfMultiple.getChildren().get(0).getValue());
        assertEquals("dataCollectionStartingAngle", parentOfMultiple.getChildren().get(1).getName());
        assertEquals("303.5800", parentOfMultiple.getChildren().get(1).getValue());
        assertEquals("dataCollectionOrgX", parentOfMultiple.getChildren().get(2).getName());
        assertEquals("1221.62", parentOfMultiple.getChildren().get(2).getValue());
        assertEquals("dataCollectionOrgY", parentOfMultiple.getChildren().get(3).getName());
        assertEquals("1257.86", parentOfMultiple.getChildren().get(3).getValue());
        assertEquals("dataCollectionDetectorDistance", parentOfMultiple.getChildren().get(4).getName());
        assertEquals("0.19050", parentOfMultiple.getChildren().get(4).getValue());
        assertEquals("dataCollectionWavelength", parentOfMultiple.getChildren().get(5).getName());
        assertEquals("0.72932", parentOfMultiple.getChildren().get(5).getValue());
        assertEquals("dataCollectionDetectorOverload", parentOfMultiple.getChildren().get(6).getName());
        assertEquals("152194", parentOfMultiple.getChildren().get(6).getValue());
        assertEquals("dataCollectionDetectorThickness", parentOfMultiple.getChildren().get(7).getName());
        assertEquals("0.000450", parentOfMultiple.getChildren().get(7).getValue());
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
}