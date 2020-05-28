package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import pl.edu.icm.pl.mxrdr.extension.importer.MetadataBlock;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.pojo.PdbDataset;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Singleton
@Startup
public class PdbApiImporter implements MetadataImporter {

    private ImporterRegistry registry;
    private PdbApiCaller apiCaller;
    private PdbXmlParser xmlParser;

    // -------------------- CONSTRUCTORS --------------------

    @Deprecated
    public PdbApiImporter() {
    }

    @Inject
    public PdbApiImporter(ImporterRegistry registry, PdbApiCaller apiCaller, PdbXmlParser xmlParser) {
        this.registry = registry;
        this.apiCaller = apiCaller;
        this.xmlParser = xmlParser;
    }

    // -------------------- LOGIC --------------------

    @PostConstruct
    public void init() {
        registry.register(this);
    }

    @Override
    public String getMetadataBlockName() {
        return MetadataBlock.MXRDR.getMetadataBlockName();
    }

    @Override
    public ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle("PdbApiImporterBundle", locale);
    }

    @Override
    public ImporterData getImporterData() {
        return new ImporterData().addField(ImporterData.ImporterField.of(PdbApiForm.STRUCTURE_ID,
                                                                  ImporterFieldType.INPUT,
                                                                  true,
                                                                  "importer.label.id",
                                                                  "importer.label.description.id"));
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> map) {

        PdbDataset pdbXml = apiCaller.fetchPdbData(generatePdbApiParams((String) map.get(PdbApiForm.STRUCTURE_ID)));

        return xmlParser.parse(pdbXml);
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        return new HashMap<>();
    }

    // -------------------- PRIVATE --------------------

    private List<NameValuePair> generatePdbApiParams(String StructureId) {
        List<NameValuePair> apiParams = new ArrayList<>();

        apiParams.add(new BasicNameValuePair("pdbids", StructureId));
        apiParams.add(new BasicNameValuePair("primaryOnly", "1"));
        apiParams.add(new BasicNameValuePair("customReportColumns",
                               "structureId,collectionTemperature,sequence,macromoleculeType,structureMolecularWeight," +
                                       "spaceGroup,lengthOfUnitCellLatticeA,lengthOfUnitCellLatticeB,lengthOfUnitCellLatticeC," +
                                       "unitCellAngleAlpha,unitCellAngleBeta,unitCellAngleGamma,resolution,name," +
                                       "residueCount,atomSiteCount,structureTitle,pdbDoi,structureAuthor,depositionDate," +
                                       "releaseDate,revisionDate,experimentalTechnique,title,pubmedId,citationAuthor," +
                                       "journalName,publicationYear"));

        return apiParams;
    }
}
