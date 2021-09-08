package pl.edu.icm.pl.mxrdr.extension.importer.cbf;


import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.apache.commons.io.FilenameUtils;
import org.omnifaces.cdi.Eager;
import pl.edu.icm.pl.mxrdr.extension.importer.MetadataBlock;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

@Eager
@ApplicationScoped
public class CbfImporter implements MetadataImporter {

    private ImporterRegistry registry;
    private CbfFileParser cbfFileParser;

    // -------------------- CONSTRUCTORS --------------------

    @Deprecated
    public CbfImporter() {
    }

    @Inject
    public CbfImporter(ImporterRegistry registry, CbfFileParser cbfFileParser) {
        this.registry = registry;
        this.cbfFileParser = cbfFileParser;
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
        return ResourceBundle.getBundle("CbfImporterBundle", locale);
    }

    @Override
    public ImporterData getImporterData() {
        return new ImporterData()
                .addDescription("cbf.modal.description")
                .addField(ImporterData.ImporterField.of(CbfImporterForm.CBF_FILE, ImporterFieldType.UPLOAD_TEMP_FILE,
                                                        true, "cbf.label", ""));
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> map) {

        if (map.containsKey(CbfImporterForm.CBF_FILE)) {
            File cbfFile = (File) map.get(CbfImporterForm.CBF_FILE);

            List<String> cbfLines = readCbfWithoutBinaryData(cbfFile);

            return cbfFileParser.parse(cbfLines);
        }

        return Collections.emptyList();
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        Map<ImporterFieldKey, String> errors = new HashMap<>();

        File cbfFile = (File) importerInput.get(CbfImporterForm.CBF_FILE);

        if (cbfFile != null && !FilenameUtils.getExtension(cbfFile.getName()).equals("cbf")) {
            errors.put(CbfImporterForm.CBF_FILE, "cbf.error.wrongFile");
            return errors;
        }

        return errors;
    }

    // -------------------- PRIVATE --------------------

    /**
     * Reads headers from cbf file while skipping binary data which is very long in terms of text lines.
     */
    private List<String> readCbfWithoutBinaryData(File cbfFile) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(cbfFile.toPath(), Charset.forName("windows-1252"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("CIF-BINARY-FORMAT-SECTION")) {
                    break;
                }
                lines.add(line);
            }

        } catch (IOException e) {
           throw new IllegalStateException("There was a problem with reading cbf file", e);
        }

        return lines;
    }
}
