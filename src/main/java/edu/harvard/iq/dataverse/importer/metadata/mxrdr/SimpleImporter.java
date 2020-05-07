package edu.harvard.iq.dataverse.importer.metadata.mxrdr;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterData.ImporterField;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.Eager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Eager
@ApplicationScoped
public class SimpleImporter implements MetadataImporter {
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
                .addField(ImporterField.of(SimpleImporterForm.FIRST, ImporterFieldType.INPUT,
                        false, "label.first", "desc.first"))
                .addField(ImporterField.of(SimpleImporterForm.SECOND, ImporterFieldType.INPUT,
                        false, "second", "qwerty"))
                .addDescription("ext.desc")
                .addField(ImporterField.of(SimpleImporterForm.THIRD, ImporterFieldType.UPLOAD_TEMP_FILE,
                        false, "Zenon", "Lorem ipsum"));
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> map) {
        List<ResultField> result = new ArrayList<>();
        result.add(ResultField.of("author",
                ResultField.of("authorName", "Zenon"),
                ResultField.of("authorIdentifierScheme", "ORCID"),
                ResultField.of("authorAffiliation", "WUML")));
        result.add(ResultField.of("producer",
                ResultField.of("producerName", "Producer 1"),
                ResultField.of("authorIdentifierScheme", "ZENON"),
                ResultField.of("qwerty", "1234"),
                ResultField.of("producerAffiliation", "WUML"),
                ResultField.of("producerURL", "http://localhost:8080")));
        result.add(ResultField.of("producer",
                ResultField.of("producerName", "Producer 2"),
                ResultField.of("4321", "qwerty"),
                ResultField.of("qwerty", "1234"),
                ResultField.of("producerAffiliation", "WUML"),
                ResultField.of("producerURL", "http://localhost:8080")));
        result.add(ResultField.of("author",
                ResultField.of("authorAffiliation", "UW"),
                ResultField.of("authorName", "Zenon 2"),
                ResultField.of("NONEZ", "nonez")));
        result.add(ResultField.of("Zenon", "Zenon"));
        result.add(ResultField.of("notesText", "Lorem ipsum dolor sit amet… Lorem ipsum dolor sit amet… " +
                "Lorem ipsum dolor sit amet… Lorem ipsum dolor sit amet…"));
        result.add(ResultField.of("subject",
                ResultField.of(StringUtils.EMPTY, "Physics"),
                ResultField.of(StringUtils.EMPTY, "Arts and Humanities"),
                ResultField.of(StringUtils.EMPTY, "Law"),
                ResultField.of(StringUtils.EMPTY, "Homeopathy")));
        return result;
    }
}
