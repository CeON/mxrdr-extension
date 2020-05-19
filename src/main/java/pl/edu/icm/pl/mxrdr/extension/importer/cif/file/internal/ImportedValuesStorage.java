package pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal;

import org.apache.commons.lang3.StringUtils;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ImportedValuesStorage {

    public static final ImportedValuesStorage EMPTY_STORAGE = new ImportedValuesStorage();

    private Map<MxrdrMetadataField, List<String>> storage = new EnumMap<>(MxrdrMetadataField.class);

    // -------------------- LOGIC --------------------

    public ImportedValuesStorage add(MxrdrMetadataField key, String value) {
        List<String> storedValues = storage.computeIfAbsent(key, k -> new ArrayList<>());
        storedValues.add(value);
        return this;
    }

    public ImportedValuesStorage add(MxrdrMetadataField key, List<String> values) {
        List<String> storedValues = storage.computeIfAbsent(key, k -> new ArrayList<>());
        storedValues.addAll(values);
        return this;
    }

    public List<String> get(MxrdrMetadataField key) {
        return storage.getOrDefault(key, Collections.emptyList());
    }

    public String getFromPositionOrEmpty(MxrdrMetadataField key, int positionZeroBased) {
        List<String> values = get(key);
        return values.size() > positionZeroBased ? values.get(positionZeroBased) : StringUtils.EMPTY;
    }

    public int maxSize(MxrdrMetadataField... keys) {
        return Arrays.stream(keys)
                .filter(k -> storage.containsKey(k))
                .map(k -> storage.get(k).size())
                .max(Integer::compareTo)
                .orElse(0);
    }
}
