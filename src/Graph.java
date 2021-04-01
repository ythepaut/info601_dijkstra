import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Graph {

    private Node<?>[] nodes;

    public Graph(Node<?>[] nodes) {
        this.nodes = nodes;
    }

    public Graph(List<Node<?>> nodes) {
        this.nodes = nodes.toArray(new Node[0]);
    }

    /**
     * Crée un graph à partir de sa representation matricielle.
     * @param matrix        Matrice representant le graph
     * @return Graph
     */
    public static Graph createFromMatrix(int[][] matrix) {
        Node<?>[] nodes = new Node<?>[matrix.length];

        for (int i = 0 ; i < matrix.length ; ++i)
            nodes[i] = new Node<>(String.valueOf(i));

        for (int i = 0 ; i < matrix.length ; ++i) {
            for (int j = 0 ; j < matrix[i].length ; ++j) {
                if (matrix[i][j] > 0) {
                    Link link = new Link(nodes[i], nodes[j], matrix[i][j]);
                    nodes[i].addLinks(link);
                    nodes[j].addLinks(link);
                }
            }
        }

        return new Graph(nodes);
    }

    /**
     * Retourne l'intersection de deux graphs
     * @param graph1        Premier graph
     * @param graph2        Deuxieme graph
     * @return Graph : graph1 ∩ graph2
     */
    public static Graph intersection(Graph graph1, Graph graph2) {
        List<Node<?>> nodes = new ArrayList<>();
        for (Node<?> node : graph1.nodes)
            if (Arrays.asList(graph2.nodes).contains(node))
                nodes.add(node);
        return new Graph(nodes);
    }

    /**
     * Retourne le complement entre deux graphs
     * @param graph1        Premier graph
     * @param graph2        Deuxieme graph
     * @return Graph
     */
    public static Graph complement(Graph graph1, Graph graph2) {
        List<Node<?>> nodes = new ArrayList<>();
        for (Node<?> node : graph1.nodes)
            if (!Arrays.asList(graph2.nodes).contains(node))
                nodes.add(node);
        return new Graph(nodes);
    }

    /**
     * Retourne un noeud selon son nom
     * @return Node|null
     */
    public Node<?> findNode(Object identifier) {
        for (Node<?> node : nodes)
            if (node.getIdentifier().equals(identifier))
                return node;
        return null;
    }

    /**
     * Depth-First Search : Parcours en profondeur
     * @param origin        Noeud à partir du quel on fait la recherche
     * @param occurred      Liste de noeuds déjà parcourus
     */
    void dfs(Node<?> origin, List<Node<?>> occurred) {

        System.out.println(origin.getIdentifier());

        if (occurred == null)
            occurred = new ArrayList<>();
        occurred.add(origin);

        for (Link link : origin.getLinks())
            if (link.getFrom().equals(origin))
                if (!occurred.contains(link.getTo()))
                    dfs(link.getTo(), occurred);
    }

    /**
     * Breadth-first search : Parcours en largeur
     * @param origin        Noeud à partir du quel on fait la recherche
     */
    void bfs(Node<?> origin) {
        // TODO
    }

    /**
     * Algo de Dijskra : Chemin entre deux noeuds
     * @param origin        Noeud d'origine
     * @param destination   Noeud de destination
     * @return Liste des noeuds à parcourir pour passer de l'origine à la destination
     */
    List<Node<?>> dijskra(Node<?> origin, Node<?> destination) {

        // weight[i] représente le poids du noeud i (nodes[i]), -1 pour poids infini (par défaut)
        int[] weights = new int[nodes.length];
        Arrays.fill(weights, -1);

        // pathsBy[i] représente le noeud par lequel le chemin est le plus court pour le noeud i
        Node<?>[] pathsBy = new Node[nodes.length];

        // Sous-graph contenant les noeuds parcourus
        Graph sub = new Graph(new Node<?>[]{});

        // Initialisation du coût à l'origine à 0 (weight[origine] = 0)
        weights[Arrays.asList(nodes).indexOf(origin)] = 0;

        // Tant qu'il existe un noeud qui n'est pas présent dans le sous graph sub
        Graph complement;
        do {
            complement = complement(this, sub);

            // Cherche le noeud de plus petite distance qui n'est pas dans sub
            int nodeIndex = -1;
            for (int i = 0 ; i < weights.length ; ++i)
                if (!Arrays.asList(sub.nodes).contains(nodes[i]))
                    if (nodeIndex == -1 || (weights[i] < weights[nodeIndex] && weights[i] != -1))
                        nodeIndex = i;
            Node<?> shortestNodeA = nodes[nodeIndex];

            // Ajout du noeud le plus "court" dans le sous graph
            sub.addNode(shortestNodeA);

            // Pour tous les sommets voisins du noeud le plus court, sauf les noeuds dans le sous graph sub
            for (Node<?> nodeB : shortestNodeA.getNeighbours()) {
                if (!Arrays.asList(sub.nodes).contains(nodeB)) {

                    int weightA = weights[Arrays.asList(nodes).indexOf(shortestNodeA)];
                    int weightB = weights[Arrays.asList(nodes).indexOf(nodeB)];
                    int weigthAB = nodeB.getLink(shortestNodeA).getWeight();

                    // Si poids du noeud courant > poids noeud le plus court + poids entre les deux noeuds
                    if ((weightB == -1 || weightB > weightA + weigthAB) && weightA != -1) {

                        // Affectation du nouveau poids du noeud B
                        weights[Arrays.asList(nodes).indexOf(nodeB)] = weightA + weigthAB;

                        // Modification du prédecesseur de B, à A
                        pathsBy[Arrays.asList(nodes).indexOf(nodeB)] = shortestNodeA;
                    }
                }
            }

        } while (complement.nodes.length > 1);

        // Debug : Affichage de tous les noeuds avec les poids et chemins
        for (int i = 0 ; i < weights.length ; ++i) {
            System.err.println(nodes[i].getIdentifier() + "\t : " + weights[i] + "\tby " + (pathsBy[i] != null ? pathsBy[i].getIdentifier() : "-"));
        }

        // Construction du chemin
        List<Node<?>> path = new ArrayList<>();
        Node<?> currentNode = destination;
        do {
            path.add(currentNode);
            currentNode = pathsBy[Arrays.asList(nodes).indexOf(currentNode)];
        } while (!currentNode.equals(origin));
        path.add(origin);

        return path;
    }

    /**
     * Ajoute un noeud au graph
     * @param node      Noeud à ajotuer
     */
    public void addNode(Node<?> node) {
        Node<?>[] newNodes = new Node<?>[nodes.length + 1];
        for (int i = 0 ; i < nodes.length ; ++i) {
            newNodes[i] = nodes[i];
        }
        newNodes[nodes.length] = node;
        nodes = newNodes;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(hashCode() + "\t- ");
        for (Node<?> node : nodes)
            string.append(node.getIdentifier()).append(" ");
        return string.toString();
    }
}
