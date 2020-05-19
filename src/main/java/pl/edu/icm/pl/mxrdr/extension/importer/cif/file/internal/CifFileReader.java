package pl.edu.icm.pl.mxrdr.extension.importer.cif.file.internal;

import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import org.rcsb.cif.CifIO;
import org.rcsb.cif.model.CifFile;
import org.rcsb.cif.schema.StandardSchemata;
import org.rcsb.cif.schema.mm.MmCifFile;
import pl.edu.icm.pl.mxrdr.extension.importer.cif.file.CifFileFormKeys;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CifFileReader {
    private static final Logger logger = Logger.getLogger(CifFileReader.class.getSimpleName());

    private static final String CIF_URL_TEMPLATE = "https://files.rcsb.org/header/%s.cif";

    // -------------------- LOGIC --------------------

    public static Optional<MmCifFile> readAndParseCifFile(Map<ImporterFieldKey, Object> importerInput) {
        String pdbId = (String) importerInput.get(CifFileFormKeys.PDB_ID);
        File cifFile = (File) importerInput.get(CifFileFormKeys.CIF_FILE);
        CifFile read = cifFile != null
                ? readFromFile(cifFile)
                : readFromWeb(String.format(CIF_URL_TEMPLATE, pdbId));
        return Optional.ofNullable(read)
                .map(f -> f.as(StandardSchemata.MMCIF));
    }

    public static CifFile readFromFile(File cifFile) {
        try {
            return CifIO.readFromPath(cifFile.toPath());
        } catch (IOException ex) {
            logger.log(Level.WARNING, ex, () -> "Exception while reading .cif file");
            throw new IllegalStateException(ex);
        }
    }

    public static CifFile readFromWeb(String uri) {
        try {
            return CifIO.readFromURL(new URI(uri).toURL());
        } catch (IOException | URISyntaxException ex) {
            logger.log(Level.WARNING, ex, () -> "Exception during accessing .cif file on web");
            throw new IllegalStateException(ex);
        }
    }
}
