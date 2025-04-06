package data.pipeline.components;

import data.pipeline.api.components.AbstractComponent;
import data.pipeline.api.error.FlowException;

public class DummyComponentMock extends AbstractComponent {

    private static Class[] inputClasses={String.class};
    private static Class[] outputClasses={String.class};
	@Override
    public Class[] getInputClasses() {return inputClasses;}
	@Override
    public Class[] getOutputClasses() {return outputClasses;}

	
	

	@Override
	protected void executeComponent() throws FlowException {
		System.out.println("Do something");
	}

	@Override
	public String getName() {
		return "Dummy Component";
	}

}
