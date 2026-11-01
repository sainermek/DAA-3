package benchmark;

import network.CityGraph;
import spanningtree.KruskalMST;
import spanningtree.PrimMST;
import components.TopologicalSort;
import components.TarjanSCC;
import routing.DijkstraRouter;

import java.util.*;

public class BenchmarkingRunner {
    public static void main(String[] args) {
        System.out.println("=== Smart City Infrastructure & Route Planning - Benchmarks ===");

        runBenchmark("Small Graph (10 vertices, 30 edges)", 10, 30, 42);
        runBenchmark("Medium Graph (100 vertices, 500 edges)", 100, 500, 42);
        runBenchmark("Large Graph (1,000 vertices, 5,000 edges)", 1000, 5000, 42);
    }

    private static void runBenchmark(String label, int vertices, int edgesCount, long seed) {
        System.out.println("\n--- " + label + " ---");
        Random random = new Random(seed);


        CityGraph undirectedGraph = new CityGraph(false);
        for (int i = 0; i < vertices; i++) {
            undirectedGraph.addVertex(i);
        }

        for (int i = 1; i < vertices; i++) {
            double weight = 1 + random.nextDouble() * 50;
            undirectedGraph.addEdge(i - 1, i, weight);
        }
        // Добавим остальные случайные ребра
        int addedEdges = vertices - 1;
        while (addedEdges < edgesCount) {
            int u = random.nextInt(vertices);
            int v = random.nextInt(vertices);
            if (u != v) {
                double weight = 1 + random.nextDouble() * 50;
                undirectedGraph.addEdge(u, v, weight);
                addedEdges++;
            }
        }

        int runs = 5;
        long kruskalTimeTotal = 0, kruskalOpsTotal = 0;
        long primTimeTotal = 0, primOpsTotal = 0;
        long dijkstraTimeTotal = 0;
        long sccTimeTotal = 0;

        KruskalMST kruskal = new KruskalMST();
        PrimMST prim = new PrimMST();
        TarjanSCC tarjan = new TarjanSCC();
        DijkstraRouter dijkstra = new DijkstraRouter();

        for (int r = 0; r < runs; r++) {
            // Kruskal
            long start = System.nanoTime();
            KruskalMST.Result kRes = kruskal.computeMST(undirectedGraph, vertices - 1);
            kruskalTimeTotal += (System.nanoTime() - start);
            kruskalOpsTotal += kRes.operations;

            // Prim
            start = System.nanoTime();
            PrimMST.Result pRes = prim.computeMST(undirectedGraph, 0);
            primTimeTotal += (System.nanoTime() - start);
            primOpsTotal += pRes.operations;

            // Tarjan SCC
            start = System.nanoTime();
            tarjan.findSCCs(undirectedGraph);
            sccTimeTotal += (System.nanoTime() - start);

            // Dijkstra
            start = System.nanoTime();
            dijkstra.findShortestPaths(undirectedGraph, 0);
            dijkstraTimeTotal += (System.nanoTime() - start);
        }

        System.out.println("Kruskal's MST -> Avg Time: " + (kruskalTimeTotal / runs / 1_000_000.0) + " ms | Ops: " + (kruskalOpsTotal / runs));
        System.out.println("Prim's MST   -> Avg Time: " + (primTimeTotal / runs / 1_000_000.0) + " ms | Ops: " + (primOpsTotal / runs));
        System.out.println("Tarjan's SCC -> Avg Time: " + (sccTimeTotal / runs / 1_000_000.0) + " ms");
        System.out.println("Dijkstra     -> Avg Time: " + (dijkstraTimeTotal / runs / 1_000_000.0) + " ms");
    }
}