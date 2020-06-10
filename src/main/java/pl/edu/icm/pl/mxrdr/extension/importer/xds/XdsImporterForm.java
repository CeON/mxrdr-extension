package pl.edu.icm.pl.mxrdr.extension.importer.xds;

import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;

public enum XdsImporterForm implements ImporterFieldKey {
    XDS_FILE;

    @Override
    public String getName() {
        return "XdsImporterForm";
    }

    @Override
    public boolean isRelevant() {
        return true;
    }
}
