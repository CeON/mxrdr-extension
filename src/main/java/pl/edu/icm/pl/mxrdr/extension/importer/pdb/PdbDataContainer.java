package pl.edu.icm.pl.mxrdr.extension.importer.pdb;

import org.apache.commons.lang3.StringUtils;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;
import pl.edu.icm.pl.mxrdr.extension.importer.ProcessingSoftwareMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.SymmetryStructureMapper;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.EntryData;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.PolymerEntityData;
import pl.edu.icm.pl.mxrdr.extension.importer.pdb.model.StructureData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.EMPTY;

class PdbDataContainer {
    private static final int FIRST_INDEX = 1;
    private static final String DATA_REDUCTION = "data reduction";
    private static final String PDB_DOI_TEMPLATE = "10.2210/pdb%s/pdb";

    private Map<MxrdrMetadataField, List<String>> container = new HashMap<>();

    // -------------------- LOGIC --------------------

    /**
     * Fills the container with values from {@link StructureData} according
     * to the needs of {@link PdbMapper}.
     *
     * Here we've made one but strong assumption: in case of taking single
     * element from the list of inner objects we always choose the first
     * (that indexed with one or first on the list) and assume that the
     * result will be consistent, which may not be necessarily true.
     */
    public PdbDataContainer init(StructureData structureData) {
        EntryData entry = structureData.getEntryData();
        return add(MxrdrMetadataField.PDB_ID, entry.getRcsbId())
                .add(MxrdrMetadataField.BEAMLINE_INSTITUTION, getFirst(EntryData::getDiffrnSource, EntryData.DiffrnSource::getDiffrnId, entry)
                        .map(EntryData.DiffrnSource::getPdbxSynchrotronSite))
                .add(MxrdrMetadataField.BEAMLINE, getFirst(EntryData::getDiffrnSource, EntryData.DiffrnSource::getDiffrnId, entry)
                        .map(EntryData.DiffrnSource::getPdbxSynchrotronBeamline))
                .add(MxrdrMetadataField.DETECTOR_TYPE, getFirst(EntryData::getDiffrnDetector, EntryData.DiffrnDetector::getDiffrnId, entry)
                        .map(EntryData.DiffrnDetector::getType))
                .addAll(MxrdrMetadataField.DATA_COLLECTION_WAVELENGTH, getAll(EntryData::getDiffrnSource, EntryData.DiffrnSource::getDiffrnId, entry).stream()
                        .map(EntryData.DiffrnSource::getPdbxWavelengthList))
                .add(MxrdrMetadataField.SPACE_GROUP, SymmetryStructureMapper.mapSpaceGroupNumber(emptyForNull(entry.getSymmetry().getIntTablesNumber())))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_A, entry.getCell().getLengthA())
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_B, entry.getCell().getLengthB())
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_C, entry.getCell().getLengthC())
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA, entry.getCell().getAngleAlpha())
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA, entry.getCell().getAngleBeta())
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA, entry.getCell().getAngleGamma())
                .add(MxrdrMetadataField.OVERALL_COMPLETENESS, entry.getPdbxVrptSummary().getDataCompleteness())
                .add(MxrdrMetadataField.OVERALL_I_SIGMA, getFirst(EntryData::getReflns, EntryData.Reflns::getPdbxOrdinal, entry)
                        .map(EntryData.Reflns::getPdbxNetIOverSigmaI))
                .add(MxrdrMetadataField.OVERALL_R_MERGE, getFirst(EntryData::getReflns, EntryData.Reflns::getPdbxOrdinal, entry)
                        .map(EntryData.Reflns::getPdbxRMergeIObs))
                .add(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_LOW, getFirst(EntryData::getReflns, EntryData.Reflns::getPdbxOrdinal, entry)
                        .map(EntryData.Reflns::getdResolutionLow))
                .add(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH, getFirst(EntryData::getReflns, EntryData.Reflns::getPdbxOrdinal, entry)
                        .map(EntryData.Reflns::getdResolutionHigh))
                .add(MxrdrMetadataField.OVERALL_NUMBER_OBSERVED_REFLECTIONS, getFirst(EntryData::getReflns, EntryData.Reflns::getPdbxOrdinal, entry)
                        .map(EntryData.Reflns::getNumberObs))
                .add(MxrdrMetadataField.HRS_I_SIGMA, getFirst(EntryData::getReflnsShell, EntryData.ReflnsShell::getPdbxOrdinal, entry)
                        .map(EntryData.ReflnsShell::getMeanIOverSigIObs))
                .add(MxrdrMetadataField.HRS_R_MERGE, getFirst(EntryData::getReflnsShell, EntryData.ReflnsShell::getPdbxOrdinal, entry)
                        .map(EntryData.ReflnsShell::getRMergeIObs))
                .add(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_LOW, getFirst(EntryData::getReflnsShell, EntryData.ReflnsShell::getPdbxOrdinal, entry)
                        .map(EntryData.ReflnsShell::getdResLow))
                .add(MxrdrMetadataField.HRS_DATA_RESOLUTION_RANGE_HIGH, getFirst(EntryData::getReflnsShell, EntryData.ReflnsShell::getPdbxOrdinal, entry)
                        .map(EntryData.ReflnsShell::getdResHigh))
                .add(MxrdrMetadataField.MONOCHROMATOR, getFirst(EntryData::getDiffrnRadiation, EntryData.DiffrnRadiation::getDiffrnId, entry)
                        .map(EntryData.DiffrnRadiation::getMonochromator))
                .addAll(MxrdrMetadataField.PROCESSING_SOFTWARE, entry.getSoftware().stream()
                        .filter(s -> s.getClassification().equals(DATA_REDUCTION))
                        .map(EntryData.Software::getName)
                        .map(ProcessingSoftwareMapper::map))
                // For the following two we do not have numeric id, so we use the order of encounter
                .add(MxrdrMetadataField.REFINEMENT_FACTOR_R_WORK, getFirst(EntryData::getRefine, index -> 1, entry)
                        .map(EntryData.Refine::getLsRFactorRWork))
                .add(MxrdrMetadataField.REFINEMENT_FACTOR_R_FREE, getFirst(EntryData::getRefine, index -> 1, entry)
                        .map(EntryData.Refine::getLsRFactorRFree))
                .add(MxrdrMetadataField.MOLECULAR_WEIGHT, entry.getRcsbEntryInfo().getMolecularWeight())
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

    PdbDataContainer add(MxrdrMetadataField key, Optional<String> value) {
        return add(key, value.orElse(EMPTY));
    }

    PdbDataContainer add(MxrdrMetadataField key, String value) {
        List<String> values = container.putIfAbsent(key, new ArrayList<>());
        values = values != null ? values : container.get(key);
        values.add(value != null ? value : EMPTY);
        return this;
    }

    PdbDataContainer addAll(MxrdrMetadataField key, Stream<String> valueList) {
        List<String> values = container.putIfAbsent(key, new ArrayList<>());
        values = values != null ? values : container.get(key);
        values.addAll(valueList.collect(Collectors.toList()));
        return this;
    }

    /**
     * Returns the first element for key
     */
    Optional<String> get(MxrdrMetadataField key) {
        return container.getOrDefault(key, Collections.emptyList()).stream()
                .findFirst();
    }

    Optional<String> getIndexed(MxrdrMetadataField key, int index) {
        List<String> values = container.getOrDefault(key, Collections.emptyList());
        return !(index < 0 || index >= values.size())
                ? Optional.ofNullable(values.get(index))
                : Optional.empty();
    }

    List<String> getAll(MxrdrMetadataField key) {
        return container.getOrDefault(key, Collections.emptyList());
    }

    /**
     * Gets all data from inner collection of entryData sorting by values of index.
     */
    static <D, T> List<T> getAll(Function<D, List<T>> getter, Function<T, Integer> indexGetter, D entryData) {
        return getter.apply(entryData)
                .stream()
                .sorted(Comparator.comparingInt(indexGetter::apply))
                .collect(Collectors.toList());
    }

    /**
     * Gets the first (according to index value) entry from inner collection of entryData.
     */
    static <D, T> Optional<T> getFirst(Function<D, List<T>> getter, Function<T, Integer> indexGetter, D entryData) {
        return getter.apply(entryData)
                .stream()
                .filter(t -> indexGetter.apply(t).equals(FIRST_INDEX))
                .findFirst();
    }

    // -------------------- PRIVATE --------------------

    private static String formatDate(String value) {
        return StringUtils.isNotBlank(value) && value.length() >= 10 && value.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d.*")
                ? value.substring(0, 10)
                : EMPTY;
    }

    private static String emptyForNull(String value) {
        return value != null ? value : EMPTY;
    }

}
