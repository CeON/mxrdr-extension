package pl.edu.icm.pl.mxrdr.extension.workflow;

import edu.harvard.iq.dataverse.dataset.datasetversion.DatasetVersionServiceBean;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.workflow.WorkflowContext;
import edu.harvard.iq.dataverse.workflow.step.Failure;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStep;
import edu.harvard.iq.dataverse.workflow.step.WorkflowStepResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

abstract class XdsWorkflowStep implements WorkflowStep {

    private static final Logger log = LoggerFactory.getLogger(XdsWorkflowStep.class);

    private final DatasetVersionServiceBean datasetVersions;

    protected DatasetVersion datasetVersion;

    // -------------------- CONSTRUCTORS --------------------

    public XdsWorkflowStep(DatasetVersionServiceBean datasetVersions) {
        this.datasetVersions = datasetVersions;
    }

    // -------------------- LOGIC --------------------

    @Override
    public final WorkflowStepResult run(WorkflowContext context) {
        try {
            datasetVersion = getDatasetVersion(context);
            Path workDir = createWorkDir();
            return runInternal(context, workDir);
        } catch (Exception e) {
            log.error("Failed XDS workflow step", e);
            return new Failure(e.getMessage());
        }
    }

    @Override
    public WorkflowStepResult resume(WorkflowContext context, Map<String, String> internalData, String externalData) {
        return WorkflowStepResult.OK;
    }

    @Override
    public void rollback(WorkflowContext context, Failure reason) {
    }

    protected abstract WorkflowStepResult runInternal(WorkflowContext context, Path workDir) throws Exception;


    protected static Path imagesDir(Path workDir) throws IOException {
        return Files.createDirectories(workDir.resolve("img"));
    }

    // -------------------- PRIVATE --------------------

    private DatasetVersion getDatasetVersion(WorkflowContext context) {
        return datasetVersions.findByVersionNumber(
                context.getDataset().getId(), context.getNextVersionNumber(), context.getNextMinorVersionNumber());
    }

    private Path createWorkDir() throws IOException {
        return Files.createDirectories(
                Paths.get(System.getProperty("java.io.tmpdir"),
                        "xds",
                        datasetVersion.getDataset().getId().toString(),
                        datasetVersion.getVersionNumber().toString(),
                        datasetVersion.getMinorVersionNumber().toString()));
    }
}
