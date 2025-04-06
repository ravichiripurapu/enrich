package data.pipeline.customer;

import data.pipeline.executor.main.SparkDataPipeline;

public class CustomerSalesApplication {
    public static void main(String args[]) {
        try {
            SparkDataPipeline.startApplication(args);
        } catch (data.pipeline.api.error.FlowException e) {
            throw new RuntimeException(e);
        }
    }
}
