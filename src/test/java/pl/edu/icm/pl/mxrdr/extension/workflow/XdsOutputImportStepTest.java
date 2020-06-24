package pl.edu.icm.pl.mxrdr.extension.workflow;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.harvard.iq.dataverse.DatasetFieldServiceBean;
import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.importer.metadata.ImporterRegistry;
import edu.harvard.iq.dataverse.importer.metadata.MetadataImporter;
import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetFieldType;
import edu.harvard.iq.dataverse.persistence.dataset.FieldType;
import edu.harvard.iq.dataverse.workflow.WorkflowExecutionContext;
import pl.edu.icm.pl.mxrdr.extension.importer.xds.XdsFileParser;
import pl.edu.icm.pl.mxrdr.extension.importer.xds.XdsImporter;

class XdsOutputImportStepTest {
    private Path workDir;

    DatasetVersionServiceBean datasetVersions = mock(DatasetVersionServiceBean.class);
    DatasetFieldServiceBean datasetFieldService = mock(DatasetFieldServiceBean.class);
    ImporterRegistry importerRegistry = mock(ImporterRegistry.class);
    Map<String, String> input = new HashMap<>();
    XdsOutputImportStep step = new XdsOutputImportStep(input, datasetFieldService, datasetVersions, importerRegistry);
    WorkflowExecutionContext context = mock(WorkflowExecutionContext.class);
    Dataset dataset = new Dataset();
    
    @BeforeEach
    public void setUp() throws Exception {
        workDir = Files.createTempDirectory("xds-test-import");
        File xdsFile = new File(getClass().getClassLoader().getResource("xds/CORRECT.LP").toURI());
        Files.copy(Paths.get(xdsFile.getPath()), workDir.resolve(XdsAdjustResultStep.CORRECT_LP));
        when(context.getDataset()).thenReturn(dataset);
        XdsImporter xdsImporter = new XdsImporter(null,  new XdsFileParser());
        Map<String, MetadataImporter> importers = new HashMap<String, MetadataImporter>();
        importers.put("xds", xdsImporter);
        when(importerRegistry.getImporters()).thenReturn(importers);
        when(datasetFieldService.findByName(any(String.class))).thenAnswer(i -> {
            String name = i.getArgument(0);
            return new DatasetFieldType(name, FieldType.TEXT, false);
        });
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.list(workDir).forEach(f -> f.toFile().delete());
        workDir.toFile().delete();
    }

    @Test
    public void shouldImportFile() throws Exception {
        step.runInternal(context, workDir);
        
        assertFalse(dataset.getEditVersion().getDatasetFields().isEmpty());
        assertTrue(dataset.getEditVersion().getDatasetFields().size() == 41);

        assertTrue(containsNameAndValue("unitCellParameterA", "78.45", dataset));
        assertTrue(containsNameAndValue("unitCellParameterB", "91.20", dataset));
        assertTrue(containsNameAndValue("unitCellParameterC", "116.82", dataset));
        assertTrue(containsNameAndValue("unitCellParameterAlpha", "90.000", dataset));
        assertTrue(containsNameAndValue("unitCellParameterBeta", "90.000", dataset));
        assertTrue(containsNameAndValue("unitCellParameterGamma", "90.000", dataset));

        assertTrue(containsNameAndValue("dataCollectionOscillationStepSize", "0.200000", dataset));
        assertTrue(containsNameAndValue("dataCollectionStartingAngle", "1.010", dataset));
        assertTrue(containsNameAndValue("dataCollectionOrgX", "1221.81", dataset));
        assertTrue(containsNameAndValue("dataCollectionOrgY", "1257.43", dataset));
        assertTrue(containsNameAndValue("dataCollectionDetectorDistance", "216.250", dataset));
        assertTrue(containsNameAndValue("dataCollectionWavelength", "1.278650", dataset));
        assertTrue(containsNameAndValue("dataCollectionDetectorOverload", "1048500", dataset));
        assertTrue(containsNameAndValue("dataCollectionDetectorThickness", "0.450000", dataset));
        assertTrue(containsNameAndValue("dataCollectionStartingAngle", "1.010", dataset));

        assertTrue(containsNameAndValue("overallCompleteness", "99.5", dataset));
        assertTrue(containsNameAndValue("overallISigma", "9.09", dataset));
        assertTrue(containsNameAndValue("overallCc", "99.7", dataset));
        assertTrue(containsNameAndValue("overallRMerge", "14.8", dataset));
        assertTrue(containsNameAndValue("overallRMeas", "16.0", dataset));
        assertTrue(containsNameAndValue("overallDataResolutionRangeLow", "49.186", dataset));
        assertTrue(containsNameAndValue("overallDataResolutionRangeHigh", "2.11", dataset));
        assertTrue(containsNameAndValue("overallNumberOfObservedReflections", "632938", dataset));
        assertTrue(containsNameAndValue("overallNumberOfUniqueReflections", "93289", dataset));
        assertTrue(containsNameAndValue("overallNumberOfNumberOfPossibleReflections", "93791", dataset));
        assertTrue(containsNameAndValue("overallAnomalousCorrelation", "5", dataset));
        assertTrue(containsNameAndValue("overallAnomalousSignal", "0.755", dataset));

        assertTrue(containsNameAndValue("hrsCompleteness", "97.1", dataset));
        assertTrue(containsNameAndValue("hrsSigma", "1.84", dataset));
        assertTrue(containsNameAndValue("hrsCc", "80.2", dataset));
        assertTrue(containsNameAndValue("hrsRMerge", "97.2", dataset));
        assertTrue(containsNameAndValue("hrsRMeas", "106.1", dataset));
        assertTrue(containsNameAndValue("hrsDataResolutionRangeLow", "2.23", dataset));
        assertTrue(containsNameAndValue("hrsDataResolutionRangeHigh", "2.11", dataset));
        assertTrue(containsNameAndValue("hrsNumberOfObservedReflections", "92851", dataset));
        assertTrue(containsNameAndValue("hrsNumberOfUniqueReflections", "14747", dataset));
        assertTrue(containsNameAndValue("hrsNumberOfNumberOfPossibleReflections", "15187", dataset));
        assertTrue(containsNameAndValue("hrsAnomalousCorrelation", "-3", dataset));
        assertTrue(containsNameAndValue("hrsAnomalousSignal", "0.639", dataset));

    }
    
    private boolean containsNameAndValue(String name, String value, Dataset dataset) {
        return dataset.getEditVersion().getDatasetFields().stream()
                .anyMatch(field -> field.getDatasetFieldType().getName().equals(name) 
                        && field.getValue().equals(value)
                        && field.getSource().contentEquals("WORKFLOW"));
    }


}