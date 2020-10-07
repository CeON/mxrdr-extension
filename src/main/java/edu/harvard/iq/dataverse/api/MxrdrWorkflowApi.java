package edu.harvard.iq.dataverse.api;

import static edu.harvard.iq.dataverse.workflow.execution.WorkflowContext.TriggerType.PostPublishDataset;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import edu.harvard.iq.dataverse.engine.command.exception.NoDatasetFilesException;
import edu.harvard.iq.dataverse.persistence.dataset.Dataset;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetRepository;
import edu.harvard.iq.dataverse.persistence.dataset.DatasetVersion;
import edu.harvard.iq.dataverse.persistence.user.AuthenticatedUser;
import edu.harvard.iq.dataverse.persistence.workflow.Workflow;
import edu.harvard.iq.dataverse.persistence.workflow.WorkflowExecution;
import edu.harvard.iq.dataverse.workflow.WorkflowServiceBean;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowContext;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowContext.TriggerType;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionFacade;
import edu.harvard.iq.dataverse.workflow.execution.WorkflowExecutionService;

@Path("mxrdr")
public class MxrdrWorkflowApi extends AbstractApiBean {

    @Inject
    private WorkflowExecutionService workflowExecutionService;
    
    @Inject
    private WorkflowExecutionFacade workflowExecutionFacade;

    @Inject
    private WorkflowServiceBean workflowServiceBean;

    @Inject
    private DatasetRepository datasetRepository; 

    @POST
    @Path("workflow/rerun")
    public Response rerunWorkflow(@QueryParam("type") String type) {
        try {
            AuthenticatedUser user = findAuthenticatedUserOrDie();
            if (!user.isSuperuser()) {
                return error(Response.Status.FORBIDDEN, "Only superusers can run workflow");
            }
            if (type == null) {
                return error(Response.Status.BAD_REQUEST, "Missing processing type - 'type=failedOnly' or 'type=notPerformedOnly' is required");
            }

            int datasetProcessed = 0;
            for (Dataset dataset:datasetRepository.findAll()) {
                DatasetVersion released = dataset.getReleasedVersion();
                if (released != null) {
                    Optional<WorkflowExecution> result = workflowExecutionService.findLatestByTriggerTypeAndDatasetVersion(TriggerType.PostPublishDataset, 
                            dataset.getId(), released.getVersionNumber(), released.getMinorVersionNumber());
                    if (result.isPresent() && type.equals("failedOnly")) {
                        WorkflowExecution execution = result.get();
                        if (execution.isFinished() && !execution.getLastStep().getFinishedSuccessfully()) {
                            rerun(dataset, released);
                            datasetProcessed++;
                        }
                    } else if (!result.isPresent() && type.equals("notPerformedOnly")) {
                        rerun(dataset, released);
                        datasetProcessed++;
                    }
                }
            }
            
            return ok("Processed " + datasetProcessed + " datasets");
        } catch (WrappedResponse ex) {
            return ex.getResponse();
        } catch (NoDatasetFilesException ex) {
            return error(Response.Status.INTERNAL_SERVER_ERROR,
                         "Unable to publish dataset, since there are no files in it.");
        }

    }

    @POST
    @Path("{id}/workflow/rerun")
    public Response rerunWorkflow(@PathParam("id") String id, @QueryParam("version") String version) {
        try {
            AuthenticatedUser user = findAuthenticatedUserOrDie();
            if (!user.isSuperuser()) {
                return error(Response.Status.FORBIDDEN, "Only superusers can run workflow");
            }
            Dataset ds = findDatasetOrDie(id);
            DatasetVersion updateVersion = null;
            if (version != null) {
                for (DatasetVersion datasetVersion:ds.getVersions()) {
                    if (version.equals(datasetVersion.getVersionNumber() + "." + datasetVersion.getMinorVersionNumber())) {
                        updateVersion = datasetVersion;
                    }
                }
                if (updateVersion == null) {
                    return error(Response.Status.BAD_REQUEST, "Unknown version: " + version);
                } else {
                    rerun(ds, updateVersion);
                }
            } else {
                return error(Response.Status.BAD_REQUEST, "Missing version number");
            }
            
            return ok("Datasets processed");
        } catch (WrappedResponse ex) {
            return ex.getResponse();
        } catch (NoDatasetFilesException ex) {
            return error(Response.Status.INTERNAL_SERVER_ERROR,
                         "Unable to publish dataset, since there are no files in it.");
        }

    }
    
    private void rerun(Dataset dataset, DatasetVersion datasetVersion) throws WrappedResponse {
        Optional<Workflow> workflow = workflowServiceBean.getDefaultWorkflow(PostPublishDataset);
        if (workflow.isPresent()) {
            workflowExecutionFacade.start(
                    workflow.get(), new WorkflowContext(PostPublishDataset, dataset.getId(), datasetVersion.getMinorVersionNumber(), datasetVersion.getVersionNumber(), createDataverseRequest(findUserOrDie()), true));
        }
    }
}
