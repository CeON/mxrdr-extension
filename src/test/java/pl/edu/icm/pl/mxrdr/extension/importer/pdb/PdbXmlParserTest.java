package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.apache.xerces.parsers.DOMParser;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.dom.DOMDocument;
import org.dom4j.io.DOMReader;
import org.junit.jupiter.api.Assertions;
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

    private PdbXmlParser pdbXmlParser;

    // -------------------- TESTS --------------------

    @Test
    public void parse() throws URISyntaxException, IOException {

        //given
        File cbfFile = new File(getClass().getClassLoader().getResource("xml/pdbFromApi.xml").toURI());
        String xmlContent = new String(Files.readAllBytes(cbfFile.toPath()));

        //when
        List<ResultField> resultFields = pdbXmlParser.parse(xmlContent);

        //then
        //resultFields.stream().filter(field -> field.getName().equals() && field.getValue().equals()).findFirst()

    }

    // -------------------- PRIVATE --------------------

}