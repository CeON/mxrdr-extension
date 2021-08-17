package pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.rcsb.cif.model.Block;
import org.rcsb.cif.model.Category;
import org.rcsb.cif.model.Column;
import org.rcsb.cif.model.ValueKind;
import org.rcsb.cif.schema.mm.AuditAuthor;
import org.rcsb.cif.schema.mm.Cell;
import org.rcsb.cif.schema.mm.Citation;
import org.rcsb.cif.schema.mm.CitationAuthor;
import org.rcsb.cif.schema.mm.Diffrn;
import org.rcsb.cif.schema.mm.DiffrnDetector;
import org.rcsb.cif.schema.mm.DiffrnRadiation;
import org.rcsb.cif.schema.mm.DiffrnSource;
import org.rcsb.cif.schema.mm.Entry;
import org.rcsb.cif.schema.mm.MmCifBlock;
import org.rcsb.cif.schema.mm.MmCifFile;
import org.rcsb.cif.schema.mm.PdbxAuditRevisionHistory;
import org.rcsb.cif.schema.mm.PdbxDatabaseStatus;
import org.rcsb.cif.schema.mm.Refine;
import org.rcsb.cif.schema.mm.Reflns;
import org.rcsb.cif.schema.mm.ReflnsShell;
import org.rcsb.cif.schema.mm.Software;
import org.rcsb.cif.schema.mm.Struct;
import org.rcsb.cif.schema.mm.Symmetry;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.ProcessingSoftwareMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.SymmetryStructureMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.time.chrono.ChronoLocalDate.timeLineOrder;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

/**
 * Handles mapping {@link MmCifFile}.
 * <p>
 * Each {@link MmCifFile CifFile} contains multiple {@link Category Categories}, with each of them containing
 * multiple {@link Column Columns} with each of them containing potentially multiple values (rows).
 */
public class CifFileMapper {

    /**
     * Delimiter for concatenating column values.
     */
    public static final String CONCAT_DELIMITER = "; ";

    /**
     * A predicate identifying invalid values to be filtered out of the result.
     */
    private static final Predicate<String> IS_VALID_RESULT_FIELD_VALUE = value ->
            value != null && value.length() > 0 && !".".equals(value) && !"?".equals(value);

    /**
     * Pattern for matching ISO dates.
     */
    private static final Pattern ISO_DATE_PATTERN = Pattern.compile("\\d{4,}-\\d{2}-\\d{2}");

    /**
     * A predicate identifying ISO date values.
     */
    private static final Predicate<String> IS_ISO_DATE = value ->
            ISO_DATE_PATTERN.matcher(value).matches();

