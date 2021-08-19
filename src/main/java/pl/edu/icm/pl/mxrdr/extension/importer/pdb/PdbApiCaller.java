package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.EntryData;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.PolymerEntityData;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.StructureData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class PdbApiCaller {

    private static final int CONNECTION_TIMEOUT_MILLIS = 10_000;

    static final String BASE_ENDPOINT_URL = "https://data.rcsb.org/rest/v1/core";
    static final String ENTRY_PATH = "entry";
    static final String POLYMER_ENTITY_PATH = "polymer_entity";

    private String baseUrl;
    private ObjectMapper objectMapper;

    // -------------------- CONSTRUCTORS --------------------

    public PdbApiCaller() {
        this(BASE_ENDPOINT_URL);
    }

    PdbApiCaller(String baseUrl) {
        this.baseUrl = baseUrl;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // -------------------- LOGIC --------------------

    public StructureData getStructureData(String structureId) {

        URI entryEndpointUri = buildUri(baseUrl, ENTRY_PATH, structureId);
        EntryData entryData = requestDataAndDeserialize(entryEndpointUri, EntryData.class);
        StructureData structureData = new StructureData(entryData);

        // Extract the number of polymer entities
        Integer polymerEntityCount = structureData.getEntryData()
                .getRcsbEntryInfo()
                .getPolymerEntityCount();

        // If the number is not present or is zero, return existing data
        if (polymerEntityCount == null || polymerEntityCount < 1) {
            return structureData;
        }

        // In other case call polymer service to fetch additional data
        for (int polymerEntityId = 1; polymerEntityId <= polymerEntityCount; polymerEntityId++) {
            URI polymerEndpointUri = buildUri(baseUrl, POLYMER_ENTITY_PATH, structureId, String.valueOf(polymerEntityId));
            PolymerEntityData polymerEntityData = requestDataAndDeserialize(polymerEndpointUri, PolymerEntityData.class);
            structureData.getPolymerEntities().add(polymerEntityData);
        }
        return structureData;
    }

    // -------------------- PRIVATE --------------------

    private <T> T requestDataAndDeserialize(URI uri, Class<T> resultType) {
        try {
            return Request.Get(uri)
                    .connectTimeout(CONNECTION_TIMEOUT_MILLIS)
                    .execute()
                    .handleResponse(new JsonMappingResponseHandler<>(resultType));
        } catch (IOException e) {
            throw new RuntimeException("Error executing request", e);
        }
    }

    private URI buildUri(String... segments) {
        try {
            return new URIBuilder(String.join("/", segments)).build();
        } catch (URISyntaxException use) {
            throw new IllegalArgumentException("Error while building URI", use);
        }
    }

    // -------------------- INNER CLASSES --------------------

    private class JsonMappingResponseHandler<T> implements ResponseHandler<T> {

        private final Class<T> resultType;

        public JsonMappingResponseHandler(Class<T> resultType) {
            this.resultType = resultType;
        }

        @Override
        public T handleResponse(HttpResponse response) throws IOException {
            StatusLine status = response.getStatusLine();
            if (status.getStatusCode() > 200) {
                throw new HttpResponseException(status.getStatusCode(),
                    String.format("Request failed with status: %d and message: %s", status.getStatusCode(), status.getReasonPhrase()));
            }

            HttpEntity entity = response.getEntity();
            if (entity == null || entity.getContentLength() == 0) {
                return null;
            }

            try (InputStream in = entity.getContent()) {
                return objectMapper.readValue(in, resultType);
            }
        }
    }
}
