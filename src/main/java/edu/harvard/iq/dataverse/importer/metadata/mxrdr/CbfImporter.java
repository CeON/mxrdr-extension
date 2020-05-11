package edu.harvard.iq.dataverse.importer.metadata.mxrdr;


import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.omnifaces.cdi.Eager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Eager
@ApplicationScoped
public class CbfImporter implements MetadataImporter {


    private final ImporterRegistry registry;

    // -------------------- CONSTRUCTORS --------------------

    @Inject
    public CbfImporter(ImporterRegistry registry) {
        this.registry = registry;
    }

    // -------------------- LOGIC --------------------

    @PostConstruct
    public void init() {
        registry.register(this);
    }

    @Override
    public String getMetadataBlockName() {
        return "macromolecularcrystallography";
    }

    @Override
    public ResourceBundle getBundle(Locale locale) {
        return null;
    }

    @Override
    public ImporterData getImporterData() {
        return new ImporterData()
                .addField(ImporterData.ImporterField.of(CbfImporterForm.CBF_FILE, ImporterFieldType.UPLOAD_TEMP_FILE,
                                      true, "", ""));
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> map) {

        map.entrySet().stream()
                .filter(entry -> entry.getKey().equals(CbfImporterForm.CBF_FILE));

        return null;
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        return null;
    }
}
