
package data.pipeline.api.util;

import data.pipeline.api.error.FlowException;
import data.pipeline.api.model.environment.EnvironmentProperties;
import data.pipeline.api.model.repo.ComponentRepository;
import data.pipeline.api.model.flow.Node;
import data.pipeline.api.model.flow.Pipeline;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import data.pipeline.api.components.AbstractComponent;
import data.pipeline.api.error.WorkflowErrorCode;
import data.pipeline.api.model.Application;
import data.pipeline.api.model.job.Job;
import data.pipeline.api.model.flow.Connection;
import data.pipeline.api.collections.ExecutorModel;
import data.pipeline.api.collections.GraphConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * 
 * Job Related Operations
 * 
 * @author Jose Alberto Guastavino
 *
 */

public class GraphAPIService {

    private static final Logger logger = LoggerFactory.getLogger(GraphAPIService.class.getName());




    private static String getComponentName(String id,String name,String version) {
        return id+"-"+name+"-"+version;
    }

    /**
     * Generates ExecutorModel based on a previous Pipeline model
     * Also check valiudation of ExecutionModel
     * @param jsonWorkflow
     * @param componentRepository
     * @return
     * @throws FlowException
     */
    public static ExecutorModel convert(String workflowFileName, Pipeline jsonWorkflow, ComponentRepository componentRepository, // arg 1: Job
                                        Job job, Application application, EnvironmentProperties environmentProperties) throws FlowException {
        // Map to store and search nodes
        Map<String,AbstractComponent> componentMap=new HashMap<String,AbstractComponent>();
        if (jsonWorkflow.getNodes().size()==0) {
            logger.error(WorkflowErrorCode.ZeroNodesGraph.getMessage());
            throw FlowException.build(WorkflowErrorCode.ZeroNodesGraph).set("graphName", workflowFileName).set("class", GraphAPIService.class);
        }
        ExecutorModel executorModel =new ExecutorModel();

        // generate nodes
        for (Node node:jsonWorkflow.getNodes()) {
            String implementationName=componentRepository.getComponents().get(
                    getComponentName(node.getComponentInfo().getId(),
                            node.getComponentInfo().getName(),
                            node.getComponentInfo().getVersion()));
            if (implementationName!=null) {
                AbstractComponent abstractMainExecutor=
                        AbstractComponent.create(
                                implementationName,node.getId(),node.getEnvironmentKey(),node.getConf());
                abstractMainExecutor.setApplication(application);
                abstractMainExecutor.setEnvironmentProperties(environmentProperties);
                abstractMainExecutor.setJob(job);
                executorModel.addExecutor(abstractMainExecutor);
                componentMap.put(node.getId(),abstractMainExecutor);
            } else {
                logger.error(WorkflowErrorCode.NoImplementationName.getMessage());
                throw FlowException.build(WorkflowErrorCode.NoImplementationName).
                        set("graphName", workflowFileName).
                        set("nodeId",node.getComponentInfo().getId()).
                        set("class", GraphAPIService.class);
            }

        }
        long idConnector=0;

        // generate connections
        for (Connection connection:jsonWorkflow.getConnections()) {
            String sourceId=connection.getFrom().getNodeId();
            String targetId=connection.getTo().getNodeId();
            if (sourceId!=null && targetId!=null) {
                AbstractComponent sourceNode=componentMap.get(sourceId);
                AbstractComponent targetNode=componentMap.get(targetId);
                if (sourceNode!=null && targetNode!=null) {
                    GraphConnection graphConnection=
                            new GraphConnection(
                                    idConnector,
                                    sourceNode,connection.getFrom().getPortIndex(),
                                    targetNode,connection.getTo().getPortIndex());
                    executorModel.addConnector(graphConnection);
                }
            } else {
                logger.error(WorkflowErrorCode.WrongIdNodeOnConnector.getMessage());
                throw FlowException.build(WorkflowErrorCode.WrongIdNodeOnConnector).
                        set("graphName", workflowFileName).
                        set("sourceId",connection.getFrom().getNodeId()).
                        set("targetId",connection.getTo().getNodeId()).
                        set("class", GraphAPIService.class);
            }
            idConnector++;
        }
        System.out.println(executorModel.toString());
        return executorModel;
    }

    /**
     * Generate plain Pipeline object from workflow string
     * @param jsonWorkflowString
     * @return
     */
    private Pipeline convert(String jsonWorkflowString) throws FlowException {
        Gson gson = new Gson();
        Pipeline result=null;
        try {
            result=gson.fromJson(jsonWorkflowString, Pipeline.class);
        } catch (JsonSyntaxException e) {
            logger.error(WorkflowErrorCode.JsonParserError.getMessage());
            throw FlowException.build(WorkflowErrorCode.JsonParserError, e).set("fileName", jsonWorkflowString).set("class", GraphAPIService.class);

        }
        return result;

    }



    /**
     * Read a workflow from file and returns the workflow in a String
     * @param fileName
     * @return
     * @
     */
    private String readJson(String fileName) throws FlowException {


        java.net.URL resource=getClass().getClassLoader().getResource(fileName);
        String jsonString= null;
        try {
            jsonString = new String(Files.readAllBytes(Paths.get(resource.toURI())));
        } catch (IOException e) {
            logger.error(WorkflowErrorCode.IOException.getMessage());
            throw FlowException.build(WorkflowErrorCode.IOException, e).set("fileName", fileName);
        } catch (URISyntaxException e) {
            logger.error(WorkflowErrorCode.URISyntaxError.getMessage());
            throw FlowException.build(WorkflowErrorCode.URISyntaxError, e).set("fileName", fileName);
        }
        logger.debug(jsonString);
        return jsonString;
    }
    
}
