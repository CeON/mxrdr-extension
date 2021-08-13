package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.EntryData;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.PolymerEntityData;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.StructureData;

import java.io.File;
import java.net.URISyntaxException;

public class StructureDataProvider {

    // -------------------- LOGIC --------------------

    StructureData createStructureData() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            StructureData structureData = new StructureData(mapper.readValue(readFile("pdb/entry.json"), EntryData.class));
            structureData.getPolymerEntities().add(mapper.readValue(readFile("pdb/polymer_1.json"), PolymerEntityData.class));
            structureData.getPolymerEntities().add(mapper.readValue(readFile("pdb/polymer_2.json"), PolymerEntityData.class));
            return structureData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // -------------------- PRIVATE --------------------

    private File readFile(String name) throws URISyntaxException {
        return new File(getClass().getClassLoader().getResource(name).toURI());
    }

}
