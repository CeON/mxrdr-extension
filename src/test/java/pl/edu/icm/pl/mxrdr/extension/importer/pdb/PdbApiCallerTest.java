package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PdbApiCallerTest {
    private static final String PDB_URL = "test";

    @Mock
    private UnirestInstance mockedUnirest;

    @Mock
    private HttpResponse<String> response;

    @Mock
    private GetRequest request;

    @Test
    public void fetchPdbData() {
        //given
        PdbApiCaller pdbApiCaller = new PdbApiCaller();
        pdbApiCaller.setApiCaller(mockedUnirest);

        //when

        Mockito.when(mockedUnirest.get(PDB_URL)).thenReturn(request);
        Mockito.when(request.queryString(Mockito.any())).thenReturn(request);
        Mockito.when(request.queryString(Mockito.anyString(), Mockito.anyString())).thenReturn(request);
        Mockito.when(request.queryString(Mockito.anyString(), Mockito.anyInt())).thenReturn(request);
        Mockito.when(request.connectTimeout(Mockito.anyInt())).thenReturn(request);
        Mockito.when(request.asString()).thenReturn(response);
        Mockito.when(response.isSuccess()).thenReturn(true);

        pdbApiCaller.fetchPdbData("1", PDB_URL);

        //then
        Mockito.verify(mockedUnirest, Mockito.times(1)).get(PDB_URL);
    }

    @Test
    public void fetchPdbData_withNegativeStatusCode() {
        //given
        PdbApiCaller pdbApiCaller = new PdbApiCaller();
        pdbApiCaller.setApiCaller(mockedUnirest);

        //when

        Mockito.when(mockedUnirest.get(PDB_URL)).thenReturn(request);
        Mockito.when(request.queryString(Mockito.any())).thenReturn(request);
        Mockito.when(request.queryString(Mockito.anyString(), Mockito.anyString())).thenReturn(request);
        Mockito.when(request.queryString(Mockito.anyString(), Mockito.anyInt())).thenReturn(request);
        Mockito.when(request.connectTimeout(Mockito.anyInt())).thenReturn(request);
        Mockito.when(request.asString()).thenReturn(response);
        Mockito.when(response.isSuccess()).thenReturn(false);


        //when & then
        Assertions.assertThrows(IllegalStateException.class, () -> pdbApiCaller.fetchPdbData("1", PDB_URL));
    }
}