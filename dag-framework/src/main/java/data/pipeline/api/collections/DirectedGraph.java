package data.pipeline.api.collections;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 *  The {@code DirectedGraph} class represents a directed graph of vertices
 *  named 0 through <em>vertices</em> - 1.
 *  It supports the following two primary operations: add an edge to the digraph,
 *  iterate over all of the vertices adjacent from a given vertex.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 * @author Ravi Chiripurapu
 *
 *
 *
 */

public class DirectedGraph {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int verticies;           // number of vertices in this digraph
    private int edges;                 // number of edges in this digraph
    private LinkedList<Integer>[] adjacenyList;    // adj[v] = adjacency list for vertex v
    private int[] indegree;        // indegree[v] = indegree of vertex v

    /**
     * Initializes an empty digraph with <em>V</em> vertices.
     *
     * @param  vertices the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public DirectedGraph(int vertices) {
        if (vertices < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.verticies = vertices;
        this.edges = 0;
        indegree = new int[vertices];
        adjacenyList = (LinkedList<Integer>[]) new LinkedList[vertices];
        for (int v = 0; v < vertices; v++) {
            adjacenyList[v] = new LinkedList<Integer>();
        }
    }

    /**
     *
     * @param numberOfElements
     * @param numberOfEdges
     * @param edgeList
     */
    public DirectedGraph(int numberOfElements, int numberOfEdges, List<Edge> edgeList) {
        try {
            this.verticies = numberOfElements;
            if (verticies < 0) throw new IllegalArgumentException("number of vertices in a Digraph must be nonnegative");
            indegree = new int[verticies];
            adjacenyList = (LinkedList<Integer>[]) new LinkedList[verticies];
            for (int vertex = 0; vertex < verticies; vertex++) {
                adjacenyList[vertex] = new LinkedList<Integer>();
            }
            int totalEdges = numberOfEdges;
            if (totalEdges < 0) throw new IllegalArgumentException("number of edges in a Digraph must be nonnegative");
            for (int edge = 0; edge < totalEdges; edge++) {
                int v = edgeList.get(edge).getFrom();
                int w = edgeList.get(edge).getTo();
                addEdge(v, w);
            }
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
        }
    }

    /**
     * Initializes a new digraph that is a deep copy of the specified digraph.
     *
     * @param  directedGraph the digraph to copy
     */
    public DirectedGraph(DirectedGraph directedGraph) {
        this(directedGraph.getVerticies());
        this.edges = directedGraph.getEdges();
        for (int v = 0; v < verticies; v++)
            this.indegree[v] = directedGraph.inDegree(v);
        for (int v = 0; v < directedGraph.getVerticies(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : directedGraph.adjacenyList[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adjacenyList[v].add(w);
            }
        }
    }

    /**
     * Returns the number of vertices in this digraph.
     *
     * @return the number of vertices in this digraph
     */
    public int getVerticies() {
        return this.verticies;
    }

    /**
     * Returns the number of edges in this digraph.
     *
     * @return the number of edges in this digraph
     */
    public int getEdges() {
        return this.edges;
    }


    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= verticies)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (verticies -1));
    }

    /**
     * Adds the directed edge v→w to this digraph.
     *
     * @param  v the tail vertex
     * @param  w the head vertex
     * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        this.adjacenyList[v].add(w);
        this.indegree[w]++;
        this.edges++;
    }

    /**
     * Returns the vertices adjacent from vertex {@code v} in this digraph.
     *
     * @param  v the vertex
     * @return the vertices adjacent from vertex {@code v} in this digraph, as an iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return this.adjacenyList[v];
    }

    /**
     * Returns the number of directed edges incident from vertex {@code v}.
     * This is known as the <em>outdegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the outdegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int outDegree(int v) {
        validateVertex(v);
        return adjacenyList[v].size();
    }

    /**
     * Returns the number of directed edges incident to vertex {@code v}.
     * This is known as the <em>indegree</em> of vertex {@code v}.
     *
     * @param  v the vertex
     * @return the indegree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int inDegree(int v) {
        validateVertex(v);
        return indegree[v];
    }

    /**
     * Returns the reverse of the digraph.
     *
     * @return the reverse of the digraph
     */
    public DirectedGraph reverse() {
        DirectedGraph reverse = new DirectedGraph(verticies);
        for (int v = 0; v < verticies; v++) {
            for (int w : adj(v)) {
                reverse.addEdge(w, v);
            }
        }
        return reverse;
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(verticies + " vertices, " + edges + " edges " + NEWLINE);
        for (int v = 0; v < verticies; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adjacenyList[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }



}
