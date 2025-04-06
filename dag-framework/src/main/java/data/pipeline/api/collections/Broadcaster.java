package data.pipeline.api.collections;

import data.pipeline.api.components.AbstractComponent;
import data.pipeline.api.error.ApiErrorCode;
import data.pipeline.api.error.FlowException;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Ravi Chiripurapu on 1/1/19.
 */
public class Broadcaster {

    private static final Logger logger = LoggerFactory.getLogger(Broadcaster.class.getName());

    private ExecutorModel executionModel;

    public Broadcaster(ExecutorModel executionModel){
        this.executionModel = executionModel;
    }

    /**
     * Method used by excomponents to send messages to each others
     * in executeComponent of excomponents should call setOutput that internally add this method... sending the message to all connected target elements
     *
     *
     * @author Ravi Chiripurapu
     * @throws FlowException
     *
     */
    public void broadcast(String nodeId, int outputIndex, Object value) throws FlowException {
    	if (outputIndex<0) {
            logger.error(ApiErrorCode.UnexistentPortId.getMessage());
            throw FlowException.build(ApiErrorCode.UnexistentPortId).set("outputIndex",outputIndex);
    	}
    	if (nodeId==null) {
            logger.error(ApiErrorCode.UnexistentNodeId.getMessage());
            throw FlowException.build(ApiErrorCode.UnexistentNodeId).set("nodeId",nodeId);
    	}
        List<GraphConnection> connectors=this.executionModel.getConnectors();
        AbstractComponent executor=this.executionModel.getExecutors().get(nodeId);
        if (executor==null) {
            logger.error(ApiErrorCode.UnexistentNodeId.getMessage());
            throw FlowException.build(ApiErrorCode.UnexistentNodeId).set("nodeId",nodeId);
        } else {
            if (outputIndex>=0 && outputIndex<executor.getTotalOutputPorts()) {
                for (GraphConnection thisConnector:connectors) {
                    if (thisConnector.getSourceId().equals(executor.getNodeId()) && thisConnector.getSourceIndex()==outputIndex) {
                        AbstractComponent target=this.executionModel.getExecutors().get(thisConnector.getTargetId());
                        int targetIndex=thisConnector.getTargetIndex();
                        target.setInputValue(targetIndex,value);;
                    }
                }
            }
        }
    }

    
    /**
     * Register this broadcaster as the listener of all setOutput in the model
     */
    public void register() {
        // makes all executors begin to listen to messages
        for (Map.Entry<String, AbstractComponent> executorModelEntry : this.executionModel.getExecutors().entrySet()) {
            executorModelEntry.getValue().setBroadcaster(this);
        }
    }

}
