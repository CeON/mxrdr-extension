package pl.edu.icm.pl.mxrdr.extension.importer.common;

import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class BaseDataContainer<C extends BaseDataContainer<C>> implements DataContainer {

    /**
     * Common constants for derived classes.
     */
    protected static final String PDB_DOI_TEMPLATE = "10.2210/pdb%s/pdb";
    protected static final String DATA_REDUCTION = "data reduction";

    protected Map<MxrdrMetadataField, List<String>> container = new HashMap<>();

    // -------------------- LOGIC --------------------

    @Override
    public Optional<String> get(MxrdrMetadataField key) {
        return container.getOrDefault(key, Collections.emptyList()).stream()
                .findFirst();
    }

    @Override
    public Optional<String> getIndexed(MxrdrMetadataField key, int index) {
        List<String> values = container.getOrDefault(key, Collections.emptyList());
        return !(index < 0 || index >= values.size())
                ? Optional.ofNullable(values.get(index))
                : Optional.empty();
    }

    @Override
    public List<String> getAll(MxrdrMetadataField key) {
        return container.getOrDefault(key, Collections.emptyList());
    }

    /**
     * Adds the value to the container.
     */
    protected C add(MxrdrMetadataField key, String value) {
        List<String> values = container.putIfAbsent(key, new ArrayList<>());
        values = values != null ? values : container.get(key);
        values.add(value != null ? value : EMPTY);
        return (C) this;
    }

    /**
     * Adds the value to the container.
     */
    protected C add(MxrdrMetadataField key, Optional<String> value) {
        return add(key, value.orElse(EMPTY));
    }

    /**
     * Adds the first encountered value of the given stream
     * to the container.
     */
    protected C add(MxrdrMetadataField key, Stream<String> valueList) {
        return add(key, valueList.findFirst());
    }

    /**
     * Adds all stream values to the container.
     */
    protected C addAll(MxrdrMetadataField key, Stream<String> valueList) {
        List<String> values = container.putIfAbsent(key, new ArrayList<>());
        values = values != null ? values : container.get(key);
        values.addAll(valueList.collect(Collectors.toList()));
        return (C) this;
    }
}
