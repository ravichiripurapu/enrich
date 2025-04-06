package data.pipeline.api.model.flow;

/**
 * Created by Ravi Chiripurapu
 */

public class Namespace {
    String id;
    String name;
    Namespace parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Namespace getParent() {
        return parent;
    }

    public void setParent(Namespace parent) {
        this.parent = parent;
    }
}
