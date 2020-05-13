package edu.harvard.iq.dataverse.importer.metadata.mxrdr;


import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import edu.harvard.iq.dataverse.importer.metadata.ImporterData;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldKey;
import edu.harvard.iq.dataverse.importer.metadata.ImporterFieldType;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.importer.metadata.ResultField;
import io.vavr.control.Try;
import org.apache.commons.io.FilenameUtils;
import org.omnifaces.cdi.Eager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Eager
@ApplicationScoped
public class CbfImporter implements MetadataImporter {

    private static final Logger logger = Logger.getLogger(CbfImporter.class.getCanonicalName());

    private ImporterRegistry registry;

    // -------------------- CONSTRUCTORS --------------------

    @Deprecated
    public CbfImporter() {
    }

    @Inject
    public CbfImporter(ImporterRegistry registry) {
        this.registry = registry;
    }

    // -------------------- LOGIC --------------------

    @PostConstruct
    public void init() {
        registry.register(this);
    }

    @Override
    public String getMetadataBlockName() {
        return "macromolecularcrystallography";
    }

    @Override
    public ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle("CbfImporterBundle", locale);
    }

    @Override
    public ImporterData getImporterData() {
        return new ImporterData()
                .addDescription("cbf.modal.description")
                .addField(ImporterData.ImporterField.of(CbfImporterForm.CBF_FILE, ImporterFieldType.UPLOAD_TEMP_FILE,
                                                        true, getBundle(Locale.ENGLISH).getString("cbf.label"),
                                                        ""));
    }

    @Override
    public List<ResultField> fetchMetadata(Map<ImporterFieldKey, Object> map) {

        if (map.containsKey(CbfImporterForm.CBF_FILE)) {
            File cbfFile = (File) map.get(CbfImporterForm.CBF_FILE);

            List<String> cbfLines = Try.of(() -> readCbfWithoutBinaryData(cbfFile))
                    .getOrElseThrow(throwable -> new IllegalStateException("Cbf file could not be loaded", throwable));

            return extractMetadataFields(cbfLines);
        }

        return Collections.emptyList();
    }

    @Override
    public Map<ImporterFieldKey, String> validate(Map<ImporterFieldKey, Object> importerInput) {
        Map<ImporterFieldKey, String> errors = new HashMap<>();

        File cbfFile = (File) importerInput.get(CbfImporterForm.CBF_FILE);

        if (cbfFile != null && !FilenameUtils.getExtension(cbfFile.getName()).equals("cbf")) {
            errors.put(CbfImporterForm.CBF_FILE, getBundle(Locale.ENGLISH).getString("cbf.error.wrongFile"));
            return errors;
        }

        return errors;
    }

    // -------------------- PRIVATE --------------------

    /**
     * Reads headers from cbf file while skipping binary data which is very long in terms of text lines.
     */
    private List<String> readCbfWithoutBinaryData(File cbfFile) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(cbfFile.toPath(), Charset.forName("windows-1252"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("CIF-BINARY-FORMAT-SECTION")) {
                    break;
                }
                lines.add(line);
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "There was a problem with reading cbf file", e);
        }

        return lines;
    }

    private List<ResultField> extractMetadataFields(List<String> cbfLines) {
        List<MetadataField> metadataFilters = new CbfMetadataFields().getMetadataFilters();
        List<ResultField> extractedMetadata = new ArrayList<>();

        for (String line : cbfLines) {
            for (MetadataField metadataFilter : metadataFilters) {

                if (metadataFilter.getChildFields().isEmpty() && !metadataFilter.getFieldFilter().apply(line).isEmpty()) {
                    extractedMetadata.add(ResultField.of(metadataFilter.getName(),
                                                         metadataFilter.getFieldFilter().apply(line)));
                } else if (!metadataFilter.getChildFields().isEmpty()) {
                    extractChildField(extractedMetadata, line, metadataFilter);
                }
            }


        }

        return extractedMetadata;
    }

    private ResultField extractChildField(List<ResultField> extractedMetadata, String line, MetadataField metadataFilter) {
        List<ResultField> parsedChildren = new ArrayList<>();

        for (MetadataField childField : metadataFilter.getChildFields()) {

            if (!childField.getFieldFilter().apply(line).isEmpty()) {
                parsedChildren.add(ResultField.of(childField.getName(), childField.getFieldFilter().apply(line)));
            }
        }

        Optional<ResultField> parentField = extractedMetadata.stream()
                .filter(field -> field.getName().equals(metadataFilter.getName()))
                .findFirst();

        ResultField freshParentField = parentField
                .map(resultField -> copyFieldWithNewChildren(resultField,
                                                             extractedMetadata,
                                                             parsedChildren))
                .orElseGet(() -> ResultField.of(metadataFilter.getName(), parsedChildren.toArray(new ResultField[0])));

        extractedMetadata.add(freshParentField);
        return freshParentField;
    }

    private ResultField copyFieldWithNewChildren(ResultField resultField, List<ResultField> extractedMetadata, List<ResultField> freshChildren) {
        extractedMetadata.remove(resultField);
        return ResultField.of(resultField.getName(),
                              Lists.newArrayList(Iterables.concat(freshChildren,
                                                                  resultField.getChildren())).toArray(new ResultField[0]));
    }
}
