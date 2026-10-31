package components;

import network.CityGraph;
import java.util.*;

public class TopologicalSort {
    public List<Integer> sort(CityGraph graph) {
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int v : graph.getVertices()) {
            inDegree.put(v, 0);
        }

        for (int v : graph.getVertices()) {
            for (CityGraph.Edge edge : graph.getNeighbors(v)) {
                inDegree.put(edge.destination, inDegree.getOrDefault(edge.destination, 0) + 1);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<Integer> topologicalOrder = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            topologicalOrder.add(u);

            for (CityGraph.Edge edge : graph.getNeighbors(u)) {
                int v = edge.destination;
                inDegree.put(v, inDegree.get(v) - 1);
                if (inDegree.get(v) == 0) {
                    queue.add(v);
                }
            }
        }

        if (topologicalOrder.size() != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle! Topological sort not possible.");
        }

        return topologicalOrder;
    }
}