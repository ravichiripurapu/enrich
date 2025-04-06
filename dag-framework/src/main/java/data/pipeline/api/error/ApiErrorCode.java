package data.pipeline.api.error;

/**
 * @author Ravi Chiripurapu

 */
public enum ApiErrorCode implements ErrorCode {

    ClassNotInstantiable(101, "Class cannot be instantiated" ),
    ClassNotFound(202, "Class not found"),
    JsonParserError(203, "Json parse error"),
    FileNotFound(203, "File not found"),
    UnableToReadFile(204, "Unable to read file"),
    IOException(205,"Error accessing filesystem"),
    URISyntaxError(206,"URI Syntax Error"),
    IllegalAccess(207,"Cannot access the class"),
	UnexistentNodeId(208,"NodeId does not exists"),
	UnexistentPortId(209,"Port Index does not exists");
	

    private final int code;
    private final String message;

    ApiErrorCode(int code, String message) {
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
