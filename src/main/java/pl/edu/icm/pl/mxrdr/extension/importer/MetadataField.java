package pl.edu.icm.pl.mxrdr.extension.importer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Class designed to hold metdata fields with filter made to extract desired field.
 */
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

    /**
     * Filter function which helps extract metadata field from String line.
     * @return extracted field value
     */
    public UnaryOperator<String> getFieldFilter() {
        return fieldFilter;
    }

    public List<MetadataField> getChildFields() {
        return childFields;
    }
}
