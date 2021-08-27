package pl.edu.icm.pl.mxrdr.extension.importer.common;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import org.apache.commons.lang3.StringUtils;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class CommonMapper {

    private DataContainer container;

    static ResultField TO_EXCLUDE = ResultField.of(EMPTY, EMPTY);

    // -------------------- CONSTRUCTORS --------------------

    public CommonMapper(DataContainer container) {
        this.container = container != null ? container : new BaseDataContainer();
    }

    // -------------------- LOGIC --------------------

    public List<ResultField> toResultFields() {
        return Stream.concat(
                Stream.of(
                        single(MxrdrMetadataField.PDB_ID),
                        single(MxrdrMetadataField.BEAMLINE_INSTITUTION),
                        single(MxrdrMetadataField.BEAMLINE),
                        single(MxrdrMetadataField.DETECTOR_TYPE),
                        vocabulary(MxrdrMetadataField.SPACE_GROUP),
                        singleCompound(MxrdrMetadataField.UNIT_CELL_PARAMETERS,
                                MxrdrMetadataField.UNIT_CELL_PARAMETER_A,
                                MxrdrMetadataField.UNIT_CELL_PARAMETER_B,
                                MxrdrMetadataField.UNIT_CELL_PARAMETER_C,
                                MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA,
                                MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA,
                                MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA),
                        singleCompound(MxrdrMetadataField.OVERALL,
                                MxrdrMetadataField.OVERALL_COMPLETENESS,
                                MxrdrMetadataField.OVERALL_I_SIGMA,
                                MxrdrMetadataField.OVERALL_CC,
                                MxrdrMetadataField.OVERALL_R_MERGE,
                                MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_LOW,
                                MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH,
                                MxrdrMetadataField.OVERALL_NUMBER_OBSERVED_REFLECTIONS,
                                MxrdrMetadataField.OVERALL_NUMBER_POSSIBLE_REFLECTIONS),
                        singleCompound(MxrdrMetadataField.HRS,
                                MxrdrMetadataField.HRS_I_SIGMA,
                                MxrdrMetadataField.HRS_R_MERGE,
                                MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_LOW,
                                MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_HIGH,
                                MxrdrMetadataField.HRS_CC,
                                MxrdrMetadataField.HRS_NUMBER_OBSERVED_REFLECTIONS,
                                MxrdrMetadataField.HRS_NUMBER_UNIQUE_REFLECTIONS,
                                MxrdrMetadataField.HRS_NUMBER_POSSIBLE_REFLECTIONS),
                        single(MxrdrMetadataField.MONOCHROMATOR),
                        vocabulary(MxrdrMetadataField.MACROMOLLECULE_TYPE),
                        vocabulary(MxrdrMetadataField.PROCESSING_SOFTWARE),
                        singleCompound(MxrdrMetadataField.REFINEMENT_FACTORS,
                                MxrdrMetadataField.REFINEMENT_FACTOR_R_WORK,
                                MxrdrMetadataField.REFINEMENT_FACTOR_R_FREE),
                        single(MxrdrMetadataField.MOLECULAR_WEIGHT),
                        single(MxrdrMetadataField.ENTITY_COUNT),
                        single(MxrdrMetadataField.RESIDUE_COUNT),
                        single(MxrdrMetadataField.ATOM_SITE_COUNT),
                        single(MxrdrMetadataField.PDB_TITLE),
                        single(MxrdrMetadataField.PDB_DOI),
                        single(MxrdrMetadataField.PDB_DEPOSIT_DATE),
                        single(MxrdrMetadataField.PDB_RELEASE_DATE),
                        single(MxrdrMetadataField.PDB_REVISION_DATE),
                        single(MxrdrMetadataField.CITATION_TITLE),
                        single(MxrdrMetadataField.CITATION_PUBMED_ID),
                        single(MxrdrMetadataField.CITATION_JOURNAL),
                        single(MxrdrMetadataField.CITATION_YEAR)
                ),
                mergeToStream(
                        multiCompound(MxrdrMetadataField.DATA_COLLECTION, MxrdrMetadataField.DATA_COLLECTION_WAVELENGTH, MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE),
                        multiCompound(MxrdrMetadataField.ENTITY, MxrdrMetadataField.ENTITY_ID, MxrdrMetadataField.ENTITY_SEQUENCE),
                        multi(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR),
                        multi(MxrdrMetadataField.CITATION_AUTHOR)))
                .filter(f -> !TO_EXCLUDE.equals(f))
                .collect(Collectors.toList());
    }

    ResultField single(MxrdrMetadataField key) {
        return container.get(key)
                .filter(CommonMapper::isProperValue)
                .map(v -> ResultField.of(key.getValue(), v))
                .orElse(TO_EXCLUDE);
    }

    ResultField singleCompound(MxrdrMetadataField mainKey, MxrdrMetadataField... keys) {
        List<ResultField> children = Arrays.stream(keys)
                .map(k -> ResultField.of(k.getValue(), container.get(k).orElse(EMPTY)))
                .filter(f -> isProperValue(f.getValue()))
                .collect(Collectors.toList());
        return !children.isEmpty() ? ResultField.of(mainKey.getValue(), children) : TO_EXCLUDE;
    }

    ResultField vocabulary(MxrdrMetadataField key) {
        List<ResultField> values = container.getAll(key).stream()
                .filter(CommonMapper::isProperValue)
                .map(ResultField::ofValue)
                .collect(Collectors.toList());
        return !values.isEmpty() ? ResultField.of(key.getValue(), values) : TO_EXCLUDE;
    }

    List<ResultField> multi(MxrdrMetadataField key) {
        return container.getAll(key).stream()
                .map(v -> ResultField.of(key.getValue(), v))
                .filter(f -> isProperValue(f.getValue()))
                .collect(Collectors.toList());
    }

    List<ResultField> multiCompound(MxrdrMetadataField mainKey, MxrdrMetadataField... keys) {
        int commonListSize = Arrays.stream(keys)
                .map(k -> container.getAll(k).size())
                .min(Integer::compareTo)
                .orElse(0);
        List<ResultField> resultFields = new ArrayList<>();
        for (int i = 0; i < commonListSize; i++) {
            final int index = i;
            List<ResultField> children = Arrays.stream(keys)
                    .map(k -> ResultField.of(k.getValue(), container.getIndexed(k, index).orElse(EMPTY)))
                    .filter(f -> isProperValue(f.getValue()))
                    .collect(Collectors.toList());
            if (!children.isEmpty()) {
                resultFields.add(ResultField.of(mainKey.getValue(), children));
            }
        }
        return resultFields;
    }

    // -------------------- PRIVATE --------------------

    private static Stream<ResultField> mergeToStream(List<ResultField>... lists) {
        return Arrays.stream(lists)
                .flatMap(Collection::stream);
    }

    private static boolean isProperValue(String value) {
        return StringUtils.isNotBlank(value);
    }
}
