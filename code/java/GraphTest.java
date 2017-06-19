import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphTest {
    public static void main(String[] args) throws Exception {

        /*
         * Make graphs d and b for DFS and BFS.
         * Based on construction of d and b, and requirements of DFS and BFS,
         * expect output in order from 1 to 12.
         * For diagrams of d and b, see
         * https://en.wikipedia.org/wiki/Depth-first_search and
         * https://en.wikipedia.org/wiki/Breadth-first_search.
         */
        List<Graph.Node> d = new ArrayList<Graph.Node>();
        for (int i = 0; i < 13; i++) {
            d.add(new Graph.Node(Integer.toString(i)));
        }
        d.get(1).neighbors.add(d.get(8));  d.get(1).neighbors.add(d.get(7)); d.get(1).neighbors.add(d.get(2));
        d.get(2).neighbors.add(d.get(6));  d.get(2).neighbors.add(d.get(3));
        d.get(3).neighbors.add(d.get(5));  d.get(3).neighbors.add(d.get(4));
        d.get(8).neighbors.add(d.get(12)); d.get(8).neighbors.add(d.get(9));
        d.get(9).neighbors.add(d.get(11)); d.get(9).neighbors.add(d.get(10));

        List<Graph.Node> b = new ArrayList<Graph.Node>();
        for (int i = 0; i < 13; i++) {
            b.add(new Graph.Node(Integer.toString(i)));
        }
        b.get(1).neighbors.add(b.get(2));  b.get(1).neighbors.add(b.get(3)); b.get(1).neighbors.add(b.get(4));
        b.get(2).neighbors.add(b.get(5));  b.get(2).neighbors.add(b.get(6));
        b.get(4).neighbors.add(b.get(7));  b.get(4).neighbors.add(b.get(8));
        b.get(5).neighbors.add(b.get(9));  b.get(5).neighbors.add(b.get(10));
        b.get(7).neighbors.add(b.get(11)); b.get(7).neighbors.add(b.get(12));

        int[] expectedDepth = {0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3};

        /*
         * Run DFS and BFS on d and b.
         * As explained above, expect output in order from 1 to 12.
         * Also test for depth results from BFS.
         */
        System.out.println("Starting DFS.");
        Graph.dfs(d.get(1));

        System.out.println("\nStarting BFS.");
        Map<Graph.Node, Integer> depth = Graph.bfs(b.get(1));

        for (int i = 1; i < 13; i++) {
            if (depth.get(b.get(i)) != expectedDepth[i]) {
                throw new Exception("Incorrect depth.");
            }
        }
        System.out.println("No problems with BFS depth.");

        /*
         * Make graph g and test Dijkstra's algorithm.
         * See graph example and expected distances on
         * http://www.cse.ust.hk/faculty/golin/COMP271Sp03/Notes/MyL09.pdf
         * page 22.
         */
        List<Graph.Node> g = new ArrayList<Graph.Node>();
        g.add(new Graph.Node("s"));
        g.add(new Graph.Node("a"));
        g.add(new Graph.Node("b"));
        g.add(new Graph.Node("c"));
        g.add(new Graph.Node("d"));
        g.get(0).neighbors.add(g.get(1)); g.get(0).weights.put(g.get(1), 2);
        g.get(0).neighbors.add(g.get(2)); g.get(0).weights.put(g.get(2), 7);
        g.get(1).neighbors.add(g.get(2)); g.get(1).weights.put(g.get(2), 3);
        g.get(1).neighbors.add(g.get(3)); g.get(1).weights.put(g.get(3), 8);
        g.get(1).neighbors.add(g.get(4)); g.get(1).weights.put(g.get(4), 5);
        g.get(2).neighbors.add(g.get(1)); g.get(2).weights.put(g.get(1), 2);
        g.get(2).neighbors.add(g.get(3)); g.get(2).weights.put(g.get(3), 1);
        g.get(3).neighbors.add(g.get(4)); g.get(3).weights.put(g.get(4), 4);
        g.get(4).neighbors.add(g.get(3)); g.get(4).weights.put(g.get(3), 5);

        int[] expectedDistance = {0, 2, 5, 6, 7};

        System.out.println("\nStarting Dijkstra's algorithm.");
        Map<Graph.Node, Integer> distance = Graph.Dijkstra(g.get(0));

        for (int i = 0; i < 5; i++) {
            if (distance.get(g.get(i)) != expectedDistance[i]) {
                throw new Exception("Problem with Dijkstra's algorithm distance.");
            }
        }
        System.out.println("No problems with Dijkstra's algorithm distance.");
    }
}
