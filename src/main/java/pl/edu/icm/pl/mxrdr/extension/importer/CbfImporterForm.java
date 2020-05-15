package pl.edu.icm.pl.mxrdr.extension.importer;

import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;

public enum CbfImporterForm implements ImporterFieldKey {
    CBF_FILE;

    @Override
    public String getName() {
        return "CbfImporterForm";
    }

    @Override
    public boolean isRelevant() {
        return true;
    }
}