    /**
     * Mapping definition in the form of list of mappers to be used on input file.
     */
    private static final List<CategoryMapper<?>> CATEGORY_MAPPERS = Arrays.asList(
            new CategoryMapper<Entry>(MmCifBlock::getEntry,
                    new BasicColumnMapper<>(MxrdrMetadataField.PDB_ID, Entry::getId)),
            new CategoryMapper<DiffrnDetector>(MmCifBlock::getDiffrnDetector,
                    new BasicColumnMapper<>(MxrdrMetadataField.DETECTOR_TYPE, DiffrnDetector::getDetector)),
            new CategoryMapper<DiffrnSource>(MmCifBlock::getDiffrnSource,
                    new BasicColumnMapper<>(MxrdrMetadataField.BEAMLINE, DiffrnSource::getPdbxSynchrotronBeamline)
                            .withValueSource(joiningSource())),
            new CategoryMapper<MergedCategory>(mergedCategory(MmCifBlock::getDiffrnSource, MmCifBlock::getDiffrn),
                    new NestedColumnMapper<>(MxrdrMetadataField.DATA_COLLECTION,
                            new BasicColumnMapper<>(MxrdrMetadataField.DATA_COLLECTION_WAVELENGTH,
                                    columnFrom(MmCifBlock::getDiffrnSource, DiffrnSource::getPdbxWavelengthList)),
                            new BasicColumnMapper<>(MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE,
                                    columnFrom(MmCifBlock::getDiffrn, Diffrn::getAmbientTemp)))
                            .withCategoryMapper(MergedCategory::flatten)),
            new CategoryMapper<Symmetry>(MmCifBlock::getSymmetry,
                    new NestedColumnMapper<>(MxrdrMetadataField.SPACE_GROUP,
                            new ValueColumnMapper<>(Symmetry::getSpaceGroupNameH_M)
                                    .withValueMapper(SymmetryStructureMapper::mapToStream))),
            new CategoryMapper<Cell>(MmCifBlock::getCell,
                    new NestedColumnMapper<>(MxrdrMetadataField.UNIT_CELL_PARAMETERS,
                            new BasicColumnMapper<>(MxrdrMetadataField.UNIT_CELL_PARAMETER_A, Cell::getLengthA),
                            new BasicColumnMapper<>(MxrdrMetadataField.UNIT_CELL_PARAMETER_B, Cell::getLengthB),
                            new BasicColumnMapper<>(MxrdrMetadataField.UNIT_CELL_PARAMETER_C, Cell::getLengthC),
                            new BasicColumnMapper<>(MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA, Cell::getAngleAlpha),
                            new BasicColumnMapper<>(MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA, Cell::getAngleBeta),
                            new BasicColumnMapper<>(MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA, Cell::getAngleGamma))),
            new CategoryMapper<MergedCategory>(mergedCategory(MmCifBlock::getReflns, MmCifBlock::getRefine),
                    new NestedColumnMapper<>(MxrdrMetadataField.OVERALL,
                            new BasicColumnMapper<>(MxrdrMetadataField.OVERALL_COMPLETENESS,
                                    columnFrom(MmCifBlock::getReflns, Reflns::getPercentPossibleObs)),
                            new BasicColumnMapper<>(MxrdrMetadataField.OVERALL_I_SIGMA,
                                    columnFrom(MmCifBlock::getReflns, Reflns::getPdbxNetIOverSigmaI)),
                            new BasicColumnMapper<>(MxrdrMetadataField.OVERALL_R_MERGE,
                                    columnFrom(MmCifBlock::getReflns, Reflns::getPdbxRmergeIObs)),
                            new BasicColumnMapper<>(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_LOW,
                                    columnFrom(MmCifBlock::getRefine, Refine::getLsDResLow)),
                            new BasicColumnMapper<>(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH,
                                    columnFrom(MmCifBlock::getRefine, Refine::getLsDResHigh)))),
            new CategoryMapper<ReflnsShell>(MmCifBlock::getReflnsShell,
                    new NestedColumnMapper<>(MxrdrMetadataField.HRS,
                            new BasicColumnMapper<>(MxrdrMetadataField.HRS_COMPLETENESS, ReflnsShell::getPercentPossibleAll),
                            new BasicColumnMapper<>(MxrdrMetadataField.HRS_I_SIGMA, ReflnsShell::getMeanIOverSigIObs),
                            new BasicColumnMapper<>(MxrdrMetadataField.HRS_R_MERGE, ReflnsShell::getRmergeIObs))),
            new CategoryMapper<DiffrnRadiation>(MmCifBlock::getDiffrnRadiation,
                    new BasicColumnMapper<>(MxrdrMetadataField.MONOCHROMATOR, DiffrnRadiation::getMonochromator)),
            new CategoryMapper<Software>(MmCifBlock::getSoftware,
                    new NestedColumnMapper<>(MxrdrMetadataField.PROCESSING_SOFTWARE,
                            new ValueColumnMapper<>(Software::getName)
                                    .withValueMapper(ProcessingSoftwareMapper::mapToStream))),
            new CategoryMapper<Struct>(MmCifBlock::getStruct,
                    new BasicColumnMapper<>(MxrdrMetadataField.PDB_TITLE, Struct::getTitle)),
            new CategoryMapper<MergedCategory>(mergedCategory(MmCifBlock::getCitation),
                    new WrappingOnlyColumnMapper<>(
                            new InvisibleColumnMapper<>(columnFrom(MmCifBlock::getCitation, Citation::getId)),
                            new BasicColumnMapper<>(MxrdrMetadataField.PDB_DOI, columnFrom(MmCifBlock::getCitation, Citation::getPdbxDatabaseIdDOI)),
                            new BasicColumnMapper<>(MxrdrMetadataField.CITATION_TITLE, columnFrom(MmCifBlock::getCitation, Citation::getTitle)),
                            new BasicColumnMapper<>(MxrdrMetadataField.CITATION_PUBMED_ID, columnFrom(MmCifBlock::getCitation, Citation::getPdbxDatabaseIdPubMed)),
                            new BasicColumnMapper<>(MxrdrMetadataField.CITATION_JOURNAL, columnFrom(MmCifBlock::getCitation, Citation::getJournalAbbrev)),
                            new BasicColumnMapper<>(MxrdrMetadataField.CITATION_YEAR, columnFrom(MmCifBlock::getCitation, Citation::getYear)))
                            .withCategoryMapper(filterByColumnValue(columnFrom(MmCifBlock::getCitation, Citation::getId), "primary"))),
            new CategoryMapper<MergedCategory>(mergedCategory(MmCifBlock::getCitationAuthor),
                    new WrappingOnlyColumnMapper<>(
                            new InvisibleColumnMapper<>(columnFrom(MmCifBlock::getCitationAuthor, CitationAuthor::getCitationId)),
                            new BasicColumnMapper<>(MxrdrMetadataField.CITATION_AUTHOR, columnFrom(MmCifBlock::getCitationAuthor, CitationAuthor::getName)))
                            .withCategoryMapper(filterByColumnValue(columnFrom(MmCifBlock::getCitationAuthor, CitationAuthor::getCitationId), "primary"))),
            new CategoryMapper<AuditAuthor>(MmCifBlock::getAuditAuthor,
                    new BasicColumnMapper<>(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR, AuditAuthor::getName)),
            new CategoryMapper<PdbxDatabaseStatus>(MmCifBlock::getPdbxDatabaseStatus,
                    new BasicColumnMapper<>(MxrdrMetadataField.PDB_DEPOSIT_DATE, PdbxDatabaseStatus::getRecvdInitialDepositionDate)),
            new CategoryMapper<PdbxAuditRevisionHistory>(MmCifBlock::getPdbxAuditRevisionHistory,
                    new BasicColumnMapper<>(MxrdrMetadataField.PDB_RELEASE_DATE, PdbxAuditRevisionHistory::getRevisionDate)
                            .withValueSource(dateSelectingSource(Stream::min)),
                    new BasicColumnMapper<>(MxrdrMetadataField.PDB_REVISION_DATE, PdbxAuditRevisionHistory::getRevisionDate)
                            .withValueSource(dateSelectingSource(Stream::max)))
    );

