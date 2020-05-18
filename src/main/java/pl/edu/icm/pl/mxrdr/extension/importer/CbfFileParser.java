package pl.edu.icm.pl.mxrdr.extension.importer;

import com.google.common.collect.Lists;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Class designed to parse cbf file.
 */
@Singleton
public class CbfFileParser {

    // -------------------- LOGIC --------------------

    /**
     * Extracts metadataFields from String lines taken from .cbf file.
     * @return list with extracted fields, or empty if none was extracted.
     */
    List<ResultField> parse(List<String> cbfLines) {

    List<ResultField> results = new ArrayList<>();
    List<ResultField> dataCollectionChildren = new ArrayList<>();

        for (String cbfLine : cbfLines) {

            addIfPresentWithResultAdd(MxrdrMetadataField.DETECTOR_TYPE.getValue(), extractDetectorType(cbfLine.toLowerCase(), cbfLine), results);

            addIfPresentWithResultAdd(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_DISTANCE.getValue(),
                                      extractField(cbfLine.toLowerCase(), cbfLine, "detector_distance", 2), dataCollectionChildren);
            addIfPresentWithResultAdd(MxrdrMetadataField.DATA_COLLECTION_OSCILLATION_STEP_SIZE.getValue(),
                    extractField(cbfLine.toLowerCase(), cbfLine, "angle_increment", 2), dataCollectionChildren);
            addIfPresentWithResultAdd(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_OVERLOAD.getValue(),
                    extractField(cbfLine.toLowerCase(), cbfLine, "count_cutoff", 2), dataCollectionChildren);
            addIfPresentWithResultAdd(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_THICKNESS.getValue(),
                    extractField(cbfLine.toLowerCase(), cbfLine, "thickness", 4), dataCollectionChildren);
            addIfPresentWithResultAdd(MxrdrMetadataField.DATA_COLLECTION_STARTING_ANGLE.getValue(),
                    extractField(cbfLine.toLowerCase(), cbfLine, "start_angle", 2), dataCollectionChildren);
            addIfPresentWithResultAdd(MxrdrMetadataField.DATA_COLLECTION_WAVE_LENGTH.getValue(),
                    extractField(cbfLine.toLowerCase(), cbfLine, "wavelength", 2), dataCollectionChildren);
            addIfPresentWithResultAdd(MxrdrMetadataField.DATA_COLLECTION_ORG_X.getValue(),
                    extractOrgX(cbfLine.toLowerCase(), cbfLine), dataCollectionChildren);
            addIfPresentWithResultAdd(MxrdrMetadataField.DATA_COLLECTION_ORG_Y.getValue(),
                    extractOrgY(cbfLine.toLowerCase(), cbfLine), dataCollectionChildren);


        }
        if (!dataCollectionChildren.isEmpty()) {
            results.add(ResultField.of(MxrdrMetadataField.DATA_COLLECTION.getValue(), dataCollectionChildren.toArray(new ResultField[0])));
        }

        return results;
    }

    // -------------------- PRIVATE --------------------

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

    public List<ResultField> addIfPresentWithResultAdd(String name, String extractedValue, List<ResultField> results) {
        if (!extractedValue.isEmpty()) {
            results.add(ResultField.of(name, extractedValue));
            return results;
        }
        return results;
    }
}
