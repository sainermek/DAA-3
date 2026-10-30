package network;

import java.util.*;

public class CityGraph {
    public static class Edge {
        public int source;
        public int destination;
        public double weight;

        public Edge(int source, int destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    private final boolean directed;
    private final Map<Integer, List<Edge>> adjacencyList;
    private final Set<Integer> vertices;

    public CityGraph(boolean directed) {
        this.directed = directed;
        this.adjacencyList = new HashMap<>();
        this.vertices = new HashSet<>();
    }

    public void addVertex(int vertex) {
        vertices.add(vertex);
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(int u, int v, double weight) {
        addVertex(u);
        addVertex(v);
        adjacencyList.get(u).add(new Edge(u, v, weight));
        if (!directed) {
            adjacencyList.get(v).add(new Edge(v, u, weight));
        }
    }

    public List<Edge> getEdges() {
        List<Edge> allEdges = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (Map.Entry<Integer, List<Edge>> entry : adjacencyList.entrySet()) {
            for (Edge edge : entry.getValue()) {
                if (!directed) {
                    String key = Math.min(edge.source, edge.destination) + "-" + Math.max(edge.source, edge.destination);
                    if (seen.add(key)) {
                        allEdges.add(edge);
                    }
                } else {
                    allEdges.add(edge);
                }
            }
        }
        return allEdges;
    }

    public List<Edge> getNeighbors(int vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptyList());
    }

    public Set<Integer> getVertices() {
        return vertices;
    }

    public int getVertexCount() {
        return vertices.size();
    }
}