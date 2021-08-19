package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.PdbDataContainerTest.InputDataObject.InnerData;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.StructureData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.list;

class PdbDataContainerTest {

    private PdbDataContainer container;

    @BeforeEach
    void setUp() {
        container = new PdbDataContainer();
    }

    // -------------------- TESTS --------------------

    @Test
    @DisplayName("Should fill container from StructureData with first diffraction data set on default " +
            "choosing properly the highest resolution shell")
    void fillContainer() throws Exception {
        // given
        StructureData structureData = new StructureDataProvider().createStructureData();

        // when
        container.init(structureData, null);

        // then
        assertThat(container.get(MxrdrMetadataField.PDB_ID)).hasValue("1ZZZ");
        assertThat(container.getAll(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR))
                .containsExactly("First, Author", "Second, Author", "Third, Author");
        assertThat(container.get(MxrdrMetadataField.BEAMLINE)).hasValue("19-ID");
        assertThat(container.get(MxrdrMetadataField.PROCESSING_SOFTWARE)).hasValue("XDS");
        assertThat(container.get(MxrdrMetadataField.OVERALL_NUMBER_OBSERVED_REFLECTIONS)).hasValue("171460");
        assertThat(container.get(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH)).hasValue("2.54");
        assertThat(container.get(MxrdrMetadataField.HRS_I_SIGMA)).hasValue("1.26");
        assertThat(container.get(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_HIGH)).hasValue("2.54");
        assertThat(container.get(MxrdrMetadataField.PDB_DEPOSIT_DATE)).hasValue("2019-01-30");
        assertThat(container.getAll(MxrdrMetadataField.ENTITY_ID)).containsExactly("1", "2");
        assertThat(container.getAll(MxrdrMetadataField.ENTITY_SEQUENCE)).containsExactly("QWERTY1", "QWERTY2");
        assertThat(container.get(MxrdrMetadataField.MACROMOLLECULE_TYPE)).hasValue("Protein");
    }

