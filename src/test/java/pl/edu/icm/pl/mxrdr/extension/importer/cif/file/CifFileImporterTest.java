package pl.edu.icm.pl.mxrdr.extension.importer.cif.file;


import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class CifFileImporterTest {

    File input;
    List<ResultField> result;

    @BeforeEach
    public void setUp() throws URISyntaxException {
        input = new File(getClass().getClassLoader().getResource("cif/1t1l.cif").toURI());
        result = new CifFileImporter().fetchMetadata(singletonMap(CifFileFormKeys.CIF_FILE, input));
    }

    @Test
    @DisplayName("Should select proper citation data: authors")
    public void shouldSelectProperCitationData_authors() {
        // given & when
        List<String> citationAuthors = filterResultToValuesList(MxrdrMetadataField.CITATION_AUTHOR);

        // then
        assertThat(citationAuthors).containsExactlyInAnyOrder("van den Berg, B.", "Black, P.N.",
                "Clemons Jr., W.M.", "Rapoport, T.A.");
    }

    @Test
    @DisplayName("Should select proper citation data: year")
    public void shouldSelectProperCitationData_year() {
        // given & when
        List<String> citationYear = filterResultToValuesList(MxrdrMetadataField.CITATION_YEAR);

        // then
        assertThat(citationYear).containsExactly("2004");
    }

    @Test
    @DisplayName("Should place structure authors in different fields")
    public void shouldPlaceStructureAuthorsInDifferentFields() {
        // given & when
        List<String> structureAuthors = filterResultToValuesList(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR);

        // then
        assertThat(structureAuthors).containsExactlyInAnyOrder("van den Berg, B.", "Black, P.N.",
                "Clemons Jr., W.M.", "Rapoport, T.A.");
    }

    @Test
    @DisplayName("Should concatenate data in chosen fields: e.g. beamline")
    public void shouldConcatenateData() {
        // given & when
        List<String> beamline = filterResultToValuesList(MxrdrMetadataField.BEAMLINE);

        // then
        assertThat(beamline).containsExactly("8-BM; X25");
    }

    @Test
    @DisplayName("Should create separate fields for chosen compound fields: e.g. dataCollection")
    public void shouldCreateSeparateFieldsForChosenCompounds() {
        // given & when
        List<Tuple3<String, String, List<ResultField>>> dataCollectionFields =
                filterResultToTuples(MxrdrMetadataField.DATA_COLLECTION)
                        .collect(toList());

        // then
        assertThat(dataCollectionFields).hasSize(2);
    }

    @Test
    @DisplayName("Should select proper release date")
    public void shouldSelectProperReleaseDate() {
        // given & when
        List<String> releaseDate = filterResultToValuesList(MxrdrMetadataField.PDB_RELEASE_DATE);

        // then
        assertThat(releaseDate).containsExactly("2004-06-15");
    }

    @Test
    @DisplayName("Should select proper revision date")
    public void shouldSelectProperRevisionDate() {
        // given & when
        List<String> releaseDate = filterResultToValuesList(MxrdrMetadataField.PDB_REVISION_DATE);

        // then
        assertThat(releaseDate).containsExactly("2011-07-13");
    }

    @Test
    @DisplayName("Should properly map software vocabulary")
    public void shouldProperlyMapSoftware() {
        // given & when
        List<String> software = filterResultToTuples(MxrdrMetadataField.PROCESSING_SOFTWARE)
                .flatMap(t -> t._3.stream())
                .map(ResultField::getValue)
                .collect(toList());

        // then
        assertThat(software).containsExactlyInAnyOrder("HKL2000/HKL3000", "Other");
    }

    // -------------------- PRIVATE --------------------

    private Stream<Tuple3<String, String, List<ResultField>>> filterResultToTuples(MxrdrMetadataField filterBy) {
        return result.stream()
                .map(r -> Tuple.of(r.getName(), r.getValue(), r.getChildren()))
                .filter(t -> filterBy.getValue().equals(t._1));
    }

    private List<String> filterResultToValuesList(MxrdrMetadataField pdbRevisionDate) {
        return filterResultToTuples(pdbRevisionDate)
                .map(t -> t._2)
                .collect(toList());
    }
}