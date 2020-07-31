package pl.edu.icm.pl.mxrdr.extension.importer.xds;

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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class XdsImporterTest {

    private final XdsImporter xdsImporter = new XdsImporter(new ImporterRegistry());

    // -------------------- TESTS --------------------

    @Test
    public void getMetadataBlockName() {
        // given & when
        String metadataBlockName = xdsImporter.getMetadataBlockName();

        // then
        assertEquals("macromolecularcrystallography", metadataBlockName);
    }

    @Test
    public void getBundle() {
        // given & when
        ResourceBundle bundle = xdsImporter.getBundle(Locale.ENGLISH);

        // then
        assertEquals("XdsImporterBundle", bundle.getBaseBundleName());
    }

    @Test
    public void getImporterData() {
        // given & when
        ImporterData importerData = xdsImporter.getImporterData();

        // then
        assertAll(() -> assertEquals(2, importerData.getImporterFormSchema().size()),
                () -> assertEquals(XdsImporterForm.XDS_FILE, importerData.getImporterFormSchema().get(1).fieldKey),
                () -> assertEquals(ImporterFieldType.UPLOAD_TEMP_FILE,
                        importerData.getImporterFormSchema().get(1).fieldType));

    }

    @Test
    public void fetchMetadata_forXdsOutputData() throws URISyntaxException {
        // given
        File xdsFile = new File(getClass().getClassLoader().getResource("xds/CORRECT.LP").toURI());
        Map<ImporterFieldKey, Object> xdsData = new HashMap<>();
        xdsData.put(XdsImporterForm.XDS_FILE, xdsFile);

        // when
        List<ResultField> resultFields = xdsImporter.fetchMetadata(xdsData);

        // then
        assertEquals(6, resultFields.size());
        assertEquals("unitCellParameters", resultFields.get(0).getName());
        assertEquals("dataCollection", resultFields.get(1).getName());
        assertEquals("overall", resultFields.get(2).getName());
        assertEquals("hrs", resultFields.get(3).getName());
        assertEquals("spaceGroup", resultFields.get(4).getName());
        assertEquals("16. P222", resultFields.get(4).getChildren().get(0).getValue());
        assertEquals("processingSoftware", resultFields.get(5).getName());
        assertEquals("XDS", resultFields.get(5).getChildren().get(0).getValue());

        assertFalse(resultFields.get(0).getChildren().isEmpty());

        ResultField parentOfUnitCell = resultFields.get(0);
        assertTrue(containsNameAndValue("unitCellParameterA", "78.45", parentOfUnitCell));
        assertTrue(containsNameAndValue("unitCellParameterB", "91.20", parentOfUnitCell));
        assertTrue(containsNameAndValue("unitCellParameterC", "116.82", parentOfUnitCell));
        assertTrue(containsNameAndValue("unitCellParameterAlpha", "90.000", parentOfUnitCell));
        assertTrue(containsNameAndValue("unitCellParameterBeta", "90.000", parentOfUnitCell));
        assertTrue(containsNameAndValue("unitCellParameterGamma", "90.000", parentOfUnitCell));

        ResultField parentOfDataCollection = resultFields.get(1);
        assertTrue(containsNameAndValue("dataCollectionOscillationStepSize", "0.200000", parentOfDataCollection));
        assertTrue(containsNameAndValue("dataCollectionStartingAngle", "1.010", parentOfDataCollection));
        assertTrue(containsNameAndValue("dataCollectionOrgX", "1221.81", parentOfDataCollection));
        assertTrue(containsNameAndValue("dataCollectionOrgY", "1257.43", parentOfDataCollection));
        assertTrue(containsNameAndValue("dataCollectionDetectorDistance", "216.250", parentOfDataCollection));
        assertTrue(containsNameAndValue("dataCollectionWavelength", "1.278650", parentOfDataCollection));
        assertTrue(containsNameAndValue("dataCollectionDetectorOverload", "1048500", parentOfDataCollection));
        assertTrue(containsNameAndValue("dataCollectionDetectorThickness", "0.450000", parentOfDataCollection));
        assertTrue(containsNameAndValue("dataCollectionStartingAngle", "1.010", parentOfDataCollection));

        ResultField parentOfOverall = resultFields.get(2);
        assertTrue(containsNameAndValue("overallCompleteness", "99.5", parentOfOverall));
        assertTrue(containsNameAndValue("overallISigma", "9.09", parentOfOverall));
        assertTrue(containsNameAndValue("overallCc", "99.7", parentOfOverall));
        assertTrue(containsNameAndValue("overallRMerge", "14.8", parentOfOverall));
        assertTrue(containsNameAndValue("overallRMeas", "16.0", parentOfOverall));
        assertTrue(containsNameAndValue("overallDataResolutionRangeLow", "49.186", parentOfOverall));
        assertTrue(containsNameAndValue("overallDataResolutionRangeHigh", "2.11", parentOfOverall));
        assertTrue(containsNameAndValue("overallNumberOfObservedReflections", "632938", parentOfOverall));
        assertTrue(containsNameAndValue("overallNumberOfUniqueReflections", "93289", parentOfOverall));
        assertTrue(containsNameAndValue("overallNumberOfPossibleReflections", "93791", parentOfOverall));
        assertTrue(containsNameAndValue("overallAnomalousCorrelation", "5", parentOfOverall));
        assertTrue(containsNameAndValue("overallAnomalousSignal", "0.755", parentOfOverall));

        ResultField parentOfHrs = resultFields.get(3);
        assertTrue(containsNameAndValue("hrsCompleteness", "97.1", parentOfHrs));
        assertTrue(containsNameAndValue("hrsISigma", "1.84", parentOfHrs));
        assertTrue(containsNameAndValue("hrsCc", "80.2", parentOfHrs));
        assertTrue(containsNameAndValue("hrsRMerge", "97.2", parentOfHrs));
        assertTrue(containsNameAndValue("hrsRMeas", "106.1", parentOfHrs));
        assertTrue(containsNameAndValue("hrsDataResolutionRangeLow", "2.23", parentOfHrs));
        assertTrue(containsNameAndValue("hrsDataResolutionRangeHigh", "2.11", parentOfHrs));
        assertTrue(containsNameAndValue("hrsNumberOfObservedReflections", "92851", parentOfHrs));
        assertTrue(containsNameAndValue("hrsNumberOfUniqueReflections", "14747", parentOfHrs));
        assertTrue(containsNameAndValue("hrsNumberOfPossibleReflections", "15187", parentOfHrs));
        assertTrue(containsNameAndValue("hrsAnomalousCorrelation", "-3", parentOfHrs));
        assertTrue(containsNameAndValue("hrsAnomalousSignal", "0.639", parentOfHrs));
    }

    @Test
    public void validate_withCorrectFile() throws URISyntaxException {
        // given
        File xdsFile = new File(getClass().getClassLoader().getResource("xds/CORRECT.LP").toURI());
        Map<ImporterFieldKey, Object> xdsData = new HashMap<>();
        xdsData.put(XdsImporterForm.XDS_FILE, xdsFile);

        // when
        Map<ImporterFieldKey, String> validate = xdsImporter.validate(xdsData);

        // then
        assertTrue(validate.isEmpty());
    }

    @Test
    public void validate_withFileMissing() throws URISyntaxException {
        // given
        File txtFile = new File(getClass().getClassLoader().getResource("txt/blank.txt").toURI());
        Map<ImporterFieldKey, Object> txtData = new HashMap<>();
        txtData.put(XdsImporterForm.XDS_FILE, txtFile);

        // when
        Map<ImporterFieldKey, String> validate = xdsImporter.validate(txtData);

        // then
        assertEquals(1, validate.size());
        assertEquals("xds.error.wrongFile", validate.get(XdsImporterForm.XDS_FILE));
    }

    // -------------------- PRIVATE --------------------

    private boolean containsNameAndValue(String name, String value, ResultField parent) {
        return parent.getChildren().stream()
                .anyMatch(field -> field.getName().equals(name) && field.getValue().equals(value));
    }

}