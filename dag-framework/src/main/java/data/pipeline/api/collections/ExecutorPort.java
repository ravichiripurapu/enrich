package data.pipeline.api.collections;

/**
 * @author Ravi Chiripurapu
 */
public class ExecutorPort {


    String name;
    Object value;
    Class allowedClass;
    boolean setted=false;

    public ExecutorPort(Class clazz, String name) {
        this.allowedClass=clazz;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        if (value!=null && this.allowedClass.isInstance(value) || value==null) {
            this.value = this.allowedClass.cast(value);
        }else {
            throw new ClassCastException(value.getClass().getName()+" is not instanceof "+allowedClass.getName()+" in port "+this.getName());
        }
    }


    public boolean isSetted() {

        return setted;
    }

    public void setSetted(boolean setted) {
        this.setted = setted;
    }

    @Override
    public String toString() {
        return "ExecutorPort{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
