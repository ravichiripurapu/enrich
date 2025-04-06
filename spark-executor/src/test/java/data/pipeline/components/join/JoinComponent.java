package data.pipeline.components.join;


import data.pipeline.api.components.AbstractComponent;
import data.pipeline.api.error.FlowException;

public class JoinComponent extends AbstractComponent {

    public String getName() {return "Join Component";}


    private static Class[] inputClasses={String.class,String.class};
    private static Class[] outputClasses={String.class};
    public Class[] getInputClasses() {return inputClasses;}
    public Class[] getOutputClasses() {return outputClasses;}


    @Override
    protected void executeComponent() throws FlowException {
        System.out.println("Join Component");
        System.out.println("input(0)="+this.getInputValue(0));
        System.out.println("input(1)="+this.getInputValue(1));

        System.out.println(this.getEnvironmentKey());
        System.out.println(this.getNodeConfiguration());
        System.out.println("input:"+this.getTotalInputPorts());
        System.out.println("output:"+this.getTotalOutputPorts());

        try {
            //sleep(60000);
            this.setOutputValue(0,"join:"+this.getInputValue(0)+" - "+this.getInputValue(1));
        } catch (Exception e) {

        }

        System.out.println("output:"+this.getOutputValue(0));

    }

    private String getJointValue() {
        return "join - " + (String) this.getInputValue(0) +
                (String) this.getInputValue(1);
    }


}
