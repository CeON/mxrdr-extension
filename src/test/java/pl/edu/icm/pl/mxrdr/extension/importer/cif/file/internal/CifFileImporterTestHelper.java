package pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CifFileImporterTestHelper {
    @SafeVarargs
    public static <T> List<T> collectToList(T... values) {
        return Arrays.stream(values)
                .collect(Collectors.toList());
    }
}
