package data.pipeline.api.util;

import com.google.gson.Gson;
import data.pipeline.api.error.FlowException;

import java.util.Objects;

import data.pipeline.api.error.WorkflowErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ravi on 8/17/17.
 */
public class JsonParser<T> {
    private final Logger logger = LoggerFactory.getLogger(JsonParser.class);
    public T parseJson(String json, Class<T> c) throws FlowException {
        Objects.requireNonNull(json);
        try {
            Gson gson = new Gson();
            return (T) gson.fromJson(json, c);
        } catch (Exception e) {
            logger.info(WorkflowErrorCode.JsonParserError.getMessage());
            throw FlowException.build(WorkflowErrorCode.JsonParserError, e).set("json", json).set("class", c);
        }
    }
}
