package pl.edu.icm.pl.mxrdr.extension.importer;

public enum MxrdrMetadataField {
    PDB_ID("pdbId"),
    DETECTOR_TYPE("detectorType"),
    DATA_COLLECTION("dataCollection"),
    DATA_COLLECTION_DETECTOR_DISTANCE("dataCollectionDetectorDistance"),
    DATA_COLLECTION_OSCILLATION_STEP_SIZE("dataCollectionOscillationStepSize"),
    DATA_COLLECTION_DETECTOR_OVERLOAD("dataCollectionDetectorOverload"),
    DATA_COLLECTION_DETECTOR_THICKNESS("dataCollectionDetectorThickness"),
    DATA_COLLECTION_STARTING_ANGLE("dataCollectionStartingAngle"),
    DATA_COLLECTION_TEMPERATURE("dataCollectionTemperature"),
    DATA_COLLECTION_WAVE_LENGTH("dataCollectionWavelength"),
    DATA_COLLECTION_ORG_X("dataCollectionOrgX"),
    DATA_COLLECTION_ORG_Y("dataCollectionOrgY"),
    DATA_COLLECTION_NUMBER_OF_FRAMES("dataCollectionNumberOfFrames"),
    ENTITY("entity"),
    ENTITY_ID("entityId"),
    ENTITY_SEQUENCE("entitySequence"),
    MOLECULAR_WEIGHT("molecularWeight"),
    SPACE_GROUP("spaceGroup"),
    UNIT_CELL_PARAMETERS("unitCellParameters"),
    UNIT_CELL_PARAMETER_A("unitCellParameterA"),
    UNIT_CELL_PARAMETER_B("unitCellParameterB"),
    UNIT_CELL_PARAMETER_C("unitCellParameterC"),
    UNIT_CELL_PARAMETER_ALPHA("unitCellParameterAlpha"),
    UNIT_CELL_PARAMETER_BETA("unitCellParameterBeta"),
    UNIT_CELL_PARAMETER_GAMMA("unitCellParameterGamma"),
    OVERALL("overall"),
    OVERALL_DATA_RESOLUTION_RANGE_HIGH("overallDataResolutionRangeHigh"),
    OVERALL_DATA_RESOLUTION_RANGE_LOW("overallDataResolutionRangeLow"),
    RESIDUE_COUNT("residueCount"),
    ATOM_SITE_COUNT("atomSiteCount"),
    PDB_TITLE("pdbTitle"),
    PDB_DOI("pdbDoi"),
    PDB_STRUCTURE_AUTHOR("pdbStructureAuthor"),
    PDB_DEPOSIT_DATE("pdbDepositDate"),
    PDB_RELEASE_DATE("pdbReleaseDate"),
    PDB_REVISION_DATE("pdbRevisionDate"),
    CITATION_TITLE("citationTitle"),
    CITATION_PUBMED_ID("citationPubmedId"),
    CITATION_AUTHOR("citationAuthor"),
    CITATION_JOURNAL("citationJournal"),
    CITATION_YEAR("citationYear"),
    OVERALL_COMPLETENESS("overallCompleteness"),
    OVERALL_SIGMA("overallISigma"),
    OVERALL_CC("overallCc"),
    OVERALL_R_MERGE("overallRMerge"), 
    OVERALL_R_MEAS("overallRMeas"), 
    OVERALL_NUMBER_OBSERVED_REFLECTIONS("overallNumberOfObservedReflections"), 
    OVERALL_NUMBER_UNIQUE_REFLECTIONS("overallNumberOfUniqueReflections"), 
    OVERALL_NUMBER_POSSIBLE_REFLECTIONS("overallNumberOfNumberOfPossibleReflections"), 
    OVERALL_ANOMALOUS_CORRELATION("overallAnomalousCorrelation"), 
    OVERALL_ANOMALOUS_SIGNAL("overallAnomalousSignal"),
    HRS("hrs"),
    HRS_COMPLETENESS("hrsCompleteness"),
    HRS_SIGMA("hrsSigma"),
    HRS_CC("hrsCc"),
    HRS_R_MERGE("hrsRMerge"), 
    HRS_R_MEAS("hrsRMeas"), 
    HRS_NUMBER_OBSERVED_REFLECTIONS("hrsNumberOfObservedReflections"), 
    HRS_NUMBER_UNIQUE_REFLECTIONS("hrsNumberOfUniqueReflections"), 
    HRS_NUMBER_POSSIBLE_REFLECTIONS("hrsNumberOfNumberOfPossibleReflections"), 
    HRS_ANOMALOUS_CORRELATION("hrsAnomalousCorrelation"), 
    HRS_ANOMALOUS_SIGNAL("hrsAnomalousSignal"),
    HRS_DATA_RESOLUTION_RANGE_LOW("hrsDataResolutionRangeLow"),
    HRS_DATA_RESOLUTION_RANGE_HIGH("hrsDataResolutionRangeHigh"),
    PROCESSING_SOFTWARE("processingSoftware");

    private final String value;

    MxrdrMetadataField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
