package pl.edu.icm.pl.mxrdr.extension.importer.xdsinp;

import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;

enum XdsInpFormKeys implements ImporterFieldKey {
    INPUT_FILE;

    @Override
    public String getName() {
        return this.name();
    }
}
