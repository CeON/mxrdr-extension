package pl.edu.icm.pl.mxrdr.extension.importer;

public enum MacromolleculeType {
    DNA("DNA"),
    RNA("RNA"),
    RNA_DNA_HYBRID("RNA/DNA Hybrid"),
    PROTEIN("Protein"),
    OTHER("");

    private String name;

    public String getName() {
        return name;
    }

    MacromolleculeType(String name) {
        this.name = name;
    }
}
