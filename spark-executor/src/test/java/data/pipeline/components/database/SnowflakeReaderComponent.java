package data.pipeline.components.database;


import data.pipeline.api.components.AbstractComponent;

public class SnowflakeReaderComponent extends AbstractComponent {

    @Override
    protected void executeComponent()  {
        System.out.println("Snowflake Reader Component");
        System.out.println("input: "+this.getInputValue(0));
        System.out.println(this.getEnvironmentKey());
        System.out.println(this.getNodeConfiguration());
        System.out.println("input:"+this.getTotalInputPorts());
        System.out.println("output:"+this.getTotalOutputPorts());
        System.out.println("input: "+this.getInputValue(0));

    }

    public String getName() {return "Snowflake Component";}

    private static Class[] inputClasses={String.class};
    private static Class[] outputClasses={};
    public Class[] getInputClasses() {return inputClasses;}
    public Class[] getOutputClasses() {return outputClasses;}



}
