package pl.edu.icm.pl.mxrdr.extension.importer.cif.file;

import com.google.common.collect.ImmutableList;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rcsb.cif.model.Column;
import org.rcsb.cif.model.ValueKind;
import org.rcsb.cif.schema.mm.MmCifFile;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.cif.file.CifDataContainer.IndexValue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;

class CifDataContainerTest {

    private CifDataContainer container;

    @BeforeEach
    void setUp() {
        container = new CifDataContainer();
    }

    @Test
    @DisplayName("Should fill container from CIF file with first diffraction data set on default " +
            "choosing properly the highest resolution shell")
    void fillContainer() throws Exception {
        // given
        File input = new File(getClass().getClassLoader().getResource("cif/4pvm.cif").toURI());
        MmCifFile mmCifFile = CifFileReader.readAndParseCifFile(input, null);

        // when
        container.init(mmCifFile.getFirstBlock(), EMPTY);

        // then
        assertThat(container.get(MxrdrMetadataField.PDB_ID)).hasValue("4PVM");
        assertThat(container.getAll(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR))
                .containsExactly("Fisher, S.J.", "Blakeley, M.P.", "Haupt, M.");
        assertThat(container.get(MxrdrMetadataField.DETECTOR_TYPE)).hasValue("LADI-III");
        assertThat(container.get(MxrdrMetadataField.PROCESSING_SOFTWARE)).hasValue("XDS");
        assertThat(container.get(MxrdrMetadataField.OVERALL_NUMBER_OBSERVED_REFLECTIONS)).hasValue("13480");
        assertThat(container.get(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH)).hasValue("2.0");
        assertThat(container.get(MxrdrMetadataField.PDB_DEPOSIT_DATE)).hasValue("2014-03-18");
        assertThat(container.getAll(MxrdrMetadataField.ENTITY_ID)).containsExactly("1");
        assertThat(container.getAll(MxrdrMetadataField.ENTITY_SEQUENCE)).containsExactly("QWERTY");
    }

    @Test
    @DisplayName("Should fill container from CIF file with second diffraction data set on demand " +
            "choosing properly the highest resolution shell")
    void fillContainerWithGivenIndex() throws Exception {
        // given
        File input = new File(getClass().getClassLoader().getResource("cif/4pvm.cif").toURI());
        MmCifFile mmCifFile = CifFileReader.readAndParseCifFile(input, null);

        // when
        container.init(mmCifFile.getFirstBlock(), "2");

        // then
        assertThat(container.get(MxrdrMetadataField.PDB_ID)).hasValue("4PVM");
        assertThat(container.getAll(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR))
                .containsExactly("Fisher, S.J.", "Blakeley, M.P.", "Haupt, M.");
        assertThat(container.get(MxrdrMetadataField.BEAMLINE)).hasValue("ID23-1");
        assertThat(container.get(MxrdrMetadataField.DETECTOR_TYPE)).hasValue("ADSC QUANTUM 315r");
        assertThat(container.get(MxrdrMetadataField.PROCESSING_SOFTWARE)).hasValue("XDS");
        assertThat(container.get(MxrdrMetadataField.OVERALL_NUMBER_OBSERVED_REFLECTIONS)).hasValue("21718");
        assertThat(container.get(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH)).hasValue("1.850");
        assertThat(container.get(MxrdrMetadataField.HRS_I_SIGMA)).hasValue("2.670");
        assertThat(container.get(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_HIGH)).hasValue("1.85");
        assertThat(container.get(MxrdrMetadataField.PDB_DEPOSIT_DATE)).hasValue("2014-03-18");
        assertThat(container.getAll(MxrdrMetadataField.ENTITY_ID)).containsExactly("1");
        assertThat(container.getAll(MxrdrMetadataField.ENTITY_SEQUENCE)).containsExactly("QWERTY");
    }

    @Test
    void presentValuesStream() {
        // given
        TestColumn column = createTestColumn();

        // when
        List<String> presentValues = container.presentValuesStream(column).collect(Collectors.toList());

        // then
        assertThat(presentValues).containsExactly("1", "3", "5");
    }

