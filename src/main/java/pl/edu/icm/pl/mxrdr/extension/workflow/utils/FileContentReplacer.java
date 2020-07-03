package pl.edu.icm.pl.mxrdr.extension.workflow.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileContentReplacer {
    private final String fileName;
    private final Path workDir;
    private final Set<PatternAndReplacement> actions = new HashSet<>();

    // -------------------- CONSTRUCTORS --------------------

    private FileContentReplacer(String fileName, Path workDir) {
        this.fileName = fileName;
        this.workDir = workDir;
    }

    // -------------------- LOGIC --------------------

    public static FileContentReplacer of(String fileName, Path workDir) {
        return new FileContentReplacer(fileName, workDir);
    }

    public FileContentReplacer add(PatternAndReplacement action) {
        if (action != PatternAndReplacement.NONE) {
            actions.add(action);
        }
        return this;
    }

    public FileContentReplacer add(List<PatternAndReplacement> actionList) {
        actionList.forEach(this::add);
        return this;
    }

    public void replace() {
        File file = workDir.resolve(fileName).toFile();
        File modifiedFile = workDir.resolve(fileName + RandomStringUtils.randomAlphanumeric(8)).toFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(modifiedFile))) {
            readReplaceAndWrite(reader, writer);
            file.delete();
            modifiedFile.renameTo(workDir.resolve(fileName).toFile());
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    // -------------------- PRIVATE --------------------

    private void readReplaceAndWrite(BufferedReader reader, BufferedWriter writer) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            for (PatternAndReplacement action : actions) {
                line = line.replaceAll(action.pattern, action.replacement);
            }
            writer.write(line);
            writer.newLine();
        }
    }

    // -------------------- INNER CLASSES --------------------

    public static class PatternAndReplacement {
        public static final PatternAndReplacement NONE = new PatternAndReplacement(StringUtils.EMPTY, StringUtils.EMPTY);
        public final String pattern;
        public final String replacement;

        // -------------------- CONSTRUCTORS --------------------

        private PatternAndReplacement(String pattern, String replacement) {
            this.pattern = pattern;
            this.replacement = replacement;
        }

        // -------------------- LOGIC --------------------

        public static PatternAndReplacement of(String pattern, String replacement) {
            return new PatternAndReplacement(pattern, replacement);
        }
    }
}
