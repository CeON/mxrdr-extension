package pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.apache.commons.lang3.StringUtils;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.ProcessingSoftwareMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.SymmetryStructureMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CifFileResultCreator {

    private ImportedValuesStorage storage;

    // -------------------- CONSTRUCTORS --------------------

    public CifFileResultCreator(ImportedValuesStorage storage) {
        this.storage = storage;
    }

    // -------------------- LOGIC --------------------

    public List<ResultField> createResultFields() {
        List<ResultField> result = new ArrayList<>();
        result.addAll(createMultipleSingleFields(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR));
        result.addAll(createSingleFields(
                MxrdrMetadataField.BEAMLINE,
                MxrdrMetadataField.DETECTOR_TYPE,
                MxrdrMetadataField.MONOCHROMATOR,
                MxrdrMetadataField.PDB_ID,
                MxrdrMetadataField.PDB_TITLE,
                MxrdrMetadataField.PDB_DOI,
                MxrdrMetadataField.PDB_DEPOSIT_DATE));
        result.addAll(
                createSingleCompoundFieldWithSimpleFields(
                        MxrdrMetadataField.UNIT_CELL_PARAMETERS,
                        MxrdrMetadataField.UNIT_CELL_PARAMETER_A,
                        MxrdrMetadataField.UNIT_CELL_PARAMETER_B,
                        MxrdrMetadataField.UNIT_CELL_PARAMETER_C,
                        MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA,
                        MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA,
                        MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA));
        result.addAll(
                createSingleCompoundFieldWithSimpleFields(
                        MxrdrMetadataField.OVERALL,
                        MxrdrMetadataField.OVERALL_COMPLETENESS,
                        MxrdrMetadataField.OVERALL_I_SIGMA,
                        MxrdrMetadataField.OVERALL_R_MERGE,
                        MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_LOW,
                        MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH));
        result.addAll(
                createSingleCompoundFieldWithSimpleFields(
                        MxrdrMetadataField.HRS,
                        MxrdrMetadataField.HRS_COMPLETENESS,
                        MxrdrMetadataField.HRS_R_MERGE,
                        MxrdrMetadataField.HRS_SIGMA));
        result.addAll(createMultipleCompoundFieldWithSimpleFields(
                MxrdrMetadataField.DATA_COLLECTION,
                MxrdrMetadataField.DATA_COLLECTION_WAVE_LENGTH,
                MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE));
        result.addAll(createVocabularyValues(
                Tuple.of(MxrdrMetadataField.SPACE_GROUP, SymmetryStructureMapper::map),
                Tuple.of(MxrdrMetadataField.PROCESSING_SOFTWARE, ProcessingSoftwareMapper::map)));
        result.addAll(selectDate(MxrdrMetadataField.PDB_RELEASE_DATE, SortedSet::first));
        result.addAll(selectDate(MxrdrMetadataField.PDB_REVISION_DATE, SortedSet::last));
        result.addAll(
                createSimpleFieldsFromZippedListsBasedOnSelector(
                        "primary",
                        MxrdrMetadataField.CITATION_TITLE,
                        MxrdrMetadataField.CITATION_YEAR,
                        MxrdrMetadataField.CITATION_AUTHOR,
                        MxrdrMetadataField.CITATION_JOURNAL,
                        MxrdrMetadataField.CITATION_PUBMED_ID));

        return result;
    }

    // -------------------- PRIVATE --------------------

    /**
     * Creates single fields according to the received metadata field names. Multiple values are glued
     * together.
     */
    List<ResultField> createSingleFields(MxrdrMetadataField... metadataFields) {
        return Arrays.stream(metadataFields)
                .map(m -> ResultField.of(m.getValue(),  String.join(CifFileConstants.CONCAT_DELIMITER, storage.get(m))))
                .filter(CifFileResultCreator::isValueMeaningful)
                .collect(Collectors.toList());
    }

    /**
     * Creates single fields, but multiple values will be put into different fields and not glued.
     */
    List<ResultField> createMultipleSingleFields(MxrdrMetadataField... metadataFields) {
        return Arrays.stream(metadataFields)
                .flatMap(m -> storage.get(m).stream()
                        .map(v -> ResultField.of(m.getValue(), v)))
                .filter(CifFileResultCreator::isValueMeaningful)
                .collect(Collectors.toList());
    }

    /**
     * Creates compound fields with parent name and the given children. The number of created compound fields
     * will be equal to the number of items in the longest list among the children. The first compound field
     * will contain first values from children's lists, the second – second values and so on.
     */
    List<ResultField> createMultipleCompoundFieldWithSimpleFields(MxrdrMetadataField parent,
                                                                          MxrdrMetadataField... children) {
        int size = storage.maxSize(children);
        return IntStream.range(0, size)
                .mapToObj(i -> Arrays.stream(children)
                        .map(c -> ResultField.of(c.getValue(), storage.getFromPositionOrEmpty(c, i)))
                        .filter(CifFileResultCreator::isValueMeaningful)
                        .toArray(ResultField[]::new))
                .filter(f -> f.length > 0)
                .map(f -> ResultField.of(parent.getValue(), f))
                .collect(Collectors.toList());
    }

    /**
     * Creates compound field with the given parent name and children. The content of children fields will
     * be glued together.
     */
    List<ResultField> createSingleCompoundFieldWithSimpleFields(MxrdrMetadataField parent,
                                                                        MxrdrMetadataField... children) {
        ResultField field = ResultField.of(
                parent.getValue(),
                createSingleFields(children).toArray(new ResultField[0]));
        return isValueMeaningful(field) || !field.getChildren().isEmpty()
                ? Collections.singletonList(field)
                : Collections.emptyList();
    }

    /**
     * Creates vocabulary values mapped to internal values with the provided mapper.
     */
    @SafeVarargs
    final List<ResultField> createVocabularyValues(Tuple2<MxrdrMetadataField, Function<String, String>>... keysWithMappers) {
        return Arrays.stream(keysWithMappers)
                .map(this::createVocabularyResultField)
                .filter(r -> !r.getChildren().isEmpty())
                .collect(Collectors.toList());
    }

    private ResultField createVocabularyResultField(Tuple2<MxrdrMetadataField, Function<String, String>> fieldAndMapper) {
        ResultField[] values = storage.get(fieldAndMapper._1).stream()
                .map(fieldAndMapper._2)
                .filter(CifFileResultCreator::isValueMeaningful)
                .distinct()
                .map(ResultField::ofValue)
                .toArray(ResultField[]::new);
        return ResultField.of(fieldAndMapper._1.getValue(), values);
    }

    /**
     * Extract values from zipped lists according to the provided string selector and put into (possibly multiple)
     * simple fields.
     */
    List<ResultField> createSimpleFieldsFromZippedListsBasedOnSelector(String selector, MxrdrMetadataField... fields) {
        return Arrays.stream(fields)
                .flatMap(f -> selectFromZippedList(f, selector).stream()
                        .map(v -> ResultField.of(f.getValue(), v)))
                .filter(CifFileResultCreator::isValueMeaningful)
                .collect(Collectors.toList());
    }

    private List<String> selectFromZippedList(MxrdrMetadataField field, String selector) {
        return storage.get(field).stream()
                .map(s -> s.split(CifFileConstants.SAFE_ITEMS_DELIMITER))
                .filter(s -> s.length > 1 && selector.equals(s[0]))
                .map(s -> s[1])
                .collect(Collectors.toList());
    }

    /**
     * Assuming that internal storage for the given metadata name contains list of strings being dates,
     * convert values to LocalDates, perform selection according to the provided selector function
     * and convert result to string again. NB. all strings are supposed to be in ISO-8601 format, ie. yyyy-MM-dd
     */
    Set<ResultField> selectDate(MxrdrMetadataField field,
                                        Function<SortedSet<LocalDate>, LocalDate> selector) {
        SortedSet<LocalDate> datesSet = toLocalDateList(storage.get(field));
        return !datesSet.isEmpty()
                ? Collections.singleton(ResultField.of(field.getValue(), selector.apply(datesSet).toString()))
                : Collections.emptySet();
    }

    private SortedSet<LocalDate> toLocalDateList(List<String> stringDates) {
        return stringDates.stream()
                .filter(s -> s != null && s.matches("\\d{4,}-\\d{2}-\\d{2}"))
                .map(s -> LocalDate.parse(s, DateTimeFormatter.ISO_DATE))
                .collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
    }

    private static boolean isValueMeaningful(String value) {
        return StringUtils.isNotBlank(value);
    }

    private static boolean isValueMeaningful(ResultField r) {
        return isValueMeaningful(r.getValue());
    }
}
