package pl.edu.icm.pl.mxrdr.extension.importer.cif.file;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.apache.commons.io.FilenameUtils;
import org.omnifaces.cdi.Eager;
import org.rcsb.cif.schema.mm.MmCifFile;
import pl.edu.icm.pl.mxrdr.extension.importer.MetadataBlock;
import pl.edu.icm.pl.mxrdr.extension.importer.common.CommonMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Eager
@ApplicationScoped
public class CifFileImporter implements MetadataImporter {
    private static final String PDB_ID_PATTERN = "[1-9][a-zA-Z0-9]{3}";

    private static final String NO_INPUT_DATA_VALIDATION_MSG_KEY = "importer.form.validation.pdbId.or.file";
    private static final String WRONG_PDB_ID_FORMAT_MSG_KEY = "importer.form.validation.pdbId.wrong.format";
    private static final String WRONG_FILE_EXTENSION_MSG_KEY ="importer.form.validation.file.wrong.extension";
    private static final String NOT_A_FILE_OR_DOES_NOT_EXIST_MSG_KEY ="importer.form.validation.not.file.or.not.existing";

    private static final String IMPORTER_BUNDLE = "CifFileImporterBundle";

    @Inject
    ImporterRegistry importerRegistry;

    @PostConstruct
    public void init() {
        importerRegistry.register(this);
    }

    @Override
    public String getMetadataBlockName() {
        return MetadataBlock.MXRDR.getMetadataBlockName();
    }

    @Override
    public ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle(IMPORTER_BUNDLE, locale);
    }

    @Override
    public ImporterData getImporterData() {
        return new ImporterData()
                .addDescription("importer.form.main.description")
                .addField(ImporterData.ImporterField.of(CifFileFormKeys.PDB_ID, ImporterFieldType.INPUT, false,
                        "importer.form.pdbId.input", "importer.form.pdbId.hint"))
                .addField(ImporterData.ImporterField.of(CifFileFormKeys.CIF_FILE, ImporterFieldType.UPLOAD_TEMP_FILE, false,
                        "importer.form.cif.upload.component", "importer.form.cif.upload.hint"))
                .addField(ImporterData.ImporterField.of(CifFileFormKeys.DIFFRN_ID, ImporterFieldType.INPUT,
                        false, "importer.form.diffrn.id.input", "importer.form.diffrn.id.hint"));
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> importerInput) {
        File cifFile = (File) importerInput.get(CifFileFormKeys.CIF_FILE);
        String pdbId = (String) importerInput.get(CifFileFormKeys.PDB_ID);
        String diffrnId = (String) importerInput.get(CifFileFormKeys.DIFFRN_ID);
        MmCifFile mmCifFile = CifFileReader.readAndParseCifFile(cifFile, pdbId);
        return new CommonMapper(new CifDataContainer().init(mmCifFile.getFirstBlock(), diffrnId))
                .toResultFields();
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        String pdbId = (String) importerInput.get(CifFileFormKeys.PDB_ID);
        File cifFile = (File) importerInput.get(CifFileFormKeys.CIF_FILE);
        Map<ImporterFieldKey, String> validationErrors = new HashMap<>();
        if (pdbId == null && cifFile == null) {
            validationErrors.put(CifFileFormKeys.PDB_ID, NO_INPUT_DATA_VALIDATION_MSG_KEY);
            validationErrors.put(CifFileFormKeys.CIF_FILE, NO_INPUT_DATA_VALIDATION_MSG_KEY);
            return validationErrors;
        }
        // Now either cifFile or pdbId must be not-null:
        if (cifFile != null) {
            if (!FilenameUtils.getExtension(cifFile.getName()).equalsIgnoreCase("cif")) {
                validationErrors.put(CifFileFormKeys.CIF_FILE, WRONG_FILE_EXTENSION_MSG_KEY);
            }
            if (!cifFile.isFile() || !cifFile.exists()) {
                validationErrors.put(CifFileFormKeys.CIF_FILE, NOT_A_FILE_OR_DOES_NOT_EXIST_MSG_KEY);
            }
        } else {
            if (!pdbId.matches(PDB_ID_PATTERN)) {
                validationErrors.put(CifFileFormKeys.PDB_ID, WRONG_PDB_ID_FORMAT_MSG_KEY);
            }
        }
        return validationErrors;
    }
}
