package pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal;


import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rcsb.cif.model.StrColumn;
import org.rcsb.cif.schema.mm.Entry;
import org.rcsb.cif.schema.mm.MmCifBlock;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal.CifFileImporterTestHelper.collectToList;

@ExtendWith(MockitoExtension.class)
class CifMetadataReaderTest {

    private static final String VALUE = "A";

    @Mock
    private MmCifBlock mainBlock;

    @Mock
    private Entry testCategory;

    @Mock
    private StrColumn testColumn;

    @InjectMocks
    private CifMetadataReader metadataReader = new CifMetadataReader();

    @Test
    @DisplayName("Should read values from parsed result changing unknowns into empty strings")
    public void shouldReturnValues() {
        // given
        String[] input = new String[] { VALUE, "?", VALUE, VALUE, "." };
        setUpColumn(true);
        Mockito.when(testColumn.stringData()).thenReturn(Arrays.stream(input));

        // when
        List<String> result = metadataReader.readValues(MmCifBlock::getEntry, Entry::getId);

        // then
        String[] expected = new String[] { VALUE, StringUtils.EMPTY, VALUE, VALUE, StringUtils.EMPTY};
        assertThat(result, contains(expected));
    }

    @Test
    @DisplayName("Should return empty list when the column is not defined in parsed result")
    public void shouldReturnEmptyListWhenNoColumnDefined() {
        // given
        setUpColumn(false);

        // when
        List<String> result = metadataReader.readValues(MmCifBlock::getEntry, Entry::getId);

        // then
        assertThat(result, empty());
    }

    @Test
    @DisplayName("Should be able to zip lists of different sizes supplying empty string when no item exists")
    public void shouldZipListsProperly() {
        // given
        List<String> first = collectToList(VALUE, VALUE, VALUE);
        List<String> second = collectToList(VALUE, VALUE, VALUE, VALUE, VALUE);
        String delimiter = "-";

        // when
        List<String> result = metadataReader.zipLists(first, second, delimiter);

        // then
        String[] expected = new String[] {
                VALUE + delimiter + VALUE,
                VALUE + delimiter + VALUE,
                VALUE + delimiter + VALUE,
                delimiter + VALUE,
                delimiter + VALUE };
        assertThat(result, contains(expected));
    }

    // -------------------- PRIVATE --------------------

    private void setUpColumn(boolean hasDefinedValues) {
        Mockito.when(mainBlock.getEntry()).thenReturn(testCategory);
        Mockito.when(testCategory.getId()).thenReturn(testColumn);
        Mockito.when(testColumn.isDefined()).thenReturn(hasDefinedValues);
    }
}