import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {

  // maps synset id to synsets string
  private final List<List<String>> idsToNouns;

  // maps nouns to set of synset ids
  private final Map<String, Set<Integer>> nounToIds;

  private final SAP sap;

  /**
   * This is the constructor that takes the name of the two input files
   * 
   * @param synsets
   * @param hypernyms
   */
  public WordNet(String synsets, String hypernyms) {
    if (synsets == null || hypernyms == null) {
      throw new IllegalArgumentException("Files are null");
    }
    idsToNouns = new ArrayList<List<String>>();
    nounToIds = new HashMap<String, Set<Integer>>();

    int nounId = -1;
    In in = new In(synsets);
    while (in.hasNextLine()) {
      String line = in.readLine();
      String[] tokens = line.split(",");

      nounId = Integer.parseInt(tokens[0]);
      String[] nouns = tokens[1].split(" ");

      List<String> nounsSet = new ArrayList<String>();
      for (String noun : nouns) {
        nounsSet.add(noun);
      }

      idsToNouns.add(nounId, nounsSet);

      for (String noun : nouns) {
        if (nounToIds.get(noun) != null) {
          nounToIds.get(noun).add(nounId);
        } else {
          Set<Integer> s = new HashSet<Integer>();
          s.add(nounId);
          nounToIds.put(noun, s);
        }
      }
    }

    Digraph digraph = new Digraph(nounId + 1);

    in = new In(hypernyms);
    while (in.hasNextLine()) {
      String line = in.readLine();
      String[] tokens = line.split(",");

      int synsetId = Integer.parseInt(tokens[0]);

      for (int i = 1; i < tokens.length; i++) {
        int hypernymId = Integer.parseInt(tokens[i]);
        digraph.addEdge(synsetId, hypernymId);
      }
    }

    // check cycle
    DirectedCycle cycleFinder = new DirectedCycle(digraph);

    if (cycleFinder.hasCycle()) {
      throw new IllegalArgumentException("Graph has a cycle");
    }

    int roots = 0;
    for (int i = 0; i < digraph.V(); i++) {
      if (digraph.outdegree(i) == 0) {
        roots++;
      }
    }

    if (roots > 1) {
      throw new IllegalArgumentException("The graph has more than one root");
    }

    sap = new SAP(digraph);

  }

  /**
   * Returns all WordNet nouns
   * 
   * @return
   */
  public Iterable<String> nouns() {
    return nounToIds.keySet();

  }

  /**
   * Returns a boolean that states weather the word is a WordNet noun
   * 
   * @param word
   * @return
   */
  public boolean isNoun(String word) {
    if (word == null) {
      throw new IllegalArgumentException();
    }
    return nounToIds.containsKey(word);

  }

  /**
   * Returns the distance between nounA and nounB (defined below)
   * 
   * @param nounA
   * @param nounB
   * @return
   */
  public int distance(String nounA, String nounB) {
    // Build 2 iterables of ids corresponding to each noun
    Set<Integer> nounAIds = nounToIds.get(nounA);
    if (nounAIds == null) {
      throw new IllegalArgumentException("noun not present " + nounA);
    }
    Set<Integer> nounBIds = nounToIds.get(nounB);
    if (nounBIds == null) {
      throw new IllegalArgumentException("noun not present " + nounB);
    }
    return sap.length(nounAIds, nounBIds);

  }

  /**
   * Returns a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB.
   * In a shortest ancestral path (defined below)
   * 
   * @param nounA
   * @param nounB
   * @return
   */
  public String sap(String nounA, String nounB) {

    Set<Integer> nounAIds = nounToIds.get(nounA);
    Set<Integer> nounBIds = nounToIds.get(nounB);
    if (nounAIds == null || nounAIds.isEmpty() || nounBIds == null || nounBIds.isEmpty()) {
      throw new IllegalArgumentException("Arguments must be nouns");
    }

    int commonAncestor = sap.ancestor(nounAIds, nounBIds);

    if (commonAncestor != -1) {
      StringBuilder sysnet = new StringBuilder();
      Iterator<String> it = idsToNouns.get(commonAncestor).iterator();
      while (it.hasNext()) {
        String nextNoun = it.next();
        if (!(sysnet.length() == 0)) {
          sysnet.append(" ");
        }
        sysnet.append(nextNoun);
      }
      return sysnet.toString();
    }
    return "";

  }

  public static void main(String[] args) {
    WordNet w = new WordNet("data/synsets.txt", "data/hypernyms.txt");

    System.out.println(w.distance("antihistamine", "nasal_decongestant"));
    System.out.println(w.sap("antihistamine", "nasal_decongestant"));
    System.out.println(w.sap("Rameses_the_Great", "Henry_Valentine_Miller"));
    System.out.println(w.distance("Rameses_the_Great", "Henry_Valentine_Miller"));
    System.out.println(w.sap("individual", "edible_fruit"));
    System.out.println(w.distance("individual", "edible_fruit"));
    try {
      w = new WordNet("synsets3.txt", "hypernymsInvalidTwoRoots.txt");
    } catch (IllegalArgumentException e) {
      System.out.println("synsets3.txt IllegalArgumentException");
    }

    try {
      w = new WordNet("synsets3.txt", "hypernymsInvalidCycle.txt");
    } catch (IllegalArgumentException e) {
      System.out.println("synsets3.txt IllegalArgumentException");
    }

  }

}
