package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.common.collect.Lists;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.pojo.PdbDataset;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class PdbApiCallerTest {

    private static final String PDB_URL = "http://localhost:8089";

    private WireMockServer wireMockServer;

    @BeforeEach
    public void before() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
    }

    @AfterEach
    public void after() {
        wireMockServer.stop();
    }

    @Test
    public void fetchPdbData() {
        //given
        PdbApiCaller pdbApiCaller = new PdbApiCaller(PDB_URL);
        BasicNameValuePair requestParams = new BasicNameValuePair("pdbid", "1");

        //when
        wireMockServer.stubFor(get(urlEqualTo(pdbApiCaller.getPdbEndpoint() + "?" + requestParams.getName() + "=" + requestParams
                .getValue()))
                                       .willReturn(aResponse().withBody(generateTestXmlBody())));

        PdbDataset pdbDs = pdbApiCaller.fetchPdbData(Lists.newArrayList(requestParams));

        //then
        Assertions.assertEquals(1, pdbDs.getRecords().size());
        Assertions.assertEquals("10.9999/testiwr/pdb", pdbDs.getRecords().get(0).getPdbDoi());
        Assertions.assertEquals("23269141", pdbDs.getRecords().get(0).getPubmedId());
    }

    @Test
    public void fetchPdbData_withNegativeStatusCode() {
        //given
        PdbApiCaller pdbApiCaller = new PdbApiCaller(PDB_URL);
        BasicNameValuePair requestParams = new BasicNameValuePair("pdbid", "1");

        //when
        wireMockServer.stubFor(get(urlEqualTo(pdbApiCaller.getPdbEndpoint() + "?" + requestParams.getName() + "=" + requestParams.getValue()))
                                       .willReturn(aResponse().withBody("")
                                                              .withStatus(500)));

        //when & then
        Assertions.assertThrows(IllegalStateException.class, () -> pdbApiCaller.fetchPdbData(Lists.newArrayList(requestParams)));
    }

    @Test
    public void fetchPdbData_withEmptyEntity() {
        //given
        PdbApiCaller pdbApiCaller = new PdbApiCaller(PDB_URL);
        BasicNameValuePair requestParams = new BasicNameValuePair("pdbid", "1");

        //when
        wireMockServer.stubFor(get(urlEqualTo(pdbApiCaller.getPdbEndpoint() + "?" + requestParams.getName() + "=" + requestParams.getValue()))
                                       .willReturn(aResponse().withBody("")));

        //when & then
        Assertions.assertThrows(IllegalStateException.class, () -> pdbApiCaller.fetchPdbData(Lists.newArrayList(requestParams)));
    }

    // -------------------- PRIVATE --------------------

    private String generateTestXmlBody() {
        return "<dataset><record><dimStructure.pdbDoi>10.9999/testiwr/pdb</dimStructure.pdbDoi><dimStructure.pubmedId>23269141</dimStructure.pubmedId>" +
                "</record></dataset>";
    }
}
