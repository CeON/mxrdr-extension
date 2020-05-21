package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.omnifaces.cdi.Eager;
import pl.edu.icm.pl.mxrdr.extension.importer.MetadataBlock;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Eager
@ApplicationScoped
public class PdbApiImporter implements MetadataImporter {

    private PdbApiCaller apiCaller;
    private PdbXmlParser xmlParser;

    // -------------------- CONSTRUCTORS --------------------

    @Deprecated
    public PdbApiImporter() {
    }

    @Inject
    public PdbApiImporter(PdbApiCaller apiCaller, PdbXmlParser xmlParser) {
        this.apiCaller = apiCaller;
        this.xmlParser = xmlParser;
    }

    // -------------------- LOGIC --------------------

    @Override
    public String getMetadataBlockName() {
        return MetadataBlock.MXRDR.getMetadataBlockName();
    }

    @Override
    public ResourceBundle getBundle(Locale locale) {
        return null;
    }

    @Override
    public ImporterData getImporterData() {
        return new ImporterData().addField(ImporterData.ImporterField.of(PdbApiForm.STRUCTURE_ID,
                                                                  ImporterFieldType.INPUT,
                                                                  true,
                                                                  "Structure id",
                                                                  "id"));
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> map) {

        String pdbXml = apiCaller.fetchPdbData((String) map.get(PdbApiForm.STRUCTURE_ID));

        return xmlParser.parse(pdbXml);
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        return new HashMap<>();
    }
}
