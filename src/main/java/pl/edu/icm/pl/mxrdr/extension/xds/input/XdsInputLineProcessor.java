package pl.edu.icm.pl.mxrdr.extension.xds.input;

import java.io.IOException;

/**
 * Defines XDS input file {@value XdsInputFileProcessor#XDS_INPUT_FILE_NAME}
 * line processor interface allowing to change it's contents.
 */
@FunctionalInterface
public interface XdsInputLineProcessor {

    /**
     * Perform necessary changes to the line of XDS input file, if needed.
     * @param line line to process.
     * @return result of given line processing.
     * @throws IOException in case of I/O issues.
     */
    String process(String line) throws IOException;
}
