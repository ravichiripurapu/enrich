package data.pipeline.api.model;

import java.util.Map;

/**
 * Created by ravi on 8/17/17.
 */
public class Application {
    private String environment;
    private boolean logging;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public boolean isLogging() {
        return logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }
}
