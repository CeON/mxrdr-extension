package pl.edu.icm.pl.mxrdr.extension.xds.input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Files.newBufferedWriter;

/**
 * Allows to adjust values in XDS input file {@value XDS_INPUT_FILE_NAME} with given line processors.
 * Will pass each line of input file to each processor oen by one.
 */
public class XdsInputFileProcessor {

    public static final String XDS_INPUT_FILE_NAME = "XDS.INP";

    public static final Charset XDS_INPUT_FILE_CHARSET = Charset.forName("windows-1252");

    private final Path workDir;
    private final Charset charset;
    private final Clock clock;

    private final List<XdsInputLineProcessor> lineProcessors = new ArrayList<>();
    private final Map<String, String> newParameters = new HashMap<>();

    // -------------------- CONSTRUCTORS --------------------

    public XdsInputFileProcessor(Path workDir) {
        this(workDir, XDS_INPUT_FILE_CHARSET);
    }

    public XdsInputFileProcessor(Path workDir, Charset charset) {
        this(workDir, charset, Clock.systemUTC());
    }

    public XdsInputFileProcessor(Path workDir, Charset charset, Clock clock) {
        this.workDir = workDir;
        this.charset = charset;
        this.clock = clock;
    }

    // -------------------- LOGIC --------------------

    public XdsInputFileProcessor with(XdsInputLineProcessor action) {
        lineProcessors.add(action);
        return this;
    }

    public XdsInputFileProcessor withAll(List<XdsInputLineProcessor> actionList) {
        actionList.forEach(this::with);
        return this;
    }

    public XdsInputFileProcessor withNewParam(String paramName, String paramValue) {
        newParameters.put(paramName, paramValue);
        return this;
    }
    
    public void process() throws IOException {
        Path source = workDir.resolve(XDS_INPUT_FILE_NAME);
        Path copy = workDir.resolve(XDS_INPUT_FILE_NAME +  "." + clock.millis());
        Files.move(source, copy);
        try (BufferedReader reader = newBufferedReader(copy, charset);
             BufferedWriter writer = newBufferedWriter(source, charset)) {
            processLines(reader, writer);
            processNewParams(writer);
        }
    }

    // -------------------- PRIVATE --------------------

    private void processLines(BufferedReader reader, BufferedWriter writer) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            for (XdsInputLineProcessor processor : lineProcessors) {
                line = processor.process(line);
            }
            writer.write(line);
            writer.newLine();
        }
    }

    private void processNewParams(BufferedWriter writer) throws IOException {
        for (Entry<String, String> param: newParameters.entrySet()) {
            writer.write(param.getKey() + "=" + param.getValue());
            writer.newLine();
        }
    }
}
