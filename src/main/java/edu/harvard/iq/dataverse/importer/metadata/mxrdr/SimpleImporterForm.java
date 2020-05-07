package edu.harvard.iq.dataverse.importer.metadata.mxrdr;

import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;

public enum SimpleImporterForm implements ImporterFieldKey {
    FIRST, SECOND, THIRD;


    @Override
    public String getName() {
        return this.name();
    }
}
