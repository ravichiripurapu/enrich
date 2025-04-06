package data.pipeline.api.model.environment;

import java.util.Map;

/**
 * Created by Ravi Chiripurapu
 */
public class EnvironmentProperties {
    private Map<String, Object> properties;

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void getProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
