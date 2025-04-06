
package data.pipeline.api.model.flow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class From {

    @SerializedName("node_id")
    @Expose
    private String nodeId;
    @SerializedName("port_index")
    @Expose
    private int portIndex;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getPortIndex() { return portIndex; }

    public void setPortIndex(int portIndex) { this.portIndex = portIndex; }
}