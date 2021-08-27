package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterData.ImporterField;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import pl.edu.icm.pl.mxrdr.extension.importer.MetadataBlock;
import pl.edu.icm.pl.mxrdr.extension.importer.common.CommonMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.StructureData;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Singleton
@Startup
public class PdbApiImporter implements MetadataImporter {

    private ImporterRegistry registry;
    private PdbApiCaller apiCaller;

    // -------------------- CONSTRUCTORS --------------------

    @Deprecated
    public PdbApiImporter() { }

    @Inject
    public PdbApiImporter(ImporterRegistry registry, PdbApiCaller apiCaller) {
        this.registry = registry;
        this.apiCaller = apiCaller;
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
        return ResourceBundle.getBundle("PdbApiImporterBundle", locale);
    }

    @Override
    public ImporterData getImporterData() {
        return new ImporterData()
                .addField(ImporterField.of(PdbApiForm.STRUCTURE_ID, ImporterFieldType.INPUT,
                        true, "importer.label.id", "importer.label.description.id"))
                .addField(ImporterField.of(PdbApiForm.DIFFRN_ID, ImporterFieldType.INPUT,
                        false, "importer.diffrn.id", "importer.diffrn.description.id"));
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> importerInput) {
        String structureId = (String) importerInput.get(PdbApiForm.STRUCTURE_ID);
        String diffractionId = (String) importerInput.get(PdbApiForm.DIFFRN_ID);
        StructureData structureData = apiCaller.getStructureData(structureId);
        return new CommonMapper(new PdbDataContainer().init(structureData, diffractionId))
                .toResultFields();
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        return Collections.emptyMap();
    }
}
