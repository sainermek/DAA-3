# Assignment 3 — Smart City Infrastructure and Route Planning

Hi! This is the project for Assignment 3. It models a smart city's road network and infrastructure in Java.

The program implements core graph algorithms: minimum spanning trees, connectivity checks, topological sorting, and shortest path calculations.

## Project Structure

The code is organized into packages:
- `src/network/` — Graph data structure (`CityGraph`) using an Adjacency List.
- `src/unionfind/` — DSU (Disjoint Set Union) with path compression and rank optimizations.
- `src/spanningtree/` — Minimum Spanning Tree (MST) algorithms: Kruskal's (`KruskalMST`) and Prim's (`PrimMST`).
- `src/components/` — Topological sort (`TopologicalSort`) and Tarjan's Strongly Connected Components (`TarjanSCC`).
- `src/routing/` — Classic Dijkstra's algorithm (`DijkstraRouter`) for shortest paths.
- `src/benchmark/` — `BenchmarkingRunner` class for running tests and measuring execution time.

## Implementation Highlights:
1. **Graph & DSU:** Core classes to represent city districts as vertices and roads as weighted edges.
2. **MST (Kruskal & Prim):** Finds the cheapest way to connect all districts. Both algorithms yield the same total weight.
3. **Connectivity:** Identifies strongly connected components (SCC) and checks for cycles (required for topological sorting).
4. **Dijkstra:** Computes shortest distances from a selected source district to all other reachable districts (with non-negative edge weights).
5. **Experiments:** Benchmarks algorithms across three graph configurations (Small, Medium, Large) running 5 times each, outputting average execution times in milliseconds.

## How to Run:
1. Open the project in **IntelliJ IDEA**.
2. Navigate to `src/benchmark/BenchmarkingRunner.java`.
3. Click the green **Run** button on the `main` method.
4. View the algorithmic results and execution times in the console.