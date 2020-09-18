package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.Dataset;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatasetMapperTest {

    // -------------------- TESTS --------------------

    @Test
    public void parse_correctXml() throws URISyntaxException, IOException {
        //given
        File cbfFile = new File(getClass().getClassLoader().getResource("xml/pdbFromApi.xml").toURI());
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Dataset dataset = xmlMapper.readValue(cbfFile, Dataset.class);
        DatasetMapper datasetMapper = new DatasetMapper(dataset);

        //when
        List<ResultField> resultFields = datasetMapper.asResultFields();

        //then
        assertEquals("4IWR", retrieveFieldValue(MxrdrMetadataField.PDB_ID.getValue(), resultFields));
        assertEquals("68792.63", retrieveFieldValue(MxrdrMetadataField.MOLECULAR_WEIGHT.getValue(), resultFields));
        assertEquals("428", retrieveFieldValue(MxrdrMetadataField.RESIDUE_COUNT.getValue(), resultFields));
        assertEquals("4513", retrieveFieldValue(MxrdrMetadataField.ATOM_SITE_COUNT.getValue(), resultFields));
        assertEquals("428", retrieveFieldValue(MxrdrMetadataField.RESIDUE_COUNT.getValue(), resultFields));
        assertEquals("C.Esp1396I bound to a 25 base pair operator site", retrieveFieldValue(MxrdrMetadataField.PDB_TITLE
                                                                                                    .getValue(), resultFields));
        assertEquals("10.2210/pdb4iwr/pdb", retrieveFieldValue(MxrdrMetadataField.PDB_DOI.getValue(), resultFields));
        assertEquals("2013-01-24", retrieveFieldValue(MxrdrMetadataField.PDB_DEPOSIT_DATE.getValue(), resultFields));
        assertEquals("2013-09-11", retrieveFieldValue(MxrdrMetadataField.PDB_RELEASE_DATE.getValue(), resultFields));
        assertEquals("Structural analysis of DNA-protein complexes regulating the restriction-modification system Esp1396I.", retrieveFieldValue(MxrdrMetadataField.CITATION_TITLE
                                                                                                                                                         .getValue(), resultFields));
        assertEquals("23989141", retrieveFieldValue(MxrdrMetadataField.CITATION_PUBMED_ID.getValue(), resultFields));
        assertEquals("Acta Crystallogr.,Sect.F", retrieveFieldValue(MxrdrMetadataField.CITATION_JOURNAL.getValue(), resultFields));
        assertEquals("2013", retrieveFieldValue(MxrdrMetadataField.CITATION_YEAR.getValue(), resultFields));

        checkFamilyFields(resultFields);
    }

    @Test
    public void parse_correctXmlWithHashSeparatedRevisionDate() throws URISyntaxException, IOException {
        //given
        File cbfFile = new File(getClass().getClassLoader().getResource("xml/pdbFromApiHashedRevisionDate.xml").toURI());
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Dataset dataset = xmlMapper.readValue(cbfFile, Dataset.class);
        DatasetMapper datasetMapper = new DatasetMapper(dataset);

        //when
        List<ResultField> resultFields = datasetMapper.asResultFields();

        //then
        assertEquals("4IWR", retrieveFieldValue(MxrdrMetadataField.PDB_ID.getValue(), resultFields));
        assertEquals("68792.63", retrieveFieldValue(MxrdrMetadataField.MOLECULAR_WEIGHT.getValue(), resultFields));
        assertEquals("428", retrieveFieldValue(MxrdrMetadataField.RESIDUE_COUNT.getValue(), resultFields));
        assertEquals("4513", retrieveFieldValue(MxrdrMetadataField.ATOM_SITE_COUNT.getValue(), resultFields));
        assertEquals("428", retrieveFieldValue(MxrdrMetadataField.RESIDUE_COUNT.getValue(), resultFields));
        assertEquals("C.Esp1396I bound to a 25 base pair operator site", retrieveFieldValue(MxrdrMetadataField.PDB_TITLE
                                                                                                    .getValue(), resultFields));
        assertEquals("10.2210/pdb4iwr/pdb", retrieveFieldValue(MxrdrMetadataField.PDB_DOI.getValue(), resultFields));
        assertEquals("2013-01-24", retrieveFieldValue(MxrdrMetadataField.PDB_DEPOSIT_DATE.getValue(), resultFields));
        assertEquals("2013-09-11", retrieveFieldValue(MxrdrMetadataField.PDB_RELEASE_DATE.getValue(), resultFields));
        assertEquals("2019-12-18", retrieveFieldValue(MxrdrMetadataField.PDB_REVISION_DATE.getValue(), resultFields));
        assertEquals("Structural analysis of DNA-protein complexes regulating the restriction-modification system Esp1396I.", retrieveFieldValue(MxrdrMetadataField.CITATION_TITLE
                                                                                                                                                         .getValue(), resultFields));
        assertEquals("23989141", retrieveFieldValue(MxrdrMetadataField.CITATION_PUBMED_ID.getValue(), resultFields));
        assertEquals("Acta Crystallogr.,Sect.F", retrieveFieldValue(MxrdrMetadataField.CITATION_JOURNAL.getValue(), resultFields));
        assertEquals("2013", retrieveFieldValue(MxrdrMetadataField.CITATION_YEAR.getValue(), resultFields));

        checkFamilyFields(resultFields);
    }

    @Test
    public void parse_incorrectXml() {
        //given
        Dataset dataset = new Dataset();
        DatasetMapper datasetMapper = new DatasetMapper(dataset);

        //when & then
        Assertions.assertThrows(IllegalStateException.class, () -> datasetMapper.asResultFields());
    }

    // -------------------- PRIVATE --------------------

    private void checkFamilyFields(List<ResultField> resultFields) {
        ResultField unitCell = retrieveField(MxrdrMetadataField.UNIT_CELL_PARAMETERS.getValue(), resultFields);
        List<ResultField> unitCellChildren = unitCell.getChildren();

        ResultField dataCollection = retrieveField(MxrdrMetadataField.DATA_COLLECTION.getValue(), resultFields);
        List<ResultField> dataCollectionChildren = dataCollection.getChildren();

        ResultField overall = retrieveField(MxrdrMetadataField.OVERALL.getValue(), resultFields);
        List<ResultField> overallChildren = overall.getChildren();

        ResultField spaceGroup = retrieveField(MxrdrMetadataField.SPACE_GROUP.getValue(), resultFields);
        List<ResultField> spaceGroupChildren = spaceGroup.getChildren();

        List<ResultField> entities = retrieveFields(MxrdrMetadataField.ENTITY.getValue(), resultFields);
        List<ResultField> entityChildren = entities
                .stream()
                .map(ResultField::getChildren)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<String> sequences = retrieveFieldValues(MxrdrMetadataField.ENTITY_SEQUENCE.getValue(), entityChildren);
        List<String> chainIds = retrieveFieldValues(MxrdrMetadataField.ENTITY_ID.getValue(), entityChildren);
        List<String> structureAuthors = retrieveFieldValues(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR.getValue(), resultFields);
        List<String> citationAuthors = retrieveFieldValues(MxrdrMetadataField.CITATION_AUTHOR.getValue(), resultFields);

        assertUnitCellChildren(unitCellChildren);
        assertDataCollectionChildren(dataCollectionChildren);
        assertOverallChildren(overallChildren);
        assertStructureAuthors(structureAuthors);
        assertCitationAuthor(citationAuthors);
        assertEntityChildren(sequences, chainIds);
        assertSpaceGroupChildren(spaceGroupChildren);
    }

    private void assertOverallChildren(List<ResultField> overallChildren) {
        assertEquals("2.4", retrieveFieldValue(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH.getValue(), overallChildren));
    }

    private void assertSpaceGroupChildren(List<ResultField> spaceGroupChildren) {
        assertEquals("145. P3(2)", retrieveFieldValue("", spaceGroupChildren));
    }

    private void assertDataCollectionChildren(List<ResultField> dataCollectionChildren) {
        assertEquals("100.0", retrieveFieldValue(MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE.getValue(), dataCollectionChildren));
    }

    private void assertEntityChildren(List<String> sequences, List<String> chainIds) {
        assertTrue(sequences.contains("GSHMESFLLSKVSFVIKKIRLEKGMTQEDLAYKSNLDRTYISGIERNSRNLTIKSLELIMKGLEVSDVVFFEMLIKEILKHD"));
        assertTrue(sequences.contains("ATGTGACTTATAGTCCGTGTGATTA"));
        assertTrue(sequences.contains("TAATCACACGGACTATAAGTCACAT"));
        assertTrue(chainIds.contains("A"));
        assertTrue(chainIds.contains("B"));
        assertTrue(chainIds.contains("C"));
        assertTrue(chainIds.contains("D"));
        assertTrue(chainIds.contains("E"));
        assertTrue(chainIds.contains("F"));
        assertTrue(chainIds.contains("G"));
        assertTrue(chainIds.contains("H"));
    }

    private void assertUnitCellChildren(List<ResultField> unitCellChildren) {
        assertEquals("48.02", retrieveFieldValue(MxrdrMetadataField.UNIT_CELL_PARAMETER_A.getValue(), unitCellChildren));
        assertEquals("48.02", retrieveFieldValue(MxrdrMetadataField.UNIT_CELL_PARAMETER_B.getValue(), unitCellChildren));
        assertEquals("218.35", retrieveFieldValue(MxrdrMetadataField.UNIT_CELL_PARAMETER_C.getValue(), unitCellChildren));
        assertEquals("90.0", retrieveFieldValue(MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA.getValue(), unitCellChildren));
        assertEquals("90.0", retrieveFieldValue(MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA.getValue(), unitCellChildren));
        assertEquals("120.0", retrieveFieldValue(MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA.getValue(), unitCellChildren));
    }

    private void assertStructureAuthors(List<String> structureAuthors) {
        assertTrue(structureAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("Martin, R.N.A.")));
        assertTrue(structureAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("McGeehan, J.E.")));
        assertTrue(structureAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("Ball, N.J.")));
        assertTrue(structureAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("Streeter, S.D.")));
        assertTrue(structureAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("Thresh, S.-J.")));
        assertTrue(structureAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("Kneale, G.G.")));
    }

    private void assertCitationAuthor(List<String> citationAuthors) {
        assertTrue(citationAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("Martin, R.N.")));
        assertTrue(citationAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("McGeehan, J.E.")));
        assertTrue(citationAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("Ball, N.J.")));
        assertTrue(citationAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("Streeter, S.D.")));
        assertTrue(citationAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("Thresh, S.J.")));
        assertTrue(citationAuthors.stream().anyMatch(fieldValue -> fieldValue.equals("Kneale, G.G.")));
    }

    private String retrieveFieldValue(String name, List<ResultField> resultFields) {
        return resultFields.stream().filter(field -> field.getName().equals(name)).findFirst().get().getValue();
    }

    private List<String> retrieveFieldValues(String name, List<ResultField> resultFields) {
        return resultFields.stream().filter(field -> field.getName().equals(name))
                           .map(ResultField::getValue)
                           .collect(Collectors.toList());
    }

    private ResultField retrieveField(String name, List<ResultField> resultFields) {
        return resultFields.stream().filter(field -> field.getName().equals(name)).findFirst().get();
    }

    private List<ResultField> retrieveFields(String name, List<ResultField> resultFields) {
        return resultFields.stream().filter(field -> field.getName().equals(name)).collect(Collectors.toList());
    }
}