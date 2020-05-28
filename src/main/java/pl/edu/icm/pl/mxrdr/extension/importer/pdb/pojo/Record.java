package pl.edu.icm.pl.mxrdr.extension.importer.pdb.pojo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Record {

    @JacksonXmlProperty(localName = "dimEntity.structureId")
    private String structureId;

    @JacksonXmlProperty(localName = "dimEntity.chainId")
    private String chainId;

    @JacksonXmlProperty(localName = "dimEntity.sequence")
    private String sequence;

    @JacksonXmlProperty(localName = "dimStructure.structureMolecularWeight")
    private String structureMolecularWeight;

    @JacksonXmlProperty(localName = "dimStructure.spaceGroup")
    private String spaceGroup;

    @JacksonXmlProperty(localName = "dimStructure.residueCount")
    private String residueCount;

    @JacksonXmlProperty(localName = "dimStructure.atomSiteCount")
    private String atomSiteCount;

    @JacksonXmlProperty(localName = "dimStructure.structureTitle")
    private String structureTitle;

    @JacksonXmlProperty(localName = "dimStructure.pdbDoi")
    private String pdbDoi;

    @JacksonXmlProperty(localName = "dimStructure.structureAuthor")
    private String structureAuthor;

    @JacksonXmlProperty(localName = "dimStructure.depositionDate")
    private String depositionDate;

    @JacksonXmlProperty(localName = "dimStructure.releaseDate")
    private String releaseDate;

    @JacksonXmlProperty(localName = "dimStructure.revisionDate")
    private String revisionDate;

    @JacksonXmlProperty(localName = "dimStructure.title")
    private String title;

    @JacksonXmlProperty(localName = "dimStructure.pubmedId")
    private String pubmedId;

    @JacksonXmlProperty(localName = "dimStructure.citationAuthor")
    private String citationAuthor;

    @JacksonXmlProperty(localName = "dimStructure.journalName")
    private String journalName;

    @JacksonXmlProperty(localName = "dimStructure.publicationYear")
    private String publicationYear;

    @JacksonXmlProperty(localName = "dimStructure.lengthOfUnitCellLatticeA")
    private String lengthOfUnitCellLatticeA;

    @JacksonXmlProperty(localName = "dimStructure.lengthOfUnitCellLatticeB")
    private String lengthOfUnitCellLatticeB;

    @JacksonXmlProperty(localName = "dimStructure.lengthOfUnitCellLatticeC")
    private String lengthOfUnitCellLatticeC;

    @JacksonXmlProperty(localName = "dimStructure.unitCellAngleAlpha")
    private String unitCellAngleAlpha;

    @JacksonXmlProperty(localName = "dimStructure.unitCellAngleBeta")
    private String unitCellAngleBeta;

    @JacksonXmlProperty(localName = "dimStructure.unitCellAngleGamma")
    private String unitCellAngleGamma;

    @JacksonXmlProperty(localName = "dimStructure.collectionTemperature")
    private String collectionTemperature;

    @JacksonXmlProperty(localName = "dimStructure.resolution")
    private String resolution;

    // -------------------- GETTERS --------------------

    public String getStructureId() {
        return structureId;
    }

    public String getChainId() {
        return chainId;
    }

    public String getSequence() {
        return sequence;
    }

    public String getStructureMolecularWeight() {
        return structureMolecularWeight;
    }

    public String getSpaceGroup() {
        return spaceGroup;
    }

    public String getResidueCount() {
        return residueCount;
    }

    public String getAtomSiteCount() {
        return atomSiteCount;
    }

    public String getStructureTitle() {
        return structureTitle;
    }

    public String getPdbDoi() {
        return pdbDoi;
    }

    public String getStructureAuthor() {
        return structureAuthor;
    }

    public String getDepositionDate() {
        return depositionDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRevisionDate() {
        return revisionDate;
    }

    public String getTitle() {
        return title;
    }

    public String getPubmedId() {
        return pubmedId;
    }

    public String getCitationAuthor() {
        return citationAuthor;
    }

    public String getJournalName() {
        return journalName;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public String getLengthOfUnitCellLatticeA() {
        return lengthOfUnitCellLatticeA;
    }

    public String getLengthOfUnitCellLatticeB() {
        return lengthOfUnitCellLatticeB;
    }

    public String getLengthOfUnitCellLatticeC() {
        return lengthOfUnitCellLatticeC;
    }

    public String getUnitCellAngleAlpha() {
        return unitCellAngleAlpha;
    }

    public String getUnitCellAngleBeta() {
        return unitCellAngleBeta;
    }

    public String getUnitCellAngleGamma() {
        return unitCellAngleGamma;
    }

    public String getCollectionTemperature() {
        return collectionTemperature;
    }

    public String getResolution() {
        return resolution;
    }

}
