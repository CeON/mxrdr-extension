package pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import io.vavr.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal.CifFileConstants.SAFE_ITEMS_DELIMITER;
import static pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal.CifFileImporterTestHelper.collectToList;
import static pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal.CifFileConstants.CONCAT_DELIMITER;


class CifFileResultCreatorTest {
    private static final String VALUE_1 = "A";
    private static final String VALUE_2 = "B";

    private ImportedValuesStorage storage;
    private CifFileResultCreator resultCreator;

    @BeforeEach
    public void setUp() {
        storage = new ImportedValuesStorage();
        resultCreator = new CifFileResultCreator(storage);
    }

    @Test
    @DisplayName("Should create single fields with concatenated values for the given set of keys")
    void createSingleFields_shouldCreateFieldsWithConcatenatedValues() {
        // given
        storage.add(MxrdrMetadataField.BEAMLINE, collectToList(VALUE_1, VALUE_1));
        storage.add(MxrdrMetadataField.DETECTOR_TYPE, collectToList(VALUE_2));

        // when
        List<ResultField> fields = resultCreator.createSingleFields(
                MxrdrMetadataField.BEAMLINE, MxrdrMetadataField.DETECTOR_TYPE);

        // then
        assertThat(fields, Matchers.hasSize(2));
        assertThat(findFirstByKey(MxrdrMetadataField.BEAMLINE, fields).getValue(), is(VALUE_1 + CONCAT_DELIMITER + VALUE_1));
        assertThat(findFirstByKey(MxrdrMetadataField.DETECTOR_TYPE, fields).getValue(), is(VALUE_2));
    }

    @Test
    @DisplayName("Should create field for each value for the given set of keys")
    void createMultipleSingleFields_shouldCreateFieldsForEachValue() {
        // given
        List<String> firstList = collectToList(VALUE_1, VALUE_1, VALUE_1);
        List<String> secondList = collectToList(VALUE_2, VALUE_2);
        storage.add(MxrdrMetadataField.BEAMLINE, firstList);
        storage.add(MxrdrMetadataField.DETECTOR_TYPE, secondList);

        // when
        List<ResultField> fields = resultCreator.createMultipleSingleFields(
                MxrdrMetadataField.BEAMLINE, MxrdrMetadataField.DETECTOR_TYPE);

        // then
        assertThat(fields, hasSize(firstList.size() + secondList.size()));
        assertThat(findValuesByKey(MxrdrMetadataField.BEAMLINE, fields), containsInAnyOrder(firstList.toArray(new String[0])));
        assertThat(findValuesByKey(MxrdrMetadataField.DETECTOR_TYPE, fields), containsInAnyOrder(secondList.toArray(new String[0])));
    }

    @Test
    @DisplayName("Should remove empty fields when creating fields for each value for the given set of keys")
    void createMultipleSingleFields_shouldRemoveEmptyFields() {
        // given
        List<String> firstList = collectToList(VALUE_1, VALUE_1, VALUE_1, StringUtils.EMPTY);
        storage.add(MxrdrMetadataField.BEAMLINE, firstList);

        // when
        List<ResultField> fields = resultCreator.createMultipleSingleFields(MxrdrMetadataField.BEAMLINE);

        // then
        int nonEmptyCount = (int) firstList.stream()
                .filter(v -> !StringUtils.EMPTY.equals(v))
                .count();
        assertThat(fields, hasSize(nonEmptyCount));
    }

    @Test
    @DisplayName("Should create as many parent fields as there are values in the longest list from the given set of keys")
    void createMultipleCompoundFieldWithSimpleFields_shouldCreateMultipleParentFields() {
        // given
        List<String> longer = collectToList(VALUE_1, VALUE_1, VALUE_1, VALUE_1);
        List<String> shorter = collectToList(VALUE_2);
        storage.add(MxrdrMetadataField.BEAMLINE, longer);
        storage.add(MxrdrMetadataField.DETECTOR_TYPE, shorter);

        // when
        List<ResultField> fields = resultCreator.createMultipleCompoundFieldWithSimpleFields(MxrdrMetadataField.DATA_COLLECTION,
                MxrdrMetadataField.BEAMLINE, MxrdrMetadataField.DETECTOR_TYPE);

        // then
        String[] expectedValues = Stream.concat(shorter.stream(), longer.stream())
                .toArray(String[]::new);
        List<String> allNonEmptyFieldValuesFromResult = fields.stream()
                .flatMap(f -> f.getChildren().stream())
                .map(ResultField::getValue)
                .collect(Collectors.toList());
        assertThat(fields, hasSize(longer.size()));
        assertThat(allNonEmptyFieldValuesFromResult, containsInAnyOrder(expectedValues));
    }

