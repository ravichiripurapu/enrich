package data.pipeline.api.model.flow;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Node {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("component_info")
    @Expose
    private ComponentInfo componentInfo;
    @SerializedName("environment_key")
    @Expose
    private String environmentKey;
    @SerializedName("entity")
    @Expose
    private String entity;
    @SerializedName("conf")
    @Expose
    private JsonElement conf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ComponentInfo getComponentInfo() { return componentInfo; }

    public void setComponentInfo(ComponentInfo component) { this.componentInfo = componentInfo; }

    public String getEnvironmentKey() { return environmentKey; }

    public void setEnvironmentKey(String environmentKey) { this.environmentKey = environmentKey; }

    public String getConf() {
        return conf.toString();
    }

    public void setConf(JsonElement conf) {
        this.conf = conf;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}