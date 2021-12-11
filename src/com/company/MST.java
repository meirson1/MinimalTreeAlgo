package com.company;

import java.util.LinkedList;
import java.util.*;
public class MST {

    private static final int V = 4;

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
        printMST(parent, graph);
        return parent;
    }

    void NewMst(int[] parents, int[][] graph, int[] saveIndexI, int[] saveIndexJ)
    {
        List<List<Integer>> adjListArray = convert(graph);
        //int max=0;
        //int indexI=0;
        //int indexJ=0;
        List<Edge> ed=new ArrayList<>();
        Edge temp=null;
        for (int i = 1; i < parents.length; i++) {
            temp=new Edge(parents[i],i,graph[i][parents[i]]);
            ed.add(temp);
        }
        temp=new Edge(saveIndexI[0],saveIndexJ[0],graph[saveIndexI[0]][saveIndexJ[0]]);
        //ed.add(temp);

        for (int i = 0; i < ed.size(); i++) {//switch
            if (temp.getDest()==ed.get(i).getSource()||temp.getSource()==ed.get(i).getDest()){//switch source with des
                int tempS=ed.get(i).getDest();
                ed.get(i).setDest(ed.get(i).getSource());
                ed.get(i).setSource(tempS);
            }
        }

//        for (int i = 0; i < ed.size(); i++) {
//            if (max<ed.get(i).weight) {
//                max = ed.get(i).weight;
//                indexI=ed.get(i).dest;
//                indexJ=ed.get(i).source;
//            }
//        }
//        removeEdge(graph,max,indexI,indexJ);
//        String[][] color=new String[V][V];
//        for (int i = 0; i < V; i++) {
//            for (int j = i+1; j < V; j++) {
//                if (graph[i][j]!=0) {
//                    color[i][j] = "white";
//                    color[j][i] = "white";
//                }
//            }
//        }
//        color[0][0]="gray";
//        int max=0;
//        for (int i = 0; i < adjListArray.size(); i++) {
//            for (int j = 0; j < adjListArray.get(i).size(); j++) {
//                if (color[adjListArray.get(i).get(j)][adjListArray.get(j).get(i)].equals("white")) {
//                    color[i][j] = "gray";
//                } else if (color[i][j] == "gray") {
//                    while (graph[i][j] != parents[i]) {
//                        if (max < graph[i][j])
//                            max = graph[i][j];
//                    }
//                    break;
//                }
//            }
//        }
//        LinkedList<Integer> circle=FindCircleDfs(adjListArray);
//        for (int i = 0; i < circle.size(); i++) {
//            if (max > graph[circle.get(i)][circle.get(i+1)]) {
//                max = graph[circle.get(i)][circle.get(i+1)];
//                indexI=circle.get(i);
//                indexJ=circle.get(i+1);
//            }
//        }
        printGraph(graph);
    }

    private void printGraph(int[][] graph) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.println(graph[i][j]);
            }
        }
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

    public LinkedList<Integer> FindCircleDfs(ArrayList<ArrayList<Integer>> adjListArray) {
        LinkedList<Integer> circle=new LinkedList<Integer>();
        circle.add(0);
        int i=0;
        int j=0;
        while (adjListArray.get(i).get(j)!=null) {
            if (!circle.contains(adjListArray.get(i).get(j))) {
                circle.add(adjListArray.get(i).get(j));
                i=adjListArray.get(i).get(j);
            }else
                j++;
        }
        return circle;
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
