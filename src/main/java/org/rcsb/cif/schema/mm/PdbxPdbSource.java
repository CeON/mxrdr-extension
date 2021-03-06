package org.rcsb.cif.schema.mm;

import org.rcsb.cif.model.*;
import org.rcsb.cif.schema.*;

import javax.annotation.Generated;

/**
 * This is a place holder for the PDB SOURCE.
 */
@Generated("org.rcsb.cif.schema.generator.SchemaGenerator")
public class PdbxPdbSource extends DelegatingCategory {
    public PdbxPdbSource(Category delegate) {
        super(delegate);
    }

    @Override
    protected Column createDelegate(String columnName, Column column) {
        switch (columnName) {
            case "id":
                return getId();
            case "text":
                return getText();
            default:
                return new DelegatingColumn(column);
        }
    }

    /**
     * NDB ID.
     * @return StrColumn
     */
    public StrColumn getId() {
        return delegate.getColumn("id", DelegatingStrColumn::new);
    }

    /**
     * PDB SOURCE record.
     * @return StrColumn
     */
    public StrColumn getText() {
        return delegate.getColumn("text", DelegatingStrColumn::new);
    }

}