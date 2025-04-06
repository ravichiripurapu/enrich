package data.pipeline.api.util;

import data.pipeline.api.error.FlowException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ravi on 8/26/17.
 */
public class FileReaderTest {

    @Test
    public void testBuildStringFromFileLocation() throws FlowException {
        String jobJsonFile = System.getProperty("user.dir")+"/src/test/resources/job.json";
        String jobJson = FileReader.buildStringFromFileLocation(jobJsonFile);
        Assert.assertNotNull(jobJson);
    }
}
