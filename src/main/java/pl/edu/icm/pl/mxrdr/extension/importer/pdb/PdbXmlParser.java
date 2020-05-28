package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.google.common.collect.Lists;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.SymmetryStructureMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.pojo.PdbDataset;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.pojo.Record;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Singleton
public class PdbXmlParser {

    private static final Logger logger = LoggerFactory.getLogger(PdbXmlParser.class);

    // -------------------- LOGIC --------------------

    /**
     * Parses the pdb xml to our metadata model and science team demands.
     */
    List<ResultField> parse(PdbDataset pdbDataset) {

        if (pdbDataset.getRecords().isEmpty()) {
            throw new IllegalStateException("Pdb xml didn't have any records");
        }

        Record firstRecord = pdbDataset.getRecords().get(0);

        List<ResultField> result = new ArrayList<>(parseSingleFields(firstRecord));
        result.addAll(parseFamilyFields(pdbDataset.getRecords()));

        return Lists.newArrayList(result);
    }

    // -------------------- PRIVATE --------------------

    private Optional<ResultField> retrieveValueSafely(MxrdrMetadataField fieldName, String value) {
        Optional<String> nodeValue = Optional.ofNullable(value)
                                             .filter(val -> !val.equals("null"));

        return nodeValue.map(extractedNodeValue -> ResultField.of(fieldName.getValue(), extractedNodeValue));
    }

    private Optional<String> retrieveValueSafely(String value) {
        return Optional.ofNullable(value)
                       .filter(val -> !val.equals("null"));
    }

    private ResultField parseAllSequenceFields(Record record) {
        List<ResultField> sequences = new ArrayList<>();

        retrieveValueSafely(MxrdrMetadataField.ENTITY_SEQUENCE, record.getSequence()).ifPresent(sequences::add);
        retrieveValueSafely(MxrdrMetadataField.ENTITY_ID, record.getChainId()).ifPresent(sequences::add);

        return ResultField.of(MxrdrMetadataField.ENTITY.getValue(), sequences.toArray(new ResultField[0]));
    }

    private Set<ResultField> parseSingleFields(Record record) {
        Set<ResultField> fields = new HashSet<>();

        retrieveValueSafely(MxrdrMetadataField.PDB_ID, record.getStructureId())
                .ifPresent(fields::add);
        retrieveValueSafely(MxrdrMetadataField.MOLECULAR_WEIGHT, record.getStructureMolecularWeight())
                .ifPresent(fields::add);
        this.retrieveValueSafely(record.getSpaceGroup())
            .ifPresent(nodeValue -> fields.add(ResultField.of(MxrdrMetadataField.SPACE_GROUP.getValue(),
                                                                  ResultField.ofValue(SymmetryStructureMapper.map(nodeValue)))));
        retrieveValueSafely(MxrdrMetadataField.RESIDUE_COUNT, record.getResidueCount())
                .ifPresent(fields::add);
        retrieveValueSafely(MxrdrMetadataField.ATOM_SITE_COUNT, record.getAtomSiteCount())
                .ifPresent(fields::add);
        retrieveValueSafely(MxrdrMetadataField.PDB_TITLE, record.getStructureTitle())
                .ifPresent(fields::add);
        retrieveValueSafely(MxrdrMetadataField.PDB_DOI, record.getPdbDoi())
                .ifPresent(fields::add);
        retrieveValueSafely(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR, record.getStructureAuthor())
                .map(field -> field.getValue().split("#"))
                .ifPresent(splitedField -> Arrays.asList(splitedField)
                                                 .forEach(field -> fields.add(ResultField.of(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR
                                                                                                     .getValue(), field))));
        retrieveValueSafely(MxrdrMetadataField.PDB_DEPOSIT_DATE, record.getDepositionDate())
                .ifPresent(fields::add);
        retrieveValueSafely(MxrdrMetadataField.PDB_RELEASE_DATE, record.getReleaseDate())
                .ifPresent(fields::add);
        retrieveValueSafely(MxrdrMetadataField.PDB_REVISION_DATE, record.getRevisionDate())
                .ifPresent(fields::add);
        retrieveValueSafely(MxrdrMetadataField.CITATION_TITLE, record.getTitle())
                .ifPresent(fields::add);
        retrieveValueSafely(MxrdrMetadataField.CITATION_PUBMED_ID, record.getPubmedId())
                .ifPresent(fields::add);
        retrieveValueSafely(MxrdrMetadataField.CITATION_AUTHOR, record.getCitationAuthor())
                .map(this::splitByEverySecondComma)
                .ifPresent(splitedField -> splitedField
                        .forEach(field -> fields.add(ResultField.of(MxrdrMetadataField.CITATION_AUTHOR
                                                                            .getValue(), field))));
        retrieveValueSafely(MxrdrMetadataField.CITATION_JOURNAL, record.getJournalName())
                .ifPresent(fields::add);
        retrieveValueSafely(MxrdrMetadataField.CITATION_YEAR, record.getPublicationYear())
                .ifPresent(fields::add);

        return fields;
    }