    @Test
    void extractFromRow__valuePresent() {
        // given
        TestColumn column = createTestColumn();

        // when
        String value = container.extractFromRow(column, 0);

        // then
        assertThat(value).isEqualTo("1");
    }

    @Test
    void extractFromRow__valueNotPresentOrUnknown() {
        // given
        TestColumn column = createTestColumn();

        // when
        String notPresent = container.extractFromRow(column, 1);
        String unknown = container.extractFromRow(column, 3);

        // then
        assertThat(notPresent).isEmpty();
        assertThat(unknown).isEmpty();
    }

    @Test
    void extractFromRow__rowIndexOutOfBounds() {
        // given
        TestColumn column = createTestColumn();

        // when
        String rowMinusOne = container.extractFromRow(column, -1);
        String rowOneHundred = container.extractFromRow(column, 100);

        // then
        assertThat(rowMinusOne).isEmpty();
        assertThat(rowOneHundred).isEmpty();
    }

    @Test
    void extractFromRows__presentValues() {
        // given
        TestColumn column = createTestColumn();

        // when
        Stream<String> values
                = container.extractFromRows(column, Tuple.of(ImmutableList.of(0, 2, 4), new IndexValue("unused")));

        // then
        assertThat(values).containsExactly("1", "3", "5");
    }

    @Test
    void extractFromRows__nonPresentValues() {
        // given
        TestColumn column = createTestColumn();

        // when
        Stream<String> values = container.extractFromRows(column, Tuple.of(ImmutableList.of(1, 3), new IndexValue("unused")));

        // then
        assertThat(values).containsExactly(EMPTY, EMPTY);
    }

    @Test
    void extractFromRows__rowIndexOutOfBounds() {
        // given
        TestColumn column = createTestColumn();

        // when
        Stream<String> values = container.extractFromRows(column, Tuple.of(ImmutableList.of(-1, 100), new IndexValue("unused")));

        // then
        assertThat(values).isEmpty();
    }

    @Test
    @DisplayName("extractFromRows(…) should return the only value of column if index value semantics allows it " +
            "and no row indexes are passed")
    void extractFromRows__noRowIndex() {
        // given
        TestColumn column = new TestColumn(Tuple.of("value", ValueKind.PRESENT));

        // when
        Stream<String> values = container.extractFromRows(column,
                Tuple.of(
                        Collections.emptyList(),
                        new IndexValue("1", "1", true)));

        // then
        assertThat(values).containsExactly("value");
    }

    @Test
    void findRowNumbers() {
        // given
        TestColumn indexColumn = new TestColumn(
                Tuple.of("indexValue", ValueKind.PRESENT),
                Tuple.of("2", ValueKind.PRESENT),
                Tuple.of("indexValue", ValueKind.PRESENT),
                Tuple.of("4", ValueKind.PRESENT));

        // when
        Tuple2<List<Integer>, IndexValue> rowNumbersData = container.findRowNumbers(indexColumn, new IndexValue("indexValue"));

        // then
        assertThat(rowNumbersData._1()).containsExactly(0, 2);
    }

    // -------------------- PRIVATE --------------------

    private TestColumn createTestColumn() {
        return new TestColumn(
                Tuple.of("1", ValueKind.PRESENT),
                Tuple.of("2", ValueKind.NOT_PRESENT),
                Tuple.of("3", ValueKind.PRESENT),
                Tuple.of("4", ValueKind.UNKNOWN),
                Tuple.of("5", ValueKind.PRESENT));
    }

    // -------------------- INNER CLASSES --------------------

    static class TestColumn implements Column<String[]> {

        private List<String> data = new ArrayList<>();
        private List<ValueKind> valueKindData = new ArrayList<>();

        TestColumn(Tuple2<String, ValueKind>... values) {
            Arrays.stream(values)
                    .forEach(v -> {
                        data.add(v._1());
                        valueKindData.add(v._2());
                    });
        }

        @Override
        public String getColumnName() {
            return this.getClass().getSimpleName();
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public String getStringData(int row) {
            return data.get(row);
        }

        @Override
        public ValueKind getValueKind(int row) {
            return valueKindData.get(row);
        }

        @Override
        public String[] getArray() {
            return data.toArray(new String[0]);
        }
    }

}