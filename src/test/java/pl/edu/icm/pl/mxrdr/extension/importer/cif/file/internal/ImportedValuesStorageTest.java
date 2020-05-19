package pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal.CifFileImporterTestHelper.collectToList;

class ImportedValuesStorageTest {

    private ImportedValuesStorage storage;

    private static final String VALUE_1 = "A";
    private static final String VALUE_2 = "B";
    private static final String VALUE_3 = "C";


    @BeforeEach
    public void setUp() {
        this.storage = new ImportedValuesStorage();
    }

    @Test
    @DisplayName("Should return item form the chosen zero-based position or return empty value if there's no item")
    public void shouldReturnItemFromTheChosenPosition() {
        // given
        MxrdrMetadataField key = MxrdrMetadataField.BEAMLINE;
        storage.add(key, collectToList(VALUE_1, VALUE_2, VALUE_3));

        // when
        List<String> readValues = IntStream.range(0, 4)
                .mapToObj(i -> storage.getFromPositionOrEmpty(key, i))
                .collect(Collectors.toList());

        // then
        assertThat(readValues, contains(VALUE_1, VALUE_2, VALUE_3, StringUtils.EMPTY));
    }

    @Test
    @DisplayName("Should return size of longest values list for the given set of keys")
    public void shouldReturnSizeOfLongestValuesList() {
        // given
        List<String> longest = collectToList(VALUE_1, VALUE_2, VALUE_2, VALUE_3, VALUE_3);
        storage.add(MxrdrMetadataField.BEAMLINE, longest);
        storage.add(MxrdrMetadataField.CITATION_AUTHOR, collectToList(VALUE_2, VALUE_1));
        storage.add(MxrdrMetadataField.DETECTOR_TYPE, Collections.emptyList());

        // when
        int maxSize = storage.maxSize(MxrdrMetadataField.BEAMLINE,
                MxrdrMetadataField.CITATION_AUTHOR,
                MxrdrMetadataField.DETECTOR_TYPE);

        // then
        assertThat(maxSize, is(longest.size()));
    }
}