package pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal;

import org.rcsb.cif.CifIO;
import org.rcsb.cif.model.CifFile;
import org.rcsb.cif.schema.StandardSchemata;
import org.rcsb.cif.schema.mm.MmCifFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CifFileReader {
    private static final Logger logger = LoggerFactory.getLogger(CifFileReader.class);

    private static final String CIF_URL_TEMPLATE = "https://files.rcsb.org/header/%s.cif";

    // -------------------- LOGIC --------------------

    public static MmCifFile readAndParseCifFile(File cifFile, String pdbId) {
        CifFile read = cifFile != null
                ? readFromFile(cifFile)
                : readFromWeb(String.format(CIF_URL_TEMPLATE, pdbId));
        return read.as(StandardSchemata.MMCIF);
    }

    public static CifFile readFromFile(File cifFile) {
        try {
            return CifIO.readFromPath(cifFile.toPath());
        } catch (IOException ex) {
            logger.warn("Exception while reading .cif file", ex);
            throw new IllegalStateException(ex);
        }
    }

    public static CifFile readFromWeb(String uri) {
        try {
            return CifIO.readFromURL(new URI(uri).toURL());
        } catch (IOException | URISyntaxException ex) {
            logger.warn("Exception during accessing .cif file on web", ex);
            throw new IllegalStateException(ex);
        }
    }
}
