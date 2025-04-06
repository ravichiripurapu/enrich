package data.pipeline.api.collections;

import static org.junit.Assert.*;

import org.junit.Test;

public class DirectedGraphTest {

    /**
     * Unit tests the {@code Digraph} data type.
     *
     * @param args the command-line arguments
     */

    DirectedGraph emptyDirectedGraph = new DirectedGraph(0,0,null);
    
    DirectedGraph baseDirectedGraph = new DirectedGraph(10);
    
    
    
    
    
    @Test
    public void testConstructor_Replicated() {
    	this.baseDirectedGraph.addEdge(0, 1);
        DirectedGraph replicatedDirectedGraph = new DirectedGraph(baseDirectedGraph);
        assertTrue(replicatedDirectedGraph!=null);
    } 
    
    @Test
    public void testReverse() {
    	this.baseDirectedGraph.addEdge(0, 1);
    	this.baseDirectedGraph.addEdge(0, 2);
        DirectedGraph reverseDirectedGraph = this.baseDirectedGraph.reverse();
        assertTrue(reverseDirectedGraph!=null);
    } 
    

    @Test
    public void testOutdegree() {
    	this.baseDirectedGraph.addEdge(0, 1);
        assertEquals(this.baseDirectedGraph.outDegree(5),0);
    	
    }

    @Test(expected=IllegalArgumentException.class)
    public void testOutdegree_Exception() {
        assertEquals(this.baseDirectedGraph.outDegree(40),0);
    	
    }
    
    @Test
    public void testToString() {
    	baseDirectedGraph.addEdge(0,1);
    	String output=this.baseDirectedGraph.toString();
    	System.out.println(this.baseDirectedGraph.toString());
        assertEquals(output.substring(0,20),"10 vertices, 1 edges");
    	
    }
    
    
}