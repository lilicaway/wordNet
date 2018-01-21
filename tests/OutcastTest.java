import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OutcastTest {

  @Test
  public void testOutcast() {
    WordNet wordnet = new WordNet("data/synsets.txt", "data/hypernyms.txt");
    Outcast outcast = new Outcast(wordnet);

    String[] words = { "horse", "zebra", "cat", "bear", "table" };

    String actualOutcast = outcast.outcast(words);

    assertEquals("table", actualOutcast);

    assertEquals("bed", outcast.outcast(new String[] {
        "water", "soda", "bed", "orange_juice",
        "milk", "apple_juice", "tea", "coffee" }));

    assertEquals("potato", outcast.outcast(new String[] {
        "apple", "pear", "peach", "banana", "lime", "lemon", "blueberry", "strawberry", "mango",
        "watermelon", "potato" }));

    assertEquals(1, wordnet.distance("Actifed", "antihistamine"));
    assertEquals(1, wordnet.distance("Actifed", "nasal_decongestant"));
  }

}
