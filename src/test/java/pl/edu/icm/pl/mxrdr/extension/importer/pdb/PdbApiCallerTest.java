package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.client.HttpResponseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.StructureData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PdbApiCallerTest {

    static WireMockServer wireMockServer;

    PdbApiCaller pdbApiCaller;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();
    }

    @BeforeEach
    void setUp() {
        wireMockServer.resetAll();
        pdbApiCaller = new PdbApiCaller(wireMockServer.baseUrl());
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    // -------------------- TESTS --------------------

    @Test
    @DisplayName("Proper response should produce filled StructureData")
    public void fetchPdbData() {
        // given
        String structureId = "1ZZZ";
        wireMockServer.stubFor(get(urlPathEqualTo(generateUrl(PdbApiCaller.ENTRY_PATH, structureId)))
                .willReturn(aResponse().withBody(loadResponse("pdb/entry.json"))));
        wireMockServer.stubFor(get(urlPathEqualTo(generateUrl(PdbApiCaller.POLYMER_ENTITY_PATH, structureId, "1")))
                .willReturn(aResponse().withBody(loadResponse("pdb/polymer_1.json"))));
        wireMockServer.stubFor(get(urlPathEqualTo(generateUrl(PdbApiCaller.POLYMER_ENTITY_PATH, structureId, "2")))
                .willReturn(aResponse().withBody(loadResponse("pdb/polymer_2.json"))));

        // when
        StructureData structureData = pdbApiCaller.getStructureData(structureId);

        // then
        assertThat(structureData.getEntryData().getRcsbId()).isEqualTo(structureId);
        assertThat(structureData.getPolymerEntities()).hasSize(2);
    }

    @Test
    @DisplayName("Server error should cause an exception to be thrown")
    public void fetchPdbData_withNegativeStatusCode() {
        // given
        String structureId = "YYYY";
        wireMockServer.stubFor(get(urlPathEqualTo(generateUrl(PdbApiCaller.ENTRY_PATH, structureId)))
                .willReturn(aResponse().withBody("").withStatus(500)));

        // when & then
        assertThatThrownBy(() -> pdbApiCaller.getStructureData(structureId))
                .isInstanceOf(RuntimeException.class)
                .getCause()
                .isInstanceOf(HttpResponseException.class)
                .hasMessageEndingWith("Request failed with status: 500 and message: Server Error");
    }

    @Test
    @DisplayName("Error 404 should cause an exception to be thrown")
    public void fetchPdbData_withEmptyEntity() {
        // given
        String structureId = "QWERTY";
        wireMockServer.stubFor(get(urlPathEqualTo(generateUrl(PdbApiCaller.ENTRY_PATH, structureId)))
                .willReturn(aResponse().withBody(notFoundResponse()).withStatus(404)));

        // when & then
        assertThatThrownBy(() -> pdbApiCaller.getStructureData(structureId))
                .isInstanceOf(RuntimeException.class)
                .getCause()
                .isInstanceOf(HttpResponseException.class)
                .hasMessageEndingWith("Request failed with status: 404 and message: Not Found");
    }

    // -------------------- PRIVATE --------------------

    private String generateUrl(String... segments) {
        return Stream.concat(Stream.of(""), Arrays.stream(segments))
                .collect(Collectors.joining("/"));
    }

    private String loadResponse(String responseFileName) {
        try {
            URI fileUri = getClass().getClassLoader().getResource(responseFileName).toURI();
            return Files.lines(Paths.get(fileUri))
                    .collect(Collectors.joining());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String notFoundResponse() {
        return "{" +
                    "\"status\":404," +
                    "\"message\":\"No data found for entryId: QWERTY\"," +
                    "\"link\":\"https://data.rcsb.org/redoc/index.html\"" +
                "}";
    }
}