    // -------------------- LOGIC --------------------

    public Stream<ResultField> asResultFields(MmCifFile file) {
        return CATEGORY_MAPPERS.stream()
                .flatMap(category -> category.asResultFields(file.getFirstBlock()));
    }

    // -------------------- PRIVATE --------------------

    /**
     * A value source function allowing to join multiple values of given column into a single one with the standard delimiter.
     * By applying this function we make sure a single {@link ResultField} will be created from given column containing
     * all the values instead of potentially multiple {@link ResultField}'s, one per each column value.
     * @return single element stream of joint column values.
     */
    private static Function<Column<?>, Stream<String>> joiningSource() {
        return column -> Stream.of(column.stringData()
                .collect(joining(CONCAT_DELIMITER)));
    }

    /**
     * A value source function allowing to select a single date from column values using given selector.
     * By applying this function we make sure a single {@link ResultField} will be created from given column containing
     * only a single value instead of potentially multiple {@link ResultField}'s, one per each column value.
     * @param selector selector to use when choosing date value (i.e. <code>Stream::min</code>, <code>Stream::max</code>).
     * @return single element stream of selected value.
     */
    private static Function<Column<?>, Stream<String>> dateSelectingSource(
            BiFunction<Stream<LocalDate>, Comparator<? super LocalDate>, Optional<LocalDate>> selector) {
        return column -> selector.apply(
                        column.stringData()
                                .filter(IS_VALID_RESULT_FIELD_VALUE.and(IS_ISO_DATE))
                                .map(s -> LocalDate.parse(s, DateTimeFormatter.ISO_DATE)),
                        timeLineOrder())
                .map(LocalDate::toString)
                .map(Stream::of)
                .orElseGet(Stream::empty);
    }

