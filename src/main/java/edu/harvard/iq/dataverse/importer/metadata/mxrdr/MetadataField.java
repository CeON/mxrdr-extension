package edu.harvard.iq.dataverse.importer.metadata.mxrdr;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class MetadataField {

    private final String name;
    private UnaryOperator<String> fieldFilter;
    private final List<MetadataField> childFields = new ArrayList<>();

    // -------------------- CONSTRUCTORS --------------------

    public MetadataField(String name, UnaryOperator<String> fieldFilter) {
        this.name = name;
        this.fieldFilter = fieldFilter;
    }

    public MetadataField(String name) {
        this.name = name;
    }

    // -------------------- GETTERS --------------------

    public String getName() {
        return name;
    }

    public UnaryOperator<String> getFieldFilter() {
        return fieldFilter;
    }

    public List<MetadataField> getChildFields() {
        return childFields;
    }
}
