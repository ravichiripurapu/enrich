package data.pipeline.components.file;

import data.pipeline.api.components.AbstractComponent;
import data.pipeline.api.error.FlowException;
import data.pipeline.components.util.JsonUtil;

import java.util.Map;


public class FileReaderComponent extends AbstractComponent {

    public FileReaderComponent() {}

    private static Class[] inputClasses={};
    private static Class[] outputClasses={String.class};
    public Class[] getInputClasses() {return inputClasses;}
    public Class[] getOutputClasses() {return outputClasses;}

    public String getName() {return "FileReaderComponent";}


    @Override
    protected void executeComponent() throws FlowException {
        System.out.println("FileReaderComponent");
        System.out.println(this.getEnvironmentKey());
        System.out.println(this.getNodeConfiguration());

        System.out.println("input:"+this.getTotalInputPorts());
        System.out.println("output:"+this.getTotalOutputPorts());

        this.setOutputValue(0,getDataset());

        System.out.println("output:"+this.getOutputValue(0));
    }

    private String getDataset() {
        String jsonString = this.getNodeConfiguration();
        Map<String, Object> jsonMap = JsonUtil.convertStringToJsonObject(jsonString);
        return (String) jsonMap.get("dataset");
    }


}
