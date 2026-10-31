package spanningtree;

import network.CityGraph;
import java.util.*;

public class PrimMST {
    public static class Result {
        public List<CityGraph.Edge> edges;
        public double totalWeight;
        public long operations;

        public Result(List<CityGraph.Edge> edges, double totalWeight, long operations) {
            this.edges = edges;
            this.totalWeight = totalWeight;
            this.operations = operations;
        }
    }

    public Result computeMST(CityGraph graph, int startVertex) {
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<CityGraph.Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));
        List<CityGraph.Edge> mst = new ArrayList<>();
        double totalWeight = 0;
        long ops = 0;

        visited.add(startVertex);
        for (CityGraph.Edge edge : graph.getNeighbors(startVertex)) {
            pq.add(edge);
            ops++;
        }

        while (!pq.isEmpty()) {
            ops++;
            CityGraph.Edge edge = pq.poll();
            if (visited.contains(edge.destination)) {
                continue;
            }

            visited.add(edge.destination);
            mst.add(edge);
            totalWeight += edge.weight;

            for (CityGraph.Edge nextEdge : graph.getNeighbors(edge.destination)) {
                ops++;
                if (!visited.contains(nextEdge.destination)) {
                    pq.add(nextEdge);
                }
            }
        }
        return new Result(mst, totalWeight, ops);
    }
}