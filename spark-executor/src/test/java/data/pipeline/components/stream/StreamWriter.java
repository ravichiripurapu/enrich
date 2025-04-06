package data.pipeline.components.stream;

import data.pipeline.api.components.AbstractComponent;
import data.pipeline.api.error.FlowException;

import static java.lang.Thread.sleep;

/**
 * Created by ravi on 1/1/19.
 */
public class StreamWriter extends AbstractComponent {

    public String getName() {return "Read from Stream";}


    private static Class[] inputClasses={String.class,String.class};
    private static Class[] outputClasses={String.class};
    public Class[] getInputClasses() {return inputClasses;}
    public Class[] getOutputClasses() {return outputClasses;}


    @Override
    protected void executeComponent() throws FlowException {
        System.out.println("Stream Component");
        System.out.println(this.getEnvironmentKey());
        System.out.println(this.getNodeConfiguration());
        System.out.println("input:"+this.getTotalInputPorts());
        System.out.println("output:"+this.getTotalOutputPorts());
        System.out.println("input(0)="+this.getInputValue(0));
        System.out.println("input(1)="+this.getInputValue(1));
        this.setOutputValue(0,"join:"+this.getInputValue(0)+" - "+this.getInputValue(1));
        try {
            sleep(300000);
        } catch (Exception e) {

        }
    }


}