    @Test
    @DisplayName("Should fill container from StructureData with second diffraction data set on demand " +
            "choosing properly the highest resolution shell")
    void fillContainerWithGivenIndex() {
        // given
        StructureData structureData = new StructureDataProvider().createStructureData();

        // when
        container.init(structureData, "2");

        // then
        assertThat(container.get(MxrdrMetadataField.PDB_ID)).hasValue("1ZZZ");
        assertThat(container.getAll(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR))
                .containsExactly("First, Author", "Second, Author", "Third, Author");
        assertThat(container.get(MxrdrMetadataField.BEAMLINE)).hasValue(StringUtils.EMPTY);
        assertThat(container.get(MxrdrMetadataField.PROCESSING_SOFTWARE)).hasValue("XDS");
        assertThat(container.get(MxrdrMetadataField.OVERALL_NUMBER_OBSERVED_REFLECTIONS)).hasValue("271460");
        assertThat(container.get(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH)).hasValue("3.54");
        assertThat(container.get(MxrdrMetadataField.HRS_I_SIGMA)).hasValue("2.26");
        assertThat(container.get(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_HIGH)).hasValue("3.54");
        assertThat(container.get(MxrdrMetadataField.PDB_DEPOSIT_DATE)).hasValue("2019-01-30");
        assertThat(container.getAll(MxrdrMetadataField.ENTITY_ID)).containsExactly("1", "2");
        assertThat(container.getAll(MxrdrMetadataField.ENTITY_SEQUENCE)).containsExactly("QWERTY1", "QWERTY2");
        assertThat(container.get(MxrdrMetadataField.MACROMOLLECULE_TYPE)).hasValue("Protein");
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
    @DisplayName("getIndexed(…) should get data with the given index from the inner collection")
    void getIndexed() {
        // given
        InputDataObject inputData = createInputData();

        // when
        InnerData innerData
                = PdbDataContainer.getIndexed(InputDataObject::getInnerData, InnerData::getIndex, 1, inputData)
                    .orElse(null);

        // then
        assertThat(innerData).extracting(InnerData::getIndex, InnerData::getValue)
                .containsExactly(1, "1");
    }

    @Test
    @DisplayName("getIndexed(…) should return the only element of the inner collection if and only if there's " +
            "an empty index field and the first index is requested")
    void getIndexed__firstIndexWithEmptyIndexField() {
        // given
        InputDataObject unindexedData = new InputDataObject(new InnerData(null, "1"));
        InputDataObject multipleUnindexedData = new InputDataObject(new InnerData(null, "a"),
                new InnerData(null, "b"));
        // when
        InnerData data1 = PdbDataContainer.getIndexed(InputDataObject::getInnerData, InnerData::getIndex, 1, unindexedData)
                .orElse(null);
        InnerData data2 = PdbDataContainer.getIndexed(InputDataObject::getInnerData, InnerData::getIndex, 1, multipleUnindexedData)
                .orElse(null);
        InnerData data3 = PdbDataContainer.getIndexed(InputDataObject::getInnerData, InnerData::getIndex, 2, unindexedData)
                .orElse(null);

        // then
        assertThat(data1.getValue()).isEqualTo("1");
        assertThat(data2).isNull();
        assertThat(data3).isNull();
    }

    @Test
    @DisplayName("getIndexedForIndexOnList(…) should get data with the given index from the inner collection")
    void getIndexedForIndexOnList() {
        // given
        InputDataObject inputData = createInputData();

        // when
        InnerData first = PdbDataContainer.getIndexedForIndexOnList(InputDataObject::getInnerData, InnerData::getMultiIndex, 1, inputData)
                .orElse(null);
        InnerData third = PdbDataContainer.getIndexedForIndexOnList(InputDataObject::getInnerData, InnerData::getMultiIndex, 3, inputData)
                .orElse(null);

        // then
        assertThat(first).extracting(InnerData::getMultiIndex, InnerData::getValue)
                .containsExactly(list(1, 2), "1");
        assertThat(third).extracting(InnerData::getMultiIndex, InnerData::getValue)
                .containsExactly(list(3), "2");
    }

    @Test
    @DisplayName("getIndexedForIndexOnList(…) should return the only element of the inner collection if and only if there's " +
            "an empty index list and the first index is requested")
    void getIndexedForIndexOnList__firstIndexWithEmptyIndexField() {
        // given
        InputDataObject unindexedData = new InputDataObject(new InnerData( "1"));
        InputDataObject multipleUnindexedData = new InputDataObject(new InnerData( "a"),
                new InnerData("b"));
        // when
        InnerData data1 = PdbDataContainer.getIndexedForIndexOnList(InputDataObject::getInnerData, InnerData::getMultiIndex, 1, unindexedData)
                .orElse(null);
        InnerData data2 = PdbDataContainer.getIndexedForIndexOnList(InputDataObject::getInnerData, InnerData::getMultiIndex, 1, multipleUnindexedData)
                .orElse(null);
        InnerData data3 = PdbDataContainer.getIndexedForIndexOnList(InputDataObject::getInnerData, InnerData::getMultiIndex, 2, unindexedData)
                .orElse(null);

        // then
        assertThat(data1.getValue()).isEqualTo("1");
        assertThat(data2).isNull();
        assertThat(data3).isNull();
    }

    // -------------------- PRIVATE --------------------

    private InputDataObject createInputData() {
        InputDataObject inputDataObject = new InputDataObject();
        List<InnerData> innerData = inputDataObject.getInnerData();

        innerData.add(new InnerData(1, "1"));
        innerData.add(new InnerData(2, "2"));
        innerData.add(new InnerData(3, "3"));

        innerData.get(0).getMultiIndex().add(1);
        innerData.get(0).getMultiIndex().add(2);
        innerData.get(1).getMultiIndex().add(3);

        return inputDataObject;
    }

    // -------------------- INNER CLASSES --------------------

    static class InputDataObject {
        private List<InnerData> innerData = new ArrayList<>();

        public List<InnerData> getInnerData() {
            return innerData;
        }

        public InputDataObject() { }

        public InputDataObject(InnerData... innerData) {
            this.innerData.addAll(Arrays.asList(innerData));
        }

        static class InnerData {
            private Integer index;
            private List<Integer> multiIndex = new ArrayList<>();
            private String value;

            public InnerData(Integer index, String value) {
                this.index = index;
                this.value = value;
            }

            public InnerData(String value, Integer... indexes) {
                this.multiIndex.addAll(Arrays.asList(indexes));
                this.value = value;
            }

            public Integer getIndex() {
                return index;
            }

            public String getValue() {
                return value;
            }

            public List<Integer> getMultiIndex() {
                return multiIndex;
            }
        }
    }
}