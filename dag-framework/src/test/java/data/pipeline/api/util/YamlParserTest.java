package data.pipeline.api.util;

import data.pipeline.api.error.FlowException;
import data.pipeline.api.model.Application;
import data.pipeline.api.model.repo.ComponentRepository;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ravi on 8/20/17.
 */
public class YamlParserTest {

    @Test
    public void readApplicationYamlTest() throws FlowException {
        String applicationFile = System.getProperty("user.dir")+"/src/test/resources/application.yml";
        YamlParser<Application> yamlParser = new YamlParser();
        Application application = yamlParser.readYaml(applicationFile, Application.class);
        Assert.assertEquals("dev", application.getEnvironment());
    }

    @Test
    public void readComponentRepoTest() throws FlowException {
        String componentRepoYml = System.getProperty("user.dir")+"/src/test/resources/repo.yml";
        YamlParser<ComponentRepository> yamlReader = new YamlParser();
        ComponentRepository componentRepository = yamlReader.readYaml(componentRepoYml, ComponentRepository.class);
        String className = componentRepository.getComponents().get("r48dd54c-ReadFile-1.0");
        Assert.assertEquals("com.onelake.workflowexecutor.components.mock.ReadFileMock",className);
    }

}
