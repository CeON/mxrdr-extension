package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import org.apache.xerces.parsers.DOMParser;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.dom.DOMDocument;
import org.dom4j.io.DOMReader;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PdbXmlParserTest {

    @Test
    public void parse() throws URISyntaxException, IOException, DocumentException {
        DOMReader reader = new DOMReader();

        File cbfFile = new File(getClass().getClassLoader().getResource("xml/pdbFromApi.xml").toURI());
        String api = new PdbApiCaller().fetchPdbData("6qky");
        List<String> strings = Files.readAllLines(cbfFile.toPath());

        Document document = DocumentHelper.parseText(api);

        List<Node> nodes = document.selectNodes("/dataset/record");

        for (Node node : nodes) {
            System.out.println(node.selectSingleNode("dimEntity.structureId").getName());
        }
        System.out.println(document);
    }
}