    private List<String> splitByEverySecondComma(ResultField field) {
        List<String> data = new ArrayList<>();

        Pattern pattern = Pattern.compile("[^,]+,[^,]+");
        Matcher matcher = pattern.matcher(field.getValue());

        while (matcher.find()) {

            Try.of(matcher::group)
               .onSuccess(value -> data.add(value.trim()))
               .onFailure(throwable -> logger.error("There was a problem with splitting " + field.getName() + " field", throwable));
        }

        return data;
    }

    private Set<ResultField> parseFamilyFields(List<Record> records) {
        Set<ResultField> fields = new HashSet<>();
        Record firstRecord = records.get(0);

        Optional.of(parseOveralls(firstRecord))
                .filter(field -> !field.getChildren().isEmpty())
                .ifPresent(fields::add);

        Optional.of(parseDataCollections(firstRecord))
                .filter(field -> !field.getChildren().isEmpty())
                .ifPresent(fields::add);

        Optional.of(parseUnitCells(firstRecord))
                .filter(field -> !field.getChildren().isEmpty())
                .ifPresent(fields::add);

        records.forEach(node -> Optional.of(parseAllSequenceFields(node))
                                            .filter(field -> !field.getChildren().isEmpty())
                                            .ifPresent(fields::add));

        return fields;
    }

    private ResultField parseUnitCells(Record firstRecord) {
        List<ResultField> unitCellChildren = new ArrayList<>();

        retrieveValueSafely(MxrdrMetadataField.UNIT_CELL_PARAMETER_A, firstRecord.getLengthOfUnitCellLatticeA())
                .ifPresent(unitCellChildren::add);
        retrieveValueSafely(MxrdrMetadataField.UNIT_CELL_PARAMETER_B, firstRecord.getLengthOfUnitCellLatticeB())
                .ifPresent(unitCellChildren::add);
        retrieveValueSafely(MxrdrMetadataField.UNIT_CELL_PARAMETER_C, firstRecord.getLengthOfUnitCellLatticeC())
                .ifPresent(unitCellChildren::add);
        retrieveValueSafely(MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA, firstRecord.getUnitCellAngleAlpha())
                .ifPresent(unitCellChildren::add);
        retrieveValueSafely(MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA, firstRecord.getUnitCellAngleBeta())
                .ifPresent(unitCellChildren::add);
        retrieveValueSafely(MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA, firstRecord.getUnitCellAngleGamma())
                .ifPresent(unitCellChildren::add);

        return ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETERS.getValue(), unitCellChildren.toArray(new ResultField[0]));
    }

    private ResultField parseDataCollections(Record firstRecord) {
        List<ResultField> dataCollectionChildren = new ArrayList<>();

        retrieveValueSafely(MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE, firstRecord.getCollectionTemperature())
                .ifPresent(dataCollectionChildren::add);

        return ResultField.of(MxrdrMetadataField.DATA_COLLECTION.getValue(), dataCollectionChildren.toArray(new ResultField[0]));
    }

    private ResultField parseOveralls(Record firstRecord) {
        List<ResultField> overallChildren = new ArrayList<>();

        retrieveValueSafely(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH, firstRecord.getResolution())
                .ifPresent(overallChildren::add);

        return ResultField.of(MxrdrMetadataField.OVERALL.getValue(), overallChildren.toArray(new ResultField[0]));
    }
}
