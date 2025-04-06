package data.pipeline.api.util;

import data.pipeline.api.error.FlowException;
import data.pipeline.api.error.WorkflowErrorCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ravi on 8/21/17.
 */
public class FileReader {
    private static final Logger logger = LoggerFactory.getLogger(FileReader.class);
    public static String buildStringFromFileLocation(String fileName) throws FlowException {
        // try with resource statement creates an auto closable reader as soon as
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(fileName))) {
            return reader.lines().collect(Collectors.joining(System.getProperty("line.separator")));
        } catch (IOException e) {
            logger.error(WorkflowErrorCode.UnableToReadFile.getMessage());
            throw FlowException.build(WorkflowErrorCode.UnableToReadFile, e).set("file", fileName);
        }

    }
}
