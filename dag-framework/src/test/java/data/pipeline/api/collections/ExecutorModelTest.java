package data.pipeline.api.collections;

import static org.junit.Assert.*;

import data.pipeline.api.components.AbstractComponent;
import data.pipeline.api.error.FlowException;
import data.pipeline.api.error.WorkflowErrorCode;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import data.pipeline.components.DummyComponent1To2;
import data.pipeline.components.DummyComponentMock;
import data.pipeline.components.DummyFinalComponentMock;
import data.pipeline.components.DummyStartComponentMock;

public class ExecutorModelTest {

	private static String NODE_ID_1="dummy-01";
	private static String NODE_ID_2="dummy-02";
	private static String NODE_ID_3="dummy-start";
	private static String NODE_ID_4="dummy-final";
	private static String NODE_ID_5="dummy-05";
	private static String NODE_ID_6="dummy-1to2";

	
	ExecutorModel emptyExecutorModel;
	ExecutorModel noStartExecutorModel;
	ExecutorModel noFinalExecutorModel;
	ExecutorModel cycleExecutorModel;
	ExecutorModel executorModel;
	ExecutorModel weaklyExecutorModel;
	ExecutorModel unconnectedPortExecutorModel;
	ExecutorModel multipleInputsExecutorModel;
	ExecutorModel orphanExecutorModel;
	
	
	@Before
	public void setUp() throws Exception {

		
		AbstractComponent executorElement=new DummyComponentMock();
		executorElement.setNodeId(NODE_ID_1);
		AbstractComponent executorElement2=new DummyComponentMock();
		executorElement2.setNodeId(NODE_ID_2);
		AbstractComponent executorElementStart=new DummyStartComponentMock();
		executorElementStart.setNodeId(NODE_ID_3);
		AbstractComponent executorElementFinal=new DummyFinalComponentMock();
		executorElementFinal.setNodeId(NODE_ID_4);
		AbstractComponent executorElement5=new DummyComponentMock();
		executorElement2.setNodeId(NODE_ID_5);
		AbstractComponent executorElement1to2=new DummyComponent1To2();
		executorElement1to2.setNodeId(NODE_ID_6);
		
		GraphConnection graphConnection12=new GraphConnection(1L,executorElement,0,executorElement2,0);
		GraphConnection graphConnection21=new GraphConnection(2L,executorElement2,0,executorElement,0);
		GraphConnection graphConnection31=new GraphConnection(2L,executorElementStart,0,executorElement,0);
		GraphConnection graphConnection24=new GraphConnection(2L,executorElement2,0,executorElementFinal,0);
		GraphConnection graphConnection26=new GraphConnection(2L,executorElement2,0,executorElement1to2,0);
		GraphConnection graphConnection6Final=new GraphConnection(2L,executorElement1to2,0,executorElementFinal,0);
		GraphConnection graphConnection6_2Final=new GraphConnection(2L,executorElement1to2,1,executorElementFinal,0);
		GraphConnection graphConnectionStartTo6=new GraphConnection(2L,executorElementStart,0,executorElement1to2,0);
		GraphConnection graphConnection6_2To1=new GraphConnection(2L,executorElement1to2,1,executorElement,0);
		
		
		this.emptyExecutorModel=new ExecutorModel();

		
		this.noStartExecutorModel=new ExecutorModel();
		this.noStartExecutorModel.addExecutor(executorElement);
		this.noStartExecutorModel.addExecutor(executorElement2);
		this.noStartExecutorModel.addConnector(graphConnection12);
		
		
		
		this.cycleExecutorModel=new ExecutorModel();
		this.cycleExecutorModel.addExecutor(executorElement);
		this.cycleExecutorModel.addExecutor(executorElement2);
		this.cycleExecutorModel.addConnector(graphConnection12);
		this.cycleExecutorModel.addConnector(graphConnection21);
		
		
		this.noFinalExecutorModel=new ExecutorModel();
		this.noFinalExecutorModel.addExecutor(executorElement);
		this.noFinalExecutorModel.addExecutor(executorElement2);
		this.noFinalExecutorModel.addExecutor(executorElementStart);
		this.noFinalExecutorModel.addConnector(graphConnection12);
		this.noFinalExecutorModel.addConnector(graphConnection31);
		

		this.executorModel=new ExecutorModel();
		this.executorModel.addExecutor(executorElement);
		this.executorModel.addExecutor(executorElement2);
		this.executorModel.addExecutor(executorElementStart);
		this.executorModel.addExecutor(executorElementFinal);
		this.executorModel.addConnector(graphConnection12);
		this.executorModel.addConnector(graphConnection31);
		this.executorModel.addConnector(graphConnection24);
		
		this.weaklyExecutorModel=new ExecutorModel();
		this.weaklyExecutorModel.addExecutor(executorElement);
		this.weaklyExecutorModel.addExecutor(executorElement2);
		this.weaklyExecutorModel.addExecutor(executorElementStart);
		this.weaklyExecutorModel.addExecutor(executorElementFinal);
		this.weaklyExecutorModel.addConnector(graphConnection12);
		this.weaklyExecutorModel.addConnector(graphConnection31);
		this.weaklyExecutorModel.addConnector(graphConnection24);
		this.weaklyExecutorModel.addExecutor(executorElement5);
		

		
		this.unconnectedPortExecutorModel=new ExecutorModel();
		this.unconnectedPortExecutorModel.addExecutor(executorElement);
		this.unconnectedPortExecutorModel.addExecutor(executorElement2);
		this.unconnectedPortExecutorModel.addExecutor(executorElementStart);
		this.unconnectedPortExecutorModel.addExecutor(executorElementFinal);
		this.unconnectedPortExecutorModel.addExecutor(executorElement1to2);
		this.unconnectedPortExecutorModel.addConnector(graphConnection12);
		this.unconnectedPortExecutorModel.addConnector(graphConnection31);
		this.unconnectedPortExecutorModel.addConnector(graphConnection26);
		this.unconnectedPortExecutorModel.addConnector(graphConnection6Final);
		

		this.multipleInputsExecutorModel=new ExecutorModel();
		this.multipleInputsExecutorModel.addExecutor(executorElement);
		this.multipleInputsExecutorModel.addExecutor(executorElement2);
		this.multipleInputsExecutorModel.addExecutor(executorElementStart);
		this.multipleInputsExecutorModel.addExecutor(executorElementFinal);
		this.multipleInputsExecutorModel.addExecutor(executorElement1to2);
		this.multipleInputsExecutorModel.addConnector(graphConnection12);
		this.multipleInputsExecutorModel.addConnector(graphConnection31);
		this.multipleInputsExecutorModel.addConnector(graphConnection26);
		this.multipleInputsExecutorModel.addConnector(graphConnection6Final);
		this.multipleInputsExecutorModel.addConnector(graphConnection6_2Final);
		

	
		this.orphanExecutorModel=new ExecutorModel();
		this.orphanExecutorModel.addExecutor(executorElement1to2);
		this.orphanExecutorModel.addExecutor(executorElementStart);
		this.orphanExecutorModel.addExecutor(executorElementFinal);

		
		
		
		this.orphanExecutorModel.addConnector(graphConnectionStartTo6);
		this.orphanExecutorModel.addConnector(graphConnection6Final);
		this.orphanExecutorModel.addConnector(graphConnection6_2To1);
		
	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidate_Empty()  {
		try {
			this.emptyExecutorModel.validate();
			fail("It should throw WorkflowErrorCode.NoStartNode Exception");
		} catch (FlowException e) {
			Assert.assertEquals(e.getErrorCode(), WorkflowErrorCode.NoStartNode);
		}
	}

	
	@Test
	public void testValidate_2Nodes() throws Exception {
		this.executorModel.validate();
	}
	
	@Test
	public void testValidate_Cycle()  {
		try {
			this.cycleExecutorModel.validate();
			fail("It should throw WorkflowErrorCode.GraphHasCycle Exception");
		} catch (FlowException e) {
			assertEquals(e.getErrorCode(),WorkflowErrorCode.GraphHasCycle);
		}
	}

	
	@Test
	public void testValidate_NoFinish()  {
		try {
			this.noFinalExecutorModel.validate();
			fail("It should throw WorkflowErrorCode.NoFinishNode Exception");
		} catch (FlowException e) {
			assertEquals(e.getErrorCode(),WorkflowErrorCode.NoFinishNode);
		}
	}

	@Test
	public void testValidate_Weakly()  {
		try {
			this.weaklyExecutorModel.validate();
			fail("It should throw WorkflowErrorCode.UnconnectedNodes Exception");
		} catch (FlowException e) {
			assertEquals(e.getErrorCode(),WorkflowErrorCode.UnconnectedNodes);
		}
	}
	
	@Test
	public void testValidate_UnusedPorts()  {
		try {
			this.unconnectedPortExecutorModel.validate();
			fail("It should throw WorkflowErrorCode.UnusedPorts Exception");
		} catch (FlowException e) {
			assertEquals(e.getErrorCode(),WorkflowErrorCode.UnusedPorts);
		}
	}
	
	@Test
	public void testValidate_InputPortsManyConnectors()  {
		try {
			this.multipleInputsExecutorModel.validate();
			fail("It should throw WorkflowErrorCode.InputPortsManyConnectors Exception");
		} catch (FlowException e) {
			assertEquals(e.getErrorCode(),WorkflowErrorCode.InputPortsManyConnectors);
		}
	}

	@Test
	public void testValidate_Orphan()  {
		try {
			this.orphanExecutorModel.validate();
			fail("It should throw WorkflowErrorCode.OrphanPort Exception");
		} catch (FlowException e) {
			assertEquals(e.getErrorCode(),WorkflowErrorCode.OrphanPort);
		}
	}
	
	@Test
	public void testAddExecutor_AlreadyExists()  {
		try {
			ExecutorModel executorModel=new ExecutorModel();
			AbstractComponent executorElement=new DummyComponentMock();
			executorElement.setNodeId(NODE_ID_1);
			AbstractComponent executorElement2=new DummyComponentMock();
			executorElement2.setNodeId(NODE_ID_1);
			
			executorModel.addExecutor(executorElement);
			executorModel.addExecutor(executorElement2);
			
			fail("It should throw WorkflowErrorCode.NodeAlreadyExists Exception");
		} catch (FlowException e) {
			assertEquals(e.getErrorCode(),WorkflowErrorCode.NodeAlreadyExists);
		}
	}
	
	@Test
	public void testAddConnector_OrphanPort()  {
		try {
			ExecutorModel executorModel=new ExecutorModel();
			AbstractComponent executorElement=new DummyComponentMock();
			executorElement.setNodeId(NODE_ID_1);
			AbstractComponent executorElement2=new DummyComponentMock();
			executorElement2.setNodeId(NODE_ID_2);
			GraphConnection graphConnection12=new GraphConnection(1L,executorElement,0,executorElement2,0);
			executorModel.addConnector(graphConnection12);
			fail("It should throw WorkflowErrorCode.OrphanPort Exception");
		} catch (FlowException e) {
			assertEquals(e.getErrorCode(),WorkflowErrorCode.OrphanPort);
		}
	}

	@Test
	public void testAddConnector_OrphanPort2()  {
		try {
			ExecutorModel executorModel=new ExecutorModel();
			AbstractComponent executorElement=new DummyComponentMock();
			executorElement.setNodeId(NODE_ID_1);
			AbstractComponent executorElement2=new DummyComponentMock();
			executorElement2.setNodeId(NODE_ID_2);
			GraphConnection graphConnection12=new GraphConnection(1L,executorElement,8,executorElement2,0);
			executorModel.addExecutor(executorElement);
			executorModel.addExecutor(executorElement2);
			executorModel.addConnector(graphConnection12);
			fail("It should throw WorkflowErrorCode.OrphanPort Exception");
		} catch (FlowException e) {
			assertEquals(e.getErrorCode(),WorkflowErrorCode.OrphanPort);
		}
	}
	
	
	
	
}
