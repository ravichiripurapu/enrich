
package data.pipeline.api.collections;

import data.pipeline.api.components.AbstractComponent;
import data.pipeline.api.error.FlowException;
import data.pipeline.api.error.WorkflowErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * Nodes and connectors arranged for an running environment. 
 * All the nodes are present in a map and all connectors in a list
 * All nodes referres to all nodes in all levels of main flows and subflows
 * 
 * @author Ravi Chiripurapu
 * 
 *
 */

public class ExecutorModel {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorModel.class.getName());

    Map<String,AbstractComponent> executors=new HashMap<String,AbstractComponent>();
	List<GraphConnection> connectors= new ArrayList<GraphConnection>();

	
	/**
	 * Components of the workflow
	 */
	public Map<String, AbstractComponent> getExecutors() {
		return executors;
	}
	public List<GraphConnection> getConnectors() {
		return connectors;
	}


    /**
     * Make checkings to ensure the Graph is correct
     *
     *  No cycle
     *  There are not start nodes
     *  there are no finish nodes
     *  All inputs and output ports are connected
     *  There are not connections to orphan ports
     *  check there are no inputs that receives more than one connector
     *
     * @throws FlowException
     */
    public void validate() throws FlowException {

        int nodeElements = this.getExecutors().size();
        Map<String,Integer> nodes=new HashMap<String,Integer>();
        int i=0;
        for (Map.Entry<String,AbstractComponent> mapEntry:this.getExecutors().entrySet()) {
            nodes.put(mapEntry.getKey(),i);
            i++;
        }

        Set<Edge> edgeSet =new HashSet<Edge>();
        for (GraphConnection connector:this.getConnectors()) {
            Integer from=nodes.get(connector.getSourceId());
            Integer to=nodes.get(connector.getTargetId());
            if (from!=null && to!=null) {
                edgeSet.add(new Edge(from,to));
            }
        }

        DirectedGraph directedGraph=convert(nodeElements,edgeSet);
        if (checkCycle(directedGraph)) {
            logger.error(WorkflowErrorCode.GraphHasCycle.getMessage());
            throw FlowException.build(WorkflowErrorCode.GraphHasCycle).set("method","Model Validation");
        }

        if (!checkWeaklyConnected(nodeElements,new ArrayList<Edge> (edgeSet))) {
            logger.error(WorkflowErrorCode.UnconnectedNodes.getMessage());
            throw FlowException.build(WorkflowErrorCode.UnconnectedNodes);
        }
        if (!hasStartNode()) {
            logger.error(WorkflowErrorCode.NoStartNode.getMessage());
            throw FlowException.build(WorkflowErrorCode.NoStartNode).set("method","Model Validation");
        }
        if (!hasFinalNode()) {
            logger.error(WorkflowErrorCode.NoFinishNode.getMessage());
            throw FlowException.build(WorkflowErrorCode.NoFinishNode).set("method","Model Validation");
        }
        Set<PortIdentifier> unconnectedPorts=getUnconnectedPorts();
        if (unconnectedPorts.size()>0) {
            logger.error(WorkflowErrorCode.UnusedPorts.getMessage());
            throw FlowException.build(WorkflowErrorCode.UnusedPorts).set("ports",unconnectedPorts);
        }
        Set<PortIdentifier> multipleInputs=getMultipleInputs();
        if (multipleInputs.size()>0) {
            logger.error(WorkflowErrorCode.InputPortsManyConnectors.getMessage());
            throw FlowException.build(WorkflowErrorCode.InputPortsManyConnectors).set("ports",multipleInputs);
        }
        Set<PortIdentifier> orphanPorts=getOrphanPorts();
        if (orphanPorts.size()>0) {
            logger.error(WorkflowErrorCode.OrphanPort.getMessage());
            throw FlowException.build(WorkflowErrorCode.OrphanPort).set("ports",orphanPorts);
        }


    }


    /**
     *
     *
     * @return
     */
    private Set<PortIdentifier> getOrphanPorts() {
        boolean result=true;
        Set<PortIdentifier> orphanPorts=new HashSet<PortIdentifier>();
        PortIdentifier port;
        for (GraphConnection connector:this.getConnectors()) {
            AbstractComponent executor=this.getExecutors().get(connector.getSourceId());
            port = new PortIdentifier(connector.getSourceId(),connector.getSourceIndex(),"OUTPUT");
            if (executor==null) {
                orphanPorts.add(port);
            } else {
                if (connector.getSourceIndex()<0 || connector.getSourceIndex()>=executor.getTotalOutputPorts()) {
                    orphanPorts.add(port);
                }
            }
            port = new PortIdentifier(connector.getTargetId(),connector.getTargetIndex(),"INPUT");
            executor=this.getExecutors().get(connector.getTargetId());
            if (executor==null) {
                orphanPorts.add(port);
            } else {
                if (connector.getTargetIndex()<0 || connector.getTargetIndex()>=executor.getTotalInputPorts()) {
                    orphanPorts.add(port);
                }
            }
        }
        return orphanPorts;
    }


    /**
     *
     * Check if input ports do not receive more than one connector
     * @return
     */
    private Set<PortIdentifier> getMultipleInputs() {
        boolean result=true;
        // Generate a set containing all the ports
        Set<PortIdentifier> multipleInputs=new HashSet<PortIdentifier>();
        Set<PortIdentifier>  ports=new HashSet<PortIdentifier>();
        for (GraphConnection connector:this.getConnectors()) {
            PortIdentifier newPort=new PortIdentifier(connector.getTargetId(),connector.getTargetIndex(),"INPUT");
            if (!ports.contains(newPort)) {
                ports.add(newPort);
            } else {
                multipleInputs.add(newPort);
            };
        }
        return multipleInputs;

    }


    /**
     * Check if all ports are used
     * @return
     *
     * @author Ravi Chiripurapu
     */
    private Set<PortIdentifier> getUnconnectedPorts() {
        // Generate a set containing all the ports
        Set<PortIdentifier>  ports=new HashSet<PortIdentifier>();
        for (Map.Entry<String,AbstractComponent> nodeEntry:this.getExecutors().entrySet()) {
            for (int i=0;i<nodeEntry.getValue().getTotalInputPorts();i++) {
                ports.add(new PortIdentifier(nodeEntry.getValue().getNodeId(),i,"INPUT"));
            }
            for (int i=0;i<nodeEntry.getValue().getTotalOutputPorts();i++) {
                ports.add(new PortIdentifier(nodeEntry.getValue().getNodeId(),i,"OUTPUT"));
            }
        }
        // Remove each port contained in a connector
        for (GraphConnection connector:this.getConnectors()) {
            ports.remove(new PortIdentifier(connector.getSourceId(),connector.getSourceIndex(),"OUTPUT"));
            ports.remove(new PortIdentifier(connector.getTargetId(),connector.getTargetIndex(),"INPUT"));
        }
        return ports;
    }


    /**
     * Check graph has at least one node with no inputs
     * @return
     * @aouthor Ravi Chiripurapu
     */
    private boolean hasStartNode() {
        boolean result=false;
        for (Map.Entry<String,AbstractComponent> entry:this.getExecutors().entrySet()) {
            if (entry.getValue().getTotalInputPorts()==0) {
                result=true;
            }
        }
        return result;
    }

    /**
     * Check graph has at least one node with no outputs
     * @return
     * @aouthor Ravi Chiripurapu
     */
    private boolean hasFinalNode() {
        boolean result=false;
        for (Map.Entry<String,AbstractComponent> entry:this.getExecutors().entrySet()) {
            if (entry.getValue().getTotalOutputPorts()==0) {
                result=true;
            }
        }
        return result;
    }


    /**
     * Check if all nodes in a directed graph are connected
     * @param totalElements
     * @param edgeList
     * @return
     *
     * @author Ravi Chiripurapu
     */
    private boolean checkWeaklyConnected(int totalElements,List<Edge> edgeList) {
        boolean result=true;
        if (totalElements>1) {
            result=false;
            List<Integer> connectedElements=new ArrayList<Integer>();
            if (edgeList.size()>0) {
                Set<Edge> edgeSet=new HashSet<Edge>(edgeList);

                Edge edge=edgeList.get(0);

                // add connected nodes
                connectedElements.add(edge.getFrom());
                if (!connectedElements.contains(edge.getTo())) {
                    connectedElements.add(edge.getTo());
                }
                edgeSet.remove(edge);
                boolean continueScanning=true;
                while (connectedElements.size()<totalElements &&  continueScanning) {
                    continueScanning=false;
                    List<Edge> checkingEdgesList=new ArrayList<Edge>(edgeSet);
                    for (Edge thisEdge:checkingEdgesList) {
                        if (connectedElements.contains(thisEdge.getFrom())) {
                            if (!connectedElements.contains(thisEdge.getTo())) {
                                connectedElements.add(thisEdge.getTo());
                            }
                            edgeSet.remove(thisEdge);
                            continueScanning=true;
                        }
                        if (connectedElements.contains(thisEdge.getTo())) {
                            if (!connectedElements.contains(thisEdge.getFrom())) {
                                connectedElements.add(thisEdge.getFrom());
                            }
                            edgeSet.remove(thisEdge);
                            continueScanning=true;
                        }
                    }
                }
                if (connectedElements.size()==totalElements) {
                    result=true;
                    for (Integer value:connectedElements) {
                        if (value>=totalElements || value<0) {
                            result=false;
                        }
                    }
                }
            }
        }
        return result;
    }


    /**
     * Check if a directed graph contains a cycle
     * @param directedGraph
     * @return
     */
    private boolean checkCycle(DirectedGraph directedGraph) {
        DirectedCycle finder=new DirectedCycle(directedGraph);
        return finder.hasCycle();
    }


    /**
     *
     * Convert the ExecutorModel to a directed graph in order to make general checkings
     *
     * @param nodeElements
     * @param edgeSet
     * @retunn
     *
     * @author Ravi Chiripurapu
     */
    private DirectedGraph convert(int nodeElements,Set<Edge> edgeSet) {

        DirectedGraph directedGraph=new DirectedGraph(nodeElements, edgeSet.size(),new ArrayList<Edge>(edgeSet));

        return directedGraph;

    }




    public void addExecutor(AbstractComponent executorElement) throws FlowException {
        if (this.getExecutors().containsKey(executorElement.getNodeId())) {
            logger.error(WorkflowErrorCode.NodeAlreadyExists.getMessage());
            throw FlowException.build(WorkflowErrorCode.NodeAlreadyExists).set("nodeId",executorElement.getNodeId());
        } else {
            this.getExecutors().put(executorElement.getNodeId(),executorElement);
        }
    }


    /**
     * Add connector
     * @param graphConnection
     * @throws FlowException
     *
     * @author Ravi Chiripurapu
     */
    public void addConnector(GraphConnection graphConnection) throws FlowException {
        AbstractComponent sourceElement=this.getExecutors().get(graphConnection.getSourceId());
        if (sourceElement==null) {
            logger.error(WorkflowErrorCode.OrphanPort.getMessage());
            throw FlowException.build(WorkflowErrorCode.OrphanPort).set("nodeId",graphConnection.getSourceId());
        } else {
            if (graphConnection.getSourceIndex()>=0 && graphConnection.getSourceIndex()<sourceElement.getTotalOutputPorts()) {
                this.getConnectors().add(graphConnection);
            } else {
                logger.error(WorkflowErrorCode.OrphanPort.getMessage());
                throw FlowException.build(WorkflowErrorCode.OrphanPort).
                        set("nodeId",graphConnection.getSourceId()).set("index",graphConnection.getSourceIndex());
            }
        }
    }


    @Override
    public String toString() {
        return String.format("ExecutorModel [\n\texecutors=%s, \n\tconnectors=%s]", executors, connectors);
    }


}
