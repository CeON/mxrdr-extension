package pl.edu.icm.pl.mxrdr.extension.importer.pdb.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class EntryData {

    @JsonProperty("rcsb_id")
    private String rcsbId;

    @JsonProperty("audit_author")
    private List<AuditAuthor> auditAuthor = new ArrayList<>();

    @JsonProperty("cell")
    private Cell cell = new Cell();

    @JsonProperty("diffrn_source")
    private List<DiffrnSource> diffrnSource = new ArrayList<>();

    @JsonProperty("diffrn_detector")
    private List<DiffrnDetector> diffrnDetector = new ArrayList<>();

    @JsonProperty("diffrn_radiation")
    private List<DiffrnRadiation> diffrnRadiation = new ArrayList<>();

    @JsonProperty("pdbx_vrpt_summary")
    private PdbxVrptSummary pdbxVrptSummary;

    @JsonProperty("rcsb_accession_info")
    private RcsbAccessionInfo rcsbAccessionInfo = new RcsbAccessionInfo();

    @JsonProperty("rcsb_entry_info")
    private RcsbEntryInfo rcsbEntryInfo = new RcsbEntryInfo();

    @JsonProperty("rcsb_primary_citation")
    private RcsbPrimaryCitation rcsbPrimaryCitation = new RcsbPrimaryCitation();

    @JsonProperty("refine")
    private List<Refine> refine = new ArrayList<>();

    @JsonProperty("reflns")
    private List<Reflns> reflns = new ArrayList<>();

    @JsonProperty("reflns_shell")
    private List<ReflnsShell> reflnsShell = new ArrayList<>();

    @JsonProperty("software")
    private List<Software> software = new ArrayList<>();

    @JsonProperty("struct")
    private Struct struct = new Struct();

    @JsonProperty("symmetry")
    private Symmetry symmetry = new Symmetry();

    // -------------------- GETTERS --------------------

    public String getRcsbId() {
        return rcsbId;
    }

    public List<AuditAuthor> getAuditAuthor() {
        return auditAuthor;
    }

    public Cell getCell() {
        return cell;
    }

    public List<DiffrnSource> getDiffrnSource() {
        return diffrnSource;
    }

    public List<DiffrnDetector> getDiffrnDetector() {
        return diffrnDetector;
    }

    public List<DiffrnRadiation> getDiffrnRadiation() {
        return diffrnRadiation;
    }

    public PdbxVrptSummary getPdbxVrptSummary() {
        return pdbxVrptSummary;
    }

    public RcsbAccessionInfo getRcsbAccessionInfo() {
        return rcsbAccessionInfo;
    }

    public RcsbEntryInfo getRcsbEntryInfo() {
        return rcsbEntryInfo;
    }

    public RcsbPrimaryCitation getRcsbPrimaryCitation() {
        return rcsbPrimaryCitation;
    }

    public List<Refine> getRefine() {
        return refine;
    }

    public List<Reflns> getReflns() {
        return reflns;
    }

    public List<ReflnsShell> getReflnsShell() {
        return reflnsShell;
    }

    public List<Software> getSoftware() {
        return software;
    }

    public Struct getStruct() {
        return struct;
    }

    public Symmetry getSymmetry() {
        return symmetry;
    }

    // -------------------- INNER CLASSES --------------------

    public static class AuditAuthor {

        @JsonProperty("name")
        private String name;

        public String getName() {
            return name;
        }
    }

    public static class Cell {

        @JsonProperty("length_a")
        private String lengthA;

        @JsonProperty("length_b")
        private String lengthB;

        @JsonProperty("length_c")
        private String lengthC;

        @JsonProperty("angle_alpha")
        private String angleAlpha;

        @JsonProperty("angle_beta")
        private String angleBeta;

        @JsonProperty("angle_gamma")
        private String angleGamma;

        public String getLengthA() {
            return lengthA;
        }

        public String getLengthB() {
            return lengthB;
        }

        public String getLengthC() {
            return lengthC;
        }

        public String getAngleAlpha() {
            return angleAlpha;
        }

        public String getAngleBeta() {
            return angleBeta;
        }

        public String getAngleGamma() {
            return angleGamma;
        }
    }

    public static class DiffrnSource {

        @JsonProperty("diffrn_id")
        private Integer diffrnId;

        @JsonProperty("pdbx_synchrotron_site")
        private String pdbxSynchrotronSite;

        @JsonProperty("pdbx_synchrotron_beamline")
        private String pdbxSynchrotronBeamline;

        @JsonProperty("pdbx_wavelength_list")
        private String pdbxWavelengthList;

        public Integer getDiffrnId() {
            return diffrnId;
        }

        public String getPdbxSynchrotronSite() {
            return pdbxSynchrotronSite;
        }

        public String getPdbxSynchrotronBeamline() {
            return pdbxSynchrotronBeamline;
        }

        public String getPdbxWavelengthList() {
            return pdbxWavelengthList;
        }
    }

    public static class DiffrnDetector {

        @JsonProperty("diffrn_id")
        private Integer diffrnId;

        @JsonProperty("type")
        private String type;

        public Integer getDiffrnId() {
            return diffrnId;
        }

        public String getType() {
            return type;
        }
    }

    public static class DiffrnRadiation {

        @JsonProperty("diffrn_id")
        private Integer diffrnId;

        @JsonProperty("monochromator")
        private String monochromator;

        public Integer getDiffrnId() {
            return diffrnId;
        }

        public String getMonochromator() {
            return monochromator;
        }
    }

    public static class PdbxVrptSummary {

        @JsonProperty("data_completeness")
        private String dataCompleteness;

        public String getDataCompleteness() {
            return dataCompleteness;
        }
    }

    public static class RcsbAccessionInfo {

        @JsonProperty("deposit_date")
        private String depositDate;

        @JsonProperty("initial_release_date")
        private String initialReleaseDate;

        @JsonProperty("revision_date")
        private String revisionDate;

        public String getDepositDate() {
            return depositDate;
        }

        public String getInitialReleaseDate() {
            return initialReleaseDate;
        }

        public String getRevisionDate() {
            return revisionDate;
        }
    }

    public static class RcsbEntryInfo {

        @JsonProperty("polymer_entity_count")
        private Integer polymerEntityCount;

        @JsonProperty("polymer_monomer_count_maximum")
        private String polymerMonomerCountMaximum;

        @JsonProperty("molecular_weight")
        private String molecularWeight;

        @JsonProperty("deposited_atom_count")
        private String depositedAtomCount;


        public Integer getPolymerEntityCount() {
            return polymerEntityCount;
        }

        public String getPolymerMonomerCountMaximum() {
            return polymerMonomerCountMaximum;
        }

        public String getMolecularWeight() {
            return molecularWeight;
        }

        public String getDepositedAtomCount() {
            return depositedAtomCount;
        }
    }

    public static class RcsbPrimaryCitation {

        @JsonProperty("title")
        private String title;

        @JsonProperty("pdbx_database_id_pub_med")
        private String pdbxDatabaseIdPubMed;

        @JsonProperty("rcsb_authors")
        private List<String> rcsbAuthors = new ArrayList<>();

        @JsonProperty("journal_abbrev")
        private String journalAbbrev;

        @JsonProperty("year")
        private String year;

        public String getTitle() {
            return title;
        }

        public String getPdbxDatabaseIdPubMed() {
            return pdbxDatabaseIdPubMed;
        }

        public List<String> getRcsbAuthors() {
            return rcsbAuthors;
        }

        public String getJournalAbbrev() {
            return journalAbbrev;
        }

        public String getYear() {
            return year;
        }
    }

    public static class Refine {

        @JsonProperty("ls_rfactor_rwork")
        private String lsRFactorRWork;

        @JsonProperty("ls_rfactor_rfree")
        private String lsRFactorRFree;

        public String getLsRFactorRWork() {
            return lsRFactorRWork;
        }

        public String getLsRFactorRFree() {
            return lsRFactorRFree;
        }
    }

    public static class Reflns {

        @JsonProperty("pdbx_ordinal")
        private Integer pdbxOrdinal;

        @JsonProperty("pdbx_net_iover_sigma_i")
        private String pdbxNetIOverSigmaI;

        @JsonProperty("pdbx_rmerge_iobs")
        private String pdbxRMergeIObs;

        @JsonProperty("dresolution_high")
        private String dResolutionHigh;

        @JsonProperty("dresolution_low")
        private String dResolutionLow;

        @JsonProperty("number_obs")
        private String numberObs;

        public Integer getPdbxOrdinal() {
            return pdbxOrdinal;
        }

        public String getPdbxNetIOverSigmaI() {
            return pdbxNetIOverSigmaI;
        }

        public String getPdbxRMergeIObs() {
            return pdbxRMergeIObs;
        }

        public String getdResolutionHigh() {
            return dResolutionHigh;
        }

        public String getdResolutionLow() {
            return dResolutionLow;
        }

        public String getNumberObs() {
            return numberObs;
        }
    }

    public static class ReflnsShell {

        @JsonProperty("pdbx_ordinal")
        private Integer pdbxOrdinal;

        @JsonProperty("mean_iover_sig_iobs")
        private String meanIOverSigIObs;

        @JsonProperty("rmerge_iobs")
        private String RMergeIObs;

        @JsonProperty("dres_high")
        private String dResHigh;

        @JsonProperty("dres_low")
        private String dResLow;

        public Integer getPdbxOrdinal() {
            return pdbxOrdinal;
        }

        public String getMeanIOverSigIObs() {
            return meanIOverSigIObs;
        }

        public String getRMergeIObs() {
            return RMergeIObs;
        }

        public String getdResHigh() {
            return dResHigh;
        }

        public String getdResLow() {
            return dResLow;
        }
    }

    public static class Software {

        @JsonProperty("classification")
        private String classification;

        @JsonProperty("name")
        private String name;

        public String getClassification() {
            return classification;
        }

        public String getName() {
            return name;
        }
    }

    public static class Struct {

        @JsonProperty("title")
        private String title;

        public String getTitle() {
            return title;
        }
    }

    public static class Symmetry {

        @JsonProperty("int_tables_number")
        private String intTablesNumber;

        public String getIntTablesNumber() {
            return intTablesNumber;
        }
    }
}
