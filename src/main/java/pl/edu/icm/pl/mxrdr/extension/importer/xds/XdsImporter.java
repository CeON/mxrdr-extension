package pl.edu.icm.pl.mxrdr.extension.importer.xds;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.omnifaces.cdi.Eager;
import pl.edu.icm.pl.mxrdr.extension.importer.MetadataBlock;
import pl.edu.icm.pl.mxrdr.extension.xds.output.XdsOutputFileParser;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static java.util.Collections.emptyList;

@Eager
@ApplicationScoped
public class XdsImporter implements MetadataImporter {

    private final ImporterRegistry registry;

    // -------------------- CONSTRUCTORS --------------------

    /**
     * @deprecated for use by EJB proxy only.
     */
    public XdsImporter() {
        this(null);
    }

    @Inject
    public XdsImporter(ImporterRegistry registry) {
        this.registry = registry;
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
        return ResourceBundle.getBundle("XdsImporterBundle", locale);
    }

    @Override
    public ImporterData getImporterData() {
        return new ImporterData().addDescription("xds.modal.description").addField(ImporterData.ImporterField
                .of(XdsImporterForm.XDS_FILE, ImporterFieldType.UPLOAD_TEMP_FILE, true, "xds.label", ""));
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> map) {
        if (map.containsKey(XdsImporterForm.XDS_FILE)) {
            File dataFile = (File) map.get(XdsImporterForm.XDS_FILE);
            return new XdsOutputFileParser(dataFile).asResultFields();
        }
        return emptyList();
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        Map<ImporterFieldKey, String> errors = new HashMap<>();
        File xdsFile = (File) importerInput.get(XdsImporterForm.XDS_FILE);
        if (xdsFile.length() < 1) {
            errors.put(XdsImporterForm.XDS_FILE, "xds.error.wrongFile");
        }
        return errors;
    }
}
