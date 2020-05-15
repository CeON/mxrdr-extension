package pl.edu.icm.pl.mxrdr.extension.importer;

public enum MxrdrMetadataField {
    DETECTOR_TYPE("detectorType"),
    DATA_COLLECTION("dataCollection"),
    DATA_COLLECTION_DETECTOR_DISTANCE("dataCollectionDetectorDistance"),
    DATA_COLLECTION_OSCILLATION_STEP_SIZE("dataCollectionOscillationStepSize"),
    DATA_COLLECTION_DETECTOR_OVERLOAD("dataCollectionDetectorOverload"),
    DATA_COLLECTION_DETECTOR_THICKNESS("dataCollectionDetectorThickness"),
    DATA_COLLECTION_STARTING_ANGLE("dataCollectionStartingAngle"),
    DATA_COLLECTION_WAVE_LENGTH("dataCollectionWavelength"),
    DATA_COLLECTION_ORG_X("dataCollectionOrgX"),
    DATA_COLLECTION_ORG_Y("dataCollectionOrgY");

    private final String value;

    MxrdrMetadataField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
