package data.pipeline.api.collections;

/**
 *
 *
 *
 * @author Ravi Chiripurapu
 *
 */
public class Edge {
    int from;
    int to;


    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (from != edge.from) return false;
        return to == edge.to;
    }

    @Override
    public int hashCode() {
        int result = from;
        result = 31 * result + to;
        return result;
    }
}
