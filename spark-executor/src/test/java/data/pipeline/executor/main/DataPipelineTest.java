package data.pipeline.executor.main;

import data.pipeline.api.error.FlowException;
import data.pipeline.executor.cli.CommandLineOptions;
import data.pipeline.api.util.FileReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DataPipelineTest {

    private String appYml = null;
    private String jobJson = null;
    private String repoYml = null;
    private String fileDirectory = null;
    private String pipeline = null;

    @Before
    public void setup() throws FlowException {
        fileDirectory = System.getProperty("user.dir")+ "/src/test/resources/";
        this.appYml = fileDirectory + "application.yml";
        this.jobJson = fileDirectory + "job.json";
        this.pipeline = fileDirectory + "pipeline.json";
        this.repoYml = fileDirectory + "repo.yml";
    }

    @Test
    public void mainTest() throws FlowException {
        String args[] = {
                String.format("-%s", CommandLineOptions.APP_YML), appYml,
                String.format("-%s", CommandLineOptions.JOB_JSON), jobJson,
                String.format("-%s", CommandLineOptions.PIPELINE_JSON), pipeline,
                String.format("-%s", CommandLineOptions.REPO_YML), repoYml
        };
        DataPipeline.startApplication(args);
    }



}
