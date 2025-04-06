package data.pipeline.api.model.flow;

import java.util.Map;

/**
 * Created by Ravi Chiripurapu
 */
public class Environment {
    String id;
    Map<String, String> info;

    public Environment(String id, Map<String, String> info) {
        this.id = id;
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getInfo() {
        return info;
    }

    public void setInfo(Map<String, String> info) {
        this.info = info;
    }
}
