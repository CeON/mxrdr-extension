package pl.edu.icm.pl.mxrdr.extension.importer.pdb.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@JacksonXmlRootElement(localName = "dataset")
public class Dataset {

    @JacksonXmlProperty(localName = "record")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Record> records = new ArrayList<>();

    // -------------------- GETTERS --------------------

    public boolean hasRecords() {
        return !records.isEmpty();
    }

    public Stream<Record> recordStream() {
        if (hasRecords()) {
            return records.stream();
        } else {
            throw new IllegalStateException("Dataset is empty");
        }
    }
}
