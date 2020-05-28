package pl.edu.icm.pl.mxrdr.extension.importer.pdb.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "dataset")
public class PdbDataset {

    @JacksonXmlProperty(localName = "record")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Record> records = new ArrayList<>();

    // -------------------- GETTERS --------------------

    public List<Record> getRecords() {
        return records;
    }
}
