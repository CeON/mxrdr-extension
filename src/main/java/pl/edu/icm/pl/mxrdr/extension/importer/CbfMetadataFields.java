package pl.edu.icm.pl.mxrdr.extension.importer;

import java.util.ArrayList;
import java.util.List;

public class CbfMetadataFields {

    private final List<MetadataField> metadataFilters;

    // -------------------- CONSTRUCTORS --------------------

    public CbfMetadataFields() {
        metadataFilters = prepareCbfFilters();
    }

    // -------------------- GETTERS --------------------

    public List<MetadataField> getMetadataFilters() {
        return metadataFilters;
    }

    // -------------------- PRIVATE --------------------

    /**
     * Filters based on script made by scientists.
     */
    private List<MetadataField> prepareCbfFilters() {
        List<MetadataField> filters = new ArrayList<>();

        filters.add(new MetadataField("detectorType", cbf -> extractDetectorType(cbf.toLowerCase(), cbf)));

        MetadataField measurementField = new MetadataField("dataCollection");
        filters.add(measurementField);

        measurementField.getChildFields().add(new MetadataField("dataCollectionDetectorDistance",
                                                                cbf -> extractField(cbf.toLowerCase(), cbf, "detector_distance", 2)));
        measurementField.getChildFields().add(new MetadataField("dataCollectionOscillationStepSize",
                                                                cbf -> extractField(cbf.toLowerCase(), cbf, "angle_increment", 2)));
        measurementField.getChildFields().add(new MetadataField("dataCollectionDetectorOverload",
                                                                cbf -> extractField(cbf.toLowerCase(), cbf, "count_cutoff", 2)));
        measurementField.getChildFields().add(new MetadataField("dataCollectionDetectorThickness",
                                                                cbf -> extractField(cbf.toLowerCase(), cbf, "thickness", 4)));
        measurementField.getChildFields().add(new MetadataField("dataCollectionStartingAngle",
                                                                cbf -> extractField(cbf.toLowerCase(), cbf, "start_angle", 2)));
        measurementField.getChildFields().add(new MetadataField("dataCollectionWavelength",
                                                                cbf -> extractField(cbf.toLowerCase(), cbf, "wavelength", 2)));
        measurementField.getChildFields().add(new MetadataField("dataCollectionOrgX", cbf -> extractOrgX(cbf.toLowerCase(), cbf)));
        measurementField.getChildFields().add(new MetadataField("dataCollectionOrgY", cbf -> extractOrgY(cbf.toLowerCase(), cbf)));

        return filters;
    }

    private String extractDetectorType(String normalizedCbfLine, String originalCbfLine) {
        if (normalizedCbfLine.contains("detector:")) {
            String[] splittedCbf = originalCbfLine.split(" ");
            return splittedCbf[2] + " " + splittedCbf[3].replaceAll(",", "");
        }
        return "";
    }

    private String extractField(String normalizedCbfLine, String originalCbfLine, String filteredField, int fieldPosition) {
        if (normalizedCbfLine.contains(filteredField)) {
            String[] splittedCbf = originalCbfLine.split(" ");
            return splittedCbf[fieldPosition];
        }
        return "";
    }

    private String extractOrgX(String normalizedCbfLine, String originalCbfLine) {
        if (normalizedCbfLine.contains("beam_xy")) {
            String[] splittedCbf = originalCbfLine.split(" ");
            return splittedCbf[2]
                    .replace("(", "")
                    .replace(",", "");
        }
        return "";
    }

    private String extractOrgY(String normalizedCbfLine, String originalCbfLine) {
        if (normalizedCbfLine.contains("beam_xy")) {
            String[] splittedCbf = originalCbfLine.split(" ");
            return splittedCbf[3]
                    .replace(")", "");
        }
        return "";
    }
}
