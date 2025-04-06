package data.pipeline.api.collections;

import static org.junit.Assert.*;


import data.pipeline.api.components.AbstractComponent;
import data.pipeline.api.error.FlowException;
import data.pipeline.components.DummyComponentMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BroadcasterTest {

	private static String NODE_ID_1="dummy-01";
	private static String NODE_ID_2="dummy-02";
	private static String NODE_ID_UNEXISTENT="node-does-not-exist";
	
	ExecutorModel emptyExecutorModel;
	ExecutorModel executorModel;
	
	
	@Before
	public void setUp() throws Exception {
		this.emptyExecutorModel=new ExecutorModel();

		
		this.executorModel=new ExecutorModel();
		AbstractComponent executorElement=new DummyComponentMock();
		executorElement.setNodeId(NODE_ID_1);
		this.executorModel.addExecutor(executorElement);
		AbstractComponent executorElement2=new DummyComponentMock();
		executorElement2.setNodeId(NODE_ID_2);
		this.executorModel.addExecutor(executorElement2);
		GraphConnection graphConnection=new GraphConnection(1L,executorElement,0,executorElement2,0);
		this.executorModel.addConnector(graphConnection);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBroadcaster() {
		Broadcaster broadcaster=new Broadcaster(this.executorModel);
		assertTrue(broadcaster!=null);
	}

	
	@Test(expected=FlowException.class)
	public void testBroadcast_Exception() throws Exception {
		Broadcaster broadcaster=new Broadcaster(this.emptyExecutorModel);
		broadcaster.broadcast(NODE_ID_UNEXISTENT, 1, null);
	}


	@Test(expected=FlowException.class)
	public void testBroadcast_NullNodeId() throws Exception {
		Broadcaster broadcaster=new Broadcaster(this.executorModel);
		broadcaster.broadcast(null, 1, null);
	}

	
	@Test(expected=FlowException.class)
	public void testBroadcast_NegativePort() throws Exception {
		Broadcaster broadcaster=new Broadcaster(this.executorModel);
		broadcaster.broadcast(NODE_ID_1, -1, "value");
	}
	
	
	@Test
	public void testBroadcast() throws Exception {
		Broadcaster broadcaster=new Broadcaster(this.executorModel);
		String message="ThisMessage";
		broadcaster.broadcast(NODE_ID_1, 0, message);
		AbstractComponent targetElement=this.executorModel.getExecutors().get(NODE_ID_2);
		String targetValue=(String) targetElement.getInputs().get(0).getValue();
		assertTrue(message.equals(targetValue));
	}

	
	@Test
	public void testRegister() throws Exception {
		Broadcaster broadcaster=new Broadcaster(this.executorModel);
		broadcaster.register();
		AbstractComponent element1=this.executorModel.getExecutors().get(NODE_ID_1);
		assertTrue(broadcaster==element1.getBroadcaster());
	}
	
	
}
