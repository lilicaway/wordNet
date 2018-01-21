public class Outcast {
  private final WordNet wordnet;

  // constructor takes a WordNet object
  public Outcast(WordNet wordnet) {
    this.wordnet = wordnet;
  }

  // given an array of WordNet nouns, return an outcast
  public String outcast(String[] nouns) {
    int distance = 0;
    String outcast = null;

    for (String noun : nouns) {
      int d = 0;

      for (String anotherNoun : nouns) {
        int dist = wordnet.distance(noun, anotherNoun);
        d += dist;
      }

      if (d > distance) {
        distance = d;
        outcast = noun;
      }
    }

    return outcast;
  }


}
