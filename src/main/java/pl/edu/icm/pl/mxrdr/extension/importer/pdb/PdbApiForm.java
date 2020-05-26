package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;

public enum PdbApiForm implements ImporterFieldKey {
    STRUCTURE_ID;

    @Override
    public String getName() {
        return "PdbApiForm";
    }

    @Override
    public boolean isRelevant() {
        return true;
    }
}
