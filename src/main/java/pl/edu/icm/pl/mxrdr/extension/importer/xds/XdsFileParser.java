package pl.edu.icm.pl.mxrdr.extension.importer.xds;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

/**
 * Class designed to parse XDS output file.
 */
@Singleton
public class XdsFileParser {

	private final String OVERALL_RESOLUTION_INDICATOR = "STANDARD ERROR OF REFLECTION INTENSITIES AS FUNCTION OF RESOLUTION";

	private final String SPACE_GROUPS_MAPPING_BUNDLE_NAME = "XdsSpaceGroups";

	private static final String SPACE_GROUP_OTHER = "Other";

	// -------------------- LOGIC --------------------

	/**
	 * Extracts metadataFields from String lines taken from XDS output file (usually
	 * "CORRECT.LP").
	 * 
	 * @return list with extracted fields, or empty if none was extracted.
	 */
	List<ResultField> parse(List<String> dataLines) {

		List<ResultField> results = new ArrayList<>();
		List<ResultField> dataCollectionChildren = new ArrayList<>();
		List<ResultField> overallCollectionChildren = new ArrayList<>();
		List<ResultField> hrsCollectionChildren = new ArrayList<>();
		List<ResultField> unitCellConstantsCollectionChildren = new ArrayList<>();
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

			addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_DISTANCE.getValue(), "DETECTOR_DISTANCE", 0,
					dataLine, dataCollectionChildren, collectedFields);

			addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_OSCILLATION_STEP_SIZE.getValue(), "OSCILLATION_RANGE",
					0, dataLine, dataCollectionChildren, collectedFields);
			addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_WAVE_LENGTH.getValue(), "X-RAY_WAVELENGTH", 0,
					dataLine, dataCollectionChildren, collectedFields);
			addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_NUMBER_OF_FRAMES.getValue(), "DATA_RANGE", 1, dataLine,
					dataCollectionChildren, collectedFields);
			addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_OVERLOAD.getValue(), "OVERLOAD", 0, dataLine,
					dataCollectionChildren, collectedFields);
			addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_ORG_X.getValue(), "ORGX", 0, dataLine,
					dataCollectionChildren, collectedFields);
			addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_ORG_Y.getValue(), "ORGY", 0, dataLine,
					dataCollectionChildren, collectedFields);
			addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_DETECTOR_THICKNESS.getValue(), "SENSOR_THICKNESS", 0,
					dataLine, dataCollectionChildren, collectedFields);
			addExtractedValue(MxrdrMetadataField.DATA_COLLECTION_STARTING_ANGLE.getValue(), "STARTING_ANGLE", 0,
					dataLine, dataCollectionChildren, collectedFields);
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
		addUnitCellConstants(unitCellConstantsCandidateDataLine, unitCellConstantsCollectionChildren);
		addOverallValues(overallCandidateDataLine, overallCollectionChildren, overallDataResolutionRangeLow);
		addHrsValues(hrsDataLine, hrsAdditionalDataLine, hrsCollectionChildren, overallCollectionChildren);

		if (!unitCellConstantsCollectionChildren.isEmpty()) {
			results.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETERS.getValue(),
					unitCellConstantsCollectionChildren.toArray(new ResultField[0])));
		}
		if (!dataCollectionChildren.isEmpty()) {
			results.add(ResultField.of(MxrdrMetadataField.DATA_COLLECTION.getValue(),
					dataCollectionChildren.toArray(new ResultField[0])));
		}
		if (!overallCollectionChildren.isEmpty()) {
			results.add(ResultField.of(MxrdrMetadataField.OVERALL.getValue(),
					overallCollectionChildren.toArray(new ResultField[0])));
		}
		if (!hrsCollectionChildren.isEmpty()) {
			results.add(ResultField.of(MxrdrMetadataField.HRS.getValue(),
					hrsCollectionChildren.toArray(new ResultField[0])));
		}

		results.add(ResultField.of(MxrdrMetadataField.SPACE_GROUP.getValue(),
				ResultField.ofValue(mapSpaceGroup(spaceGroupNumber))));
		results.add(ResultField.of(MxrdrMetadataField.PROCESSING_SOFTWARE.getValue(), ResultField.ofValue("XDS")));

		return results;
	}

	// -------------------- PRIVATE --------------------

	private String parseOverallDataResolutionRangeLow(String dataLine) {
		String[] values = StringUtils.normalizeSpace(dataLine).split(" ");
		if (values.length == 9 && values[0].matches("[\\d.]*")) {
			return values[0];
		}
		return null;
	}

	String mapSpaceGroup(String spaceGroupNumberCandidate) {

		if (ResourceBundle.getBundle(SPACE_GROUPS_MAPPING_BUNDLE_NAME).containsKey(spaceGroupNumberCandidate)) {
			return ResourceBundle.getBundle(SPACE_GROUPS_MAPPING_BUNDLE_NAME).getString(spaceGroupNumberCandidate);
		} else {
			return SPACE_GROUP_OTHER;
		}

	}

	private boolean containsOverall(String dataLine) {
		if (dataLine.trim().startsWith("total")) {
			String[] values = StringUtils.normalizeSpace(dataLine).split(" ");
			if (values.length == 14) {
				return true;
			}
		}
		return false;
	}

	private boolean containsUnitCellConstants(String dataLine) {
		if (dataLine.trim().startsWith("UNIT_CELL_CONSTANTS")) {
			String[] values = StringUtils.normalizeSpace(dataLine).split(" ");
			if (!(values.length < 6)) {
				return true;
			}
		}
		return false;
	}

	private String extractSpaceGroupNumber(String dataLine) {
		if (dataLine.trim().startsWith("SPACE_GROUP_NUMBER")) {
			String[] values = normalizeDataLine(dataLine).split(" ");
			if (values.length > 0) {
				String[] spaceGroupNameValue = values[0].split("=");
				return spaceGroupNameValue[1];
			}
		}
		return "";
	}

	private boolean containsHrs(String dataLine) {
		String[] values = StringUtils.normalizeSpace(dataLine).split(" ");
		if (values.length == 14) {
			double resolution = 0.0;
			double sigma = 0.0;
			double cc = 0.0;

			try {
				resolution = Float.valueOf(clearNonDigits(values[0]));
				sigma = Float.valueOf(clearNonDigits(values[8]));
				cc = Float.valueOf(clearNonDigits(values[10]));
			} catch (NumberFormatException e) {
			}

			if (resolution > 0.3 && resolution < 5.0 && sigma > 1.19 && sigma <= 100 && cc > 50.0
					&& values[4].endsWith("%") && values[5].endsWith("%") && values[6].endsWith("%")) {
				return true;
			}
		}
		return false;
	}

	private void addHrsValues(String dataLine, String additionalDataLine, List<ResultField> results,
			List<ResultField> overallResults) {
		String[] values = StringUtils.normalizeSpace(dataLine).split(" ");
		if (values.length == 14) {
			results.add(ResultField.of(MxrdrMetadataField.HRS_COMPLETENESS.getValue(), clearNonDigits(values[4])));
			results.add(ResultField.of(MxrdrMetadataField.HRS_SIGMA.getValue(), clearNonDigits(values[8])));
			results.add(ResultField.of(MxrdrMetadataField.HRS_CC.getValue(), clearNonDigits(values[10])));
			results.add(ResultField.of(MxrdrMetadataField.HRS_R_MERGE.getValue(), clearNonDigits(values[5])));
			results.add(ResultField.of(MxrdrMetadataField.HRS_R_MEAS.getValue(), clearNonDigits(values[9])));
			results.add(ResultField.of(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_HIGH.getValue(),
					clearNonDigits(values[0])));
			overallResults.add(ResultField.of(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH.getValue(),
					clearNonDigits(values[0])));
			results.add(ResultField.of(MxrdrMetadataField.HRS_NUMBER_OBSERVED_REFLECTIONS.getValue(),
					clearNonDigits(values[1])));
			results.add(ResultField.of(MxrdrMetadataField.HRS_NUMBER_UNIQUE_REFLECTIONS.getValue(),
					clearNonDigits(values[2])));
			results.add(ResultField.of(MxrdrMetadataField.HRS_NUMBER_POSSIBLE_REFLECTIONS.getValue(),
					clearNonDigits(values[3])));
			results.add(ResultField.of(MxrdrMetadataField.HRS_ANOMALOUS_CORRELATION.getValue(),
					clearNonDigits(values[11])));
			results.add(ResultField.of(MxrdrMetadataField.HRS_ANOMALOUS_SIGNAL.getValue(), clearNonDigits(values[12])));
		}
		String[] additionalValues = StringUtils.normalizeSpace(additionalDataLine).split(" ");
		if (values.length == 14) {
			results.add(ResultField.of(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_LOW.getValue(),
					clearNonDigits(additionalValues[0])));
		}
	}

	private void addOverallValues(String dataLine, List<ResultField> results, String overallDataResolutionRangeLow) {
		String[] values = StringUtils.normalizeSpace(dataLine).split(" ");
		if (values.length == 14) {
			results.add(ResultField.of(MxrdrMetadataField.OVERALL_COMPLETENESS.getValue(), clearNonDigits(values[4])));
			results.add(ResultField.of(MxrdrMetadataField.OVERALL_SIGMA.getValue(), clearNonDigits(values[8])));
			results.add(ResultField.of(MxrdrMetadataField.OVERALL_CC.getValue(), clearNonDigits(values[10])));
			results.add(ResultField.of(MxrdrMetadataField.OVERALL_R_MERGE.getValue(), clearNonDigits(values[5])));
			results.add(ResultField.of(MxrdrMetadataField.OVERALL_R_MEAS.getValue(), clearNonDigits(values[9])));
			results.add(ResultField.of(MxrdrMetadataField.OVERALL_NUMBER_OBSERVED_REFLECTIONS.getValue(),
					clearNonDigits(values[1])));
			results.add(ResultField.of(MxrdrMetadataField.OVERALL_NUMBER_UNIQUE_REFLECTIONS.getValue(),
					clearNonDigits(values[2])));
			results.add(ResultField.of(MxrdrMetadataField.OVERALL_NUMBER_POSSIBLE_REFLECTIONS.getValue(),
					clearNonDigits(values[3])));
			results.add(ResultField.of(MxrdrMetadataField.OVERALL_ANOMALOUS_CORRELATION.getValue(),
					clearNonDigits(values[11])));
			results.add(
					ResultField.of(MxrdrMetadataField.OVERALL_ANOMALOUS_SIGNAL.getValue(), clearNonDigits(values[12])));
			if (StringUtils.isNotEmpty(overallDataResolutionRangeLow)) {
				results.add(ResultField.of(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_LOW.getValue(),
						clearNonDigits(overallDataResolutionRangeLow)));

			}
		}
	}

	private void addUnitCellConstants(String dataLine, List<ResultField> results) {

		String[] lineSplit = dataLine.split("=");

		if (lineSplit.length > 1) {
			String[] values = StringUtils.normalizeSpace(lineSplit[1]).split(" ");
			if (values.length == 6) {
				results.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_A.getValue(), values[0]));
				results.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_B.getValue(), values[1]));
				results.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_C.getValue(), values[2]));
				results.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA.getValue(), values[3]));
				results.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA.getValue(), values[4]));
				results.add(ResultField.of(MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA.getValue(), values[5]));
			}
		}
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

	public void addExtractedValue(String name, String sourceName, int sourcePosition, String dataline,
			List<ResultField> results, List<String> collected) {
		if (!collected.contains(name)) {
			String extractedValue = extractField(normalizeDataLine(dataline), sourceName, sourcePosition);
			if (!extractedValue.isEmpty()) {
				results.add(ResultField.of(name, extractedValue));
				collected.add(name);
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

}
