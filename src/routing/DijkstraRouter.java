package routing;

import network.CityGraph;
import java.util.*;

public class DijkstraRouter {
    public static class PathResult {
        public Map<Integer, Double> distances;
        public Map<Integer, Integer> predecessors;

        public PathResult(Map<Integer, Double> distances, Map<Integer, Integer> predecessors) {
            this.distances = distances;
            this.predecessors = predecessors;
        }
    }

    public PathResult findShortestPaths(CityGraph graph, int source) {
        Map<Integer, Double> distances = new HashMap<>();
        Map<Integer, Integer> predecessors = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.distance));

        for (int v : graph.getVertices()) {
            distances.put(v, Double.MAX_VALUE);
        }
        distances.put(source, 0.0);
        pq.add(new NodeDistance(source, 0.0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            int u = current.vertex;

            if (current.distance > distances.get(u)) {
                continue;
            }

            for (CityGraph.Edge edge : graph.getNeighbors(u)) {
                int v = edge.destination;
                double weight = edge.weight;

                if (distances.get(u) + weight < distances.get(v)) {
                    double newDist = distances.get(u) + weight;
                    distances.put(v, newDist);
                    predecessors.put(v, u);
                    pq.add(new NodeDistance(v, newDist));
                }
            }
        }
        return new PathResult(distances, predecessors);
    }

    private static class NodeDistance {
        int vertex;
        double distance;

        NodeDistance(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
}