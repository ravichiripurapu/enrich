package data.pipeline.api.error;

/**
 * Created by ravi on 8/20/17.
 */
public enum TestErrorCode implements ErrorCode {
    TestError(32655, "Test Error Code");

    private final int code;
    private final String message;

    TestErrorCode(int code, String message) {
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
