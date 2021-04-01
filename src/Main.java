import java.util.List;

public class Main {

    public static void main(String[] args) {

        Graph graph1 = Graph.createFromMatrix(new int[][]{
                {0, 0, 1, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 1, 1, 1, 0}
        });

        Graph graph2 = Graph.createFromMatrix(new int[][]{
                {0, 7, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {7, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 0, 0},
                {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {0, 4, 4, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 2, 0, 5, 0, 0, 0},
                {0, 0, 0, 0, 5, 0, 0, 0, 3, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 3, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 4, 4, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 6, 4},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 6, 0, 4},
                {0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 4, 4, 0}
        });

        System.out.println("DFS :");
        graph1.dfs(graph1.findNode("4"), null);

        System.out.println("\nDijskra : ");
        List<Node<?>> path = graph2.dijskra(graph2.findNode("0"), graph2.findNode("5"));

        System.out.println("Chemin : ");
        for (Node<?> node : path) {
            System.out.println(node.getIdentifier());
        }
    }

}
