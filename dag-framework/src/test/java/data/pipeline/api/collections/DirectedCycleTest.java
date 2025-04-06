package data.pipeline.api.collections;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DirectedCycleTest {
    DirectedGraph G = new DirectedGraph(0,0,null);
    


    @Test
    public void testCycle_Empty() {
        DirectedCycle finder = new DirectedCycle(G);
        boolean result=finder.hasCycle();
    	assertTrue(!result);
    }

    @Test
    public void testCycle_True() {
    	ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
        List<Edge> edgeCycleList=new ArrayList<Edge>();
        Edge edge1=new Edge(0,1);
        Edge edge2=new Edge(1,0);
        
        edgeCycleList.add(edge1);
        edgeCycleList.add(edge2);
        
        DirectedGraph graphWithCycle = new DirectedGraph(2,2,edgeCycleList);
    	
    	
    	
    	
    	DirectedCycle finder = new DirectedCycle(graphWithCycle);
    	boolean result=finder.hasCycle();
    	assertTrue(result);
    	
    }



}
