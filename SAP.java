import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public final class SAP {
    private final Bag<Integer>[] st;
    private final Digraph graph;



    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        st = new Bag[G.V()];
        graph = new Digraph(G.V());
        int v = G.V();
        for (int i = 0; i < v; i++) {
            Iterable<Integer> tr = G.adj(i);
            for (int j : tr) {
                graph.addEdge(i, j);
            }
        }
        for (int i = 0; i < G.V(); i++) {
            Iterable<Integer> tt = graph.adj(i);
            if (st[i] == null) {
                st[i] = new Bag<>();
            }
            for (int j : tt) {
                if (st[j] == null) {
                    st[j] = new Bag<>();
                }
                st[j].add(i);
            }
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        Queue<Integer> que = new Queue<>();
        boolean[] arr = new boolean[graph.V()];
        BreadthFirstDirectedPaths bt1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bt2 = new BreadthFirstDirectedPaths(graph, w);
        que.enqueue(v);
        que.enqueue(w);
        int max = Integer.MAX_VALUE;
        while (!que.isEmpty()) {
            int x = que.dequeue();
            if (!arr[x]) {
                arr[x] = true;
                if ((!bt1.hasPathTo(x) || !bt2.hasPathTo(x))) {
                    for (int i : graph.adj(x)) {
                        que.enqueue(i);
                    }
                    for (int i : st[x]) {
                        que.enqueue(i);
                    }
                } else {
                    int trt = bt1.distTo(x) + bt2.distTo(x);
                    if (trt < max) {
                        max = trt;
                    }
                    for (int i : graph.adj(x)) {
                        que.enqueue(i);
                    }
                    for (int i : st[x]) {
                        que.enqueue(i);
                    }
                }
            }
        }
        if (max != Integer.MAX_VALUE) {
            return max;
        } else return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        Queue<Integer> que = new Queue<>();
        boolean[] arr = new boolean[graph.V()];
        BreadthFirstDirectedPaths bt1 = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bt2 = new BreadthFirstDirectedPaths(graph, w);
        int anc = -1;
        int max = Integer.MAX_VALUE;
        que.enqueue(v);
        que.enqueue(w);
        while (!que.isEmpty()) {
            int x = que.dequeue();
            if (!arr[x]) {
                arr[x] = true;
                boolean bt11 = bt1.hasPathTo(x);
                boolean bt22 = bt2.hasPathTo(x);
                if (!bt11 || !bt22) {
                    for (int i : graph.adj(x)) {
                        que.enqueue(i);
                    }
                    for (int i : st[x]) {
                        que.enqueue(i);
                    }
                } else {
                    int yj = bt1.distTo(x) + bt2.distTo(x);
                    if (yj < max) {
                        max = yj;
                        anc = x;
                    }
                    for (int i : graph.adj(x)) {
                        que.enqueue(i);
                    }
                    for (int i : st[x]) {
                        que.enqueue(i);
                    }
                }

            }
        }
        return anc;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v != null && w != null) {
            BreadthFirstDirectedPaths bt1;
            BreadthFirstDirectedPaths bt2;
            Queue<Integer> que = new Queue<>();
            boolean[] tt = new boolean[graph.V()];
            for (Integer i : v) {
                if(i!=null&&i>=0&&i<graph.V())
                    que.enqueue(i);
                else throw new IllegalArgumentException("Item was null");
            }
            for (Integer i : w) {
                if(i!=null&&i>=0&&i<graph.V())
                    que.enqueue(i);
                else throw new IllegalArgumentException("item was null");
            }
            try {
                bt1  = new BreadthFirstDirectedPaths(graph, v);
                bt2 = new BreadthFirstDirectedPaths(graph, w);
            }
            catch(IllegalArgumentException e){
                return -1;
            }
            int max = Integer.MAX_VALUE;
            while (!que.isEmpty()) {
                int i = que.dequeue();
                if (!tt[i]) {
                    tt[i] = true;
                    if (bt1.hasPathTo(i) && bt2.hasPathTo(i)) {
                        int x = bt1.distTo(i);
                        int y = bt2.distTo(i);
                        if (x + y < max) {
                            max = x + y;
                        }

                    }
                    for (int j : graph.adj(i)) {
                        que.enqueue(j);
                    }
                    for (int j : st[i]) {
                        que.enqueue(j);
                    }


                }
            }
            return max;
        } else throw new IllegalArgumentException("Item is null");


    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v != null && w != null) {
            Queue<Integer> que = new Queue<>();
            boolean[] arr = new boolean[graph.V()];
            BreadthFirstDirectedPaths bt1;
            BreadthFirstDirectedPaths bt2;
            int anc = -1;
            for (Integer i : v) {
                if(i!=null&&i>=0&&i<graph.V())
                    que.enqueue(i);
                else throw new IllegalArgumentException("Item was null");
            }
            for (Integer i : w) {
                if(i!=null&&i>=0&&i<graph.V())
                    que.enqueue(i);
                else throw new IllegalArgumentException("Item was null");
            }
            try {
               bt1  = new BreadthFirstDirectedPaths(graph, v);
                bt2 = new BreadthFirstDirectedPaths(graph, w);
            }
            catch(IllegalArgumentException e){
                return -1;
            }
            int max = Integer.MAX_VALUE;
            while (!que.isEmpty()) {
                int x = que.dequeue();
                if (!arr[x]) {
                    arr[x] = true;
                    int xj = bt1.distTo(x) + bt2.distTo(x);
                    if (xj < max && bt1.hasPathTo(x) && bt2.hasPathTo(x)) {
                        max = xj;
                        anc = x;
                    }
                    for (int i : graph.adj(x)) {
                        que.enqueue(i);
                    }
                    for (int i : st[x]) {
                        que.enqueue(i);
                    }


                }
            }
            return anc;


        }
        else throw new IllegalArgumentException("Item is null");
    }
    // do unit testing of this class
    public static void main(String[] args){
            In in = new In(args[0]);
            Digraph G = new Digraph(in);
            SAP sap = new SAP(G);
        ArrayList<Integer> tt = new ArrayList<>();
        tt.add(0);
        tt.add(1);
        tt.add(7);
        tt.add(9);
        tt.add(12);
        ArrayList<Integer> tr = new ArrayList<>();
        tt.add(1);
        tt.add(2);
        tt.add(4);
        tt.add(null);
        tt.add(10);
        int length = sap.length(tt, tr);
        int ancestor = sap.ancestor(tt, tr);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//            while (!StdIn.isEmpty()) {
//                String[] v = StdIn.readLine().split(", ");
//                String[] w = StdIn.readLine().split(", ");
////                if(v.length!=1&&w.length!=1) {
//                    Bag<Integer> vv = new Bag<>();
//                    Bag<Integer> ww = new Bag<>();
//                    for (String i : v) {
//                        vv.add(Integer.parseInt(i));
//                    }
//                    for (String i : w) {
//                        ww.add(Integer.parseInt(i));
//                    }
//                    int length = sap.length(vv, ww);
//                    int ancestor = sap.ancestor(vv, ww);
//                    StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
////                }
////                int length = sap.length(Integer.parseInt(v[0]),Integer.parseInt(w[0]));
////                int ancestor = sap.ancestor(Integer.parseInt(v[0]), Integer.parseInt(w[0]));
////                StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//            }

    }
}