package com.company;

import java.util.LinkedList;
import java.util.*;
public class MST {

    private static final int V = 20;

    int minKey(int[] key, Boolean[] mstSet)
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (mstSet[v] == false && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    void printMST(int[] parent, int[][] graph)
    {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < V; i++) {
            System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
        }
    }

    int[] primMST(int[][] graph)
    {
        // Array to store constructed MST
        int[] parent = new int[V];

        // Key values used to pick minimum weight edge in cut
        int[] key = new int[V];

        // To represent set of vertices included in MST
        Boolean[] mstSet = new Boolean[V];

        // Initialize all keys as INFINITE
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        // Always include first 1st vertex in MST.
        key[0] = 0; // Make key 0 so that this vertex is
        // picked as first vertex
        parent[0] = -1; // First node is always root of MST

        // The MST will have V vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick thd minimum key vertex from the set of vertices
            // not yet included in MST
            int u = minKey(key, mstSet);

            // Add the picked vertex to the MST Set
            mstSet[u] = true;

            // Update key value and parent index of the adjacent
            // vertices of the picked vertex. Consider only those
            // vertices which are not yet included in MST
            for (int v = 0; v < V; v++)

                // graph[u][v] is non zero only for adjacent vertices of m
                // mstSet[v] is false for vertices not yet included in MST
                // Update the key only if graph[u][v] is smaller than key[v]
                if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }
        // print the constructed MST
        return parent;
    }

    void NewMst(int[][] graph)
    {
        List<List<Integer>> adjListArray = convert(graph);
        List<Integer> VofCycle=isCyclic(adjListArray);

        List<Edge> ed=new ArrayList<>();
        Edge temp=null;
        int i;
        for (i = 0; i < VofCycle.size()-1; i++) {
            temp=new Edge(VofCycle.get(i),VofCycle.get(i+1),graph[VofCycle.get(i)][VofCycle.get(i+1)]);
            ed.add(temp);
        }
        temp=new Edge(VofCycle.get(VofCycle.size()-1),VofCycle.get(0),graph[VofCycle.get(VofCycle.size()-1)][VofCycle.get(0)]);
        ed.add(temp);
        int max=0;
        int indexI=0;
        int indexJ=0;
        for (int j = 0; j < ed.size(); j++) {
            if (max<ed.get(j).getWeight()) {
                max = ed.get(j).getWeight();
                indexI = ed.get(j).getDest();
                indexJ = ed.get(j).getSource();
            }
        }
        removeEdge(graph,max,indexI,indexJ);
        Main.printGraph(graph);

    }
    public static List<List<Integer>> convert(int[][] graph) {
        int l = graph[0].length;
        List<List<Integer>> adjListArray = new ArrayList<>(l);

        // Create a new list for each
        // vertex such that adjacent
        // nodes can be stored
        for (int i = 0; i < l; i++) {
            adjListArray.add(new LinkedList<>());
        }

        for (int i = 0; i < graph[0].length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] != 0) {
                    adjListArray.get(i).add(j);
                }
            }
        }

        return adjListArray;
    }

    Boolean isCyclicUtil(int v, Boolean[] visited, int parent, List<List<Integer>> adjListArray,List<Integer> listCycle) {
        // Mark the current node as visited
        visited[v] = true;
        Integer i;

        // Recur for all the vertices
        // adjacent to this vertex
        Iterator<Integer> it = adjListArray.get(v).iterator();
        while (it.hasNext()) {
            i = it.next();

            // If an adjacent is not
            // visited, then recur for that
            // adjacent
            if (!visited[i]) {
                if (isCyclicUtil(i, visited, v, adjListArray, listCycle)) {
                    listCycle.add(i);
                    return true;
                }
            }

            // If an adjacent is visited
            // and not parent of current
            // vertex, then there is a cycle.
            else if (i != parent)
                return true;
        }
        return false;
    }

    // Returns true if the graph
    // contains a cycle, else false.
    List<Integer> isCyclic(List<List<Integer>> adjListArray) {
        List<Integer> SaveCycle=new ArrayList<>();
        // Mark all the vertices as
        // not visited and not part of
        // recursion stack
        Boolean[] visited = new Boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;

        // Call the recursive helper
        // function to detect cycle in
        // different DFS trees
        for (int u = 0; u < V; u++) {

            // Don't recur for u if already visited
            if (!visited[u])
                if (isCyclicUtil(u, visited, -1,adjListArray,SaveCycle)) {
                    SaveCycle.add(u);
                    break;
                }
        }
        return SaveCycle;
    }

    public void removeEdge(int[][] graph,int max,int i,int j){
        graph[i][j]=0;
        graph[j][i]=0;
    }

    public class Edge {
        int source,dest,weight;
        public Edge(int source, int dest,int weight)
        {
            this.source = source;
            this.dest = dest;
            this.weight=weight;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getDest() {
            return dest;
        }

        public void setDest(int dest) {
            this.dest = dest;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
