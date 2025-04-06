package data.pipeline.executor.main;

import data.pipeline.api.error.FlowException;
import data.pipeline.api.util.FileReader;
import data.pipeline.executor.cli.CommandLineOptions;
import org.junit.Before;
import org.junit.Test;


public class SparkDataPipelineTest {

    private String appYml = null;
    private String jobJson = null;
    private String repoYml = null;

    private String pipeline = null;
    private String fileDirectory = null;

    @Before
    public void setup() throws FlowException {
        fileDirectory = System.getProperty("user.dir")+ "/src/test/resources/";
        this.appYml = fileDirectory + "application.yml";
        this.jobJson = fileDirectory + "job.json";
        this.pipeline = fileDirectory + "spark_pipeline.json";
        this.repoYml = fileDirectory + "repo1.yml";
    }

    @Test
    public void mainTest() throws FlowException {
        String args[] = {
                String.format("-%s", CommandLineOptions.APP_YML), appYml,
                String.format("-%s", CommandLineOptions.JOB_JSON), jobJson,
                String.format("-%s", CommandLineOptions.PIPELINE_JSON), pipeline,
                String.format("-%s", CommandLineOptions.REPO_YML), repoYml
        };
        SparkDataPipeline.startApplication(args);
    }



}
