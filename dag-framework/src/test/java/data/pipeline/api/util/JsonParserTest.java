package data.pipeline.api.util;

import data.pipeline.api.error.FlowException;
import data.pipeline.api.model.flow.Pipeline;
import data.pipeline.api.model.job.JobFile;
import data.pipeline.api.model.job.Job;
import data.pipeline.api.model.flow.Node;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ravi on 8/20/17.
 */
public class JsonParserTest {

    @Test
    public void readJobJsonTest() throws FlowException {
        String jobJsonFile = System.getProperty("user.dir")+"/src/test/resources/job.json";
        String jobJson = FileReader.buildStringFromFileLocation(jobJsonFile);
        JsonParser<Job> jsonParser = new JsonParser();
        Job job = jsonParser.parseJson(jobJson, Job.class);
        JobFile jobFile = job.getInstances().get(0);
        Assert.assertEquals("07987087987-98779878-asdfa-asdfasdf", job.getJobUuid());
        Assert.assertEquals("08151976",job.getOutputInstance());
        Assert.assertEquals("employee",jobFile.getEntity());
        Assert.assertEquals("file:../employees.txt",jobFile.getUrl());
        Assert.assertEquals(90890898,jobFile.getFileSize());
        Assert.assertEquals("08151976",jobFile.getInstanceId());
    }

    @Test
    public void readWorkflowJsonTest() throws FlowException {
        String workflowJsonFile = System.getProperty("user.dir") + "/src/test/resources/workflow.json";
        String workflowJson = FileReader.buildStringFromFileLocation(workflowJsonFile);
        JsonParser<Pipeline> jsonParser = new JsonParser();
        Pipeline workflow = jsonParser.parseJson(workflowJson, Pipeline.class);
        Node firstNode = workflow.getNodes().get(0);
        Assert.assertEquals("bbf34cbb-9ee6-442f-80a8-22886b860933", workflow.getId());
        Assert.assertNotNull(firstNode);
        Assert.assertEquals("abc-def",firstNode.getEnvironmentKey());
        Assert.assertEquals("742743c3",firstNode.getId());
    }
}
