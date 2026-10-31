package components;

import network.CityGraph;
import java.util.*;

public class TarjanSCC {
    private int timer;
    private int[] tin, low;
    private boolean[] onStack;
    private Stack<Integer> stack;
    private List<List<Integer>> sccList;
    private Map<Integer, Integer> vertexToIndex;
    private Map<Integer, Integer> indexToVertex;

    public List<List<Integer>> findSCCs(CityGraph graph) {
        Set<Integer> vertices = graph.getVertices();
        int n = vertices.size();
        tin = new int[n];
        low = new int[n];
        onStack = new boolean[n];
        stack = new Stack<>();
        sccList = new ArrayList<>();
        vertexToIndex = new HashMap<>();
        indexToVertex = new HashMap<>();

        int idx = 0;
        for (int v : vertices) {
            vertexToIndex.put(v, idx);
            indexToVertex.put(idx, v);
            tin[idx] = -1;
            idx++;
        }

        timer = 0;
        for (int i = 0; i < n; i++) {
            if (tin[i] == -1) {
                dfs(i, graph);
            }
        }
        return sccList;
    }

    private void dfs(int uIdx, CityGraph graph) {
        tin[uIdx] = low[uIdx] = timer++;
        stack.push(uIdx);
        onStack[uIdx] = true;

        int u = indexToVertex.get(uIdx);
        for (CityGraph.Edge edge : graph.getNeighbors(u)) {
            int v = edge.destination;
            int vIdx = vertexToIndex.get(v);

            if (tin[vIdx] == -1) {
                dfs(vIdx, graph);
                low[uIdx] = Math.min(low[uIdx], low[vIdx]);
            } else if (onStack[vIdx]) {
                low[uIdx] = Math.min(low[uIdx], tin[vIdx]);
            }
        }

        if (low[uIdx] == tin[uIdx]) {
            List<Integer> scc = new ArrayList<>();
            while (true) {
                int vIdx = stack.pop();
                onStack[vIdx] = false;
                scc.add(indexToVertex.get(vIdx));
                if (vIdx == uIdx) break;
            }
            sccList.add(scc);
        }
    }
}