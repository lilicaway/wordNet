
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/*
 * Note: All methods should throw a java.lang.IllegalArgumentException if any argument is null or if
 * any argument vertex is invalid—not between 0 and G.V() - 1.
 */
public class SAP {

  private final Digraph digraph;

  /**
   * This is the constructor that takes a digraph (not necessarily a DAG)
   * 
   * @param G
   */
  public SAP(Digraph G) {
    if (G == null) {
      throw new IllegalArgumentException("Graph Argument cannot be null");
    }
    digraph = new Digraph(G);
  }

  /**
   * Returns the length of shortest ancestral path between v and w; -1 if no such path
   * 
   * @param v
   * @param w
   * @return
   */
  public int length(int v, int w) {
    if (!isValidVertexId(v) || !isValidVertexId(w)) {
      throw new IllegalArgumentException();
    }

    BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

    List<Integer> graphResult = getCommonAncestorAndShortestLength(bfsV, bfsW);

    // In pos 1 is stored the shortestLength
    return graphResult.get(1);
  }

  /**
   * Returns a common ancestor of v and w that participates in a shortest ancestral path; -1 if no
   * such path
   * 
   * @param v
   * @param w
   * @return
   */
  public int ancestor(int v, int w) {
    if (!isValidVertexId(v) || !isValidVertexId(w)) {
      throw new IllegalArgumentException("Vertices ids are not valid v:" + v + " w: " + w);
    }

    BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

    List<Integer> graphResult = getCommonAncestorAndShortestLength(bfsV, bfsW);
    // In pos 0 is the common acestor
    return graphResult.get(0);
  }

  /**
   * Returns the length of shortest ancestral path between any vertex in v and any vertex in w; -1
   * if no such path
   * 
   * @param v
   * @param w
   * @return
   */
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null || w == null) {
      throw new IllegalArgumentException("Iterables cannot be null");
    }

    if (!isValid(v, w)) {
      throw new IllegalArgumentException("Iterables cannot contain invalid vertices");
    }

    BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

    List<Integer> graphResult = getCommonAncestorAndShortestLength(bfsV, bfsW);

    // In pos 1 is stored the shortest length
    return graphResult.get(1);
  }

  /**
   * Returns a common ancestor that participates in shortest ancestral path; -1 if no such path This
   * is the method for multiple origins
   * 
   * @param v
   * @param w
   * @return
   */
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null || w == null) {
      throw new IllegalArgumentException("Iterables cannot be null");
    }

    if (!isValid(v, w)) {
      throw new IllegalArgumentException("Iterables cannot contain invalid vertices");
    }

    BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(digraph, v);
    BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(digraph, w);

    List<Integer> graphResult = getCommonAncestorAndShortestLength(bfsV, bfsW);

    // In pos 0 is stored the common ancestor
    return graphResult.get(0);

  }


  private boolean isValidVertexId(int v) {
    return (v >= 0 && v <= this.digraph.V() - 1);
  }

  private boolean isValid(Iterable<Integer> v, Iterable<Integer> w) {
    if (!v.iterator().hasNext() || !w.iterator().hasNext()) {
      return false;
    }

    for (int vertex : v) {
      if (!isValidVertexId(vertex)) {
        return false;
      }
    }
    for (int vertex : w) {
      if (!isValidVertexId(vertex)) {
        return false;
      }
    }
    return true;
  }

  private List<Integer> getCommonAncestorAndShortestLength(BreadthFirstDirectedPaths bfsV,
      BreadthFirstDirectedPaths bfsW) {

    int shortestDistance = Integer.MAX_VALUE;
    int commonAncestor = -1;

    for (int adj = 0; adj < digraph.V(); adj++) {
      if (bfsV.hasPathTo(adj) && bfsW.hasPathTo(adj)) {
        int distToAdj = bfsV.distTo(adj) + bfsW.distTo(adj);
        if (distToAdj < shortestDistance) {
          shortestDistance = distToAdj;
          commonAncestor = adj;
        }
      }
    }
    if (commonAncestor == -1) {
      shortestDistance = -1;
    }

    List<Integer> graphResult = new ArrayList<Integer>();
    graphResult.add(0, commonAncestor);
    graphResult.add(1, shortestDistance);
    return graphResult;
  }

  // do unit testing of this class
  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}
