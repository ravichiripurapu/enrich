package data.pipeline.api.util;


import data.pipeline.api.error.ApiErrorCode;
import data.pipeline.api.error.FlowException;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
/**
 * Created by ravi on 8/17/17.
 */
public class YamlParser<T> {
    private static final Logger logger = LoggerFactory.getLogger(YamlParser.class.getName());
    public T readYaml(String fileName, Class<T> c) throws FlowException
    {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            T t = mapper.readValue(new File(fileName), c);
            logger.info(ReflectionToStringBuilder.toString(t,ToStringStyle.MULTI_LINE_STYLE));
            return t;
        } catch (Exception e) {
            logger.error(ApiErrorCode.UnableToReadFile.getMessage());
            throw FlowException.build(ApiErrorCode.UnableToReadFile, e).set("fileName", fileName).set("class", c);
        }

    }
}
