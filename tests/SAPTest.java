import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SAPTest {

  @Test(expected = IllegalArgumentException.class)
  public void testContructorThrowsExceptionWithNullGraph() {
    new SAP(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLengthWithIterablesWithNullArgumentShouldThrowexception() throws Exception {
    SAP sap = new SAP(createDigraph());

    sap.length(null, null);
  }

  private Digraph createDigraph() {
    In in = new In("data/digraph1.txt");
    return new Digraph(in);
  }

  @Test(expected = java.lang.IllegalArgumentException.class)
  public void testAncestorWithIterablesArgumentsWithNullArgumentsShouldThrowexception()
      throws Exception {
    SAP sap = new SAP(createDigraph());

    sap.ancestor(null, new ArrayList<Integer>());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAncestorWithIntArgumentsWithInvalidVerticesFirst() throws Exception {
    SAP sap = new SAP(createDigraph());

    sap.ancestor(-3, 4);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAncestorWithIntArgumentsWithInvalidVerticesSecond() throws Exception {
    SAP sap = new SAP(createDigraph());

    sap.ancestor(4, 1500);
  }

  @Test
  public void testCommonAncestorWithIntArguments() throws Exception {
    SAP sap = new SAP(createDigraph());

    int actualAncestor = sap.ancestor(3, 11);

    assertEquals(1, actualAncestor);
  }

  @Test
  public void testLenghtWithIntArguments() throws Exception {
    SAP sap = new SAP(createDigraph());

    int actualLenght = sap.length(3, 11);

    assertEquals(4, actualLenght);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAncestorWithIterablesThrowExpWithEmptyIterables() throws Exception {
    SAP sap = new SAP(createDigraph());

    List<Integer> v = new ArrayList<>();
    List<Integer> w = new ArrayList<>();


    sap.ancestor(v, w);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAncestorWithIterablesThrowExpWithInvalidIterables() throws Exception {
    SAP sap = new SAP(createDigraph());

    List<Integer> v = new ArrayList<>();
    v.add(3);
    List<Integer> w = new ArrayList<>();
    w.add(100);

    sap.ancestor(v, w);
  }

  @Test
  public void testAncestorWithIterables() throws Exception {
    SAP sap = new SAP(createDigraph());

    List<Integer> v = new ArrayList<>();
    v.add(3);
    List<Integer> w = new ArrayList<>();
    w.add(11);

    int actualAncestor = sap.ancestor(v, w);

    assertEquals(1, actualAncestor);
  }

  @Test
  public void testLengthWithIterables() throws Exception {
    SAP sap = new SAP(createDigraph());

    List<Integer> v = new ArrayList<>();
    v.add(3);
    List<Integer> w = new ArrayList<>();
    w.add(11);

    int actualAncestor = sap.length(v, w);

    assertEquals(4, actualAncestor);
  }
}
