package data.pipeline.executor.cli;

import data.pipeline.api.error.FlowException;
import data.pipeline.api.error.WorkflowErrorCode;
import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ravi Chiripurapu on 1/1/19.
 */
public class CommandLineOptions {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineOptions.class.getName());

    public static final String HELP="help",
                               JOB_JSON = "jobJson",
                               APP_YML = "appYml",
                               PIPELINE_JSON = "pipelineJson",
                               REPO_YML = "repoYml";
    private Options commandOptions = new Options();
    private Map<String, String> optionsMap = new HashMap<String, String>();

    public CommandLineOptions(String[] args) {
        this.commandOptions = createOptions();
    }

    private Options createOptions() {
        Options options = new Options();
        options.addOption(buildRequiredOption("j",JOB_JSON, true, "[Required] - name of the file containing the job json."));
        options.addOption(buildRequiredOption("a",APP_YML, true, "[Required] - name of the file containing application yml."));
        options.addOption(buildRequiredOption("r",REPO_YML, true, "[Required] - name of the file containing components."));
        options.addOption(buildRequiredOption("p",PIPELINE_JSON, true, "[Required] - name of the file containing workflow json."));
        options.addOption("h",HELP, false, "[Optional] - show help.");
        return options;
    }

    private Option buildRequiredOption(String shortName, String optionName, boolean hasArg, String description) {
        Option option = new Option(shortName, optionName, hasArg, description);
        option.setRequired(true);
        return option;
    }

    public void parse(String[] args) throws FlowException {
        try {
            CommandLine cmd = (new BasicParser()).parse(commandOptions, args);
            if (cmd.hasOption(HELP)) {
                printHelp();
            }

            List<Option> optionsList = new ArrayList<>(commandOptions.getOptions());
            for (Option option : optionsList) {
                optionsMap.put(option.getOpt(), readOptionFromCommand(cmd, option));
            }
        } catch (ParseException pe) {
            printHelp();
            logger.error(WorkflowErrorCode.InsufficientCommandOptions.getMessage());
            throw FlowException.build(WorkflowErrorCode.InsufficientCommandOptions, pe);
        }
    }

    private String readOptionFromCommand(CommandLine cmd, Option option) throws FlowException {
        String optionValue = null;
        String optionName = option.getOpt();
        if (cmd.hasOption(optionName)) {
            optionValue = cmd.getOptionValue(optionName);
        } else if (option.isRequired()) {
            printHelp();
            logger.error(WorkflowErrorCode.InsufficientCommandOptions.getMessage());
            throw FlowException.build(WorkflowErrorCode.InsufficientCommandOptions);
        }
        return optionValue;
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("App", commandOptions);
    }

    public String get(String optionName) { return optionsMap.get(optionName); }
}
