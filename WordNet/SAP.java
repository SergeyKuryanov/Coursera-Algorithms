import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph digraph;

    private class SAPResult {
        int ancestor;
        int length;

        public SAPResult(int ancestor, int length) {
            this.ancestor = ancestor;
            this.length = length;
        }
    }

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        
        digraph = new Digraph(G);
    }
 
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkArguments(v, w);

        return findSAP(v, w).length;
    }
 
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        checkArguments(v, w);

        return findSAP(v, w).ancestor;
    }
 
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkArguments(v, w);

        return findSAP(v, w).length;
    }
 
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkArguments(v, w);

        return findSAP(v, w).ancestor;
    }
 
    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private void checkArguments(int v, int w) {
        if (v < 0 || w < 0 || v > digraph.V() - 1 || w > digraph.V() - 1) {
            throw new IllegalArgumentException();
        }
    }

    private void checkArguments(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }

        for (int value : v) {
            if (value > digraph.V() - 1) {
                throw new IllegalArgumentException();
            }
        }

        for (int value : w) {
            if (value > digraph.V() - 1) {
                throw new IllegalArgumentException();
            }
        }
    }

    private SAPResult findSAP(int v, int w) {
        BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(digraph, w);

        return findSAP(vBFS, wBFS);
    }

    private SAPResult findSAP(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(digraph, w);

        return findSAP(vBFS, wBFS);
    }

    private SAPResult findSAP(BreadthFirstDirectedPaths vBFS, BreadthFirstDirectedPaths wBFS) {
        SAPResult result = new SAPResult(-1, -1);

        for (int i = 0; i < digraph.V(); i++) {
            if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
                int length = vBFS.distTo(i) + wBFS.distTo(i);
                if (result.length == -1 || result.length > length) {
                    result.length = length;
                    result.ancestor = i;
                }
            }
        }

        return result;
    }
 }