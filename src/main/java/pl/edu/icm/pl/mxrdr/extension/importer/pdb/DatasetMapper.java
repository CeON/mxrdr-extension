package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import io.vavr.control.Try;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.SymmetryStructureMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.Dataset;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

/**
 * Maps a {@link Dataset} into zero or more {@link ResultField}'s.
 */
public class DatasetMapper {

    private static final Logger log = LoggerFactory.getLogger(DatasetMapper.class);

    private static final List<RecordMapper> RECORD_MAPPERS = Arrays.asList(
            new FirstRecordMapper(MxrdrMetadataField.PDB_ID, Record::getStructureId),
            new FirstRecordMapper(MxrdrMetadataField.MOLECULAR_WEIGHT, Record::getStructureMolecularWeight),
            new FirstRecordMapper(MxrdrMetadataField.SPACE_GROUP,
                    new FirstRecordMapper(null, Record::getSpaceGroup)
                            .withValueMapper(SymmetryStructureMapper::map)),
            new FirstRecordMapper(MxrdrMetadataField.RESIDUE_COUNT, Record::getResidueCount),
            new FirstRecordMapper(MxrdrMetadataField.ATOM_SITE_COUNT, Record::getAtomSiteCount),
            new FirstRecordMapper(MxrdrMetadataField.PDB_TITLE, Record::getStructureTitle),
            new FirstRecordMapper(MxrdrMetadataField.PDB_DOI, Record::getPdbDoi),
            new FirstRecordMapper(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR, Record::getStructureAuthor)
                    .withValueMapper((field, value) -> Stream.of(value.split("#"))
                            .map(val -> ResultField.of(field.getValue(), val))),
            new FirstRecordMapper(MxrdrMetadataField.PDB_DEPOSIT_DATE, Record::getDepositionDate),
            new FirstRecordMapper(MxrdrMetadataField.PDB_RELEASE_DATE, Record::getReleaseDate),
            new FirstRecordMapper(MxrdrMetadataField.PDB_REVISION_DATE, Record::getRevisionDate),
            new FirstRecordMapper(MxrdrMetadataField.CITATION_TITLE, Record::getTitle),
            new FirstRecordMapper(MxrdrMetadataField.CITATION_PUBMED_ID, Record::getPubmedId),
            new FirstRecordMapper(MxrdrMetadataField.CITATION_AUTHOR, Record::getCitationAuthor)
                    .withValueMapper((field, value) -> splitByEverySecondComma(value).stream()
                            .map(val -> ResultField.of(field.getValue(), val))),
            new FirstRecordMapper(MxrdrMetadataField.CITATION_JOURNAL, Record::getJournalName),
            new FirstRecordMapper(MxrdrMetadataField.CITATION_YEAR, Record::getPublicationYear),
            new FirstRecordMapper(MxrdrMetadataField.OVERALL,
                    new FirstRecordMapper(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH, Record::getResolution)),
            new FirstRecordMapper(MxrdrMetadataField.DATA_COLLECTION,
                    new FirstRecordMapper(MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE, Record::getCollectionTemperature)),
            new FirstRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETERS,
                    new FirstRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_A, Record::getLengthOfUnitCellLatticeA),
                    new FirstRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_B, Record::getLengthOfUnitCellLatticeB),
                    new FirstRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_C, Record::getLengthOfUnitCellLatticeC),
                    new FirstRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA, Record::getUnitCellAngleAlpha),
                    new FirstRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA, Record::getUnitCellAngleBeta),
                    new FirstRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA, Record::getUnitCellAngleGamma)),
            new RecordMapper(MxrdrMetadataField.ENTITY,
                    new RecordMapper(MxrdrMetadataField.ENTITY_SEQUENCE, Record::getSequence),
                    new RecordMapper(MxrdrMetadataField.ENTITY_ID, Record::getChainId))
    );

    private final Dataset dataset;

    // -------------------- CONSTRUCTORS --------------------

    public DatasetMapper(Dataset dataset) {
        this.dataset = dataset;
    }

    // -------------------- LOGIC --------------------

    /**
     * Parses the pdb xml to our metadata model and science team demands.
     */
    List<ResultField> asResultFields() {
        return dataset.recordStream()
                .flatMap(record -> RECORD_MAPPERS.stream()
                        .flatMap(mapper -> mapper.asResultFields(record)))
                .collect(toList());
    }

    // -------------------- PRIVATE --------------------

    private static List<String> splitByEverySecondComma(String fieldValue) {
        Pattern pattern = Pattern.compile("[^,]+,[^,]+");
        Matcher matcher = pattern.matcher(fieldValue);

        List<String> data = new ArrayList<>();
        while (matcher.find()) {
            Try.of(matcher::group)
                    .onSuccess(value -> data.add(value.trim()))
                    .onFailure(throwable -> log.error("There was a problem with splitting field", throwable));
        }
        return data;
    }

    // -------------------- INNER CLASSES --------------------

    /**
     * Maps a {@link Record} into zero or more {@link ResultField}'s.
     */
    static class RecordMapper {

        /**
         * Default mapping resulting in a single {@link ResultField} from given {@link Record} value.
         */
        private static final BiFunction<MxrdrMetadataField, String, Stream<ResultField>> DEFAULT_VALUE_MAPPER =
                (field, value) -> Stream.of(ResultField.of(fieldName(field), value));

        private final MxrdrMetadataField field;
        private final Function<Record, String> valueSource;
        private BiFunction<MxrdrMetadataField, String, Stream<ResultField>> valueMapper = DEFAULT_VALUE_MAPPER;
        private final List<RecordMapper> children = new ArrayList<>();

        RecordMapper(MxrdrMetadataField field, Function<Record, String> valueSource) {
            this.field = field;
            this.valueSource = valueSource;
        }

        RecordMapper(MxrdrMetadataField field, RecordMapper...children) {
            this.field = field;
            this.valueSource = r -> null;
            this.children.addAll(Arrays.asList(children));
        }

        /**
         * Sets custom transformation of {@link Record} value in case {@link #DEFAULT_VALUE_MAPPER} is not applicable.
         * @param valueMapper transformation function.
         * @return this mapper with new transformation set.
         */
        RecordMapper withValueMapper(BiFunction<MxrdrMetadataField, String, Stream<ResultField>> valueMapper) {
            this.valueMapper = valueMapper;
            return this;
        }

        /**
         * Performs the mapping of given record.
         * @param record record to map from.
         * @return resulting fields.
         */
        Stream<ResultField> asResultFields(Record record) {
            if (children.isEmpty()) {
                return Stream.of(valueSource.apply(record))
                        .filter(nonNullOrEmpty())
                        .flatMap(value -> valueMapper.apply(field, value));
            } else {
                return Stream.of(ResultField.of(fieldName(field),
                        children.stream()
                                .flatMap(child -> child.asResultFields(record))
                                .toArray(ResultField[]::new)))
                        .filter(field -> !field.getChildren().isEmpty());
            }
        }

        private static String fieldName(MxrdrMetadataField field) {
            return ofNullable(field)
                    .map(MxrdrMetadataField::getValue)
                    .orElse(StringUtils.EMPTY);
        }

        private static Predicate<String> nonNullOrEmpty() {
            return value -> value != null && value.length() > 0 && !"null".equals(value);
        }
    }

    /**
     * Stateful extension of {@link RecordMapper} allowing to be called only once
     * to enable mapping from first passed {@link Record} only.
     */
    static class FirstRecordMapper extends RecordMapper {

        private boolean collected = false;

        public FirstRecordMapper(MxrdrMetadataField field, Function<Record, String> valueSource) {
            super(field, valueSource);
        }

        public FirstRecordMapper(MxrdrMetadataField field, RecordMapper... children) {
            super(field, children);
        }

        /**
         * Performs the mapping only once. Each subsequent call will return an empty stream.
         * @param record record to map from.
         * @return resulting fields, or empty stream on subsequent calls.
         */
        @Override
        Stream<ResultField> asResultFields(Record record) {
            if (collected) {
                return Stream.empty();
            }
            collected = true;
            return super.asResultFields(record);
        }
    }
}
