package pl.edu.icm.pl.mxrdr.extension.importer.pdb.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolymerEntityData {

    @JsonProperty("entity_poly")
    private EntityPoly entityPoly = new EntityPoly();

    // -------------------- GETTERS --------------------

    public EntityPoly getEntityPoly() {
        return entityPoly;
    }

    // -------------------- INNER CLASSES --------------------

    public static class EntityPoly {

        @JsonProperty("pdbx_seq_one_letter_code")
        private String pdbxSeqOneLetterCode;

        // -------------------- GETTERS --------------------

        public String getPdbxSeqOneLetterCode() {
            return pdbxSeqOneLetterCode;
        }

    }
}