    /**
     * Factory method constructing a {@link MergedCategory} from given {@link MmCifBlock}
     * and a list of {@link Category} functions.
     * @param categories {@link Category} functions to apply on given {@link MmCifBlock}.
     * @return {@link MergedCategory} factory function.
     */
    @SafeVarargs
    static Function<MmCifBlock, MergedCategory> mergedCategory(Function<MmCifBlock, Category>...categories) {
        return block -> new MergedCategory(
                mergedCategoryName(categories).apply(block),
                mergedCategoryColumns(categories).apply(block));
    }

    /**
     * Builder function for {@link MergedCategory} name, being a union of merged categories names
     * joint with and underscore.
     * @param categories merged {@link Category Categories} to name.
     * @return {@link MergedCategory} name building function.
     */
    @SafeVarargs
    static Function<MmCifBlock, String> mergedCategoryName(Function<MmCifBlock, Category>...categories) {
        return block -> Stream.of(categories)
                .map(category -> category.apply(block))
                .map(Category::getCategoryName)
                .collect(joining("_"));
    }

    /**
     * Builder function for {@link MergedCategory} columns, being a union of merged categories columns,
     * but prefixed with names original category name to avoid name conflicts.
     * @param categories merged {@link Category Categories} to compose columns for.
     * @return {@link MergedCategory} columns building function.
     */
    @SafeVarargs
    static Function<MmCifBlock, Map<String, Column<?>>> mergedCategoryColumns(Function<MmCifBlock, Category>...categories) {
        return block -> Stream.of(categories)
                .map(category -> category.apply(block))
                .flatMap(category -> category.columns()
                        .map(column -> new MergedColumn<>(category, column)))
                .collect(toMap(MergedColumn::getColumnName, c -> c));
    }

    /**
     * Function that extracts given {@link Column} from a {@link MergedCategory}.
     * @param category source {@link Category} for requested column.
     * @param column source {@link Column} to get.
     * @param <C> type of {@link Category} to search the {@link Column} in.
     * @return the {@link MergedColumn} for given category and column.
     */
    static <C extends Category> Function<MergedCategory, Column<?>> columnFrom(Function<MmCifBlock, C> category,
                                                                               Function<C, Column<?>> column) {
        return merged -> {
            Column<?> nameFinder = column.apply(category.apply(new MmCifBlock(new NameInterceptingBlock())));
            return merged.getColumn(nameFinder.getColumnName());
        };
    }

    /**
     * Function that is used to filter only those rows for a set of columns, where one of the columns (selector) has
     * the chosen value.
     * @param selectorColumnGetter source {@link Column} for selector column.
     * @param selectorValue value of selector column that will be used for selecting rows
     * @return function that could be used as a category mapper for {@link WrappingOnlyColumnMapper}
     */
    static Function<MergedCategory, Stream<MergedCategory>> filterByColumnValue(Function<MergedCategory, Column<?>> selectorColumnGetter,
                                                                                String selectorValue) {
        return mergedCategory -> mergedCategory.setSelectedColumn(selectorColumnGetter.apply(mergedCategory))
                .flatten().filter(m -> m.getSelectedColumn().getStringData(0).equals(selectorValue));
    }

    // -------------------- INNER CLASSES --------------------

    /**
     * Handles mapping a {@link Category}.
     * @param <C> type of {@link Category} to be mapped.
     */
    static class CategoryMapper<C extends Category> {

        private final Function<MmCifBlock, C> category;
        private final List<ColumnMapper<C>> columns;

        @SafeVarargs
        CategoryMapper(Function<MmCifBlock, C> category, ColumnMapper<C>...columns) {
            this.category = category;
            this.columns = Arrays.asList(columns);
        }

        Stream<ResultField> asResultFields(MmCifBlock block) {
            return columns.stream()
                    .flatMap(column -> column.asResultFields(category.apply(block)));
        }
    }

    /**
     * Handles mapping a {@link Column}.
     * @param <C> type of {@link Category} mapped column belongs to.
     */
    interface ColumnMapper<C extends Category> {

        Stream<ResultField> asResultFields(C category);

    }

    /**
     * Maps a {@link Column} into a basic {@link ResultField}'s with name and value.
     * @param <C> type of {@link Category} mapped column belongs to.
     */
    static class BasicColumnMapper<C extends Category> implements ColumnMapper<C> {

        private final MxrdrMetadataField field;
        private final Function<C, Column<?>> column;
        private Function<Column<?>, Stream<String>> valueSource =
                column -> column.stringData().filter(IS_VALID_RESULT_FIELD_VALUE);

