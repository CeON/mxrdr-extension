package pl.edu.icm.pl.mxrdr.extension.importer.cif.file;

import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;

public enum CifFileFormKeys implements ImporterFieldKey {
    PDB_ID,
    DIFFRN_ID,
    CIF_FILE;

    @Override
    public String getName() {
        return this.name();
    }
}
