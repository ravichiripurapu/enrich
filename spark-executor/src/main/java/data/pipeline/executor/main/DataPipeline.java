package data.pipeline.executor.main;

import data.pipeline.api.error.FlowException;
import data.pipeline.api.components.AbstractComponent;
import data.pipeline.api.model.flow.Pipeline;
import data.pipeline.executor.cli.CommandLineOptions;
import data.pipeline.api.collections.ExecutorModel;
import data.pipeline.api.collections.Broadcaster;
import data.pipeline.api.model.Application;
import data.pipeline.api.model.environment.EnvironmentProperties;
import data.pipeline.api.model.job.Job;
import data.pipeline.api.model.repo.ComponentRepository;
import data.pipeline.api.util.FileReader;
import data.pipeline.api.util.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import data.pipeline.api.util.GraphAPIService;
import data.pipeline.api.model.repo.YamlParser;

import java.util.Map;

/**
 * Main execution context that manages running processes
 * Starts / Stops and manage running flows
 *
 * @author Ravi Chiripurapu on 1/1/19
 */
public class DataPipeline {

    private static final Logger logger = LoggerFactory.getLogger(DataPipeline.class.getName());

    /**
     * @param args
     * @throws FlowException
     */
    public static void startApplication(String[] args) throws FlowException {

        CommandLineOptions commandLineOptions = new CommandLineOptions(args);
        commandLineOptions.parse(args);

        // arg 1: Job
        String jobString = commandLineOptions.get("j");
        String jobJson = FileReader.buildStringFromFileLocation(jobString);

        Job job = parseJob(jobJson);

        // arg 2: application.yml
        Application application = parseApplication(commandLineOptions);

        String repoXml = commandLineOptions.get("r");
        System.out.println(repoXml);
        ComponentRepository componentRepository = parseComponentRepository(repoXml);

        // arg 3 : get Pipeline
        Pipeline pipeline = parsePipeline(commandLineOptions);

        EnvironmentProperties environmentProperties = null;

        ExecutorModel executorModel = GraphAPIService.convert(commandLineOptions.get(CommandLineOptions.PIPELINE_JSON), pipeline,
                componentRepository, job, application, environmentProperties);

        // Validate the execution Model
        executorModel.validate();

        run(executorModel);

    }

    private static ComponentRepository parseComponentRepository(String repoYml) throws FlowException {
        YamlParser<ComponentRepository> componentRepositoryYamlReader = new YamlParser<>();
        return componentRepositoryYamlReader.readYaml(repoYml, ComponentRepository.class);
    }

    private static Pipeline parsePipeline(CommandLineOptions clo) throws FlowException {
        String pipelineJsonFile = clo.get("p");
        String workflowJson = FileReader.buildStringFromFileLocation(pipelineJsonFile);
        JsonParser<Pipeline> jsonReader = new JsonParser<>();
        return jsonReader.parseJson(workflowJson, Pipeline.class);
    }

    private static Job parseJob(String jobJson) throws FlowException {
        JsonParser<Job> jsonReader = new JsonParser<Job>();
        return jsonReader.parseJson(jobJson, Job.class);
    }

    private static Application parseApplication(CommandLineOptions clo) throws FlowException {
        String applicationYmlFile = clo.get("a");
        data.pipeline.api.util.YamlParser<Application> applicationYamlReader = new data.pipeline.api.util.YamlParser<>();
        return applicationYamlReader.readYaml(applicationYmlFile, Application.class);
    }

    private static void run(ExecutorModel executionModel) throws FlowException {

        // Initializing broadcaster
        Broadcaster broadcaster = new Broadcaster(executionModel);
        broadcaster.register();

        boolean executorsThatCanBeStarted=true;
        while (executorsThatCanBeStarted) {
            executorsThatCanBeStarted=false;
            for (Map.Entry<String, AbstractComponent> executorEntry:executionModel.getExecutors().entrySet()) {
                if (executorEntry.getValue().isReadyToStart()) {
                    executorsThatCanBeStarted=true;
                    executorEntry.getValue().run();
                }
            }
        }
    }
}
