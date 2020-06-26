package pl.edu.icm.pl.mxrdr.extension.importer.xds;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.SymmetryStructureMapper;

/**
 * Class designed to parse XDS output file.
 */
@Singleton
public class XdsFileParser {

    private static final Logger logger = LoggerFactory.getLogger(XdsFileParser.class);

    private final String OVERALL_RESOLUTION_INDICATOR = "STANDARD ERROR OF REFLECTION INTENSITIES AS FUNCTION OF RESOLUTION";


    // -------------------- LOGIC --------------------

    
    public List<ResultField> parse(File xdsOutput) {
        return parse(readXdsMetadata(xdsOutput));
    }
    
    
    /**
     * Extracts metadataFields from String lines taken from XDS output file (usually
     * "CORRECT.LP").
     * 
     * @return list with extracted fields, or empty if none was extracted.
     */
    List<ResultField> parse(List<String> dataLines) {

        List<ResultField> results = new ArrayList<>();
        List<ResultField> dataCollectionChildren = new ArrayList<>();
        List<String> collectedFields = new ArrayList<String>();

        String overallCandidateDataLine = "";
        String spaceGroupNumber = "";
        String unitCellConstantsCandidateDataLine = "";

        String hrsDataLine = "";
        String hrsAdditionalDataLine = "";

        String previousDataline = "";

        boolean resolveOverallResolutionRangeLow = false;

        String overallDataResolutionRangeLow = "";

        for (String dataLine : dataLines) {

            addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_DISTANCE, "DETECTOR_DISTANCE", dataLine,
                    dataCollectionChildren, collectedFields);

            addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_OSCILLATION_STEP_SIZE, "OSCILLATION_RANGE", dataLine,
                    dataCollectionChildren, collectedFields);
            addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_WAVE_LENGTH, "X-RAY_WAVELENGTH", dataLine,
                    dataCollectionChildren, collectedFields);
            addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_NUMBER_OF_FRAMES, "DATA_RANGE", 1, dataLine,
                    dataCollectionChildren, collectedFields);
            addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_OVERLOAD, "OVERLOAD", dataLine,
                    dataCollectionChildren, collectedFields);
            addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_ORG_X, "ORGX", dataLine, dataCollectionChildren,
                    collectedFields);
            addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_ORG_Y, "ORGY", dataLine, dataCollectionChildren,
                    collectedFields);
            addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_THICKNESS, "SENSOR_THICKNESS", dataLine,
                    dataCollectionChildren, collectedFields);
            addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_STARTING_ANGLE, "STARTING_ANGLE", dataLine,
                    dataCollectionChildren, collectedFields);
            if (containsOverall(dataLine)) {
                overallCandidateDataLine = dataLine;
            }
            if (resolveOverallResolutionRangeLow) {
                overallDataResolutionRangeLow = parseOverallDataResolutionRangeLow(dataLine);
                if (StringUtils.isNotEmpty(overallDataResolutionRangeLow)) {
                    resolveOverallResolutionRangeLow = false;
                }

            }
            if (containsUnitCellConstants(dataLine)) {
                unitCellConstantsCandidateDataLine = dataLine;
            }
            String spaceGroupNumberCandidate = extractSpaceGroupNumber(dataLine);
            if (!StringUtils.isEmpty(spaceGroupNumberCandidate)) {
                spaceGroupNumber = spaceGroupNumberCandidate;
            }

            if (containsHrs(dataLine)) {
                hrsAdditionalDataLine = previousDataline;
                hrsDataLine = dataLine;
            }
            previousDataline = dataLine;
            if (dataLine.contains(OVERALL_RESOLUTION_INDICATOR)) {
                resolveOverallResolutionRangeLow = true;
            }

        }

        results.addAll(addUnitCellConstants(unitCellConstantsCandidateDataLine));
        if (!dataCollectionChildren.isEmpty()) {
            results.add(ResultField.of(MxrdrMetadataField.DATA_COLLECTION.getValue(),
                    dataCollectionChildren.toArray(new ResultField[0])));
        }
        List<ResultField> hrsFields = addHrsValues(hrsDataLine, hrsAdditionalDataLine);
        results.addAll(addOverallValues(overallCandidateDataLine, overallDataResolutionRangeLow, hrsFields));
        results.addAll(hrsFields);

        results.add(ResultField.of(MxrdrMetadataField.SPACE_GROUP.getValue(),
                ResultField.ofValue(SymmetryStructureMapper.mapSpaceGroupNumber(spaceGroupNumber))));
        results.add(ResultField.of(MxrdrMetadataField.PROCESSING_SOFTWARE.getValue(), ResultField.ofValue("XDS")));

        return results;
    }

    // -------------------- PRIVATE --------------------

    private String parseOverallDataResolutionRangeLow(String dataLine) {
        String[] values = splitSpacesDelimited(dataLine);
        if (values.length == 9 && values[0].matches("[\\d.]*")) {
            return values[0];
        }
        return null;
    }

    private boolean containsOverall(String dataLine) {
        if (!dataLine.trim().startsWith("total")) {
            return false;
        }
        String[] values = splitSpacesDelimited(dataLine);
        return values.length == 14;

    }

    private boolean containsUnitCellConstants(String dataLine) {
        if (!dataLine.trim().startsWith("UNIT_CELL_CONSTANTS")) {
            return false;
        }
        String[] values = splitSpacesDelimited(dataLine);
        return !(values.length < 6);
    }

    private String extractSpaceGroupNumber(String dataLine) {
        if (!dataLine.trim().startsWith("SPACE_GROUP_NUMBER")) {
            return "";
        }
        String[] values = normalizeDataLine(dataLine).split(" ");
        if (values.length > 0) {
            String[] spaceGroupNameValue = values[0].split("=");
            return spaceGroupNameValue.length > 0 ? spaceGroupNameValue[1] : "";
        }
        return "";
    }

    private boolean containsHrs(String dataLine) {
        String[] values = splitSpacesDelimited(dataLine);
        if (values.length == 14) {
            double resolution = 0.0;
            double sigma = 0.0;
            double cc = 0.0;

            try {
                resolution = Float.valueOf(clearNonDigits(values[0]));
                sigma = Float.valueOf(clearNonDigits(values[8]));
                cc = Float.valueOf(clearNonDigits(values[10]));
            } catch (NumberFormatException e) {
                logger.info(e.getMessage());
            }

            if (resolution > 0.3 && resolution < 5.0 && sigma > 1.19 && sigma <= 100 && cc > 50.0
                    && values[4].endsWith("%") && values[5].endsWith("%") && values[6].endsWith("%")) {
                return true;
            }
        }
        return false;
    }

    private String[] splitSpacesDelimited(String dataLine) {
        return StringUtils.normalizeSpace(dataLine).split(" ");
    }

    private List<ResultField> addHrsValues(String dataLine, String additionalDataLine) {

        List<ResultField> children = new ArrayList<>();

        String[] values = splitSpacesDelimited(dataLine);
        if (values.length == 14) {
            children.add(ResultField.of(MxrdrMetadataField.HRS_COMPLETENESS.getValue(), clearNonDigits(values[4])));
            children.add(ResultField.of(MxrdrMetadataField.HRS_SIGMA.getValue(), clearNonDigits(values[8])));
            children.add(ResultField.of(MxrdrMetadataField.HRS_CC.getValue(), clearNonDigits(values[10])));
            children.add(ResultField.of(MxrdrMetadataField.HRS_R_MERGE.getValue(), clearNonDigits(values[5])));
            children.add(ResultField.of(MxrdrMetadataField.HRS_R_MEAS.getValue(), clearNonDigits(values[9])));
            children.add(ResultField.of(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_HIGH.getValue(),
                    clearNonDigits(values[0])));
            children.add(ResultField.of(MxrdrMetadataField.HRS_NUMBER_OBSERVED_REFLECTIONS.getValue(),
                    clearNonDigits(values[1])));
            children.add(ResultField.of(MxrdrMetadataField.HRS_NUMBER_UNIQUE_REFLECTIONS.getValue(),
                    clearNonDigits(values[2])));
            children.add(ResultField.of(MxrdrMetadataField.HRS_NUMBER_POSSIBLE_REFLECTIONS.getValue(),
                    clearNonDigits(values[3])));
            children.add(ResultField.of(MxrdrMetadataField.HRS_ANOMALOUS_CORRELATION.getValue(),
                    clearNonDigits(values[11])));
            children.add(
                    ResultField.of(MxrdrMetadataField.HRS_ANOMALOUS_SIGNAL.getValue(), clearNonDigits(values[12])));
        }
        String[] additionalValues = splitSpacesDelimited(additionalDataLine);
        if (values.length == 14) {
            children.add(ResultField.of(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_LOW.getValue(),
                    clearNonDigits(additionalValues[0])));
        }
        List<ResultField> results = new ArrayList<>();
        if (!children.isEmpty()) {
            results.add(ResultField.of(MxrdrMetadataField.HRS.getValue(), children.toArray(new ResultField[0])));

        }
        return results;

    }

    private List<ResultField> addOverallValues(String dataLine, String overallDataResolutionRangeLow,
            List<ResultField> hrsResults) {

        List<ResultField> children = new ArrayList<>();

        String[] values = splitSpacesDelimited(dataLine);
        if (values.length == 14) {
            children.add(ResultField.of(MxrdrMetadataField.OVERALL_COMPLETENESS.getValue(), clearNonDigits(values[4])));
            children.add(ResultField.of(MxrdrMetadataField.OVERALL_SIGMA.getValue(), clearNonDigits(values[8])));
            children.add(ResultField.of(MxrdrMetadataField.OVERALL_CC.getValue(), clearNonDigits(values[10])));
            children.add(ResultField.of(MxrdrMetadataField.OVERALL_R_MERGE.getValue(), clearNonDigits(values[5])));
            children.add(ResultField.of(MxrdrMetadataField.OVERALL_R_MEAS.getValue(), clearNonDigits(values[9])));
            children.add(ResultField.of(MxrdrMetadataField.OVERALL_NUMBER_OBSERVED_REFLECTIONS.getValue(),
                    clearNonDigits(values[1])));
            children.add(ResultField.of(MxrdrMetadataField.OVERALL_NUMBER_UNIQUE_REFLECTIONS.getValue(),
                    clearNonDigits(values[2])));
            children.add(ResultField.of(MxrdrMetadataField.OVERALL_NUMBER_POSSIBLE_REFLECTIONS.getValue(),
                    clearNonDigits(values[3])));
            children.add(ResultField.of(MxrdrMetadataField.OVERALL_ANOMALOUS_CORRELATION.getValue(),
                    clearNonDigits(values[11])));
            children.add(
                    ResultField.of(MxrdrMetadataField.OVERALL_ANOMALOUS_SIGNAL.getValue(), clearNonDigits(values[12])));
            if (StringUtils.isNotEmpty(overallDataResolutionRangeLow)) {
                children.add(ResultField.of(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_LOW.getValue(),
                        clearNonDigits(overallDataResolutionRangeLow)));

            }

            if (!hrsResults.isEmpty()) {
                for (ResultField field : hrsResults.get(0).getChildren()) {
                    if (field.getName().equals(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_HIGH.getValue())) {
                        children.add(ResultField.of(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH.getValue(),
                                field.getValue()));
                    }
                }
            }

        }
        List<ResultField> results = new ArrayList<>();
        if (!children.isEmpty()) {
            results.add(ResultField.of(MxrdrMetadataField.OVERALL.getValue(), children.toArray(new ResultField[0])));

        }
        return results;
    }

    private List<ResultField> addUnitCellConstants(String dataLine) {

        List<ResultField> children = new ArrayList<>();

        String[] lineSplit = dataLine.split("=");

        if (lineSplit.length > 1) {
            String[] values = StringUtils.normalizeSpace(lineSplit[1]).split(" ");
            if (values.length == 6) {
                children.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_A.getValue(), values[0]));
                children.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_B.getValue(), values[1]));
                children.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_C.getValue(), values[2]));
                children.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA.getValue(), values[3]));
                children.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA.getValue(), values[4]));
                children.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA.getValue(), values[5]));
            }
        }

        List<ResultField> results = new ArrayList<>();
        if (!children.isEmpty()) {
            results.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETERS.getValue(),
                    children.toArray(new ResultField[0])));

        }
        return results;
    }

    private String extractField(String normalizedLine, String filteredField, int fieldPosition) {
        if (normalizedLine.contains(filteredField)) {

            int startIndex = normalizedLine.indexOf(filteredField + "=") + filteredField.length() + 1;
            String fieldValue = normalizedLine.substring(startIndex);
            if (fieldValue.contains("=")) {
                fieldValue = fieldValue.substring(0, fieldValue.indexOf(" "));
            }

            String[] splittedValues = fieldValue.split(" ");

            return splittedValues[fieldPosition];
        }
        return "";
    }

    public void addExtractedValue(MxrdrMetadataField field, String sourceName, String dataline,
            List<ResultField> results, List<String> collected) {
        addExtractedValue(field, sourceName, 0, dataline, results, collected);
    }

    public void addExtractedValue(MxrdrMetadataField field, String sourceName, int sourcePosition, String dataline,
            List<ResultField> results, List<String> collected) {
        if (!collected.contains(field.getValue())) {
            String extractedValue = extractField(normalizeDataLine(dataline), sourceName, sourcePosition);
            if (!extractedValue.isEmpty()) {
                results.add(ResultField.of(field.getValue(), extractedValue));
                collected.add(field.getValue());
            }
        }
    }

    private String normalizeDataLine(String input) {
        String normalizedSpaces = StringUtils.normalizeSpace(input);
        String output = normalizedSpaces.replaceAll("\\s*=\\s*", "=");
        return output;
    }

    private String clearNonDigits(String value) {
        return value.replaceAll("[^\\d.+-]", "");
    }

    private List<String> readXdsMetadata(File dataFile) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(dataFile.toPath(), Charset.forName("windows-1252"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException e) {
            throw new IllegalStateException("There was a problem with reading XDS file", e);
        }

        return lines;
    }

}
