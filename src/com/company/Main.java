package com.company;

//Name:Noam Meir ID:206379216
//Name:Gal Levy ID:315828954

import java.util.Random;

public class Main {

    static int N=20;

    public static void main(String[] args) {
        MST t = new MST();
        Random rand = new Random();
        int[][] graph = new int[N][N];
        for (int i = 0; i < N ; i++) {
            for (int j = i+1 ; j < N; j++) {
               graph[i][j] = 10+rand.nextInt(15);
               graph[j][i] = graph[i][j];
            }
        }
        System.out.println("this matrix represent the original graph: ");
        printGraph(graph);
        System.out.println();

        int[] parents=t.primMST(graph);
        Minimaltree(parents,graph);//create new graph minimal trees

        System.out.println("this matrix represent the minimal set tree graph: ");
        printGraph(graph);
        System.out.println();

        System.out.println("the next matrices represent the minimal set tree graph with a spare edge  : ");
        System.out.println("this matrix with spare edge that doesn't change the minimal set tree graph:");
        int max = getMax(graph);
        AddEdge(rand, max +1 ,parents,graph);
        t.NewMst(graph);
        System.out.println();

        System.out.println("this matrix with spare edge that does change the minimal set tree graph:");
        AddEdge(rand,1 ,parents,graph);
        t.NewMst(graph);
        System.out.println();



    }

    public static int getMax(int[][] graph) {
        int maxWieght = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if(maxWieght < graph[i][j])
                    maxWieght = graph[i][j];
            }
        }
        return maxWieght;
    }
    public static void printGraph(int[][] graph) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(graph[i][j]+ " ");
            }
            System.out.println();
        }
    }


    public static void Minimaltree(int[] parents,int[][]graph)
    {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i!=parents[j]&&j!=parents[i])
                    graph[i][j]=0;
            }
        }
    }



    public static void AddEdge(Random rand, int weight , int[] parents, int[][] graph)
    {
        boolean flag=false;
        do {
            int indexI=1+rand.nextInt(N-1);
            int indexJ=1+rand.nextInt(N-1);
            if (graph[indexI][indexJ]!=weight && indexI!=indexJ)
            {
                if (indexI!=parents[indexJ]) {
                    graph[indexI][indexJ]=weight;
                    graph[indexJ][indexI]=weight;
                    flag = true;
                    System.out.println("The new edge is (" + indexI +"," + indexJ + ") with weight of " + weight);
                    System.out.println();
                }
            }
        }while (!flag);
    }

}
