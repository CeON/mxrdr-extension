package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.StructureData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class PdbDataContainerTest {

    private PdbDataContainer container;

    @BeforeEach
    void setUp() {
        container = new PdbDataContainer();
    }

    // -------------------- TESTS --------------------

    @Test
    @DisplayName("Should fill container from StructureData")
    void fillContainer() throws Exception {
        // given
        StructureData structureData = new StructureDataProvider().createStructureData();

        // when
        container.init(structureData);

        // then
        assertThat(container.get(MxrdrMetadataField.PDB_ID)).hasValue("1ZZZ");
        assertThat(container.getAll(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR))
                .containsExactly("First, Author", "Second, Author", "Third, Author");
        assertThat(container.get(MxrdrMetadataField.PROCESSING_SOFTWARE)).hasValue("XDS");
        assertThat(container.get(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_HIGH)).hasValue("2.54");
        assertThat(container.get(MxrdrMetadataField.PDB_DEPOSIT_DATE)).hasValue("2019-01-30");
        assertThat(container.getAll(MxrdrMetadataField.ENTITY_ID)).containsExactly("1", "2");
        assertThat(container.getAll(MxrdrMetadataField.ENTITY_SEQUENCE)).containsExactly("QWERTY1", "QWERTY2");
    }

    @Test
    @DisplayName("Should add and retrieve single added elements")
    void addAndRetrieve() {
        // given & when
        container.add(MxrdrMetadataField.UNIT_CELL_PARAMETER_A, "A");
        container.add(MxrdrMetadataField.UNIT_CELL_PARAMETER_B, Optional.ofNullable("B"));

        String elementA = container.get(MxrdrMetadataField.UNIT_CELL_PARAMETER_A).orElse(null);
        String elementB = container.get(MxrdrMetadataField.UNIT_CELL_PARAMETER_B).orElse(null);

        // then
        assertThat(elementA).isEqualTo("A");
        assertThat(elementB).isEqualTo("B");
    }

    @Test
    @DisplayName("Should add and retrieve collection of elements")
    void addAndRetrieveCollection() {
        // given & when
        container.addAll(MxrdrMetadataField.ENTITY_ID, Stream.of("1", "2", "3"));

        List<String> elements = container.getAll(MxrdrMetadataField.ENTITY_ID);

        // then
        assertThat(elements).containsExactly("1", "2", "3");
    }

    @Test
    @DisplayName("Should be able to retrieve first or the chosen by index or all elements for key")
    void retrieveElements() {
        // given
        container.addAll(MxrdrMetadataField.ENTITY_ID, Stream.of("1", "2", "3"));

        // when
        String first = container.get(MxrdrMetadataField.ENTITY_ID).orElse(null);
        String last = container.getIndexed(MxrdrMetadataField.ENTITY_ID, 2).orElse(null);
        List<String> all = container.getAll(MxrdrMetadataField.ENTITY_ID);

        // then
        assertThat(first).isEqualTo("1");
        assertThat(last).isEqualTo("3");
        assertThat(all).containsExactly("1", "2", "3");
    }

    @Test
    @DisplayName("Should get data from the inner collection of input data object and sort according to the chosen index")
    void getAll() {
        // given
        InputDataObject inputData = createInputData();

        // when
        List<InputDataObject.InnerData> innerData
                = PdbDataContainer.getAll(InputDataObject::getInnerData, InputDataObject.InnerData::getIndex, inputData);

        // then
        assertThat(innerData)
                .extracting(InputDataObject.InnerData::getIndex, InputDataObject.InnerData::getValue)
                .containsExactly(tuple(1, "1"), tuple(2, "2"), tuple(3, "3"));
    }

    @Test
    @DisplayName("Should get first (according to index) element from the inner collection of input data object")
    void getFirst() {
        // given
        InputDataObject inputData = createInputData();

        // when
        InputDataObject.InnerData first
                = PdbDataContainer.getFirst(InputDataObject::getInnerData, InputDataObject.InnerData::getIndex, inputData).orElse(null);

        // then
        assertThat(first)
                .extracting(InputDataObject.InnerData::getIndex, InputDataObject.InnerData::getValue)
                .containsExactly(1, "1");
    }

    // -------------------- PRIVATE --------------------

    private InputDataObject createInputData() {
        InputDataObject inputDataObject = new InputDataObject();
        List<InputDataObject.InnerData> innerData = inputDataObject.getInnerData();

        innerData.add(new InputDataObject.InnerData(1, "1"));
        innerData.add(new InputDataObject.InnerData(3, "3"));
        innerData.add(new InputDataObject.InnerData(2, "2"));

        return inputDataObject;
    }

    // -------------------- INNER CLASSES --------------------

    static class InputDataObject {
        private List<InnerData> innerData = new ArrayList<>();

        public List<InnerData> getInnerData() {
            return innerData;
        }

        static class InnerData {
            private Integer index;
            private String value;

            public InnerData(Integer index, String value) {
                this.index = index;
                this.value = value;
            }

            public Integer getIndex() {
                return index;
            }

            public String getValue() {
                return value;
            }
        }
    }
}