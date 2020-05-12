package edu.harvard.iq.dataverse.importer.metadata.mxrdr;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CbfImporterIT {

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
        //given

        //when

        //then
    }

    @Test
    public void getImporterData() {
        //given & when
        ImporterData importerData = cbfImporter.getImporterData();

        //then
        assertAll(() -> assertEquals(1, importerData.getImporterFormSchema().size()),
                  () -> assertEquals(CbfImporterForm.CBF_FILE, importerData.getImporterFormSchema().get(0).fieldKey),
                  () -> assertEquals(ImporterFieldType.UPLOAD_TEMP_FILE, importerData.getImporterFormSchema().get(0).fieldType));

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
    public void validate() {
        //given

        //when

        //then
    }
}