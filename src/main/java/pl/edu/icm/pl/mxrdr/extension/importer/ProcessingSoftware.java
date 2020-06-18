package pl.edu.icm.pl.mxrdr.extension.importer;

public enum ProcessingSoftware {
    AUTOPROC("Autoproc"),
    DENZO("Denzo"),
    DIALS("Dials"),
    D_TREK("d*TREK"),
    HKL("HKL2000/HKL3000"),
    MOSFLM("MOSFLM/iMOSFLM"),
    XDS("XDS"),
    XDSAPP("xdsapp"),
    XIA2("Xia2"),
    OTHER("Other");

    private String name;

    public String getName() {
        return name;
    }

    ProcessingSoftware(String name) {
        this.name = name;
    }
}
