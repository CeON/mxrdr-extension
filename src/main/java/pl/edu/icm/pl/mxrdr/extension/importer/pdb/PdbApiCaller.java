package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import com.google.common.collect.Maps;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class PdbApiCaller {

    // -------------------- LOGIC --------------------

    String fetchPdbData(String structureId) {

        HttpResponse<String> pdbResponse = Unirest.get("https://www.rcsb.org/pdb/rest/customReport.xml")
                .queryString("pdbids", structureId)
                .queryString(getCustomReportColumns())
                .queryString("primaryOnly", 1)
                .asString();

        return pdbResponse.getBody();
    }

    // -------------------- PRIVATE --------------------

    HashMap<String, Object> getCustomReportColumns() {
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
}
