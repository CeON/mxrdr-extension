package pl.edu.icm.pl.mxrdr.extension.importer.xds;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.omnifaces.cdi.Eager;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import pl.edu.icm.pl.mxrdr.extension.importer.MetadataBlock;

@Eager
@ApplicationScoped
public class XdsImporter implements MetadataImporter {

    private ImporterRegistry registry;
    private XdsFileParser xdsFileParser;

    // -------------------- CONSTRUCTORS --------------------

    @Deprecated
    public XdsImporter() {
    }

    @Inject
    public XdsImporter(ImporterRegistry registry, XdsFileParser xdsFileParser) {
        this.registry = registry;
        this.xdsFileParser = xdsFileParser;
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
        return new ImporterData()
                .addDescription("xds.modal.description")
                .addField(ImporterData.ImporterField.of(XdsImporterForm.XDS_FILE, ImporterFieldType.UPLOAD_TEMP_FILE,
                                                        true, getBundle(Locale.ENGLISH).getString("xds.label"),
                                                        ""));
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> map) {

        if (map.containsKey(XdsImporterForm.XDS_FILE)) {
            File dataFile = (File) map.get(XdsImporterForm.XDS_FILE);

            List<String> dataRecords = readXdsMetadata(dataFile);

            return xdsFileParser.parse(dataRecords);
        }

        return Collections.emptyList();
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        Map<ImporterFieldKey, String> errors = new HashMap<>();
        File xdsFile = (File) importerInput.get(XdsImporterForm.XDS_FILE);
        if (xdsFile.length() < 1) {
            errors.put(XdsImporterForm.XDS_FILE, getBundle(Locale.ENGLISH).getString("xds.error.wrongFile"));
        }

        return errors;
    }

    // -------------------- PRIVATE --------------------

    /**
     * Reads content of XDS file.
     */
    private List<String> readXdsMetadata(File dataFile) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(dataFile.toPath(), Charset.forName("windows-1252"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException e) {
           throw new IllegalStateException("There was a problem with reading XDS file", e);
        }

        return lines;
    }
}
