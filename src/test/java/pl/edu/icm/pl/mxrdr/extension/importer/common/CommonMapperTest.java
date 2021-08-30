package pl.edu.icm.pl.mxrdr.extension.importer.common;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.PdbDataContainer;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.StructureDataProvider;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class CommonMapperTest {

    private BaseDataContainer<?> container;
    private CommonMapper mapper;

    @BeforeEach
    void setUp() {
        container = new BaseDataContainer<>();
        mapper = new CommonMapper(container);
    }

    // -------------------- TESTS --------------------

    @Test
    @DisplayName("Should create ResultFields from filled container")
    void toResultFields() {
        // given
        PdbDataContainer pdbDataContainer = new PdbDataContainer().init(new StructureDataProvider().createStructureData(), null);
        mapper = new CommonMapper(pdbDataContainer);

        // when
        List<ResultField> resultFields = mapper.toResultFields();

        // then
        assertThat(resultFields).filteredOn(f -> "pdbId".equals(f.getName()))
                .extracting(ResultField::getValue)
                .containsExactly("1ZZZ");
        assertThat(resultFields).filteredOn(f -> "pdbStructureAuthor".equals(f.getName()))
                .extracting(ResultField::getValue)
                .containsExactly("First, Author", "Second, Author", "Third, Author");
        assertThat(resultFields).filteredOn(f -> "unitCellParameters".equals(f.getName()))
                .flatExtracting(ResultField::getChildren)
                .extracting(ResultField::getName, ResultField::getValue)
                .containsExactly(
                        tuple("unitCellParameterA", "104.81"),
                        tuple("unitCellParameterB", "138.62"),
                        tuple("unitCellParameterC", "107.25"),
                        tuple("unitCellParameterAlpha", "90.0"),
                        tuple("unitCellParameterBeta", "117.21"),
                        tuple("unitCellParameterGamma", "90.0"));
        assertThat(resultFields).filteredOn(f -> "entity".equals(f.getName()))
                .flatExtracting(ResultField::getChildren)
                .extracting(ResultField::getName, ResultField::getValue)
                .containsExactly(
                        tuple("entityId", "1"), tuple("entitySequence", "QWERTY1"),
                        tuple("entityId", "2"), tuple("entitySequence", "QWERTY2"));
        assertThat(resultFields).filteredOn(f -> "processingSoftware".equals(f.getName()))
                .flatExtracting(ResultField::getChildren)
                .extracting(ResultField::getValue)
                .containsExactly("XDS");
    }

    @Test
    @DisplayName("Should create single ResultField with value")
    void single() {
        // given
        container.add(MxrdrMetadataField.PDB_ID, "1ZZZ");

        // when
        ResultField single = mapper.single(MxrdrMetadataField.PDB_ID);

        // then
        assertThat(single.getName()).isEqualTo("pdbId");
        assertThat(single.getValue()).isEqualTo("1ZZZ");
        assertThat(single.getChildren()).isEmpty();
    }

    @Test
    @DisplayName("Should create single compound ResultField with nested values")
    void singleCompound() {
        // given
        container.add(MxrdrMetadataField.UNIT_CELL_PARAMETER_A, "1");
        container.add(MxrdrMetadataField.UNIT_CELL_PARAMETER_B, "2");

        // when
        ResultField singleCompound =
                mapper.singleCompound(MxrdrMetadataField.UNIT_CELL_PARAMETERS, MxrdrMetadataField.UNIT_CELL_PARAMETER_A,
                        MxrdrMetadataField.UNIT_CELL_PARAMETER_B);

        // then
        assertThat(singleCompound.getName()).isEqualTo("unitCellParameters");
        assertThat(singleCompound.getChildren())
                .extracting(ResultField::getName, ResultField::getValue)
                .containsExactly(
                        tuple("unitCellParameterA", "1"),
                        tuple("unitCellParameterB", "2"));
    }

    @Test
    @DisplayName("Should create multiple ResultFields")
    void multi() {
        // given
        container.addAll(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR, Stream.of("Author 1", "Author 2"));

        // when
        List<ResultField> multi = mapper.multi(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR);

        // then
        assertThat(multi).extracting(ResultField::getName, ResultField::getValue)
                .containsExactly(
                        tuple("pdbStructureAuthor", "Author 1"),
                        tuple("pdbStructureAuthor", "Author 2"));
    }

    @Test
    @DisplayName("Should create multiple compound ResultFields with nested values")
    void multiCompound() {
        // given
        container.addAll(MxrdrMetadataField.ENTITY_ID, Stream.of("1", "2"));
        container.addAll(MxrdrMetadataField.ENTITY_SEQUENCE, Stream.of("QWERTY1", "QWERTY2"));

        // when
        List<ResultField> multiCompound
                = mapper.multiCompound(MxrdrMetadataField.ENTITY, MxrdrMetadataField.ENTITY_ID, MxrdrMetadataField.ENTITY_SEQUENCE);

        // then
        assertThat(multiCompound).extracting(ResultField::getName)
                .allMatch("entity"::equals);
        assertThat(multiCompound).flatExtracting(ResultField::getChildren)
                .extracting(ResultField::getName, ResultField::getValue)
                .containsExactly(
                        tuple("entityId", "1"), tuple("entitySequence", "QWERTY1"),
                        tuple("entityId", "2"), tuple("entitySequence", "QWERTY2"));
    }

    @Test
    @DisplayName("Should create vocabulary ResultField")
    void vocabulary() {
        // given
        container.addAll(MxrdrMetadataField.PROCESSING_SOFTWARE, Stream.of("A", "B"));

        // when
        ResultField vocabulary = mapper.vocabulary(MxrdrMetadataField.PROCESSING_SOFTWARE);

        // then
        assertThat(vocabulary.getName()).isEqualTo("processingSoftware");
        assertThat(vocabulary.getChildren()).extracting(ResultField::getName, ResultField::getValue)
                .containsExactly(
                        tuple(StringUtils.EMPTY, "A"),
                        tuple(StringUtils.EMPTY, "B"));
    }

    @Test
    @DisplayName("Should create empty single/vocabulary field when there is no data or no proper data for the given key")
    void emptySingle() {
        // given & when
        ResultField single = mapper.single(MxrdrMetadataField.ENTITY_COUNT);
        ResultField singleCompound = mapper.singleCompound(MxrdrMetadataField.UNIT_CELL_PARAMETERS,
                MxrdrMetadataField.UNIT_CELL_PARAMETER_A, MxrdrMetadataField.UNIT_CELL_PARAMETER_B);
        ResultField vocabulary = mapper.vocabulary(MxrdrMetadataField.PROCESSING_SOFTWARE);

        // then
        assertThat(Stream.of(single, singleCompound, vocabulary))
                .allMatch(f -> StringUtils.EMPTY.equals(f.getName()) && StringUtils.EMPTY.equals(f.getValue()));
    }

    @Test
    @DisplayName("Should create empty list for multiple field when there is no data or no proper data for the given key")
    void emptyMulti() {
        // given & when
        List<ResultField> multi = mapper.multi(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR);
        List<ResultField> multiCompound = mapper.multiCompound(MxrdrMetadataField.ENTITY, MxrdrMetadataField.ENTITY_ID, MxrdrMetadataField.ENTITY_SEQUENCE);

        // then
        assertThat(multi).isEmpty();
        assertThat(multiCompound).isEmpty();
    }
}