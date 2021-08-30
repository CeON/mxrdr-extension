package pl.edu.icm.pl.mxrdr.extension.importer.common;

import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.List;
import java.util.Optional;

public interface DataContainer {

    /**
     * Returns the first element for key.
     */
    Optional<String> get(MxrdrMetadataField key);

    /**
     * Returns an element with the given index for key.
     */
    Optional<String> getIndexed(MxrdrMetadataField key, int index);

    /**
     * Returns all the elements for key.
     */
    List<String> getAll(MxrdrMetadataField key);
}