    @Test
    @DisplayName("Should create single compound field with glued values for children if they have more then one value")
    void createSingleCompoundFieldWithSimpleFields() {
        // given
        List<String> multiValued = collectToList(VALUE_1, VALUE_1, VALUE_1);
        List<String> singleValued = collectToList(VALUE_2);
        storage.add(MxrdrMetadataField.BEAMLINE, multiValued);
        storage.add(MxrdrMetadataField.DETECTOR_TYPE, singleValued);

        // when
        List<ResultField> fields = resultCreator.createSingleCompoundFieldWithSimpleFields(MxrdrMetadataField.DATA_COLLECTION,
                MxrdrMetadataField.BEAMLINE, MxrdrMetadataField.DETECTOR_TYPE);

        // then
        assertThat(fields, hasSize(1));
        ResultField beamline = findFirstByKey(MxrdrMetadataField.BEAMLINE, fields.get(0).getChildren());
        ResultField detector = findFirstByKey(MxrdrMetadataField.DETECTOR_TYPE, fields.get(0).getChildren());
        assertThat(beamline.getValue(), is(String.join(CONCAT_DELIMITER, multiValued)));
        assertThat(detector.getValue(), is(String.join(CONCAT_DELIMITER, singleValued)));
    }

    @Test
    @DisplayName("Should create vocabulary values mapped with the provided mapper excluding duplicates")
    void createVocabularyValues_shouldExcludeDuplicatedValues() {
        // given
        List<String> input = collectToList("a", "1", "qqq", "...");
        Function<String, String> allIntoOneMapper = s -> VALUE_1;
        storage.add(MxrdrMetadataField.BEAMLINE, input);

        // when
        List<ResultField> fields = resultCreator.createVocabularyValues(Tuple.of(MxrdrMetadataField.BEAMLINE, allIntoOneMapper));

        // then
        assertThat(fields, hasSize(1));
        List<ResultField> createdVocabularyValues = fields.get(0).getChildren();
        assertThat(createdVocabularyValues, hasSize(1));
        assertThat(createdVocabularyValues.get(0).getValue(), is(VALUE_1));
    }

    @Test
    @DisplayName("Should extract values from zipped lists into simple fields based on string selector")
    void createSimpleFieldsFromZippedListsBasedOnSelector_shouldExtractValues() {
        // given
        List<String> input = collectToList(VALUE_1, VALUE_1, VALUE_1, VALUE_2, VALUE_2);
        List<String> zippedList = input.stream()
                .map(v -> v + SAFE_ITEMS_DELIMITER + v)
                .collect(Collectors.toList());
        storage.add(MxrdrMetadataField.BEAMLINE, zippedList);

        // when
        List<ResultField> fields =
                resultCreator.createSimpleFieldsFromZippedListsBasedOnSelector(VALUE_1, MxrdrMetadataField.BEAMLINE);

        // then
        String[] expected = input.stream()
                .filter(VALUE_1::equals)
                .toArray(String[]::new);
        List<String> actualValues = fields.stream()
                .map(ResultField::getValue)
                .collect(Collectors.toList());
        assertThat(actualValues, contains(expected));
    }

    @Test
    @DisplayName("Should select date according to the provided selector function")
    void selectDate() {
        // given
        String mostRecentDate = "2020-06-01";
        List<String> dates = collectToList(mostRecentDate, "2019-01-01", "2017-01-01");
        storage.add(MxrdrMetadataField.PDB_DEPOSIT_DATE, dates);

        // when
        Set<ResultField> fields = resultCreator.selectDate(MxrdrMetadataField.PDB_DEPOSIT_DATE, SortedSet::last);

        // then
        assertThat(fields, hasSize(1));
        ResultField[] result = fields.stream().toArray(ResultField[]::new);
        assertThat(result[0].getValue(), is(mostRecentDate));
    }

    // -------------------- PRIVATE --------------------

    private List<String> findValuesByKey(MxrdrMetadataField key, List<ResultField> values) {
        return values.stream()
                .filter(v -> key.getValue().equals(v.getName()))
                .map(ResultField::getValue)
                .collect(Collectors.toList());
    }

    private ResultField findFirstByKey(MxrdrMetadataField key, List<ResultField> values) {
        return values.stream()
                .filter(v -> key.getValue().equals(v.getName()))
                .findFirst()
                .orElseGet(() -> ResultField.of(StringUtils.EMPTY, StringUtils.EMPTY));
    }
}