package data.pipeline.api.collections;

import java.util.Stack;

/**
 *  The {@code DirectedCycle} class represents a data type for
 *  determining whether a DirectedGraph has a directed cycle.
 *  The <em>hasCycle</em> operation determines whether the DirectedGraph has
 *  a directed cycle and, and of so, the <em>cycle</em> operation
 *  returns one.
 *
 * @author Ravi Chiripurapu
 *
 *
 */
public class DirectedCycle {
    private boolean[] marked;        // marked[v] = has vertex v been marked?
    private int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
    private boolean[] onStack;       // onStack[v] = is vertex on the stack?
    private Stack<Integer> cycle;    // directed cycle (or null if no such cycle)

    /**
     * Determines whether the DirectedGraph {@code G} has a directed cycle and, if so,
     * finds such a cycle.
     * @param G the DirectedGraph
     */
    public DirectedCycle(DirectedGraph G) {
        marked  = new boolean[G.getVerticies()];
        onStack = new boolean[G.getVerticies()];
        edgeTo  = new int[G.getVerticies()];
        for (int v = 0; v < G.getVerticies(); v++)
            if (!marked[v] && cycle == null) dfs(G, v);
    }

    // check that algorithm computes either the topological order or finds a directed cycle
    private void dfs(DirectedGraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v)) {

            // short circuit if directed cycle found
            if (cycle != null) return;

                // found new vertex, so recur
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }

            // trace back directed cycle
            else if (onStack[w]) {
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
                assert check();
            }
        }
        onStack[v] = false;
    }

    /**
     * Does the DirectedGraph have a directed cycle?
     * @return {@code true} if the DirectedGraph has a directed cycle, {@code false} otherwise
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a directed cycle if the DirectedGraph has a directed cycle, and {@code null} otherwise.
     * @return a directed cycle (as an iterable) if the DirectedGraph has a directed cycle,
     *    and {@code null} otherwise
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }


    // certify that DirectedGraph has a directed cycle if it reports one
    private boolean check() {

        if (hasCycle()) {
            // verify cycle
            int first = -1, last = -1;
            for (int v : cycle()) {
                if (first == -1) first = v;
                last = v;
            }
            if (first != last) {
                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
            }
        }


        return true;
    }


}
