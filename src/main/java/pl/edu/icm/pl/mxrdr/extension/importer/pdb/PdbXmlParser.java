package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import io.vavr.control.Try;
import org.apache.xerces.parsers.DOMParser;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.dom.DOMDocument;
import org.dom4j.io.DOMReader;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class PdbXmlParser {

    // -------------------- LOGIC --------------------

    public List<ResultField> parse(String pdbXml) {
        List<ResultField> result = new ArrayList<>();

        Document document = Try.of(() -> DocumentHelper.parseText(pdbXml))
                .getOrElseThrow(throwable -> new IllegalStateException("There was a problem with parsing pdb xml",
                                                                       throwable));

        List<Node> nodes = document.selectNodes("/dataset/record");
        Node firstNode = Try.of(() -> nodes.get(0)).
                getOrElseThrow(throwable -> new IllegalStateException("Pdb xml didn't have any nodes", throwable));

        retrieveNodeValue(MxrdrMetadataField.PDB_ID, "dimEntity.structureId", firstNode).ifPresent(result::add);
        retrieveNodeValue(MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE,
                          "dimStructure.collectionTemperature",
                          firstNode)
                .ifPresent(field -> {
                    ResultField parent = ResultField.of(MxrdrMetadataField.DATA_COLLECTION.getValue(), field);
                    result.add(parent);
                });
        retrieveNodeValue(MxrdrMetadataField.MOLECULAR_WEIGHT,"dimStructure.structureMolecularWeight", firstNode).ifPresent(result::add);
        retrieveNodeValue(MxrdrMetadataField.SEQUENCE,"dimEntity.sequence", firstNode).ifPresent(result::add);
        retrieveNodeValue(MxrdrMetadataField.SPACE_GROUP,"dimStructure.spaceGroup", firstNode).ifPresent(result::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_A,"dimStructure.lengthOfUnitCellLatticeA", firstNode).ifPresent(result::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_B,"dimStructure.lengthOfUnitCellLatticeB", firstNode).ifPresent(result::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_C,"dimStructure.lengthOfUnitCellLatticeC", firstNode).ifPresent(result::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_ALPHA,"dimStructure.lengthOfUnitCellLatticeAlpha", firstNode).ifPresent(result::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_BETA,"dimStructure.lengthOfUnitCellLatticeBeta", firstNode).ifPresent(result::add);
        retrieveNodeValue(MxrdrMetadataField.UNIT_CELL_PARAMETERS_GAMMA,"dimStructure.lengthOfUnitCellLatticeGamma", firstNode).ifPresent(result::add);
        retrieveNodeValue("dimStructure.resolution", firstNode).ifPresent(result::add);


        return new ArrayList<>();
    }

    // -------------------- PRIVATE --------------------

    private Optional<ResultField> retrieveNodeValue(MxrdrMetadataField fieldName, String nodeName, Node node) {
        String nodeValue = Optional.ofNullable(node.selectSingleNode(nodeName))
                .map(Node::getText)
                .orElse("");

        return Optional.of(nodeValue)
                .map(extractedNodeValue -> ResultField.of(fieldName.getValue(), extractedNodeValue));
    }
}
