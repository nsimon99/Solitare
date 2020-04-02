import static org.junit.Assert.assertEquals;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.Card.Suits;
import cs3500.freecell.hw02.Card.Value;
import org.junit.Test;

/**
 * tests for {@link Card}.
 */

public class CardTest {

  Card c1 = new Card(Suits.hearts, Value.Three);
  Card c2 = new Card(Suits.spades, Value.A);
  Card c3 = new Card(Suits.diamonds, Value.K);

  @Test
  public void testEqualsTrue() {
    Card c4 = new Card(Suits.diamonds, Value.K);
    assertEquals(true, c1.equals(c1));
    assertEquals(true, c3.equals(c4));
  }

  @Test
  public void testEqualsFalse() {
    Card c5 = new Card(Suits.clubs, Value.Three);
    assertEquals(false, c2.equals(c3));
    assertEquals(false, c5.equals(c1));
  }

  @Test
  public void testHashCode() {
    assertEquals(c1.hashCode(), c1.hashCode());
    assertEquals(c2.hashCode(), c2.hashCode());

  }

  @Test
  public void testGetSuit() {
    assertEquals(Suits.hearts, c1.getSuit());
  }

  @Test
  public void testGetValue() {
    assertEquals(1, c2.getValue());
  }

  @Test
  public void testToString() {
    assertEquals("K♦", c3.toString());
  }


}
