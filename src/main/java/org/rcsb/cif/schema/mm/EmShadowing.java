package org.rcsb.cif.schema.mm;

import org.rcsb.cif.model.*;
import org.rcsb.cif.schema.*;

import javax.annotation.Generated;

/**
 * Data items related to shadowing of an EM specimen
 */
@Generated("org.rcsb.cif.schema.generator.SchemaGenerator")
public class EmShadowing extends DelegatingCategory {
    public EmShadowing(Category delegate) {
        super(delegate);
    }

    @Override
    protected Column createDelegate(String columnName, Column column) {
        switch (columnName) {
            case "angle":
                return getAngle();
            case "details":
                return getDetails();
            case "id":
                return getId();
            case "material":
                return getMaterial();
            case "specimen_id":
                return getSpecimenId();
            case "thickness":
                return getThickness();
            default:
                return new DelegatingColumn(column);
        }
    }

    /**
     * The shadowing angle (degrees)
     * @return FloatColumn
     */
    public FloatColumn getAngle() {
        return delegate.getColumn("angle", DelegatingFloatColumn::new);
    }

    /**
     * Additional details about specimen shadowing
     * @return StrColumn
     */
    public StrColumn getDetails() {
        return delegate.getColumn("details", DelegatingStrColumn::new);
    }

    /**
     * This data item is the primary key of the category.
     * @return StrColumn
     */
    public StrColumn getId() {
        return delegate.getColumn("id", DelegatingStrColumn::new);
    }

    /**
     * The material used in the shadowing.
     * @return StrColumn
     */
    public StrColumn getMaterial() {
        return delegate.getColumn("material", DelegatingStrColumn::new);
    }

    /**
     * Foreign key relationship to the EMD SPECIMEN category
     * @return StrColumn
     */
    public StrColumn getSpecimenId() {
        return delegate.getColumn("specimen_id", DelegatingStrColumn::new);
    }

    /**
     * Thickness of the deposited shadow coat, in Angstroms.
     * @return FloatColumn
     */
    public FloatColumn getThickness() {
        return delegate.getColumn("thickness", DelegatingFloatColumn::new);
    }

}