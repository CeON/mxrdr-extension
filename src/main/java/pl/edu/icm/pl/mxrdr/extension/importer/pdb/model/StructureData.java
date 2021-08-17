package pl.edu.icm.pl.mxrdr.extension.importer.pdb.model;

import java.util.ArrayList;
import java.util.List;

public class StructureData {
    private EntryData entryData;
    private List<PolymerEntityData> polymerEntities = new ArrayList<>();

    // -------------------- CONSTRUCTORS --------------------

    public StructureData(EntryData entryData) {
        this.entryData = entryData != null ? entryData : new EntryData();
    }

    // -------------------- GETTERS --------------------

    public EntryData getEntryData() {
        return entryData;
    }

    public List<PolymerEntityData> getPolymerEntities() {
        return polymerEntities;
    }
}
