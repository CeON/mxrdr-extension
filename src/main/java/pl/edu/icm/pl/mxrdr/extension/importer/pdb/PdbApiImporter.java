package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import pl.edu.icm.pl.mxrdr.extension.importer.MetadataBlock;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
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

        String pdbXml = apiCaller.fetchPdbData((String) map.get(PdbApiForm.STRUCTURE_ID), "https://www.rcsb.org/pdb/rest/customReport.xml");

        return xmlParser.parse(pdbXml);
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        return new HashMap<>();
    }
}
