package pl.edu.icm.pl.mxrdr.extension.importer;

public enum MetadataBlock {
    MXRDR("macromolecularcrystallography");

    private final String metadataBlockName;

    MetadataBlock(String metadataBlockName) {
        this.metadataBlockName = metadataBlockName;
    }

    public String getMetadataBlockName() {
        return metadataBlockName;
    }
}
