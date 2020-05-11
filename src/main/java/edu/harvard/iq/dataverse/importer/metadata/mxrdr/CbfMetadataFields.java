package edu.harvard.iq.dataverse.importer.metadata.mxrdr;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class CbfMetadataFields {

    private Map<String, UnaryOperator<String>> metadataFilters;

    // -------------------- CONSTRUCTORS --------------------

    public CbfMetadataFields() {
        metadataFilters = prepareCbfFilters();
    }

    // -------------------- PRIVATE --------------------

    private Map<String, UnaryOperator<String>> prepareCbfFilters() {
        HashMap<String, UnaryOperator<String>> filters = new HashMap<>();

        filters.put("detectorType", cbf -> {
            if (cbf.contains("Detector:")) {
                String[] splittedCbf = cbf.split(" ");
                return splittedCbf[2] + " " + splittedCbf[3].replaceAll(",","");
            }
            return "";
        });

        filters.put("detectorSerialNumber", cbf -> {
            if (cbf.contains("S/N")) {
                String[] splittedCbf = cbf.split(" ");
                return splittedCbf[5];
            }
            return "";
        });

        filters.put("detectorDistance", cbf -> {
            if (cbf.contains("Detector_distance")) {
                String[] splittedCbf = cbf.split(" ");
                return splittedCbf[2];
            }
            return "";
        });

        filters.put("oscillationStepSize", cbf -> {
            if (cbf.contains("Angle_increment")) {
                String[] splittedCbf = cbf.split(" ");
                return splittedCbf[2];
            }
            return "";
        });

        filters.put("overload", cbf -> {
            if (cbf.contains("Count_cutoff")) {
                String[] splittedCbf = cbf.split(" ");
                return splittedCbf[2];
            }
            return "";
        });

        filters.put("orgX", cbf -> {
            if (cbf.contains("Beam_xy")) {
                String[] splittedCbf = cbf.split(" ");
                return splittedCbf[2]
                        .replace("(", "")
                        .replace(",","");
            }
            return "";
        });

        filters.put("orgY", cbf -> {
            if (cbf.contains("Beam_xy")) {
                String[] splittedCbf = cbf.split(" ");
                return splittedCbf[3]
                        .replace(")", "");
            }
            return "";
        });

        filters.put("sensorThickness", cbf -> {
            if (cbf.contains("thickness")) {
                String[] splittedCbf = cbf.split(" ");
                return splittedCbf[4];
            }
            return "";
        });

        filters.put("startingAngle", cbf -> {
            if (cbf.contains("Start_angle")) {
                String[] splittedCbf = cbf.split(" ");
                return splittedCbf[2];
            }
            return "";
        });

        filters.put("wavelength", cbf -> {
            if (cbf.contains("Wavelength")) {
                String[] splittedCbf = cbf.split(" ");
                return splittedCbf[2];
            }
            return "";
        });

        return filters;
    }
}
