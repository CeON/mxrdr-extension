package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import kong.unirest.Config;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;

import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class PdbApiCaller {
    public static final int TEN_SECONDS = 10000;

    private UnirestInstance apiCaller = new UnirestInstance(new Config());

    // -------------------- LOGIC --------------------

    /**
     * Created to mainly call protein data bank in order to retrieve entity xml.
     * Watch out the api doesn't work very well, it returns 200 even if it can't find the entity.
     */
    String fetchPdbData(String structureId, String apiURL) {

        HttpResponse<String> pdbResponse = apiCaller.get(apiURL)
                .queryString("pdbids", structureId)
                .queryString(getCustomReportColumns())
                .queryString("primaryOnly", 1)
                .connectTimeout(TEN_SECONDS)
                .asString();

        if (!pdbResponse.isSuccess() || pdbResponse.getBody().isEmpty()) {
            throw new IllegalStateException("Retrieving entity from pdb failed with status: " + pdbResponse.getStatus() +
                                                    " and message: "+ pdbResponse.getStatusText());
        }

        return pdbResponse.getBody();
    }

    // -------------------- PRIVATE --------------------

    private HashMap<String, Object> getCustomReportColumns() {
        HashMap<String, Object> customReportColumns = new HashMap<>();

        customReportColumns.put("customReportColumns",
                                "structureId,collectionTemperature,sequence,macromoleculeType,structureMolecularWeight," +
                                        "spaceGroup,lengthOfUnitCellLatticeA,lengthOfUnitCellLatticeB,lengthOfUnitCellLatticeC," +
                                        "unitCellAngleAlpha,unitCellAngleBeta,unitCellAngleGamma,resolution,name," +
                                        "residueCount,atomSiteCount,structureTitle,pdbDoi,structureAuthor,depositionDate," +
                                        "releaseDate,revisionDate,experimentalTechnique,title,pubmedId,citationAuthor," +
                                        "journalName,publicationYear");

        return customReportColumns;
    }

    public void setApiCaller(UnirestInstance apiCaller) {
        this.apiCaller = apiCaller;
    }
}
