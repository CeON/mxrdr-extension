package pl.edu.icm.pl.mxrdr.extension.importer.xds;


public enum XdsAnalysisField {
    
    SPACE_GROUP("spaceGroup"),
    UNIT_CELL_PARAMETER_A("unitCellParameterA"),
    UNIT_CELL_PARAMETER_B("unitCellParameterB"),
    UNIT_CELL_PARAMETER_C("unitCellParameterC"),
    UNIT_CELL_PARAMETER_ALPHA("unitCellParameterAlpha"),
    UNIT_CELL_PARAMETER_BETA("unitCellParameterBeta"),
    UNIT_CELL_PARAMETER_GAMMA("unitCellParameterGamma"),
    OVERALL_COMPLETENESS("overallCompleteness"),
    OVERALL_SIGMA("overallISigma"),
    OVERALL_CC("overallCc"),
    OVERALL_R_MERGE("overallRMerge"),
    OVERALL_R_MEAS("overallRMeas"),
    OVERALL_DATA_RESOLUTION_RANGE_LOW("overallDataResolutionRangeLow"),
    OVERALL_DATA_RESOLUTION_RANGE_HIGH("overallDataResolutionRangeHigh"),
    OVERALL_NUMBER_OBSERVED_REFLECTIONS("overallNumberOfObservedReflections"), 
    OVERALL_NUMBER_UNIQUE_REFLECTIONS("overallNumberOfUniqueReflections"), 
    OVERALL_NUMBER_POSSIBLE_REFLECTIONS("overallNumberOfNumberOfPossibleReflections"), 
    OVERALL_ANOMALOUS_CORRELATION("overallAnomalousCorrelation"), 
    OVERALL_ANOMALOUS_SIGNAL("overallAnomalousSignal"),
    HRS_COMPLETENESS("hrsCompleteness"),
    HRS_SIGMA("hrsSigma"),
    HRS_CC("hrsCc"),
    HRS_R_MERGE("hrsRMerge"),
    HRS_R_MEAS("hrsRMeas"), 
    HRS_DATA_RESOLUTION_RANGE_LOW("hrsDataResolutionRangeLow"),
    HRS_DATA_RESOLUTION_RANGE_HIGH("hrsDataResolutionRangeHigh"),
    HRS_NUMBER_OBSERVED_REFLECTIONS("hrsNumberOfObservedReflections"), 
    HRS_NUMBER_UNIQUE_REFLECTIONS("hrsNumberOfUniqueReflections"), 
    HRS_NUMBER_POSSIBLE_REFLECTIONS("hrsNumberOfNumberOfPossibleReflections"), 
    HRS_ANOMALOUS_CORRELATION("hrsAnomalousCorrelation"), 
    HRS_ANOMALOUS_SIGNAL("hrsAnomalousSignal"),
    PROCESSING_SOFTWARE("processingSoftware");

    private final String name;

    XdsAnalysisField(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
