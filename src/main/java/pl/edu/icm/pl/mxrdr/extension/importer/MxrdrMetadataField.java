package pl.edu.icm.pl.mxrdr.extension.importer;

public enum MxrdrMetadataField {
    ATOM_SITE_COUNT("atomSiteCount"),
    BEAMLINE("beamline"),
    CITATION_AUTHOR("citationAuthor"),
    CITATION_JOURNAL("citationJournal"),
    CITATION_PUBMED_ID("citationPubmedId"),
    CITATION_TITLE("citationTitle"),
    CITATION_YEAR("citationYear"),
    DATA_COLLECTION("dataCollection"),
    DATA_COLLECTION_DETECTOR_DISTANCE("dataCollectionDetectorDistance"),
    DATA_COLLECTION_DETECTOR_OVERLOAD("dataCollectionDetectorOverload"),
    DATA_COLLECTION_DETECTOR_THICKNESS("dataCollectionDetectorThickness"),
    DATA_COLLECTION_ORG_X("dataCollectionOrgX"),
    DATA_COLLECTION_ORG_Y("dataCollectionOrgY"),
    DATA_COLLECTION_OSCILLATION_STEP_SIZE("dataCollectionOscillationStepSize"),
    DATA_COLLECTION_STARTING_ANGLE("dataCollectionStartingAngle"),
    DATA_COLLECTION_TEMPERATURE("dataCollectionTemperature"),
    DATA_COLLECTION_WAVE_LENGTH("dataCollectionWavelength"),
    DATA_COLLECTION_NUMBER_OF_FRAMES("dataCollectionNumberOfFrames"),
    DETECTOR_TYPE("detectorType"),
    ENTITY("entity"),
    ENTITY_ID("entityId"),
    ENTITY_SEQUENCE("entitySequence"),
    HRS("hrs"),
    HRS_COMPLETENESS("hrsCompleteness"),
    HRS_R_MERGE("hrsRMerge"),
    HRS_SIGMA("hrsISigma"),
    MOLECULAR_WEIGHT("molecularWeight"),
    MONOCHROMATOR("monochromator"),
    OVERALL("overall"),
    OVERALL_COMPLETENESS("overallCompleteness"),
    OVERALL_DATA_RESOLUTION_RANGE_HIGH("overallDataResolutionRangeHigh"),
    OVERALL_DATA_RESOLUTION_RANGE_LOW("overallDataResolutionRangeLow"),
    OVERALL_I_SIGMA("overallISigma"),
    OVERALL_R_MERGE("overallRMerge"),
    PDB_DEPOSIT_DATE("pdbDepositDate"),
    PDB_DOI("pdbDoi"),
    PDB_ID("pdbId"),
    PDB_RELEASE_DATE("pdbReleaseDate"),
    PDB_REVISION_DATE("pdbRevisionDate"),
    OVERALL_SIGMA("overallISigma"),
    OVERALL_CC("overallCc"),
    OVERALL_R_MEAS("overallRMeas"), 
    OVERALL_NUMBER_OBSERVED_REFLECTIONS("overallNumberOfObservedReflections"), 
    OVERALL_NUMBER_UNIQUE_REFLECTIONS("overallNumberOfUniqueReflections"), 
    OVERALL_NUMBER_POSSIBLE_REFLECTIONS("overallNumberOfPossibleReflections"), 
    OVERALL_ANOMALOUS_CORRELATION("overallAnomalousCorrelation"), 
    OVERALL_ANOMALOUS_SIGNAL("overallAnomalousSignal"),
    HRS_CC("hrsCc"),
    HRS_R_MEAS("hrsRMeas"), 
    HRS_NUMBER_OBSERVED_REFLECTIONS("hrsNumberOfObservedReflections"), 
    HRS_NUMBER_UNIQUE_REFLECTIONS("hrsNumberOfUniqueReflections"), 
    HRS_NUMBER_POSSIBLE_REFLECTIONS("hrsNumberOfPossibleReflections"), 
    HRS_ANOMALOUS_CORRELATION("hrsAnomalousCorrelation"), 
    HRS_ANOMALOUS_SIGNAL("hrsAnomalousSignal"),
    HRS_DATA_RESOLUTION_RANGE_LOW("hrsDataResolutionRangeLow"),
    HRS_DATA_RESOLUTION_RANGE_HIGH("hrsDataResolutionRangeHigh"),
    PDB_STRUCTURE_AUTHOR("pdbStructureAuthor"),
    PDB_TITLE("pdbTitle"),
    PROCESSING_SOFTWARE("processingSoftware"),
    RESIDUE_COUNT("residueCount"),
    SPACE_GROUP("spaceGroup"),
    UNIT_CELL_PARAMETERS("unitCellParameters"),
    UNIT_CELL_PARAMETER_A("unitCellParameterA"),
    UNIT_CELL_PARAMETER_ALPHA("unitCellParameterAlpha"),
    UNIT_CELL_PARAMETER_B("unitCellParameterB"),
    UNIT_CELL_PARAMETER_BETA("unitCellParameterBeta"),
    UNIT_CELL_PARAMETER_C("unitCellParameterC"),
    UNIT_CELL_PARAMETER_GAMMA("unitCellParameterGamma")
    ;

    private final String value;

    MxrdrMetadataField(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
