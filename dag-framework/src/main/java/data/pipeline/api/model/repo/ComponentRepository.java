package data.pipeline.api.model.repo;

import java.util.Map;

/**
 * Created by ravi on 8/21/17.
 */
public class ComponentRepository {
    private Map<String, String> components;

    public Map<String, String> getComponents() {
        return components;
    }

    public void setComponents(Map<String, String> components) {
        this.components = components;
    }
}
