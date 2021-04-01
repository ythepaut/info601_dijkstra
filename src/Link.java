public class Link {

    private Node<?> from;
    private Node<?> to;
    private int weight;

    public Link(Node<?> from, Node<?> to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Node<?> getFrom() {
        return from;
    }

    public Node<?> getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }
}
