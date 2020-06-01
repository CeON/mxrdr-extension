package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.Dataset;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Singleton
public class PdbApiCaller {

    private static final int CONNECTION_TIMEOUT_MILLIS = 10_000;

    static final String PDB_REPORT_ENDPOINT = "/pdb/rest/customReport.xml";
    static final String PDB_IDS_PARAM_NAME = "pdbids";
    static final String PRIMARY_ONLY_PARAM_NAME = "primaryOnly";
    static final String CUSTOM_REPORT_COLUMNS_PARAM_NAME = "customReportColumns";
    static final String PDB_REPORT_COLUMNS =
            "structureId,collectionTemperature,sequence,macromoleculeType,structureMolecularWeight," +
            "spaceGroup,lengthOfUnitCellLatticeA,lengthOfUnitCellLatticeB,lengthOfUnitCellLatticeC," +
            "unitCellAngleAlpha,unitCellAngleBeta,unitCellAngleGamma,resolution,name," +
            "residueCount,atomSiteCount,structureTitle,pdbDoi,structureAuthor,depositionDate," +
            "releaseDate,revisionDate,experimentalTechnique,title,pubmedId,citationAuthor," +
            "journalName,publicationYear";

    private final String baseUrl;
    private final XmlMapper xmlMapper;

    // -------------------- CONSTRUCTORS --------------------

    public PdbApiCaller() {
        this("https://www.rcsb.org");
    }

    PdbApiCaller(String baseUrl) {
        this.baseUrl = baseUrl;
        this.xmlMapper = new XmlMapper();
        this.xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // -------------------- LOGIC --------------------

    /**
     * Obtains PDB data for given protein. Also accounts for a fact, that instead of standard HTTP
     * <code>404 Not Found</code>, the API returns an empty XML to represent unknown structure identifiers.
     * @param structureId PDB identifier of the structure to get report for.
     * @return PDB report for given structure if found.
     */
    Optional<Dataset> getStructureData(String structureId) {
        URI reportUri = new SafeUriBuilder(baseUrl).buildWith(builder -> builder
                .setPath(PDB_REPORT_ENDPOINT)
                .addParameter(PDB_IDS_PARAM_NAME, structureId)
                .addParameter(PRIMARY_ONLY_PARAM_NAME, "1")
                .addParameter(CUSTOM_REPORT_COLUMNS_PARAM_NAME, PDB_REPORT_COLUMNS));

        return ofNullable(executeForObject(Request::Get, reportUri, Dataset.class))
                .filter(Dataset::hasRecords);
    }

    // -------------------- PRIVATE --------------------

    private <T> T executeForObject(Function<URI, Request> request, URI uri, Class<T> resultType) {
        try {
            return request.apply(uri)
                    .connectTimeout(CONNECTION_TIMEOUT_MILLIS)
                    .execute()
                    .handleResponse(new XmlMappingResponseHandler<>(resultType));
        } catch (IOException e) {
            throw new RuntimeException("Error executing request", e);
        }
    }

    // -------------------- INNER CLASSES --------------------

    private static class SafeUriBuilder {

        private final String baseUrl;

        public SafeUriBuilder(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        URI buildWith(Consumer<URIBuilder> customizer) {
            try {
                URIBuilder builder = new URIBuilder(baseUrl);
                customizer.accept(builder);
                return builder.build();
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Invalid URI", e);
            }
        }
    }

    private class XmlMappingResponseHandler<T> implements ResponseHandler<T> {

        private final Class<T> resultType;

        public XmlMappingResponseHandler(Class<T> resultType) {
            this.resultType = resultType;
        }

        @Override
        public T handleResponse(HttpResponse response) throws IOException {
            StatusLine status = response.getStatusLine();
            if (status.getStatusCode() > 200) {
                throw new HttpResponseException(status.getStatusCode(),
                        String.format("Request failed with status: %d and message: %s",
                                status.getStatusCode(), status.getReasonPhrase()));
            }

            HttpEntity entity = response.getEntity();
            if (entity == null || entity.getContentLength() == 0) {
                return null;
            }

            try (InputStream in = entity.getContent()) {
                return xmlMapper.readValue(in, resultType);
            }
        }
    }
}
