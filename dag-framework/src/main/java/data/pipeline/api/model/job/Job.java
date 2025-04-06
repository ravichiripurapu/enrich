package data.pipeline.api.model.job;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Job {

    @SerializedName("job_uuid")
    @Expose
    private String jobUuid;
    @SerializedName("output_instance")
    @Expose
    private String outputInstance;
    @SerializedName("instances")
    @Expose
    private List<JobFile> instances = null;
    @SerializedName("re_run_job")
    @Expose
    private boolean reRunJob;

    public String getJobUuid() {
        return jobUuid;
    }

    public void setJobUuid(String jobUuid) {
        this.jobUuid = jobUuid;
    }

    public String getOutputInstance() {
        return outputInstance;
    }

    public void setOutputInstance(String outputInstance) {
        this.outputInstance = outputInstance;
    }

    public List<JobFile> getInstances() {
        return instances;
    }

    public void setInstances(List<JobFile> instances) {
        this.instances = instances;
    }

    public boolean isReRunJob() {
        return reRunJob;
    }

    public void setReRunJob(boolean reRunJob) {
        this.reRunJob = reRunJob;
    }
}