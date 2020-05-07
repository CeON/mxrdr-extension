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
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Eager
@ApplicationScoped
public class AnotherImporter implements MetadataImporter {
    @Inject
    ImporterRegistry registry;

    @PostConstruct
    public void init() {
        registry.register(this);
    }

    @Override
    public String getMetadataBlockName() {
        return "citation";
    }

    @Override
    public ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle("SimpleImporterBundle", locale);
    }

    @Override
    public ImporterData getImporterData() {
        return new ImporterData()
                .addDescription("ext.desc")
                .addField(ImporterData.ImporterField.of(SimpleImporterForm.FIRST, ImporterFieldType.INPUT,
                        true, "Liczba parzysta", "desc.first"))
                .addField(ImporterData.ImporterField.of(SimpleImporterForm.SECOND, ImporterFieldType.INPUT,
                        true, "Liczba nieparzysta", "qwerty"))
                .addDescription("ext.desc")
                .addField(ImporterData.ImporterField.of(SimpleImporterForm.THIRD, ImporterFieldType.UPLOAD_TEMP_FILE,
                        true, "Zenon", "Lorem ipsum"));
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        Map<ImporterFieldKey, String> result = new HashMap<>();
        int value;
        for (ImporterFieldKey key : importerInput.keySet()) {
            if (key == SimpleImporterForm.FIRST) {
                try {
                    value = Integer.parseInt((String) importerInput.get(SimpleImporterForm.FIRST));
                    if (value % 2 == 1) {
                        result.put(SimpleImporterForm.FIRST, "Liczba musi być parzysta.");
                    }
                } catch (NumberFormatException nex) {
                    result.put(SimpleImporterForm.FIRST, "To nie jest poprawna liczba całkowita.");
                }
            } else if (key == SimpleImporterForm.SECOND) {
                try {
                    value = Integer.parseInt((String) importerInput.get(SimpleImporterForm.SECOND));
                    if (value % 2 == 0) {
                        result.put(SimpleImporterForm.SECOND, "Liczba musi być nieparzysta.");
                    }
                } catch (NumberFormatException nex) {
                    result.put(SimpleImporterForm.SECOND, "To nie jest poprawna liczba całkowita.");
                }
            } else if (key == SimpleImporterForm.THIRD) {
                File file = (File) importerInput.get(SimpleImporterForm.THIRD);
                if (file.length() < 10L) {
                    result.put(SimpleImporterForm.THIRD, "Plik jest zbyt maly.");
                }
            }
        }
        return result;
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> map) {
    return Collections.emptyList();
    }
}
