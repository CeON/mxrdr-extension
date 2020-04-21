package edu.harvard.iq.dataverse.importer.metadata.mxrdr;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterInput;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import org.omnifaces.cdi.Eager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
        return ResourceBundle.getBundle("AnotherImporterBundle", locale);
    }

    @Override
    public ImporterData getImporterData() {
        return null;
    }

    @Override
    public Map<Object, Object> fetchMetadata(ImporterInput importerInput) {
        return null;
    }
}
