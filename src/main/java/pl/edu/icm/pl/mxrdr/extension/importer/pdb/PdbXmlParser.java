package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.google.common.collect.Lists;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import io.vavr.control.Try;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Singleton
public class PdbXmlParser {

    // -------------------- LOGIC --------------------

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
            parseAllSequenceFields(node)
                    .ifPresent(addIfNotInList(result));
        }

        return Lists.newArrayList(result);
    }

    // -------------------- PRIVATE --------------------

    private Optional<ResultField> retrieveNodeValue(MxrdrMetadataField fieldName, String nodeName, Node node) {
        String nodeValue = Optional.ofNullable(node.selectSingleNode(nodeName))
                                   .map(Node::getText)
                                   .filter(value -> !value.equals("null"))
                                   .orElse("");

        return Optional.of(nodeValue)
                       .map(extractedNodeValue -> ResultField.of(fieldName.getValue(), extractedNodeValue));
    }

    private Optional<ResultField> parseAllSequenceFields(Node node) {
        return retrieveNodeValue(MxrdrMetadataField.SEQUENCE, "dimEntity.sequence", node);
    }

    private Set<ResultField> parseSingleFields(Node firstNode) {
        Set<ResultField> fields = new HashSet<>();

        retrieveNodeValue(MxrdrMetadataField.PDB_ID, "dimEntity.structureId", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.MOLECULAR_WEIGHT, "dimStructure.structureMolecularWeight", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.SPACE_GROUP, "dimStructure.spaceGroup", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.RESIDUE_COUNT, "dimStructure.residueCount", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.ATOM_SITE_COUNT, "dimStructure.atomSiteCount", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.PDB_TITLE, "dimStructure.structureTitle", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.PDB_DOI, "dimStructure.pdbDoi", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR, "dimStructure.structureAuthor", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .map(field -> field.getValue().split(","))
                .ifPresent(splitedField -> Arrays.stream(splitedField)
                                                 .forEach(field -> fields.add(ResultField.of(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR.getValue(), field))));
        retrieveNodeValue(MxrdrMetadataField.PDB_DEPOSIT_DATE, "dimStructure.depositionDate", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.PDB_RELEASE_DATE, "dimStructure.releaseDate", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.PDB_REVISION_DATE, "dimStructure.revisionDate", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.CITATION_TITLE, "dimStructure.title", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.CITATION_PUBMED_ID, "dimStructure.pubmedId", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.CITATION_AUTHOR, "dimStructure.citationAuthor", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .map(field -> field.getValue().split(","))
                .ifPresent(splitedField -> Arrays.stream(splitedField)
                                                 .forEach(field -> fields.add(ResultField.of(MxrdrMetadataField.CITATION_AUTHOR.getValue(), field))));
        retrieveNodeValue(MxrdrMetadataField.CITATION_JOURNAL, "dimStructure.journalName", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);
        retrieveNodeValue(MxrdrMetadataField.CITATION_YEAR, "dimStructure.publicationYear", firstNode)
                .filter(field -> !field.getValue().isEmpty())
                .ifPresent(fields::add);

        return fields;
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
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_ALPHA, "dimStructure.lengthOfUnitCellLatticeAlpha", firstNode)
                .ifPresent(unitCellChildren::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_BETA, "dimStructure.lengthOfUnitCellLatticeBeta", firstNode)
                .ifPresent(unitCellChildren::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_GAMMA, "dimStructure.lengthOfUnitCellLatticeGamma", firstNode)
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

        retrieveNodeValue(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH, "dimStructure.resolution", firstNode).ifPresent(overallChildren::add);
        ;

        return ResultField.of(MxrdrMetadataField.OVERALL.getValue(), overallChildren.toArray(new ResultField[0]));
    }

    private Consumer<ResultField> addIfNotInList(List<ResultField> result) {
        return field -> {
            if (result.stream().noneMatch(addedField -> addedField.getValue().equals(field.getValue()))) {
                result.add(field);
            }
        };
    }
}
