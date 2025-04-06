package data.pipeline.api.util;

import data.pipeline.api.error.FlowException;
import data.pipeline.api.error.WorkflowErrorCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ravi on 8/17/17.
 */
public class ClassBuilder<T> {
    private static final Logger logger = LoggerFactory.getLogger(ClassBuilder.class);

    public T buildInstance(String className) throws FlowException {
        try {
            return (T) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            logger.error(WorkflowErrorCode.ClassNotInstantiable.getMessage());
            throw FlowException.build(WorkflowErrorCode.ClassNotInstantiable, e).set("class", className);
        } catch (IllegalAccessException e) {
            logger.error(WorkflowErrorCode.ClassNotFound.getMessage());
            throw FlowException.build(WorkflowErrorCode.ClassNotFound, e).set("class", className);
        } catch (ClassNotFoundException e) {
            logger.error(WorkflowErrorCode.ClassNotFound.getMessage());
            throw FlowException.build(WorkflowErrorCode.ClassNotFound, e).set("class", className);
        }

    }

    public static Class buildClass(String className) throws FlowException {
        Class c = null;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error(WorkflowErrorCode.ClassNotFound.getMessage());
            throw FlowException.build(WorkflowErrorCode.ClassNotFound, e).set("class", className);
        }
        return c;
    }
}
