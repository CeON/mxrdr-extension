package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import io.vavr.control.Try;
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

import static java.util.stream.Collectors.toList;

/**
 * Maps a {@link Dataset} into zero or more {@link ResultField}'s.
 */
public class DatasetMapper {

    private static final Logger log = LoggerFactory.getLogger(DatasetMapper.class);

    private final List<RecordMapper> RECORD_MAPPERS = Arrays.asList(
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.PDB_ID, Record::getStructureId)),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.MOLECULAR_WEIGHT, Record::getStructureMolecularWeight)),
            new FirstRecordMapper(new NestedRecordMapper(MxrdrMetadataField.SPACE_GROUP,
                    new ValueRecordMapper(Record::getSpaceGroup)
                            .withValueMapper(SymmetryStructureMapper::mapToStream))),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.RESIDUE_COUNT, Record::getResidueCount)),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.ATOM_SITE_COUNT, Record::getAtomSiteCount)),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.PDB_TITLE, Record::getStructureTitle)),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.PDB_DOI, Record::getPdbDoi)),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR, Record::getStructureAuthor)
                    .withValueMapper((field, value) -> Stream.of(value.split("#"))
                            .map(val -> ResultField.of(field.getValue(), val)))),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.PDB_DEPOSIT_DATE, Record::getDepositionDate)),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.PDB_RELEASE_DATE, Record::getReleaseDate)),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.PDB_REVISION_DATE, Record::getRevisionDate)),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.CITATION_TITLE, Record::getTitle)),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.CITATION_PUBMED_ID, Record::getPubmedId)),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.CITATION_AUTHOR, Record::getCitationAuthor)
                    .withValueMapper((field, value) -> splitByEverySecondComma(value).stream()
                            .map(val -> ResultField.of(field.getValue(), val)))),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.CITATION_JOURNAL, Record::getJournalName)),
            new FirstRecordMapper(new BasicRecordMapper(MxrdrMetadataField.CITATION_YEAR, Record::getPublicationYear)),
            new FirstRecordMapper(new NestedRecordMapper(MxrdrMetadataField.OVERALL,
                    new BasicRecordMapper(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH, Record::getResolution))),
            new FirstRecordMapper(new NestedRecordMapper(MxrdrMetadataField.DATA_COLLECTION,
                    new BasicRecordMapper(MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE, Record::getCollectionTemperature))),
            new FirstRecordMapper(new NestedRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETERS,
                    new BasicRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_A, Record::getLengthOfUnitCellLatticeA),
                    new BasicRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_B, Record::getLengthOfUnitCellLatticeB),
                    new BasicRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_C, Record::getLengthOfUnitCellLatticeC),
                    new BasicRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA, Record::getUnitCellAngleAlpha),
                    new BasicRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA, Record::getUnitCellAngleBeta),
                    new BasicRecordMapper(MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA, Record::getUnitCellAngleGamma))),
            new NestedRecordMapper(MxrdrMetadataField.ENTITY,
                    new BasicRecordMapper(MxrdrMetadataField.ENTITY_SEQUENCE, Record::getSequence),
                    new BasicRecordMapper(MxrdrMetadataField.ENTITY_ID, Record::getChainId))
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
    interface RecordMapper {

        /**
         * Performs the mapping of given record.
         * @param record record to map from.
         * @return resulting fields.
         */
        Stream<ResultField> asResultFields(Record record);

        /**
         * Predicate defining a valid value for mapped {@link ResultField}s.
         * Values that are {@literal null}, empty or equal to {@literal "null"} are deemed invalid.
         */
        Predicate<String> IS_VALID_RESULT_FILED_VALUE = value ->
                value != null && value.length() > 0 && !"null".equals(value);
    }

    /**
     * Maps a {@link Record} into a basic {@link ResultField}'s with name and value.
     */
    static class BasicRecordMapper implements RecordMapper {

        private final MxrdrMetadataField field;
        private final Function<Record, String> valueSource;
        private BiFunction<MxrdrMetadataField, String, Stream<ResultField>> valueMapper =
                (field, value) -> Stream.of(ResultField.of(field.getValue(), value));

        BasicRecordMapper(MxrdrMetadataField field, Function<Record, String> valueSource) {
            this.field = field;
            this.valueSource = valueSource;
        }

        BasicRecordMapper withValueMapper(BiFunction<MxrdrMetadataField, String, Stream<ResultField>> valueMapper) {
            this.valueMapper = valueMapper;
            return this;
        }

        @Override
        public Stream<ResultField> asResultFields(Record record) {
            return Stream.of(valueSource.apply(record))
                    .filter(IS_VALID_RESULT_FILED_VALUE)
                    .flatMap(value -> valueMapper.apply(field, value));
        }
    }

    /**
     * Maps a {@link Record} into unnamed, value only {@link ResultField}'s.
     */
    static class ValueRecordMapper implements RecordMapper {

        private final Function<Record, String> valueSource;
        private Function<String, Stream<ResultField>> valueMapper =
                value -> Stream.of(ResultField.ofValue(value));

        ValueRecordMapper(Function<Record, String> valueSource) {
            this.valueSource = valueSource;
        }

        ValueRecordMapper withValueMapper(Function<String, Stream<ResultField>> valueMapper) {
            this.valueMapper = valueMapper;
            return this;
        }

        @Override
        public Stream<ResultField> asResultFields(Record record) {
            return Stream.of(valueSource.apply(record))
                    .filter(IS_VALID_RESULT_FILED_VALUE)
                    .flatMap(valueMapper);
        }
    }

    /**
     * Maps a {@link Record} into a nested {@link ResultField}'s
     * with children obtained from their respective {@link RecordMapper}'s.
     */
    static class NestedRecordMapper implements RecordMapper {

        private final MxrdrMetadataField field;
        private final List<RecordMapper> children;

        NestedRecordMapper(MxrdrMetadataField field, RecordMapper...children) {
            this.field = field;
            this.children = Arrays.asList(children);
        }

        @Override
        public Stream<ResultField> asResultFields(Record record) {
            return Stream.of(ResultField.of(field.getValue(),
                    children.stream()
                            .flatMap(child -> child.asResultFields(record))
                            .toArray(ResultField[]::new)))
                    .filter(field -> !field.getChildren().isEmpty());
        }
    }

    /**
     * Stateful wrapper of {@link RecordMapper} allowing to be called only once
     * to enable mapping from first passed {@link Record} only.
     */
    static class FirstRecordMapper implements RecordMapper {

        private final RecordMapper mapper;
        private boolean mapped = false;

        public FirstRecordMapper(RecordMapper mapper) {
            this.mapper = mapper;
        }

        @Override
        public Stream<ResultField> asResultFields(Record record) {
            if (mapped) {
                return Stream.empty();
            }
            mapped = true;
            return mapper.asResultFields(record);
        }
    }
}
