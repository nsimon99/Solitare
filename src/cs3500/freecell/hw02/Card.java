package cs3500.freecell.hw02;

/**
 * Represents a Card in the card game Freecell. Each card has a suit(spades,hearts,diamonds,or
 * clubs) and a value. Ace is considered the lowest value card.
 */
public class Card {

  /**
   * enumeration representing the suits for each card.
   */

  public enum Suits {
    spades("♠"), hearts("♥"), diamonds("♦"), clubs("♣");

    private final String suit;

    Suits(String suit) {
      this.suit = suit;
    }


    /**
     * gets suit of this card.
     */
    public String getSuit() {
      return this.suit;
    }
  }

  /**
   * enumeration representing the values for each card.
   */
  public enum Value {
    A(1), Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7), Eight(8), Nine(9), Ten(10),
    J(11), Q(12), K(13);

    private final int val;

    Value(int val) {
      this.val = val;
    }

    /**
     * gets value of this card.
     */
    public int getVal() {
      return this.val;
    }
  }

  private final Suits s;
  private final Value v;

  /**
   * creates a card with a suit and value.
   *
   * @param s the suit of the card.
   * @param v the value of the card
   */
  public Card(Suits s, Value v) {
    this.s = s;
    this.v = v;
  }

  @Override
  public String toString() {
    return this.valueToString() + s.suit;
  }

  /**
   * formats value to string correctly depending if it is a numerical value.
   *
   * @return value in string format
   */
  public String valueToString() {
    if (this.getValue() > 1 && this.getValue() < 11) {
      return Integer.toString(this.getValue());
    } else {
      return v.toString();
    }
  }

  @Override
  public boolean equals(Object that) {
    if (!(that instanceof Card)) {
      return false;
    }
    if (this == that) {
      return true;
    }
    return this.v == ((Card) that).v && this.s == ((Card) that).s;
  }

  @Override
  public int hashCode() {
    return this.s.hashCode() + this.v.hashCode();
  }

  /**
   * returns the suit of this card.
   */
  public Suits getSuit() {
    Suits suit;
    return suit = this.s;
  }

  /**
   * returns the suit of this card.
   *
   * @return an int copy of a value
   */
  public int getValue() {
    int value;
    return value = this.v.val;
  }


}



