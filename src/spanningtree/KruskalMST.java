package spanningtree;

import network.CityGraph;
import unionfind.DisjointSetUnion;
import java.util.*;

public class KruskalMST {
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

    public Result computeMST(CityGraph graph, int maxVertexId) {
        List<CityGraph.Edge> edges = graph.getEdges();
        edges.sort(Comparator.comparingDouble(e -> e.weight));

        DisjointSetUnion dsu = new DisjointSetUnion(maxVertexId + 1);
        List<CityGraph.Edge> mst = new ArrayList<>();
        double totalWeight = 0;
        long ops = 0;

        for (CityGraph.Edge edge : edges) {
            ops++;
            int rootU = dsu.find(edge.source);
            int rootV = dsu.find(edge.destination);
            ops += 2;

            if (rootU != rootV) {
                dsu.union(edge.source, edge.destination);
                mst.add(edge);
                totalWeight += edge.weight;
                ops++;
            }
        }
        return new Result(mst, totalWeight, ops);
    }
}