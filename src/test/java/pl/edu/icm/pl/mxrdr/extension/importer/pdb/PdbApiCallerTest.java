package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.http.client.HttpResponseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.Dataset;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pl.edu.icm.pl.mxrdr.extension.importer.pdb.PdbApiCaller.CUSTOM_REPORT_COLUMNS_PARAM_NAME;
import static pl.edu.icm.pl.mxrdr.extension.importer.pdb.PdbApiCaller.PDB_IDS_PARAM_NAME;
import static pl.edu.icm.pl.mxrdr.extension.importer.pdb.PdbApiCaller.PDB_REPORT_ENDPOINT;
import static pl.edu.icm.pl.mxrdr.extension.importer.pdb.PdbApiCaller.PDB_REPORT_COLUMNS;
import static pl.edu.icm.pl.mxrdr.extension.importer.pdb.PdbApiCaller.PRIMARY_ONLY_PARAM_NAME;

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

    @Test
    public void fetchPdbData() {
        // given
        String structureId = "x";
        wireMockServer.stubFor(get(urlPathEqualTo(PDB_REPORT_ENDPOINT))
                .withQueryParam(PDB_IDS_PARAM_NAME, equalTo(structureId))
                .withQueryParam(PRIMARY_ONLY_PARAM_NAME, equalTo("1"))
                .withQueryParam(CUSTOM_REPORT_COLUMNS_PARAM_NAME, equalTo(PDB_REPORT_COLUMNS))
                .willReturn(aResponse().withBody(generateSampleXmlBody())));
        // when
        Optional<Dataset> result = pdbApiCaller.getStructureData(structureId);
        // then
        assertThat(result).isPresent();
        result.map(d -> d.recordStream().collect(toList())).ifPresent(records -> {
            assertThat(records.size()).isEqualTo(1);
            assertThat(records.get(0).getPdbDoi()).isEqualTo("10.9999/testiwr/pdb");
            assertThat(records.get(0).getPubmedId()).isEqualTo("23269141");
        });
    }

    @Test
    public void fetchPdbData_withNegativeStatusCode() {
        // given
        String structureId = "y";
        wireMockServer.stubFor(get(urlPathEqualTo(PDB_REPORT_ENDPOINT))
                .withQueryParam(PDB_IDS_PARAM_NAME, equalTo(structureId))
                .willReturn(aResponse().withBody("").withStatus(500)));
        // expect
        assertThatThrownBy(() -> pdbApiCaller.getStructureData(structureId))
                .isInstanceOf(RuntimeException.class)
                .getCause()
                .isInstanceOf(HttpResponseException.class)
                .hasMessage("Request failed with status: 500 and message: Server Error");
    }

    @Test
    public void fetchPdbData_withNoContentEntity() {
        // given
        String structureId = "z";
        wireMockServer.stubFor(get(urlPathEqualTo(PDB_REPORT_ENDPOINT))
                .withQueryParam(PDB_IDS_PARAM_NAME, equalTo(structureId))
                .willReturn(aResponse().withHeader("Content-Length", "0").withBody("")));
        // when
        Optional<Dataset> result = pdbApiCaller.getStructureData(structureId);
        // then
        assertThat(result).isNotPresent();
    }

    @Test
    public void fetchPdbData_withEmptyEntity() {
        // given
        String structureId = "z";
        wireMockServer.stubFor(get(urlPathEqualTo(PDB_REPORT_ENDPOINT))
                .withQueryParam(PDB_IDS_PARAM_NAME, equalTo(structureId))
                .willReturn(aResponse().withBody(generateEmptyXmlBody())));
        // when
        Optional<Dataset> result = pdbApiCaller.getStructureData(structureId);
        // then
        assertThat(result).isNotPresent();
    }

    // -------------------- PRIVATE --------------------

    private String generateSampleXmlBody() {
        return "<?xml version='1.0' standalone='no' ?>\n" +
                "<dataset>\n" +
                "    <record>\n" +
                "        <dimStructure.pdbDoi>10.9999/testiwr/pdb</dimStructure.pdbDoi>\n" +
                "        <dimStructure.pubmedId>23269141</dimStructure.pubmedId>\n" +
                "    </record>\n" +
                "</dataset>\n";
    }

    private String generateEmptyXmlBody() {
        return "<?xml version='1.0' standalone='no' ?>\n" +
                "<dataset />\n";
    }
}
