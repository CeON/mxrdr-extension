package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.pojo.PdbDataset;

import javax.inject.Singleton;
import java.net.URI;
import java.util.List;

@Singleton
public class PdbApiCaller {
    public static final int TEN_SECONDS = 10000;
    private String schemeWithHostname = "";
    private final String pdbEndpoint = "/pdb/rest/customReport.xml";

    // -------------------- CONSTRUCTORS --------------------

    public PdbApiCaller() {
        schemeWithHostname = "https://www.rcsb.org";
    }

    public PdbApiCaller(String schemeWithHostname) {
        this.schemeWithHostname = schemeWithHostname;
    }

    // -------------------- GETTERS --------------------

    public String getPdbEndpoint() {
        return pdbEndpoint;
    }


    // -------------------- LOGIC --------------------

    /**
     * Created to mainly call protein data bank in order to retrieve entity xml.
     * Watch out the api doesn't work very well, it returns 200 even if it can't find the entity.
     */
    PdbDataset fetchPdbData(List<NameValuePair> queryParameters) {
        PdbDataset pdbDataset;

        try {
            URI builtQuery = new URIBuilder(schemeWithHostname + pdbEndpoint)
                    .addParameters(queryParameters)
                    .build();

            HttpResponse pdbRespone = Request.Get(builtQuery)
                                             .connectTimeout(TEN_SECONDS)
                                             .execute()
                                             .returnResponse();


            pdbDataset = parseApiResponse(pdbRespone);
            StatusLine status = pdbRespone.getStatusLine();

            if (status.getStatusCode() > 200 || pdbDataset.getRecords().isEmpty()) {
                throw new IllegalStateException("Retrieving entity from pdb failed with status: " + status.getStatusCode() +
                                                        " and message: " + status.getReasonPhrase());
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

        return pdbDataset;
    }

    // -------------------- PRIVATE --------------------

    private PdbDataset parseApiResponse(HttpResponse pdbRespone) throws java.io.IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return xmlMapper. readValue(pdbRespone.getEntity().getContent(), PdbDataset.class);
    }
}
