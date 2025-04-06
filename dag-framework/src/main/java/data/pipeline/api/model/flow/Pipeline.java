package data.pipeline.api.model.flow;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pipeline {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("nodes")
    @Expose
    private List<Node> nodes = null;
    @SerializedName("entities")
    @Expose
    private List<Entity> entities = null;
    @SerializedName("environments")
    @Expose
    private List<Environment> environments = null;
    @SerializedName("connections")
    @Expose
    private List<Connection> connections = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public List<Environment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(List<Environment> environments) {
        this.environments = environments;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}