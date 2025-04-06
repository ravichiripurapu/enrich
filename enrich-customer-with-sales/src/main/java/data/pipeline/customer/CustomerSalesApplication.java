package data.pipeline.customer;

import data.pipeline.api.error.FlowException;
import data.pipeline.executor.cli.CommandLineOptions;
import data.pipeline.executor.main.SparkDataPipeline;

public class CustomerSalesApplication {
    public static void main(String args[]) {

        String fileDirectory = System.getProperty("user.dir")+ "/enrich-customer-with-sales/src/main/resources/";
        String appYml = fileDirectory + "application.yml";
        String jobJson = fileDirectory + "job.json";
        String pipeline = fileDirectory + "pipeline.json";
        String repoYml = fileDirectory + "repo.yml";

        String args1[] = {
                String.format("-%s", CommandLineOptions.APP_YML), appYml,
                String.format("-%s", CommandLineOptions.JOB_JSON), jobJson,
                String.format("-%s", CommandLineOptions.PIPELINE_JSON), pipeline,
                String.format("-%s", CommandLineOptions.REPO_YML), repoYml
        };

        try {
            SparkDataPipeline.startApplication(args1);
        } catch (FlowException fe) {

        }
    }
}
