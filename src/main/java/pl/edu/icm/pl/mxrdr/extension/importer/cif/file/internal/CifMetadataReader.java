package pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal;

import org.apache.commons.lang3.StringUtils;
import org.rcsb.cif.model.Column;
import org.rcsb.cif.schema.mm.AuditAuthor;
import org.rcsb.cif.schema.mm.Cell;
import org.rcsb.cif.schema.mm.Citation;
import org.rcsb.cif.schema.mm.CitationAuthor;
import org.rcsb.cif.schema.mm.Diffrn;
import org.rcsb.cif.schema.mm.DiffrnDetector;
import org.rcsb.cif.schema.mm.DiffrnRadiation;
import org.rcsb.cif.schema.mm.DiffrnSource;
import org.rcsb.cif.schema.mm.Entry;
import org.rcsb.cif.schema.mm.MmCifBlock;
import org.rcsb.cif.schema.mm.MmCifFile;
import org.rcsb.cif.schema.mm.PdbxAuditRevisionHistory;
import org.rcsb.cif.schema.mm.PdbxDatabaseStatus;
import org.rcsb.cif.schema.mm.Refine;
import org.rcsb.cif.schema.mm.Reflns;
import org.rcsb.cif.schema.mm.ReflnsShell;
import org.rcsb.cif.schema.mm.Software;
import org.rcsb.cif.schema.mm.Struct;
import org.rcsb.cif.schema.mm.Symmetry;
import pl.edu.icm.pl.mxrdr.extension.importer.MxrdrMetadataField;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CifMetadataReader {

    private MmCifBlock mainBlock;

    // -------------------- LOGIC --------------------

    public ImportedValuesStorage readToResultFields(MmCifFile mmCifFile) {
        if (mmCifFile == null || mmCifFile.getBlocks().size() == 0) {
            return ImportedValuesStorage.EMPTY_STORAGE;
        }

        // Only first block of file will be processed
        this.mainBlock = mmCifFile.getFirstBlock();
        if (mainBlock == null) {
            return ImportedValuesStorage.EMPTY_STORAGE;
        }
        return fill();
    }

    // -------------------- PRIVATE --------------------

    private ImportedValuesStorage fill() {
        List<String> citationOrder = readValues(MmCifBlock::getCitation, Citation::getId);
        return new ImportedValuesStorage()
                .add(MxrdrMetadataField.PDB_ID, readValues(MmCifBlock::getEntry, Entry::getId))
                .add(MxrdrMetadataField.BEAMLINE, readValues(MmCifBlock::getDiffrnSource, DiffrnSource::getPdbxSynchrotronBeamline))
                .add(MxrdrMetadataField.DETECTOR_TYPE, readValues(MmCifBlock::getDiffrnDetector, DiffrnDetector::getDetector))
                .add(MxrdrMetadataField.DATA_COLLECTION_WAVE_LENGTH, readValues(MmCifBlock::getDiffrnSource, DiffrnSource::getPdbxWavelengthList))
                .add(MxrdrMetadataField.DATA_COLLECTION_TEMPERATURE, readValues(MmCifBlock::getDiffrn, Diffrn::getAmbientTemp))
                .add(MxrdrMetadataField.SPACE_GROUP, readValues(MmCifBlock::getSymmetry, Symmetry::getSpaceGroupNameH_M))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_A, readValues(MmCifBlock::getCell, Cell::getLengthA))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_B, readValues(MmCifBlock::getCell, Cell::getLengthB))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_C, readValues(MmCifBlock::getCell, Cell::getLengthC))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_ALPHA, readValues(MmCifBlock::getCell, Cell::getAngleAlpha))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_BETA, readValues(MmCifBlock::getCell, Cell::getAngleBeta))
                .add(MxrdrMetadataField.UNIT_CELL_PARAMETER_GAMMA, readValues(MmCifBlock::getCell, Cell::getAngleGamma))
                .add(MxrdrMetadataField.OVERALL_COMPLETENESS, readValues(MmCifBlock::getReflns, Reflns::getPercentPossibleObs))
                .add(MxrdrMetadataField.OVERALL_I_SIGMA, readValues(MmCifBlock::getReflns, Reflns::getPdbxNetIOverSigmaI))
                .add(MxrdrMetadataField.OVERALL_R_MERGE, readValues(MmCifBlock::getReflns, Reflns::getPdbxRmergeIObs))
                .add(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_LOW, readValues(MmCifBlock::getRefine, Refine::getLsDResLow))
                .add(MxrdrMetadataField.OVERALL_DATA_RESOLUTION_RANGE_HIGH, readValues(MmCifBlock::getRefine, Refine::getLsDResHigh))
                .add(MxrdrMetadataField.HRS_COMPLETENESS, readValues(MmCifBlock::getReflnsShell, ReflnsShell::getPercentPossibleAll))
                .add(MxrdrMetadataField.HRS_SIGMA, readValues(MmCifBlock::getReflnsShell, ReflnsShell::getMeanIOverSigIObs))
                .add(MxrdrMetadataField.HRS_R_MERGE, readValues(MmCifBlock::getReflnsShell, ReflnsShell::getRmergeIObs))
                .add(MxrdrMetadataField.MONOCHROMATOR, readValues(MmCifBlock::getDiffrnRadiation, DiffrnRadiation::getMonochromator))
                .add(MxrdrMetadataField.PROCESSING_SOFTWARE, readValues(MmCifBlock::getSoftware, Software::getName))
                .add(MxrdrMetadataField.PDB_TITLE, readValues(MmCifBlock::getStruct, Struct::getTitle))
                .add(MxrdrMetadataField.PDB_DOI, readValues(MmCifBlock::getCitation, Citation::getPdbxDatabaseIdDOI))
                .add(MxrdrMetadataField.PDB_STRUCTURE_AUTHOR, readValues(MmCifBlock::getAuditAuthor, AuditAuthor::getName))
                .add(MxrdrMetadataField.PDB_DEPOSIT_DATE, readValues(MmCifBlock::getPdbxDatabaseStatus, PdbxDatabaseStatus::getRecvdInitialDepositionDate))
                .add(MxrdrMetadataField.PDB_RELEASE_DATE, readValues(MmCifBlock::getPdbxAuditRevisionHistory, PdbxAuditRevisionHistory::getRevisionDate))
                .add(MxrdrMetadataField.PDB_REVISION_DATE, readValues(MmCifBlock::getPdbxAuditRevisionHistory, PdbxAuditRevisionHistory::getRevisionDate))
                .add(MxrdrMetadataField.CITATION_TITLE, zipLists(citationOrder, readValues(MmCifBlock::getCitation, Citation::getTitle), CifFileConstants.SAFE_ITEMS_DELIMITER))
                .add(MxrdrMetadataField.CITATION_PUBMED_ID, zipLists(citationOrder, readValues(MmCifBlock::getCitation, Citation::getPdbxDatabaseIdPubMed), CifFileConstants.SAFE_ITEMS_DELIMITER))
                .add(MxrdrMetadataField.CITATION_JOURNAL, zipLists(citationOrder, readValues(MmCifBlock::getCitation, Citation::getJournalAbbrev), CifFileConstants.SAFE_ITEMS_DELIMITER))
                .add(MxrdrMetadataField.CITATION_YEAR, zipLists(citationOrder, readValues(MmCifBlock::getCitation, Citation::getYear), CifFileConstants.SAFE_ITEMS_DELIMITER))
                .add(MxrdrMetadataField.CITATION_AUTHOR, zipLists(
                        readValues(MmCifBlock::getCitationAuthor, CitationAuthor::getCitationId),
                        readValues(MmCifBlock::getCitationAuthor, CitationAuthor::getName), CifFileConstants.SAFE_ITEMS_DELIMITER));
    }

    <T> List<String> readValues(Function<MmCifBlock, T> categoryGetter,
                                        Function<T, Column<?>> itemGetter) {
        return Optional.ofNullable(categoryGetter.apply(mainBlock))
                .map(itemGetter)
                .filter(Column::isDefined)
                .map(Column::stringData)
                .orElseGet(Stream::empty)
                .map(v -> "?".equals(v) || ".".equals(v) ? StringUtils.EMPTY : v)
                .collect(Collectors.toList());
    }

    List<String> zipLists(List<String> first, List<String> second, String delimiter) {
        int firstSize = first.size();
        int secondSize = second.size();

        if (firstSize != secondSize) {
            List<String> listToExtend = firstSize > secondSize ? second : first;
            int difference = Math.abs(firstSize - secondSize);
            for (int i = 0; i < difference; i++) {
                listToExtend.add(StringUtils.EMPTY);
            }
        }

        return IntStream.range(0, first.size()) // need to call again as the list might have been extended
                .mapToObj(i -> first.get(i) + delimiter + second.get(i))
                .collect(Collectors.toList());
    }

}
