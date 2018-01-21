import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WordNetTest {

  @Test
  public void testWordNetGrapBuild15() {
    WordNet wordnet = new WordNet("data/synsets15.txt", "data/hypernyms15Tree.txt");
    
    assertTrue(wordnet.isNoun("d"));
    assertTrue(wordnet.isNoun("c"));

    System.out.println(wordnet.distance("d", "c"));
  }

  @Test
  public void testWordNetGrapBuild() {
    WordNet wordnet = new WordNet("data/synsets.txt", "data/hypernyms.txt");

    assertTrue(wordnet.isNoun("d"));
    assertTrue(wordnet.isNoun("c"));

    System.out.println(wordnet.distance("d", "c"));
  }
}