        BasicColumnMapper(MxrdrMetadataField field, Function<C, Column<?>> column) {
            this.field = field;
            this.column = column;
        }

        BasicColumnMapper<C> withValueSource(Function<Column<?>, Stream<String>> valueSource) {
            this.valueSource = valueSource;
            return this;
        }

        @Override
        public Stream<ResultField> asResultFields(C category) {
            return valueSource.apply(column.apply(category))
                    .flatMap(value -> Stream.of(ResultField.of(field.getValue(), value)));
        }
    }

    /**
     * Column that could be accessed during data processing, but will not create any {@link ResultField}s.
     * @param <C> type of {@link Category} mapped column belongs to.
     */
    static class InvisibleColumnMapper<C extends Category> extends BasicColumnMapper<C> {
        public InvisibleColumnMapper(Function<C, Column<?>> column) {
            super(null, column);
        }

        @Override
        public Stream<ResultField> asResultFields(C category) {
            return Stream.empty();
        }
    }

    /**
     * Maps a {@link Column} into unnamed, value only {@link ResultField}'s.
     * @param <C> type of {@link Category} mapped column belongs to.
     */
    static class ValueColumnMapper<C extends Category> implements ColumnMapper<C> {

        private final Function<C, Column<?>> column;
        private Function<String, Stream<ResultField>> valueMapper =
                value -> Stream.of(ResultField.ofValue(value));

        ValueColumnMapper(Function<C, Column<?>> column) {
            this.column = column;
        }

        ValueColumnMapper<C> withValueMapper(Function<String, Stream<ResultField>> valueMapper) {
            this.valueMapper = valueMapper;
            return this;
        }

        @Override
        public Stream<ResultField> asResultFields(C category) {
            return column.apply(category)
                    .stringData()
                    .filter(IS_VALID_RESULT_FIELD_VALUE)
                    .flatMap(v -> valueMapper.apply(v))
                    .distinct();
        }
    }

    /**
     * Maps a {@link Column} into a nested {@link ResultField}'s
     * with children obtained from their respective {@link ColumnMapper}'s.
     * @param <C> type of {@link Category} mapped column belongs to.
     */
    static class NestedColumnMapper<C extends Category> implements ColumnMapper<C> {

        protected final MxrdrMetadataField field;
        protected final List<ColumnMapper<C>> children;
        protected Function<C, Stream<C>> categoryMapper = Stream::of;

        @SafeVarargs
        NestedColumnMapper(MxrdrMetadataField field, ColumnMapper<C>...children) {
            this.field = field;
            this.children = Arrays.asList(children);
        }

        NestedColumnMapper<C> withCategoryMapper(Function<C, Stream<C>> categoryMapper) {
            this.categoryMapper = categoryMapper;
            return this;
        }

        @Override
        public Stream<ResultField> asResultFields(C category) {
            return categoryMapper.apply(category)
                    .map(c -> ResultField.of(field.getValue(),
                            children.stream()
                                    .flatMap(child -> child.asResultFields(c))
                                    .toArray(ResultField[]::new)))
                    .filter(field -> !field.getChildren().isEmpty());
        }
    }

    /**
     * Allows to use category mapper like in {@link NestedColumnMapper}, but does not create compound field as a result.
     * @param <C> type of {@link Category} mapped column belongs to.
     */
    static class WrappingOnlyColumnMapper<C extends Category> extends NestedColumnMapper<C> {

        @SafeVarargs
        public WrappingOnlyColumnMapper(ColumnMapper<C>... children) {
            super(null, children);
        }

        @Override
        public Stream<ResultField> asResultFields(C category) {

            return categoryMapper.apply(category)
                    .flatMap(c -> children.stream()
                            .flatMap(child -> child.asResultFields(c)));
        }
    }
    /**
     * Represents a merged {@link Category} that is a union of multiple simple categories. This allows to access
     * multiple categories via a single interface that is the same as for each regular category. It is used when
     * we need to create a complex {@link ResultField} using data from more than one category.
     */
    static class MergedCategory implements Category {

        private final String name;
        private final Map<String, Column<?>> columns;
        private String selectedColumnName;

        MergedCategory(String name, Map<String, Column<?>> columns) {
            this.name = name;
            this.columns = columns;
        }

