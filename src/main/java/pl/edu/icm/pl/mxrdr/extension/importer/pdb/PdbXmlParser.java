package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.google.common.collect.Lists;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import io.vavr.control.Try;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // -------------------- LOGIC --------------------

    /**
     * Parses the pdb xml to our metadata model and science team demands.
     */
    List<ResultField> parse(String pdbXml) {

        Document document = Try.of(() -> DocumentHelper.parseText(pdbXml))
                               .getOrElseThrow(throwable -> new IllegalStateException("There was a problem with parsing pdb xml",
                                                                                      throwable));

        List<Node> nodes = document.selectNodes("/dataset/record");
        Node firstNode = Try.of(() -> nodes.get(0)).
                getOrElseThrow(throwable -> new IllegalStateException("Pdb xml didn't have any nodes", throwable));

        List<ResultField> result = new ArrayList<>(parseSingleFields(firstNode));
        result.addAll(parseFamilyFields(firstNode));

        for (Node node : nodes) {
            result.add(parseAllSequenceFields(node));
        }

        return Lists.newArrayList(result);
    }

    // -------------------- PRIVATE --------------------

    private Optional<ResultField> retrieveNodeValue(MxrdrMetadataField fieldName, String nodeName, Node node) {
        Optional<String> nodeValue = Optional.ofNullable(node.selectSingleNode(nodeName))
                                             .map(Node::getText)
                                             .filter(value -> !value.equals("null"));

        return nodeValue.map(extractedNodeValue -> ResultField.of(fieldName.getValue(), extractedNodeValue));
    }

    private ResultField parseAllSequenceFields(Node node) {
        List<ResultField> sequences = new ArrayList<>();

        retrieveNodeValue(MxrdrMetadataField.ENTITY_SEQUENCE, "dimEntity.sequence", node).ifPresent(sequences::add);
        retrieveNodeValue(MxrdrMetadataField.ENTITY_ID, "dimEntity.chainId", node).ifPresent(sequences::add);

        return ResultField.of(MxrdrMetadataField.ENTITY.getValue(), sequences.toArray(new ResultField[0]));
    }

    private Set<ResultField> parseSingleFields(Node firstNode) {
        Set<ResultField> fields = new HashSet<>();

        retrieveNodeValue(MxrdrMetadataField.PDB_ID, "dimEntity.structureId", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.MOLECULAR_WEIGHT, "dimStructure.structureMolecularWeight", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.SPACE_GROUP, "dimStructure.spaceGroup", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.RESIDUE_COUNT, "dimStructure.residueCount", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.ATOM_SITE_COUNT, "dimStructure.atomSiteCount", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.PDB_TITLE, "dimStructure.structureTitle", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.PDB_DOI, "dimStructure.pdbDoi", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR, "dimStructure.structureAuthor", firstNode)
                .map(field -> field.getValue().split("#"))
                .ifPresent(splitedField -> Arrays.asList(splitedField)
                                                 .forEach(field -> fields.add(ResultField.of(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR
                                                                                                     .getValue(), field))));
        retrieveNodeValue(MxrdrMetadataField.PDB_DEPOSIT_DATE, "dimStructure.depositionDate", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.PDB_RELEASE_DATE, "dimStructure.releaseDate", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.PDB_REVISION_DATE, "dimStructure.revisionDate", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.CITATION_TITLE, "dimStructure.title", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.CITATION_PUBMED_ID, "dimStructure.pubmedId", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.CITATION_AUTHOR, "dimStructure.citationAuthor", firstNode)
                .map(this::splitByEverySecondComma)
                .ifPresent(splitedField -> splitedField
                                                 .forEach(field -> fields.add(ResultField.of(MxrdrMetadataField.CITATION_AUTHOR
                                                                                                     .getValue(), field))));
        retrieveNodeValue(MxrdrMetadataField.CITATION_JOURNAL, "dimStructure.journalName", firstNode)
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.CITATION_YEAR, "dimStructure.publicationYear", firstNode)
                .ifPresent(fields::add);

        return fields;
    }

    private List<String> splitByEverySecondComma(ResultField field) {
        List<String> data = new ArrayList<>();

        Pattern pattern = Pattern.compile("[^,]+,[^,]+");
        Matcher matcher = pattern.matcher(field.getValue());

        while (matcher.find()){

            Try.of(matcher::group)
            .onSuccess(value -> data.add(value.trim()))
            .onFailure(throwable -> logger.error("There was a problem with splitting "+ field.getName() + " field", throwable));
        }

        return data;
    }

    private Set<ResultField> parseFamilyFields(Node firstNode) {
        Set<ResultField> fields = new HashSet<>();

        Optional.of(parseOveralls(firstNode))
                .filter(field -> !field.getChildren().isEmpty())
                .ifPresent(fields::add);

        Optional.of(parseDataCollections(firstNode))
                .filter(field -> !field.getChildren().isEmpty())
                .ifPresent(fields::add);

        Optional.of(parseUnitCells(firstNode))
                .filter(field -> !field.getChildren().isEmpty())
                .ifPresent(fields::add);

        return fields;
    }

    private ResultField parseUnitCells(Node firstNode) {
        List<ResultField> unitCellChildren = new ArrayList<>();

        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_A, "dimStructure.lengthOfUnitCellLatticeA", firstNode)
                .ifPresent(unitCellChildren::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_B, "dimStructure.lengthOfUnitCellLatticeB", firstNode)
                .ifPresent(unitCellChildren::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_C, "dimStructure.lengthOfUnitCellLatticeC", firstNode)
                .ifPresent(unitCellChildren::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_ALPHA, "dimStructure.unitCellAngleAlpha", firstNode)
                .ifPresent(unitCellChildren::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_BETA, "dimStructure.unitCellAngleBeta", firstNode)
                .ifPresent(unitCellChildren::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_GAMMA, "dimStructure.unitCellAngleGamma", firstNode)
                .ifPresent(unitCellChildren::add);

        return ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETERS.getValue(), unitCellChildren.toArray(new ResultField[0]));
    }

    private ResultField parseDataCollections(Node firstNode) {
        List<ResultField> dataCollectionChildren = new ArrayList<>();

        retrieveNodeValue(MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE, "dimStructure.collectionTemperature", firstNode)
                .ifPresent(dataCollectionChildren::add);

        return ResultField.of(MxrdrMetadataField.DATA_COLLECTION.getValue(), dataCollectionChildren.toArray(new ResultField[0]));
    }

    private ResultField parseOveralls(Node firstNode) {
        List<ResultField> overallChildren = new ArrayList<>();

        retrieveNodeValue(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH, "dimStructure.resolution", firstNode)
                .ifPresent(overallChildren::add);

        return ResultField.of(MxrdrMetadataField.OVERALL.getValue(), overallChildren.toArray(new ResultField[0]));
    }
}
