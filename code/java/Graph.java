import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Graph {

    public static class Node {
        public String name;
        public List<Node> neighbors;
        public Map<Node, Integer> weights;
        public Node(String name) {
            this.name = name;
            this.neighbors = new ArrayList<Node>();
            this.weights = new HashMap<Node, Integer>();
        }
    }

    private static void visitNode(Node node) {
        System.out.println(node.name);
    }

    public static void dfs(Node root) {
        Stack<Node> frontier = new Stack<Node>();
        Map<Node, Integer> distance = new HashMap<Node, Integer>();
        frontier.push(root);
        distance.put(root, 0);
        while (!frontier.isEmpty()) {
            Node curr = frontier.pop();
            for (Node neighbor : curr.neighbors) {
                if (distance.get(neighbor) == null) {
                    frontier.push(neighbor);
                    // Distance is used as a flag for DFS.
                    distance.put(neighbor, 0);
                }
            }
            visitNode(curr);
        }
    }

    public static Map<Node, Integer> bfs(Node root) {
        Queue<Node> frontier = new ArrayDeque<Node>();
        Map<Node, Integer> distance = new HashMap<Node, Integer>();
        frontier.add(root);
        distance.put(root,  0);
        while (!frontier.isEmpty()) {
            Node curr = frontier.remove();
            for (Node neighbor : curr.neighbors) {
                if (distance.get(neighbor) == null) {
                    frontier.add(neighbor);
                    // Distance is meaningful for BFS.
                    distance.put(neighbor, distance.get(curr) + 1);
                }
            }
            visitNode(curr);
        }
        return distance;
    }

    public static class Pair implements Comparable<Pair> {
        public Node node;
        public int distance;
        public Pair(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
        @Override
        public int compareTo(Pair p) {
            return this.distance - p.distance;
        }
    }

    public static Map<Node, Integer> Dijkstra(Node root) {
        PriorityQueue<Pair> frontier = new PriorityQueue<Pair>();
        Map<Node, Integer> distance = new HashMap<Node, Integer>();
        frontier.add(new Pair(root, 0));
        distance.put(root, 0);
        while (!frontier.isEmpty()) {
            Pair curr = frontier.remove();
            if (curr.distance > distance.get(curr.node)) continue;
            for (Node neighbor : curr.node.neighbors) {
                int distNew = curr.distance + curr.node.weights.get(neighbor);
                if (!distance.containsKey(neighbor)) {
                    frontier.add(new Pair(neighbor, distNew));
                    distance.put(neighbor, distNew);
                } else if (distNew < distance.get(neighbor)) {
                    frontier.add(new Pair(neighbor, distNew));
                    distance.put(neighbor, distNew);
                }
            }
        }
        return distance;
    }
}
