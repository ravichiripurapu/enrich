package data.pipeline.api.components;

import data.pipeline.api.collections.ExecutorPort;
import data.pipeline.api.error.FlowException;
import data.pipeline.api.model.environment.EnvironmentProperties;
import data.pipeline.api.collections.Broadcaster;
import data.pipeline.api.error.WorkflowErrorCode;
import data.pipeline.api.model.Application;
import data.pipeline.api.model.job.Job;
import data.pipeline.api.model.job.JobFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * Class used by the runner to manage execute elements.
 * this class contains the information for a running process based in a tool and an component
 * 
 * @author Ravi Chiripurapu
 *
 */
public abstract class AbstractComponent {
    private static final Logger logger = LoggerFactory.getLogger(AbstractComponent.class.getName());

	private String nodeId;
	private List<ExecutorPort> inputs;
	private List<ExecutorPort> outputs;
	private boolean finished=false;

    private Job job;
    private JobFile jobFile;
    private Application application;
    private EnvironmentProperties environmentProperties;

    private Broadcaster broadcaster;






    /**
     * Name of the tool to be shown in tool gallery
     *
     * @author Ravi Chiripurapu
     *
     */
    private String name;




    String environmentKey;
    String nodeConfiguration;


    public String getEnvironmentKey() {
        return environmentKey;
    }

    public void setEnvironmentKey(String environmentKey) {
        this.environmentKey = environmentKey;
    }

    public String getNodeConfiguration() {
        return nodeConfiguration;
    }

    public void setNodeConfiguration(String nodeConfiguration) {
        this.nodeConfiguration = nodeConfiguration;
    }

    /**
     * Number of input interfaces (Can be data or no data interfaces)
     * no data interfaces referes to a precedence relation
     *
     * 	Number of input interfaces (Includes data and no data interfaces)
     * Input interfaces are represented as connectors that can receive data  from another element.
     *
     *
     * @author Ravi Chiripurapu
     *
     *
     */

    /**
     * Number of output data interfaces. This number does not include the async output connector.
     * Components can send data to other excomponents calling
     * setOutput(index,message) having index between 1 and totalOutputPorts
     *
     *
     */

    public final int getTotalInputPorts() {return getInputClasses().length;}
    public final int getTotalOutputPorts() {return getOutputClasses().length;}

    abstract public Class[] getInputClasses();
    abstract public Class[] getOutputClasses();

	/**
	 * 
	 *
	 * 
	 * @author Ravi Chiripurapu
	 */
	public AbstractComponent() {
		this.inputs=new ArrayList<ExecutorPort>(getTotalInputPorts());
		for (int i = 0; i< getTotalInputPorts(); i++) {
                inputs.add(new ExecutorPort(this.getInputClasses()[i],Integer.toString(i)));
		}
		this.outputs=new ArrayList<ExecutorPort>(this.getTotalOutputPorts());
		for (int i = 0; i< this.getTotalOutputPorts(); i++) {
            outputs.add(new ExecutorPort(this.getOutputClasses()[i],Integer.toString(i)));
		}
		this.finished=false;
	}



    public static AbstractComponent create(
            String className,
            String id,
            String environmentKey,
            String nodeConfiguration) throws FlowException {
        Class<?> clazz;

        try {
            clazz = Class.forName(className);
            System.out.println(className);
            AbstractComponent executorElement =(AbstractComponent) clazz.newInstance();
            executorElement.setNodeId(id);
            executorElement.setEnvironmentKey(environmentKey);
            executorElement.setNodeConfiguration(nodeConfiguration);
            return executorElement;
        } catch (ClassNotFoundException e) {
            logger.error(WorkflowErrorCode.ClassNotFound.getMessage());
            throw FlowException.build(WorkflowErrorCode.ClassNotFound, e).set("className", className).set("class",AbstractComponent.class);
        } catch (IllegalAccessException e) {
            logger.error(WorkflowErrorCode.IllegalAccess.getMessage());
            throw FlowException.build(WorkflowErrorCode.IllegalAccess, e).set("className", className).set("class",AbstractComponent.class);
        } catch (InstantiationException e) {
            logger.error(WorkflowErrorCode.ClassNotInstantiable.getMessage());
            throw FlowException.build(WorkflowErrorCode.ClassNotInstantiable, e).set("className", className).set("class",AbstractComponent.class);
        }
    }

    public Object getOutputValue(int index)  {
        return this.getOutputs().get(index).getValue();
    }

    public Object getInputValue(int index)  {
        return this.getInputs().get(index).getValue();
    }

    public void setInputValue(int index,Object value)  {
        this.getInputs().get(index).setValue(value);
        this.getInputs().get(index).setSetted(true);
    }

    protected void setOutputValue(int index, Object value) throws FlowException {
        System.out.println("index:"+index);
        System.out.println("broadcaster:"+this.broadcaster);
	    this.getOutputs().get(index).setValue(value);
	    this.getOutputs().get(index).setSetted(true);
	    this.broadcaster.broadcast(this.getNodeId(),index,value);
    }


    /**
     * Main entry point on receiving a message from another component or starting it
     *
     * @author Ravi Chiripurapu
     * @throws FlowException
     *
     */
    public void run() throws FlowException {
        executeComponent();
        // call to emtpy outputs to set those outputs that were not setted by the component during execution
        emptyOutputs();
        System.out.println("-------------------------------------------------------------------------");
        this.finished=true;
    }


    private void emptyOutputs() throws FlowException {
        for (int i = 0; i<this.getTotalOutputPorts(); i++) {
            if (!this.getOutputs().get(i).isSetted()) {
                this.setOutputValue(i,null);
            }
        }
    }

    /**
     * Method each plugin element component should implement
     *
     * Inside elements can be calles
     * 	setOutput(index,message): to set the message to be sent to another component
     *  sendMessage(....) : to show information in the output console of the app
     *  getEnvironment()... : to use shared resources of the execution runnning environment
     *  getProperties() : to access customized data of the element setted in each instance
     * @throws FlowException
     *
     * @
     *
     * @author Ravi Chiripurapu
     *
     */
    protected abstract void executeComponent() throws FlowException;


    // END CORE EXECUTION


	public List<ExecutorPort> getInputs() {
		return inputs;
	}

	public List<ExecutorPort> getOutputs() {
		return outputs;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isReadyToStart() {

		boolean result=true;
		if (finished) {
            result=false;
        } else {
            for (int i = 0; i<this.getTotalInputPorts(); i++) {
                if (!this.inputs.get(i).isSetted()) {
                    result=false;
                }
            }
        }
		return result;
	}

    public abstract String getName();

	@Override
	public String toString() {
		return String.format(
				"AbstractComponent [nodeId=%s, inputs=%s, outputs=%s, finished=%s]",
                nodeId, inputs, outputs, finished);
	}

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public EnvironmentProperties getEnvironmentProperties() {
        return environmentProperties;
    }

    public void setEnvironmentProperties(EnvironmentProperties environmentProperties) {
        this.environmentProperties = environmentProperties;
    }

    public Broadcaster getBroadcaster() {
        return broadcaster;
    }

    public void setBroadcaster(Broadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    public JobFile getJobFile() {
        return jobFile;
    }

    public void setJobFile(JobFile jobFile) {
        this.jobFile = jobFile;
    }
}
