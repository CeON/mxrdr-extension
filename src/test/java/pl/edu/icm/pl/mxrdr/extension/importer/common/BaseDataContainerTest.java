package pl.edu.icm.pl.mxrdr.extension.importer.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BaseDataContainerTest {

    private BaseDataContainer<?> container;

    @BeforeEach
    void setUp() {
        container = new BaseDataContainer<>();
    }

    // -------------------- TESTS --------------------

    @Test
    @DisplayName("Should add and retrieve single added elements")
    void addAndRetrieve() {
        // given & when
        container.add(MxrdrMetadataField.UNIT_CELL_PARAMETER_A, "A")
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_B, Optional.of("B"));

        String elementA = container.get(MxrdrMetadataField.UNIT_CELL_PARAMETER_A).orElse(null);
        String elementB = container.get(MxrdrMetadataField.UNIT_CELL_PARAMETER_B).orElse(null);

        // then
        assertThat(elementA).isEqualTo("A");
        assertThat(elementB).isEqualTo("B");
    }

    @Test
    @DisplayName("Should add first element from the stream")
    void addSingleFromStream() {
        // given
        container.add(MxrdrMetadataField.PDB_TITLE, Stream.of("Title 1", "Title 2"));

        // when
        List<String> titles = container.getAll(MxrdrMetadataField.PDB_TITLE);

        // then
        assertThat(titles).containsExactly("Title 1");
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
}