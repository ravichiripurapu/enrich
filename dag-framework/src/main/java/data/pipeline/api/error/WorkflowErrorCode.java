package data.pipeline.api.error;

/**
 * @author Ravi Chiripurapu
 */
public enum WorkflowErrorCode implements ErrorCode {

    NoSuchVertex(101,"No Such Vertex found"),
    ClassNotInstantiable(102, "Class cannot be instantiated" ),
    ClassNotFound(102, "Class not found"),
    JsonParserError(103, "Json parse error"),
    InsufficientCommandOptions(104, "Failed to parse command line options"),
    FileNotFound(105, "File not found"),
    UnableToReadFile(106, "Unable to read file"),
    IOException(107,"Error accessing filesystem"),
    URISyntaxError(108,"URI Syntax Error"),
    IllegalAccess(109,"Cannot access the class"),
    ZeroNodesGraph(110,"Graph contains 0 nodes"),
    NoImplementationName(111,"Node does not have implementation name"),
    WrongIdNodeOnConnector(112,"Wrong node Id on connector"),
    GraphHasCycle(113,"Graph contains at least one cycle"),
    UnconnectedNodes(114,"There are unconnected nodes in the workflow"),
    NoStartNode(115,"Pipeline does not have start node (with 0 inputs)"),
    NoFinishNode(116,"Pipeline does not have finish node (with 0 outputs)"),
    UnusedPorts(117,"There are at least one not used port"),
    InputPortsManyConnectors(118,"There are input ports receiving more than one connector"),
    OrphanPort(119,"There are connectors pointing to orphan ports"),
    NodeAlreadyExists(120,"Executor Node already exists");

    private final int code;
    private final String message;

    WorkflowErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
