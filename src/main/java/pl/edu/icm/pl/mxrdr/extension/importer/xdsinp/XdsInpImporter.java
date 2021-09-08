package pl.edu.icm.pl.mxrdr.extension.importer.xdsinp;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterData.ImporterField;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import edu.harvard.iq.dataverse.settings.SettingsServiceBean;
import org.apache.commons.io.FilenameUtils;
import org.omnifaces.cdi.Eager;
import pl.edu.icm.pl.mxrdr.extension.importer.MetadataBlock;
import pl.edu.icm.pl.mxrdr.extension.importer.xdsinp.XdsInpRunner.ProducedPaths;
import pl.edu.icm.pl.mxrdr.extension.xds.output.XdsOutputFileParser;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Eager
@ApplicationScoped
public class XdsInpImporter implements MetadataImporter {

    private static final String BUNDLE = "XdsInpImporterBundle";
    private static final String[] ALLOWED_EXTENSIONS = new String[] {"bz2", "gz", "xz", "cbf", "img", "h5"};
    private static final String WRONG_FILE_EXTENSION = "importer.form.validation.file.wrong.extension";

    private ImporterRegistry importerRegistry;
    private SettingsServiceBean settingsService;

    @PostConstruct
    public void init() {
        importerRegistry.register(this);
    }

    // -------------------- CONSTRUCTORS --------------------

    public XdsInpImporter() { }

    @Inject
    public XdsInpImporter(ImporterRegistry importerRegistry, SettingsServiceBean settingsService) {
        this.importerRegistry = importerRegistry;
        this.settingsService = settingsService;
    }

    // -------------------- LOGIC --------------------

    @Override
    public String getMetadataBlockName() {
        return MetadataBlock.MXRDR.getMetadataBlockName();
    }

    @Override
    public ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle(BUNDLE, locale);
    }

    @Override
    public ImporterData getImporterData() {
        return new ImporterData()
                .addDescription("importer.form.description")
                .addField(ImporterField.of(
                        XdsInpFormKeys.INPUT_FILE, ImporterFieldType.UPLOAD_TEMP_FILE, true,
                        "importer.form.file.upload.component", "importer.form.file.upload.hint"));
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> importerInput) {
        File inputFile = (File) importerInput.get(XdsInpFormKeys.INPUT_FILE);
        XdsInpRunner xdsInpRunner = new XdsInpRunner(settingsService);
        ProducedPaths producedPaths = xdsInpRunner.prepareFiles(inputFile);
        try {
            xdsInpRunner.runGenerateXdsInp(producedPaths);
            return new XdsOutputFileParser(producedPaths.resolveXdsInp().toFile())
                    .asResultFields();
        } finally {
            xdsInpRunner.cleanUp(producedPaths);
        }
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        File inputFile = (File) importerInput.get(XdsInpFormKeys.INPUT_FILE);
        if (inputFile == null) {
            return Collections.emptyMap();
        }
        String extension = FilenameUtils.getExtension(inputFile.getName()).toLowerCase();
        return !Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)
                ? Collections.singletonMap(XdsInpFormKeys.INPUT_FILE, WRONG_FILE_EXTENSION)
                : Collections.emptyMap();
    }

    @Override
    public long getMaxUploadedFileSize() {
        return 300 * 1024 * 1024; // 300 MB
    }
}
