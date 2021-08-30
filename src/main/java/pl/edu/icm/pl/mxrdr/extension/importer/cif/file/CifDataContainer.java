package pl.edu.icm.pl.mxrdr.extension.importer.cif.file;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import org.apache.commons.lang3.StringUtils;
import org.rcsb.cif.model.Column;
import org.rcsb.cif.model.FloatColumn;
import org.rcsb.cif.model.StrColumn;
import org.rcsb.cif.model.ValueKind;
import org.rcsb.cif.schema.mm.MmCifBlock;
import org.rcsb.cif.schema.mm.ReflnsShell;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.ProcessingSoftwareMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.SymmetryStructureMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.common.BaseDataContainer;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.StructureData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class CifDataContainer extends BaseDataContainer<CifDataContainer> {

    // -------------------- LOGIC --------------------

    /**
     * Fills the container with values from {@link StructureData} according
     * to the needs of {@link pl.edu.icm.pl.mxrdr.extension.importer.common.CommonMapper}.
     */
    public CifDataContainer init(MmCifBlock cifBlock, String diffractionSetId) {

        IndexValue diffractionIdIndex = new IndexValue(StringUtils.isNotBlank(diffractionSetId) ? diffractionSetId : "1", "1", true);
        IndexValue primaryCitationIndex = new IndexValue("primary");
        IndexValue dataReductionIndex = new IndexValue(DATA_REDUCTION);

        int hrsRow = chooseReflectionsShellRow(diffractionIdIndex, cifBlock);
        return add(MxrdrMetadataField.PDB_ID, cifBlock.getEntry().getId().values())
                .add(MxrdrMetadataField.BEAMLINE_INSTITUTION,
                        extractFromRows(cifBlock.getDiffrnSource().getPdbxSynchrotronSite(),
                                findRowNumbers(cifBlock.getDiffrnSource().getDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.BEAMLINE,
                        extractFromRows(cifBlock.getDiffrnSource().getPdbxSynchrotronBeamline(),
                                findRowNumbers(cifBlock.getDiffrnSource().getDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.DETECTOR_TYPE,
                        extractFromRows(cifBlock.getDiffrnDetector().getType(),
                                findRowNumbers(cifBlock.getDiffrnDetector().getDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.DATA_COLLECTION_WAVELENGTH,
                        extractFromRows(cifBlock.getDiffrnSource().getPdbxWavelengthList(),
                                findRowNumbers(cifBlock.getDiffrnSource().getDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE,
                        extractFromRows(cifBlock.getDiffrn().getAmbientTemp(),
                                findRowNumbers(cifBlock.getDiffrn().getId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.SPACE_GROUP,
                        presentValuesStream(cifBlock.getSymmetry().getIntTablesNumber())
                                .map(SymmetryStructureMapper::mapSpaceGroupNumber))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_A, presentValuesStream(cifBlock.getCell().getLengthA()))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_B, presentValuesStream(cifBlock.getCell().getLengthB()))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_C, presentValuesStream(cifBlock.getCell().getLengthC()))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA, presentValuesStream(cifBlock.getCell().getAngleAlpha()))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA, presentValuesStream(cifBlock.getCell().getAngleBeta()))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA, presentValuesStream(cifBlock.getCell().getAngleGamma()))
                .add(MxrdrMetadataField.OVERALL_I_SIGMA,
                        extractFromRows(cifBlock.getReflns().getPdbxNetIOverSigmaI(),
                                findRowNumbers(cifBlock.getReflns().getPdbxDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.OVERALL_CC,
                        extractFromRows(cifBlock.getReflns().getPdbxCCHalf(),
                                findRowNumbers(cifBlock.getReflns().getPdbxDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.OVERALL_R_MERGE,
                        extractFromRows(cifBlock.getReflns().getPdbxRmergeIObs(),
                                findRowNumbers(cifBlock.getReflns().getPdbxDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH,
                        extractFromRows(cifBlock.getReflns().getDResolutionHigh(),
                                findRowNumbers(cifBlock.getReflns().getPdbxDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_LOW,
                        extractFromRows(cifBlock.getReflns().getDResolutionLow(),
                                findRowNumbers(cifBlock.getReflns().getPdbxDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.OVERALL_NUMBER_OBSERVED_REFLECTIONS,
                        extractFromRows(cifBlock.getReflns().getNumberObs(),
                                findRowNumbers(cifBlock.getReflns().getPdbxDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.HRS_I_SIGMA,
                        extractFromRow(cifBlock.getReflnsShell().getMeanIOverSigIObs(), hrsRow))
                .add(MxrdrMetadataField.HRS_CC,
                        extractFromRow(cifBlock.getReflnsShell().getPdbxCCHalf(), hrsRow))
                .add(MxrdrMetadataField.HRS_R_MERGE,
                        extractFromRow(cifBlock.getReflnsShell().getRmergeIObs(), hrsRow))
                .add(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_HIGH,
                        extractFromRow(cifBlock.getReflnsShell().getDResHigh(), hrsRow))
                .add(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_LOW,
                        extractFromRow(cifBlock.getReflnsShell().getDResLow(), hrsRow))
                .add(MxrdrMetadataField.HRS_NUMBER_OBSERVED_REFLECTIONS,
                        extractFromRow(cifBlock.getReflnsShell().getNumberMeasuredObs(), hrsRow))
                .add(MxrdrMetadataField.HRS_NUMBER_UNIQUE_REFLECTIONS,
                        extractFromRow(cifBlock.getReflnsShell().getNumberUniqueAll(), hrsRow))
                .add(MxrdrMetadataField.MONOCHROMATOR,
                        extractFromRows(cifBlock.getDiffrnRadiation().getMonochromator(),
                                findRowNumbers(cifBlock.getDiffrnRadiation().getDiffrnId(), diffractionIdIndex)))
                .addAll(MxrdrMetadataField.PROCESSING_SOFTWARE,
                        extractFromRows(cifBlock.getSoftware().getName(),
                                findRowNumbers(cifBlock.getSoftware().getClassification(), dataReductionIndex))
                                .map(ProcessingSoftwareMapper::map))
                .add(MxrdrMetadataField.REFINEMENT_FACTOR_R_WORK,
                        extractFromRows(cifBlock.getRefine().getLsRFactorRWork(),
                                findRowNumbers(cifBlock.getRefine().getPdbxDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.REFINEMENT_FACTOR_R_FREE,
                        extractFromRows(cifBlock.getRefine().getLsRFactorRFree(),
                                findRowNumbers(cifBlock.getRefine().getPdbxDiffrnId(), diffractionIdIndex)))
                .add(MxrdrMetadataField.ENTITY_COUNT,
                        String.valueOf(cifBlock.getEntityPoly().getPdbxSeqOneLetterCode().values()
                                .filter(StringUtils::isNotBlank)
                                .count()))
                .addAll(MxrdrMetadataField.ENTITY_ID, cifBlock.getEntityPoly().getEntityId().values())
                .addAll(MxrdrMetadataField.ENTITY_SEQUENCE, cifBlock.getEntityPoly().getPdbxSeqOneLetterCode().values())
                .add(MxrdrMetadataField.PDB_TITLE, cifBlock.getStruct().getTitle().values())
                .add(MxrdrMetadataField.PDB_DOI, cifBlock.getEntry().getId().values()
                        .map(s -> String.format(PDB_DOI_TEMPLATE, s)))
                .addAll(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR, cifBlock.getAuditAuthor().getName().values())
                .add(MxrdrMetadataField.PDB_DEPOSIT_DATE, cifBlock.getPdbxDatabaseStatus().getRecvdInitialDepositionDate().values())
                .add(MxrdrMetadataField.PDB_RELEASE_DATE,
                        cifBlock.getPdbxAuditRevisionHistory().getRevisionDate().values()
                                .filter(StringUtils::isNotBlank)
                                .map(LocalDate::parse)
                                .min(LocalDate::compareTo)
                                .map(LocalDate::toString))
                .add(MxrdrMetadataField.PDB_REVISION_DATE,
                        cifBlock.getPdbxAuditRevisionHistory().getRevisionDate().values()
                                .filter(StringUtils::isNotBlank)
                                .map(LocalDate::parse)
                                .max(LocalDate::compareTo)
                                .map(LocalDate::toString))
                .add(MxrdrMetadataField.CITATION_TITLE,
                        extractFromRows(cifBlock.getCitation().getTitle(),
                                findRowNumbers(cifBlock.getCitation().getId(), primaryCitationIndex)))
                .add(MxrdrMetadataField.CITATION_PUBMED_ID,
                        extractFromRows(cifBlock.getCitation().getPdbxDatabaseIdPubMed(),
                                findRowNumbers(cifBlock.getCitation().getId(), primaryCitationIndex)))
                .addAll(MxrdrMetadataField.CITATION_AUTHOR,
                        extractFromRows(cifBlock.getCitationAuthor().getName(),
                                findRowNumbers(cifBlock.getCitationAuthor().getCitationId(), primaryCitationIndex)))
                .add(MxrdrMetadataField.CITATION_JOURNAL,
                        extractFromRows(cifBlock.getCitation().getJournalFull(),
                                findRowNumbers(cifBlock.getCitation().getId(), primaryCitationIndex)))
                .add(MxrdrMetadataField.CITATION_YEAR,
                        extractFromRows(cifBlock.getCitation().getYear(),
                                findRowNumbers(cifBlock.getCitation().getId(), primaryCitationIndex)));

    }

    /**
     * Creates stream from present values of column. Should be used for columns
     * with numeric type as the missing values are represented by zeros, so the
     * value kind must be checked to avoid false values.
     */
    <T> Stream<String> presentValuesStream(Column<T> column) {
        List<String> values = column.stringData().collect(Collectors.toList());
        List<ValueKind> valueKinds = column.valueKinds().collect(Collectors.toList());
        List<String> presentValues = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            if (ValueKind.PRESENT.equals(valueKinds.get(i))) {
                presentValues.add(values.get(i));
            }
        }
        return presentValues.stream();
    }

    /**
     * Extracts value with the given index if the value is present.
     */
    <T> String extractFromRow(Column<T> column, int rowIndex) {
        return column.getRowCount() > rowIndex && rowIndex > -1
                && ValueKind.PRESENT.equals(column.getValueKind(rowIndex))
                ? column.getStringData(rowIndex)
                : EMPTY;
    }

    /**
     * Extracts values from the given rows and for the given index.
     * <p>If index object allows it and column has only one row, in the case of
     * non-existing index column the only value of column is taken.
     */
    <T> Stream<String> extractFromRows(Column<T> column, Tuple2<List<Integer>, IndexValue> rowsAndIndex) {
        List<Integer> rowIndexes = rowsAndIndex._1();
        IndexValue indexValue = rowsAndIndex._2();
        if (!rowIndexes.isEmpty()) {
            return column.isDefined() && rowIndexes.stream().allMatch(i -> column.getRowCount() > i && i > -1)
                    ? rowIndexes.stream()
                    .map(i -> ValueKind.PRESENT.equals(column.getValueKind(i))
                            ? column.getStringData(i) : EMPTY)
                    : Stream.empty();
        } else if (indexValue.shouldGetOnNoIndexColumn() && column.getRowCount() == 1) {
            return column.stringData();
        }
        return Stream.empty();
    }

    /**
     * Finds the row numbers for those rows of index column that have values
     * equal to the value of the given {@link IndexValue} object.
     */
    <I> Tuple2<List<Integer>, IndexValue> findRowNumbers(Column<I> indexColumn, IndexValue indexValue) {
        if (!indexColumn.isDefined()) {
            return Tuple.of(Collections.emptyList(), indexValue);
        }
        List<String> indexValues = indexColumn.stringData().collect(Collectors.toList());
        List<Integer> rowNumbers = new ArrayList<>();
        for (int i = 0; i < indexValues.size(); i++) {
            if (indexValue.getValue().equals(indexValues.get(i))) {
                rowNumbers.add(i);
            }
        }
        return Tuple.of(rowNumbers, indexValue);
    }

    // -------------------- PRIVATE --------------------

    /**
     * For the given id of diffraction set read dResolutionHigh from
     * Reflns with that id, then from ReflnsShell objects for that
     * diffraction set choose the one with resHigh = dResolutionHigh
     * and return its row number.
     */
    private int chooseReflectionsShellRow(IndexValue diffractionIndex, MmCifBlock cifBlock) {
        String diffractionSetId = diffractionIndex.getValue();
        final int ROW_NOT_FOUND = -1;
        ReflnsShell shell = cifBlock.getReflnsShell();

        long shellsWithoutDiffrnId = shell.getPdbxDiffrnId().values()
                .filter(StringUtils::isBlank)
                .count();

        // If ReflnsShells do not have diffraction set id (eg. 1TIL) and we
        // want data for the first diffraction set, we treat these shells as
        // belonging to that set.
        if (shellsWithoutDiffrnId == shell.getRowCount() && "1".equals(diffractionSetId)) {
            List<Double> resolutionHigh = shell.getDResHigh().values()
                    .boxed()
                    .collect(Collectors.toList());
            return !resolutionHigh.isEmpty()
                    ? resolutionHigh.indexOf(Collections.min(resolutionHigh))
                    : ROW_NOT_FOUND;
        } else {
            String maxResolution = extractFromRows(cifBlock.getReflns().getDResolutionHigh(),
                    findRowNumbers(cifBlock.getReflns().getPdbxDiffrnId(), diffractionIndex))
                    .filter(StringUtils::isNotEmpty)
                    .map(v -> String.valueOf(Double.valueOf(v))) // we need that conversion as we could have trailing zeros
                                                                 // that are not present in the shell data (cf. 4PVM)
                    .findFirst().orElse("-1");
            FloatColumn resolutionHighColumn = shell.getDResHigh();
            StrColumn diffractionIdColumn = shell.getPdbxDiffrnId();
            if (resolutionHighColumn.getRowCount() != diffractionIdColumn.getRowCount() || !resolutionHighColumn.isDefined()) {
                return ROW_NOT_FOUND;
            }
            List<Tuple3<Integer, String, String>> idsAndResolutions = new ArrayList<>();
            for (int i = 0; i < resolutionHighColumn.getRowCount(); i++) {
                idsAndResolutions.add(
                        Tuple.of(i, diffractionIdColumn.getStringData(i), resolutionHighColumn.getStringData(i)));
            }
            return idsAndResolutions.stream()
                    .filter(i -> maxResolution.equals(i._3) && diffractionSetId.equals(i._2))
                    .map(Tuple3::_1)
                    .findFirst().orElse(ROW_NOT_FOUND);
        }
    }

    // -------------------- INNER CLASSES --------------------

    /**
     * The class that stores the data about index value used for extraction
     * of row numbers from parsed cif data.
     */
    static class IndexValue {
        /**
         * Value that will be compared to index column values.
         */
        private String value;

        /**
         * Value that will be compared to {@link IndexValue#value} when
         * there is no filled index column in parsed cif data.
         * <p>If the {@link IndexValue#getOnNoColumn} is set to {@code true}
         * and there an equality between {@code value} and {@code getOnNoColumnValue}
         * it's an indication that some data could be extracted even when no
         * index column is available.
         */
        private String getOnNoColumnValue = EMPTY;

        /**
         * Flag for indicating whether we can extract the data when no index
         * column is present.
         */
        private boolean getOnNoColumn = false;

        // -------------------- CONSTRUCTORS --------------------

        public IndexValue(String value) {
            this.value = value;
        }

        public IndexValue(String value, String getOnNoColumnValue, boolean getOnNoColumn) {
            this.value = value;
            this.getOnNoColumnValue = getOnNoColumnValue;
            this.getOnNoColumn = getOnNoColumn;
        }

        // -------------------- GETTERS --------------------

        public String getValue() {
            return value;
        }

        // -------------------- LOGIC --------------------

        /**
         * Returns whether index semantic allows us to extract data when no
         * index column data is present in parsed cif data.
         */
        public boolean shouldGetOnNoIndexColumn() {
            return getOnNoColumn && getOnNoColumnValue.equals(value);
        }
    }
}
