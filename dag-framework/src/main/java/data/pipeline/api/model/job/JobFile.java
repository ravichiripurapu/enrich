package data.pipeline.api.model.job;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobFile {

    @SerializedName("instance_id")
    @Expose
    private String instanceId;
    @SerializedName("entity")
    @Expose
    private String entity;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("file_size")
    @Expose
    private long fileSize;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getFileSize() { return fileSize; }

    public void setFileSize(long fileSize) { this.fileSize = fileSize; }
}