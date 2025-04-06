package data.pipeline.components.file;


import data.pipeline.api.components.AbstractComponent;

public class FileWriterComponent extends AbstractComponent {

    @Override
    protected void executeComponent()  {
        System.out.println("File Writer Component");
        System.out.println("input(0)="+this.getInputValue(0));
        System.out.println(this.getEnvironmentKey());
        System.out.println(this.getNodeConfiguration());
        System.out.println("input:"+this.getTotalInputPorts());
        System.out.println("output:"+this.getTotalOutputPorts());

        System.out.println("input: "+this.getInputValue(0));

    }

    public String getName() {return "File Writer Component";}

    private static Class[] inputClasses={String.class};
    private static Class[] outputClasses={};
    public Class[] getInputClasses() {return inputClasses;}
    public Class[] getOutputClasses() {return outputClasses;}



}