        MergedCategory(String name, Map<String, Column<?>> columns, String selectedColumnName) {
            this.name = name;
            this.columns = columns;
            this.selectedColumnName = selectedColumnName;
        }

        public MergedCategory setSelectedColumn(Column<?> column) {
            this.selectedColumnName = column.getColumnName();
            return this;
        }

        public Column<?> getSelectedColumn() {
            return getColumn(selectedColumnName);
        }

        @Override
        public String getCategoryName() {
            return name;
        }

        @Override
        public int getRowCount() {
            return columns.size();
        }

        @Override
        public Column<?> getColumn(String name) {
            return columns.getOrDefault(name, new Column.EmptyColumn(name));
        }

        @Override
        public Map<String, Column<?>> getColumns() {
            return columns;
        }

        public Stream<MergedCategory> flatten() {
            int maxRowCount = columns.values().stream().mapToInt(Column::getRowCount).max().orElse(1);
            if (maxRowCount > 1) {
                return IntStream.range(0, maxRowCount)
                        .mapToObj(row -> new MergedCategory(name + "_" + row, flattenColumnsFor(row), selectedColumnName));
            } else {
                return Stream.of(this);
            }
        }

        private Map<String, Column<?>> flattenColumnsFor(int row) {
            return columns.values().stream()
                    .map(column -> new FlattenColumn<>(column, row))
                    .filter(FlattenColumn::isDefined)
                    .collect(toMap(FlattenColumn::getColumnName, c -> c));
        }
    }

    /**
     * Represents a {@link Column} from a {@link MergedCategory} that is also aware of it's source {@link Category}.
     * @param <T> the array type of this column (int[], double[], String[])
     */
    static class MergedColumn<T> implements Column<T> {

        private final Category category;
        private final Column<T> column;

        MergedColumn(Category category, Column<T> column) {
            this.category = category;
            this.column = column;
        }

        @Override
        public String getColumnName() {
            return category.getCategoryName() + "_" + column.getColumnName();
        }

        @Override
        public int getRowCount() {
            return column.getRowCount();
        }

        @Override
        public String getStringData(int row) {
            return column.getStringData(row);
        }

        @Override
        public ValueKind getValueKind(int row) {
            return column.getValueKind(row);
        }

        @Override
        public T getArray() {
            return column.getArray();
        }
    }

    /**
     * Represents a {@link Column} created by flattening regular column by its rows, meaning instead of a single
     * {@link Column} with multiple rows, we get multiple {@link FlattenColumn}s with a single row each.
     * @param <T> the array type of this column (int[], double[], String[])
     */
    static class FlattenColumn<T> implements Column<T> {

        private final Column<T> column;
        private final int row;

        FlattenColumn(Column<T> column, int row) {
            this.column = column;
            this.row = row;
        }

        public boolean isDefined() {
            return column.getRowCount() > row;
        }

        @Override
        public String getColumnName() {
            return column.getColumnName();
        }

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public String getStringData(int row) {
            return column.getStringData(this.row);
        }

        @Override
        public ValueKind getValueKind(int row) {
            return column.getValueKind(this.row);
        }

        @Override
        public T getArray() {
            throw new UnsupportedOperationException("No array available for flatten column");
        }
    }

    /**
     * Empty {@link Block} implementation to intercept the name of requested {@link Category}.
     * This is used in conjunction with {@link NameInterceptingCategory} to construct the name
     * of requested {@link MergedColumn}.
     */
    static class NameInterceptingBlock implements Block {

        @Override
        public Category getCategory(String name) {
            return new NameInterceptingCategory(name);
        }

        @Override
        public String getBlockHeader() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<String, Category> getCategories() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Block> getSaveFrames() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Empty {@link Category} implementation to intercept the name of requested {@link Column}
     * and join it with previously intercepted {@link Category} name. This is used in conjunction
     * with {@link NameInterceptingBlock} to construct the name of requested {@link MergedColumn}.
     */
    static class NameInterceptingCategory implements Category {

        private final String name;

        public NameInterceptingCategory(String name) {
            this.name = name;
        }

        @Override
        public Column<?> getColumn(String name) {
            return new Column.EmptyColumn(this.name + "_" + name);
        }

        @Override
        public String getCategoryName() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getRowCount() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Map<String, Column<?>> getColumns() {
            throw new UnsupportedOperationException();
        }
    }
}
