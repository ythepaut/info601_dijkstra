import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node<T> {

    private T identifier;
    private List<Link> links;

    public Node(T identifier) {
        this(identifier, new ArrayList<>());
    }

    public Node(T identifier, List<Link> links) {
        this.identifier = identifier;
        this.links = links;
    }

    public List<Node<?>> getNeighbours() {
        List<Node<?>> neighbours = new ArrayList<>();
        for (Link link : links)
            if (link.getFrom().equals(this))
                neighbours.add(link.getTo());
        return neighbours;
    }

    public void addLinks(Link... links) {
        Collections.addAll(this.links, links);
    }

    public Link getLink(Node<?> to) {
        for (Link link : links)
            if (link.getTo().equals(to))
                return link;
        return null;
    }

    public T getIdentifier() {
        return identifier;
    }

    public List<Link> getLinks() {
        return links;
    }
}
