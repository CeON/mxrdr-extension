package pl.edu.icm.pl.mxrdr.extension.importer;

import edu.harvard.iq.dataverse.importer.metadata.ResultField;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ProcessingSoftwareMapper {
    private static Map<String, ProcessingSoftware> MAPPING = ProcessingSoftwareMapper.Initializer.createSoftwareMapping();

    public static Stream<ResultField> mapToStream(String value) {
        return Stream.of(ResultField.ofValue(ProcessingSoftwareMapper.map(value)));
    }

    public static String map(String value) {
        return MAPPING.getOrDefault(value.trim().toUpperCase(), ProcessingSoftware.OTHER)
                .getName();
    }

    private static class Initializer {
        static Map<String, ProcessingSoftware> createSoftwareMapping() {
            HashMap<String, ProcessingSoftware> mapping = new HashMap<>();
            mapping.put("ABS", ProcessingSoftware.OTHER);
            mapping.put("ABSCALE", ProcessingSoftware.OTHER);
            mapping.put("ABSCOR", ProcessingSoftware.OTHER);
            mapping.put("ACORN", ProcessingSoftware.OTHER);
            mapping.put("ADDREF", ProcessingSoftware.OTHER);
            mapping.put("ADSC", ProcessingSoftware.OTHER);
            mapping.put("AMBER", ProcessingSoftware.OTHER);
            mapping.put("AMPLE", ProcessingSoftware.OTHER);
            mapping.put("AMORE", ProcessingSoftware.OTHER);
            mapping.put("APEX", ProcessingSoftware.OTHER);
            mapping.put("APEX 2", ProcessingSoftware.OTHER);
            mapping.put("APRV", ProcessingSoftware.OTHER);
            mapping.put("ARP", ProcessingSoftware.OTHER);
            mapping.put("ARP/WARP", ProcessingSoftware.OTHER);
            mapping.put("AUTOMAR", ProcessingSoftware.OTHER);
            mapping.put("ADXV", ProcessingSoftware.OTHER);
            mapping.put("AGROVATA", ProcessingSoftware.OTHER);
            mapping.put("AIMLESS", ProcessingSoftware.OTHER);
            mapping.put("ARCIMBOLDO", ProcessingSoftware.OTHER);
            mapping.put("AUTO-RICKSHAW", ProcessingSoftware.OTHER);
            mapping.put("AUTOPROC", ProcessingSoftware.AUTOPROC);
            mapping.put("AUTOPROCESS", ProcessingSoftware.AUTOPROC);
            mapping.put("AUTOSOL", ProcessingSoftware.OTHER);
            mapping.put("BALBES", ProcessingSoftware.OTHER);
            mapping.put("BEAST", ProcessingSoftware.OTHER);
            mapping.put("BILDER", ProcessingSoftware.OTHER);
            mapping.put("BIOMOL", ProcessingSoftware.OTHER);
            mapping.put("BLU-MAX", ProcessingSoftware.OTHER);
            mapping.put("BOS", ProcessingSoftware.OTHER);
            mapping.put("BRUTE", ProcessingSoftware.OTHER);
            mapping.put("BSS", ProcessingSoftware.OTHER);
            mapping.put("BUCCANEER", ProcessingSoftware.OTHER);
            mapping.put("BUSTER", ProcessingSoftware.OTHER);
            mapping.put("BABEL", ProcessingSoftware.OTHER);
            mapping.put("BLU-ICE", ProcessingSoftware.OTHER);
            mapping.put("CBASS", ProcessingSoftware.OTHER);
            mapping.put("CHAINSAW", ProcessingSoftware.OTHER);
            mapping.put("CNS", ProcessingSoftware.OTHER);
            mapping.put("CNX", ProcessingSoftware.OTHER);
            mapping.put("COMBAT", ProcessingSoftware.OTHER);
            mapping.put("COMO", ProcessingSoftware.OTHER);
            mapping.put("CORELS", ProcessingSoftware.OTHER);
            mapping.put("CRANK", ProcessingSoftware.OTHER);
            mapping.put("CRANK2", ProcessingSoftware.OTHER);
            mapping.put("CASPR", ProcessingSoftware.OTHER);
            mapping.put("CHEETAH", ProcessingSoftware.OTHER);
            mapping.put("COOT", ProcessingSoftware.OTHER);
            mapping.put("CRYSALISPRO", ProcessingSoftware.OTHER);
            mapping.put("CRYSTFEL", ProcessingSoftware.OTHER);
            mapping.put("CRYSTALCLEAR", ProcessingSoftware.OTHER);
            mapping.put("DENZO", ProcessingSoftware.DENZO);
            mapping.put("DIALS", ProcessingSoftware.DIALS);
            mapping.put("DIFDAT", ProcessingSoftware.OTHER);
            mapping.put("DIMPLE", ProcessingSoftware.OTHER);
            mapping.put("DM", ProcessingSoftware.OTHER);
            mapping.put("DMMULTI", ProcessingSoftware.OTHER);
            mapping.put("DNA", ProcessingSoftware.OTHER);
            mapping.put("DPS", ProcessingSoftware.OTHER);
            mapping.put("DIRAX", ProcessingSoftware.OTHER);
            mapping.put("EDNA", ProcessingSoftware.OTHER);
            mapping.put("ELVES", ProcessingSoftware.OTHER);
            mapping.put("EPMR", ProcessingSoftware.OTHER);
            mapping.put("EREF", ProcessingSoftware.OTHER);
            mapping.put("EVAL15", ProcessingSoftware.OTHER);
            mapping.put("EPINORM", ProcessingSoftware.OTHER);
            mapping.put("FFFEAR", ProcessingSoftware.OTHER);
            mapping.put("FFT", ProcessingSoftware.OTHER);
            mapping.put("FRAMBO", ProcessingSoftware.OTHER);
            mapping.put("FRFS", ProcessingSoftware.OTHER);
            mapping.put("FRODO", ProcessingSoftware.OTHER);
            mapping.put("FORCE FIELD X", ProcessingSoftware.OTHER);
            mapping.put("FRAGON", ProcessingSoftware.OTHER);
            mapping.put("GDA", ProcessingSoftware.OTHER);
            mapping.put("GLRF", ProcessingSoftware.OTHER);
            mapping.put("GPRLSA", ProcessingSoftware.OTHER);
            mapping.put("HKL-2000", ProcessingSoftware.HKL);
            mapping.put("HKL-3000", ProcessingSoftware.HKL);
            mapping.put("HKL2MAP", ProcessingSoftware.HKL);
            mapping.put("ISIR", ProcessingSoftware.OTHER);
            mapping.put("ISOLDE", ProcessingSoftware.OTHER);
            mapping.put("INSIGHT II", ProcessingSoftware.OTHER);
            mapping.put("JACK-LEVITT", ProcessingSoftware.OTHER);
            mapping.put("JBLUICE-EPICS", ProcessingSoftware.OTHER);
            mapping.put("JDIRECTOR", ProcessingSoftware.OTHER);
            mapping.put("KYLIN", ProcessingSoftware.OTHER);
            mapping.put("LAUEGEN", ProcessingSoftware.OTHER);
            mapping.put("LSCALE", ProcessingSoftware.OTHER);
            mapping.put("LAUEVIEW", ProcessingSoftware.OTHER);
            mapping.put("MADNESS", ProcessingSoftware.OTHER);
            mapping.put("MADSYS", ProcessingSoftware.OTHER);
            mapping.put("MAIN", ProcessingSoftware.OTHER);
            mapping.put("MAR345", ProcessingSoftware.OTHER);
            mapping.put("MAR345DTB", ProcessingSoftware.OTHER);
            mapping.put("MD2", ProcessingSoftware.OTHER);
            mapping.put("MERLOT", ProcessingSoftware.OTHER);
            mapping.put("MLPHARE", ProcessingSoftware.OTHER);
            mapping.put("MOLEMAN2", ProcessingSoftware.OTHER);
            mapping.put("MOLREP", ProcessingSoftware.OTHER);
            mapping.put("MOSFLM", ProcessingSoftware.MOSFLM);
            mapping.put("MR-ROSETTA", ProcessingSoftware.OTHER);
            mapping.put("MANTID", ProcessingSoftware.OTHER);
            mapping.put("MORDA", ProcessingSoftware.OTHER);
            mapping.put("MOLPROBITY", ProcessingSoftware.OTHER);
            mapping.put("MRBUMP", ProcessingSoftware.OTHER);
            mapping.put("MXCUBE", ProcessingSoftware.OTHER);
            mapping.put("MXDC", ProcessingSoftware.OTHER);
            mapping.put("NUCLSQ", ProcessingSoftware.OTHER);
            mapping.put("O", ProcessingSoftware.OTHER);
            mapping.put("OASIS", ProcessingSoftware.OTHER);
            mapping.put("PARROT", ProcessingSoftware.OTHER);
            mapping.put("PDB-REDO", ProcessingSoftware.OTHER);
            mapping.put("PDBSET", ProcessingSoftware.OTHER);
            mapping.put("PDB_EXTRACT", ProcessingSoftware.OTHER);
            mapping.put("PHASER", ProcessingSoftware.OTHER);
            mapping.put("PHASES", ProcessingSoftware.OTHER);
            mapping.put("PHENIX", ProcessingSoftware.OTHER);
            mapping.put("PROCESS", ProcessingSoftware.OTHER);
            mapping.put("PROCOR", ProcessingSoftware.OTHER);
            mapping.put("PROFFT", ProcessingSoftware.OTHER);
            mapping.put("PROLSQ", ProcessingSoftware.OTHER);
            mapping.put("PROTEUM", ProcessingSoftware.OTHER);
            mapping.put("PROTEUM PLUS", ProcessingSoftware.OTHER);
            mapping.put("PROTEUM2", ProcessingSoftware.OTHER);
            mapping.put("PRECOGNITION", ProcessingSoftware.OTHER);
            mapping.put("PRODC", ProcessingSoftware.OTHER);
            mapping.put("QUANTA", ProcessingSoftware.OTHER);
            mapping.put("QUEEN OF SPADES", ProcessingSoftware.OTHER);
            mapping.put("RANTAN", ProcessingSoftware.OTHER);
            mapping.put("RAVE", ProcessingSoftware.OTHER);
            mapping.put("REFMAC", ProcessingSoftware.OTHER);
            mapping.put("REFPK", ProcessingSoftware.OTHER);
            mapping.put("RESOLVE", ProcessingSoftware.OTHER);
            mapping.put("RESTRAIN", ProcessingSoftware.OTHER);
            mapping.put("ROTAPREP", ProcessingSoftware.OTHER);
            mapping.put("ROTAVATA", ProcessingSoftware.OTHER);
            mapping.put("RSPS", ProcessingSoftware.OTHER);
            mapping.put("REMDAQ", ProcessingSoftware.OTHER);
            mapping.put("ROSETTA", ProcessingSoftware.OTHER);
            mapping.put("SADABS", ProcessingSoftware.OTHER);
            mapping.put("SAINT", ProcessingSoftware.OTHER);
            mapping.put("SBC-COLLECT", ProcessingSoftware.OTHER);
            mapping.put("SCALA", ProcessingSoftware.OTHER);
            mapping.put("SCALEIT", ProcessingSoftware.OTHER);
            mapping.put("SCALEPACK", ProcessingSoftware.OTHER);
            mapping.put("SDMS", ProcessingSoftware.OTHER);
            mapping.put("SERGUI", ProcessingSoftware.OTHER);
            mapping.put("SGXPRO", ProcessingSoftware.OTHER);
            mapping.put("SHARP", ProcessingSoftware.OTHER);
            mapping.put("SHELX", ProcessingSoftware.OTHER);
            mapping.put("SHELXCD", ProcessingSoftware.OTHER);
            mapping.put("SHELXD", ProcessingSoftware.OTHER);
            mapping.put("SHELXDE", ProcessingSoftware.OTHER);
            mapping.put("SHELXE", ProcessingSoftware.OTHER);
            mapping.put("SHELXL", ProcessingSoftware.OTHER);
            mapping.put("SHELXL-97", ProcessingSoftware.OTHER);
            mapping.put("SHELXPREP", ProcessingSoftware.OTHER);
            mapping.put("SHELXS", ProcessingSoftware.OTHER);
            mapping.put("SIGMAA", ProcessingSoftware.OTHER);
            mapping.put("SIMBAD", ProcessingSoftware.OTHER);
            mapping.put("SOLOMON", ProcessingSoftware.OTHER);
            mapping.put("SOLVE", ProcessingSoftware.OTHER);
            mapping.put("SORTAV", ProcessingSoftware.OTHER);
            mapping.put("SORTRF", ProcessingSoftware.OTHER);
            mapping.put("SQUASH", ProcessingSoftware.OTHER);
            mapping.put("STARANISO", ProcessingSoftware.OTHER);
            mapping.put("SIR2014", ProcessingSoftware.OTHER);
            mapping.put("SNB", ProcessingSoftware.OTHER);
            mapping.put("STRUCTURESTUDIO", ProcessingSoftware.OTHER);
            mapping.put("TFFC", ProcessingSoftware.OTHER);
            mapping.put("TFORM", ProcessingSoftware.OTHER);
            mapping.put("TNT", ProcessingSoftware.OTHER);
            mapping.put("TRUNCATE", ProcessingSoftware.OTHER);
            mapping.put("UCSD-SYSTEM", ProcessingSoftware.OTHER);
            mapping.put("VAGABOND", ProcessingSoftware.OTHER);
            mapping.put("WARP", ProcessingSoftware.OTHER);
            mapping.put("WEIS", ProcessingSoftware.OTHER);
            mapping.put("WEB-ICE", ProcessingSoftware.OTHER);
            mapping.put("X-AREA", ProcessingSoftware.OTHER);
            mapping.put("X-GEN", ProcessingSoftware.OTHER);
            mapping.put("X-PLOR", ProcessingSoftware.OTHER);
            mapping.put("XDS", ProcessingSoftware.XDS);
            mapping.put("XFIT", ProcessingSoftware.OTHER);
            mapping.put("XPREP", ProcessingSoftware.OTHER);
            mapping.put("XSCALE", ProcessingSoftware.OTHER);
            mapping.put("XTALVIEW", ProcessingSoftware.OTHER);
            mapping.put("ZANUDA", ProcessingSoftware.OTHER);
            mapping.put("AUTOBUSTER", ProcessingSoftware.OTHER);
            mapping.put("AUTOSHARP", ProcessingSoftware.OTHER);
            mapping.put("AUTOXDS", ProcessingSoftware.XDS);
            mapping.put("BIOTEX", ProcessingSoftware.OTHER);
            mapping.put("CCTBX.PRIME", ProcessingSoftware.OTHER);
            mapping.put("CCTBX.XFEL", ProcessingSoftware.OTHER);
            mapping.put("CXI.MERGE", ProcessingSoftware.OTHER);
            mapping.put("D*TREK", ProcessingSoftware.D_TREK);
            mapping.put("IMOSFLM", ProcessingSoftware.MOSFLM);
            mapping.put("NCNS", ProcessingSoftware.OTHER);
            mapping.put("PIRATE", ProcessingSoftware.OTHER);
            mapping.put("POINTLESS", ProcessingSoftware.OTHER);
            mapping.put("XIA2", ProcessingSoftware.XIA2);
            return mapping;
        }
    }
}
