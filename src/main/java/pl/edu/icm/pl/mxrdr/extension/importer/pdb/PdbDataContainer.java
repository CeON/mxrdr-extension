package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import org.apache.commons.lang3.StringUtils;
import pl.edu.icm.pl.mxrdr.extension.importer.MacromolleculeType;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.ProcessingSoftwareMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.SymmetryStructureMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.common.BaseDataContainer;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.EntryData;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.PolymerEntityData;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.StructureData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class PdbDataContainer extends BaseDataContainer<PdbDataContainer> {

    // -------------------- LOGIC --------------------

    /**
     * Fills the container with values from {@link StructureData} according
     * to the needs of {@link pl.edu.icm.pl.mxrdr.extension.importer.common.CommonMapper}.
     */
    public PdbDataContainer init(StructureData structureData, String diffrnId) {
        Integer diffractionSetId = Optional.ofNullable(diffrnId)
                .filter(StringUtils::isNotBlank)
                .map(Integer::valueOf)
                .orElse(1);
        EntryData entry = structureData.getEntryData();
        EntryData.ReflnsShell reflectionsShell = chooseReflectionsShell(diffractionSetId, entry);
        return add(MxrdrMetadataField.PDB_ID, entry.getRcsbId())
                .add(MxrdrMetadataField.BEAMLINE_INSTITUTION,
                        getIndexed(EntryData::getDiffrnSource, EntryData.DiffrnSource::getDiffrnId, diffractionSetId, entry)
                        .map(EntryData.DiffrnSource::getPdbxSynchrotronSite))
                .add(MxrdrMetadataField.BEAMLINE,
                        getIndexed(EntryData::getDiffrnSource, EntryData.DiffrnSource::getDiffrnId, diffractionSetId, entry)
                        .map(EntryData.DiffrnSource::getPdbxSynchrotronBeamline))
                .add(MxrdrMetadataField.DETECTOR_TYPE,
                        getIndexed(EntryData::getDiffrnDetector, EntryData.DiffrnDetector::getDiffrnId, diffractionSetId, entry)
                        .map(EntryData.DiffrnDetector::getType))
                .add(MxrdrMetadataField.DATA_COLLECTION_WAVELENGTH,
                        getIndexed(EntryData::getDiffrnSource, EntryData.DiffrnSource::getDiffrnId, diffractionSetId, entry)
                        .map(EntryData.DiffrnSource::getPdbxWavelengthList))
                .add(MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE,
                        getIndexed(EntryData::getDiffrn, EntryData.Diffrn::getId, diffractionSetId, entry)
                        .map(EntryData.Diffrn::getAmbientTemp))
                .add(MxrdrMetadataField.SPACE_GROUP, SymmetryStructureMapper.mapSpaceGroupNumber(emptyForNull(entry.getSymmetry().getIntTablesNumber())))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_A, entry.getCell().getLengthA())
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_B, entry.getCell().getLengthB())
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_C, entry.getCell().getLengthC())
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA, entry.getCell().getAngleAlpha())
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA, entry.getCell().getAngleBeta())
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA, entry.getCell().getAngleGamma())
                .add(MxrdrMetadataField.OVERALL_COMPLETENESS, Optional.ofNullable(entry.getPdbxVrptSummary())
                        .map(EntryData.PdbxVrptSummary::getDataCompleteness).orElse(EMPTY))
                .add(MxrdrMetadataField.OVERALL_I_SIGMA,
                        getIndexedForIndexOnList(EntryData::getReflns, EntryData.Reflns::getPdbxDiffrnId, diffractionSetId, entry)
                        .map(EntryData.Reflns::getPdbxNetIOverSigmaI))
                .add(MxrdrMetadataField.OVERALL_CC,
                        getIndexedForIndexOnList(EntryData::getReflns, EntryData.Reflns::getPdbxDiffrnId, diffractionSetId, entry)
                        .map(EntryData.Reflns::getPdbxCcHalf))
                .add(MxrdrMetadataField.OVERALL_R_MERGE,
                        getIndexedForIndexOnList(EntryData::getReflns, EntryData.Reflns::getPdbxDiffrnId, diffractionSetId, entry)
                        .map(EntryData.Reflns::getPdbxRMergeIObs))
                .add(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_LOW,
                        getIndexedForIndexOnList(EntryData::getReflns, EntryData.Reflns::getPdbxDiffrnId, diffractionSetId, entry)
                        .map(EntryData.Reflns::getdResolutionLow))
                .add(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH,
                        getIndexedForIndexOnList(EntryData::getReflns, EntryData.Reflns::getPdbxDiffrnId, diffractionSetId, entry)
                        .map(EntryData.Reflns::getdResolutionHigh))
                .add(MxrdrMetadataField.OVERALL_NUMBER_OBSERVED_REFLECTIONS,
                        getIndexedForIndexOnList(EntryData::getReflns, EntryData.Reflns::getPdbxDiffrnId, diffractionSetId, entry)
                        .map(EntryData.Reflns::getNumberObs))
                .add(MxrdrMetadataField.OVERALL_NUMBER_POSSIBLE_REFLECTIONS, get(MxrdrMetadataField.OVERALL_NUMBER_OBSERVED_REFLECTIONS)
                        .filter(StringUtils::isNotBlank)
                        .map(Double::valueOf)
                        .flatMap(observed -> get(MxrdrMetadataField.OVERALL_COMPLETENESS)
                                .filter(StringUtils::isNotBlank)
                                .map(Double::valueOf)
                                .map(completeness -> (100 / completeness) * observed)
                                .map(Math::round)
                                .map(String::valueOf)))
                .add(MxrdrMetadataField.HRS_I_SIGMA, reflectionsShell.getMeanIOverSigIObs())
                .add(MxrdrMetadataField.HRS_R_MERGE, reflectionsShell.getRMergeIObs())
                .add(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_LOW, reflectionsShell.getdResLow())
                .add(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_HIGH, reflectionsShell.getdResHigh())
                .add(MxrdrMetadataField.HRS_CC, reflectionsShell.getPdbxCcHalf())
                .add(MxrdrMetadataField.HRS_NUMBER_OBSERVED_REFLECTIONS, reflectionsShell.getNumberMeasuredObs())
                .add(MxrdrMetadataField.HRS_NUMBER_UNIQUE_REFLECTIONS, reflectionsShell.getNumberUniqueAll())
                .add(MxrdrMetadataField.HRS_NUMBER_POSSIBLE_REFLECTIONS, reflectionsShell.getNumberPossible())
                .add(MxrdrMetadataField.MONOCHROMATOR,
                        getIndexed(EntryData::getDiffrnRadiation, EntryData.DiffrnRadiation::getDiffrnId, diffractionSetId, entry)
                        .map(EntryData.DiffrnRadiation::getMonochromator))
                .addAll(MxrdrMetadataField.PROCESSING_SOFTWARE, entry.getSoftware().stream()
                        .filter(s -> s.getClassification().equals(DATA_REDUCTION))
                        .map(EntryData.Software::getName)
                        .map(ProcessingSoftwareMapper::map))
                .add(MxrdrMetadataField.REFINEMENT_FACTOR_R_WORK,
                        getIndexedForIndexOnList(EntryData::getRefine, EntryData.Refine::getPdbxDiffrnId, diffractionSetId, entry)
                        .map(EntryData.Refine::getLsRFactorRWork))
                .add(MxrdrMetadataField.REFINEMENT_FACTOR_R_FREE,
                        getIndexedForIndexOnList(EntryData::getRefine, EntryData.Refine::getPdbxDiffrnId, diffractionSetId, entry)
                        .map(EntryData.Refine::getLsRFactorRFree))
                .addAll(MxrdrMetadataField.MACROMOLLECULE_TYPE, determineMacromolleculeType(entry))
                .add(MxrdrMetadataField.MOLECULAR_WEIGHT, Optional.ofNullable(entry.getRcsbEntryInfo().getMolecularWeight())
                        .filter(StringUtils::isNotBlank)
                        .map(Double::valueOf)
                        .map(v -> v * 1000.0)
                        .map(String::valueOf))
                .addAll(MxrdrMetadataField.ENTITY_ID, IntStream.rangeClosed(1, entry.getRcsbEntryInfo().getPolymerEntityCount())
                        .mapToObj(String::valueOf))
                .addAll(MxrdrMetadataField.ENTITY_SEQUENCE, structureData.getPolymerEntities().stream()
                        .map(PolymerEntityData::getEntityPoly)
                        .map(PolymerEntityData.EntityPoly::getPdbxSeqOneLetterCode))
                .add(MxrdrMetadataField.ENTITY_COUNT, String.valueOf(entry.getRcsbEntryInfo().getPolymerEntityCount()))
                .add(MxrdrMetadataField.RESIDUE_COUNT, entry.getRcsbEntryInfo().getPolymerMonomerCountMaximum())
                .add(MxrdrMetadataField.ATOM_SITE_COUNT, entry.getRcsbEntryInfo().getDepositedAtomCount())
                .add(MxrdrMetadataField.PDB_TITLE, entry.getStruct().getTitle())
                .add(MxrdrMetadataField.PDB_DOI, String.format(PDB_DOI_TEMPLATE, entry.getRcsbId()))
                .addAll(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR, entry.getAuditAuthor().stream()
                        .map(EntryData.AuditAuthor::getName))
                .add(MxrdrMetadataField.PDB_DEPOSIT_DATE, formatDate(entry.getRcsbAccessionInfo().getDepositDate()))
                .add(MxrdrMetadataField.PDB_RELEASE_DATE, formatDate(entry.getRcsbAccessionInfo().getInitialReleaseDate()))
                .add(MxrdrMetadataField.PDB_REVISION_DATE, formatDate(entry.getRcsbAccessionInfo().getRevisionDate()))
                .add(MxrdrMetadataField.CITATION_TITLE, entry.getRcsbPrimaryCitation().getTitle())
                .add(MxrdrMetadataField.CITATION_PUBMED_ID, entry.getRcsbPrimaryCitation().getPdbxDatabaseIdPubMed())
                .addAll(MxrdrMetadataField.CITATION_AUTHOR, entry.getRcsbPrimaryCitation().getRcsbAuthors().stream())
                .add(MxrdrMetadataField.CITATION_JOURNAL, entry.getRcsbPrimaryCitation().getJournalAbbrev())
                .add(MxrdrMetadataField.CITATION_YEAR, entry.getRcsbPrimaryCitation().getYear());
    }

    /**
     * Gets the element with the given index from the inner collection of data
     * object (when field containing index can store only one value) or the only
     * element of that collection when it has only one element and the index
     * field is empty and the given index was equal to one.
     */
    static <D, T> Optional<T> getIndexed(Function<D, List<T>> getter, Function<T, Integer> indexGetter, Integer indexValue, D entryData) {
        List<T> innerData = getter.apply(entryData);
        return innerData.size() == 1 && indexGetter.apply(innerData.get(0)) == null && indexValue == 1
                ? Optional.ofNullable(innerData.get(0))
                : innerData.stream()
                    .filter(t -> indexValue.equals(indexGetter.apply(t)))
                    .findFirst();
    }

    /**
     * Gets the element with the given index from the inner collection of data
     * object (when field containing index can store multiple indices – ie.
     * it's an array in json) or the only element of that collection when it
     * has only one element and the index field is empty and the given index
     * was equal to one.
     */
    static <D, T> Optional<T> getIndexedForIndexOnList(Function<D, List<T>> getter, Function<T, List<Integer>> indexGetter, Integer indexValue, D entryData) {
        List<T> innerData = getter.apply(entryData);
        return innerData.size() == 1 && indexGetter.apply(innerData.get(0)).isEmpty() && indexValue == 1
                ? Optional.ofNullable(innerData.get(0))
                : innerData.stream()
                .filter(t -> indexGetter.apply(t).contains(indexValue))
                .findFirst();
    }

    // -------------------- PRIVATE --------------------

    /**
     * For the given id of diffraction set read dResolutionHigh from
     * Reflns with that id, then from ReflnsShell objects for that
     * diffraction set choose the one with resHigh = dResolutionHigh.
     */
    private EntryData.ReflnsShell chooseReflectionsShell(Integer diffractionSetId, EntryData entry) {
        List<EntryData.ReflnsShell> reflnsShells = entry.getReflnsShell();
        long shellsWithoutDiffrnId = reflnsShells.stream()
                .filter(s -> s.getPdbxDiffrnId().isEmpty())
                .count();

        // If ReflnsShells do not have diffraction set id (eg. 1TIL) and we
        // want data for the first diffraction set, we treat these shells as
        // belonging to that set.
        if (shellsWithoutDiffrnId == reflnsShells.size() && diffractionSetId == 1) {
            return reflnsShells.stream()
                    .filter(s -> StringUtils.isNotBlank(s.getdResHigh()))
                    .min(Comparator.comparingDouble(s -> Double.parseDouble(s.getdResHigh())))
                    .orElse(new EntryData.ReflnsShell());
        } else {
            String maxResolution = entry.getReflns().stream()
                    .filter(r -> r.getPdbxDiffrnId().contains(diffractionSetId))
                    .map(EntryData.Reflns::getdResolutionHigh)
                    .filter(Objects::nonNull)
                    .findFirst().orElse(null);

            return reflnsShells.stream()
                    .filter(s -> s.getPdbxDiffrnId().contains(diffractionSetId))
                    .filter(s -> Objects.equals(s.getdResHigh(), maxResolution))
                    .findFirst().orElse(new EntryData.ReflnsShell());
        }
    }

    private Stream<String> determineMacromolleculeType(EntryData entryData) {
        Set<MacromolleculeType> types = new HashSet<>();
        EntryData.RcsbEntryInfo entryInfo = entryData.getRcsbEntryInfo();
        if (hasPositiveCount(entryInfo.getPolymerEntityCountDna())) {
            types.add(MacromolleculeType.DNA);
        }
        if (hasPositiveCount(entryInfo.getPolymerEntityCountRna())) {
            types.add(MacromolleculeType.RNA);
        }
        if (hasPositiveCount(entryInfo.getPolymerEntityCountNucleicAcid())) {
            types.add(MacromolleculeType.DNA);
            types.add(MacromolleculeType.RNA);
        }
        if (hasPositiveCount(entryInfo.getPolymerEntityCountNucleicAcidHybrid())) {
            types.add(MacromolleculeType.RNA_DNA_HYBRID);
        }
        if (hasPositiveCount(entryInfo.getPolymerEntityCountProtein())) {
            types.add(MacromolleculeType.PROTEIN);
        }
        return types.stream()
                .map(MacromolleculeType::getName);
    }

    private boolean hasPositiveCount(Integer count) {
        return count != null && count > 0;
    }

    private static String formatDate(String value) {
        return StringUtils.isNotBlank(value) && value.length() >= 10
               && value.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d.*")
                    ? value.substring(0, 10)
                    : EMPTY;
    }

    private static String emptyForNull(String value) {
        return value != null ? value : EMPTY;
    }

}
