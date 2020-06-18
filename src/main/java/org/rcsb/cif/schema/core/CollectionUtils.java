package org.rcsb.cif.schema.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectionUtils {
    public static <K,V> Map<K,V> mapOf(K key, V value) {
        HashMap<K,V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public static <T> List<T> listOf(T... items) {
        return Arrays.stream(items)
                .collect(Collectors.toList());
    }
}
