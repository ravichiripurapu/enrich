package data.pipeline.api.collections;


/**
 * @author Ravi Chiripurapu
 */

public class PortIdentifier {
    String nodeId;
    int index;
    String type;


    public PortIdentifier(String nodeId, int index, String type) {
        this.nodeId = nodeId;
        this.index = index;
        this.type = type;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    /**
     * INPUT or OUTPUT
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PortIdentifier that = (PortIdentifier) o;

        if (index != that.index) return false;
        if (nodeId != null ? !nodeId.equals(that.nodeId) : that.nodeId != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = nodeId != null ? nodeId.hashCode() : 0;
        result = 31 * result + index;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PortIdentifier{" +
                "nodeId='" + nodeId + '\'' +
                ", index=" + index +
                ", type='" + type + '\'' +
                '}';
    }
}
