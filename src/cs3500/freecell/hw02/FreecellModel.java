package cs3500.freecell.hw02;

import cs3500.freecell.hw02.Card.Suits;
import cs3500.freecell.hw02.Card.Value;
import java.util.ArrayList;
import java.util.List;

/**
 * Builds the freecell card game implementing the methods from the {@link FreecellOperations}.
 */

public class FreecellModel implements FreecellOperations<Card> {

  protected ArrayList<ArrayList<Card>> cascadePile;
  protected ArrayList<ArrayList<Card>> openPile;
  protected ArrayList<ArrayList<Card>> foundationPile;
  protected boolean isStartGame;

  /**
   * constructor for Freecell game.
   *
   * @param cascadePile    represents cascade piles
   * @param openPile       represents open piles
   * @param foundationPile represents foundation piles
   * @param isStartGame    represents if game has started
   */
  public FreecellModel(
      ArrayList<ArrayList<Card>> cascadePile,
      ArrayList<ArrayList<Card>> openPile,
      ArrayList<ArrayList<Card>> foundationPile,
      boolean isStartGame) {
    this.cascadePile = new ArrayList<>();
    this.openPile = new ArrayList<>();
    this.foundationPile = new ArrayList<>();
    this.isStartGame = false;
  }

  //convience constructor for testing
  public FreecellModel() {
    this.foundationPile = new ArrayList<>();
    this.isStartGame = false;
  }


  @Override
  public List<Card> getDeck() {
    ArrayList<Card> deck = new ArrayList<>();
    for (Suits s : Suits.values()) {
      for (Value v : Value.values()) {
        deck.add(new Card(s, v));
      }
    }
    return deck;

  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    this.cascadePile = new ArrayList<>();
    this.openPile = new ArrayList<>();
    this.foundationPile = new ArrayList<>();
    List<Card> shuffledDeck = new ArrayList<Card>(deck);

    if (deck.size() != 52) {
      throw new IllegalArgumentException("Invalid Deck");
    }
    for (Suits s : Suits.values()) {
      for (Value v : Value.values()) {
        if (!deck.contains(new Card(s, v))) {
          throw new IllegalArgumentException("Invalid Deck");
        }
      }
    }
    if (deck == null) {
      throw new IllegalArgumentException("null deck");
    }
    if (numCascadePiles < 4) {
      throw new IllegalArgumentException("Must have at least 4 Cascading piles");
    }
    if (numOpenPiles < 1) {
      throw new IllegalArgumentException("Must have at least 1 open pile");
    }

    this.isStartGame = true;

    if (shuffle) {
      this.shuffleDeck(shuffledDeck);
    }

    for (int i = 0; i < numCascadePiles; i++) {
      cascadePile.add(new ArrayList<>());
    }
    for (int i = 0; i <= deck.size() - 1; i++) {
      cascadePile.get(i % numCascadePiles).add(shuffledDeck.get(i));
    }

    for (int i = 0; i <= numOpenPiles - 1; i++) {
      openPile.add(new ArrayList<>());
    }
    for (int i = 0; i <= 3; i++) {
      foundationPile.add(new ArrayList<>());
    }
  }

  /**
   * shuffles the given deck randomly.
   *
   * @param deck original deck that is unshuffled
   */
  public void shuffleDeck(List<Card> deck) {
    for (int i = 0; i < deck.size(); i++) {
      int randIndex = (int) (Math.random() * i + 1);
      Card randCard = deck.get(i);
      deck.set(i, randCard);
      deck.set(randIndex, deck.get(i));
    }
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException {
    Card movedCard = null;
    if (!isStartGame) {
      throw new IllegalStateException("cant make move before game has started");
    }
    if (source == destination && pileNumber == destPileNumber) {
      return;
    }
    if (source == null || destination == null) {
      throw new IllegalArgumentException("piles are null");
    }
    if (source == PileType.CASCADE) {
      if (pileNumber < 0 || pileNumber > cascadePile.size() - 1) {
        throw new IllegalArgumentException("pile out of bounds");
      }
      if (cardIndex < 0 || cardIndex > cascadePile.get(pileNumber).size() - 1) {
        throw new IllegalArgumentException("card out of index");

      } else if (cardIndex != cascadePile.get(pileNumber).size() - 1) {
        throw new IllegalArgumentException("can only move last card");
      } else {
        movedCard = cascadePile.get(pileNumber).get(cardIndex);
        cascadePile.get(pileNumber).remove(movedCard);


      }
    }
    if (source == PileType.OPEN) {
      if (pileNumber < 0 || pileNumber > openPile.size() - 1) {
        throw new IllegalArgumentException("pile out of bounds");
      } else if (cardIndex < 0 || cardIndex > openPile.get(pileNumber).size() - 1) {
        throw new IllegalArgumentException("card out of index");
      } else if (openPile.get(pileNumber).size() == 0) {
        throw new IllegalArgumentException("no card to be removed");
      } else {
        movedCard = openPile.get(pileNumber).get(cardIndex);
        openPile.get(pileNumber).remove(movedCard);

      }
    }

    if (source == PileType.FOUNDATION) {
      throw new IllegalArgumentException("cant move card from foundation pile");
    }

    if (destination == PileType.CASCADE) {
      if (destPileNumber > cascadePile.size() - 1 || destPileNumber < 0) {
        throw new IllegalArgumentException("destintion out of index");
      }
      assert movedCard != null;
      if (movedCard.getValue()
          != cascadePile.get(destPileNumber).get(cascadePile.get(destPileNumber).size()
          - 1).getValue() - 1) {
        throw new IllegalArgumentException("must be one less then card on top");
      } else if ((cascadePile.get(destPileNumber).get(cascadePile.get(destPileNumber).size()
          - 1).getSuit() == Suits.spades
          || cascadePile.get(destPileNumber).get(cascadePile.get(destPileNumber).size()
          - 1).getSuit() == Suits.clubs) && (!(movedCard.getSuit() == Suits.hearts
          || movedCard.getSuit() == Suits.diamonds))) {
        throw new IllegalArgumentException("colors must alternate");
      } else if ((cascadePile.get(destPileNumber).get(cascadePile.get(destPileNumber).size()
          - 1).getSuit() == Suits.hearts
          || cascadePile.get(destPileNumber).get(cascadePile.get(destPileNumber).size()
          - 1).getSuit() == Suits.diamonds) && (!(movedCard.getSuit() == Suits.clubs
          || movedCard.getSuit() == Suits.spades))) {
        throw new IllegalArgumentException("colors must alternate");
      } else {
        cascadePile.get(destPileNumber).add(movedCard);
      }
    }

    if (destination == PileType.OPEN) {
      if (destPileNumber > openPile.size() - 1 || destPileNumber < 0) {
        throw new IllegalArgumentException("destintion out of index");
      } else if (openPile.get(destPileNumber).size() == 1) {
        throw new IllegalArgumentException("card already in open pile");
      } else {
        openPile.get(destPileNumber).add(movedCard);
      }
    }

    if (destination == PileType.FOUNDATION) {
      if (destPileNumber > foundationPile.size() - 1 || destPileNumber < 0) {
        throw new IllegalArgumentException("destintion out of index");
      } else {
        assert movedCard != null;
        if (foundationPile.get(destPileNumber).size() == 0 && (movedCard.getValue() != 1)) {
          throw new IllegalArgumentException("first card in foundation pile must be Ace");
        } else if (foundationPile.get(destPileNumber).size() > 0 && (movedCard.getSuit()
            != foundationPile.get(destPileNumber).get(foundationPile.get(destPileNumber).size() - 1)
            .getSuit())) {
          throw new IllegalArgumentException("suits must match");
        } else if (foundationPile.get(destPileNumber).size() > 0 && (movedCard.getValue()
            != foundationPile.get(destPileNumber).get(foundationPile.get(destPileNumber).size() - 1)
            .getValue() + 1)) {
          throw new IllegalArgumentException("value must be one higher");
        } else {
          foundationPile.get(destPileNumber).add(movedCard);
        }
      }

    }
  }


  @Override
  public boolean isGameOver() {
    if (!isStartGame) {
      return false;
    } else {
      return (foundationPile.get(0).size() == 13
          && foundationPile.get(1).size() == 13
          && foundationPile.get(2).size() == 13
          && foundationPile.get(3).size() == 13);
    }
  }

  @Override
  public Card getCard(PileType pile, int pileNumber, int cardIndex) {
    if (!isStartGame) {
      throw new IllegalStateException("game has not started");
    }
    if (pile == null) {
      throw new IllegalArgumentException("pile is null");
    }
    Card c = null;
    if (pile == PileType.CASCADE) {
      c = validCard(pileNumber, cardIndex, cascadePile);
    } else if (pile == PileType.OPEN) {
      c = validCard(pileNumber, cardIndex, openPile);

    } else if (pile == PileType.FOUNDATION) {
      c = validCard(pileNumber, cardIndex, foundationPile);
    }
    return c;
  }

  /**
   * helper for getCard. Checks to see if card exists.
   *
   * @param pileNumber represents pile that card is in
   * @param cardIndex  where card is located in pile
   * @param pile       the pile type
   * @return card if exsit
   * @throws IllegalArgumentException if card does not exist
   */
  public Card validCard(int pileNumber, int cardIndex, ArrayList<ArrayList<Card>> pile) {
    Card c;
    if (pileNumber < 0 || pileNumber > pile.size() - 1) {
      throw new IllegalArgumentException("pile out of bounds");
    } else if (cardIndex > pile.get(pileNumber).size() - 1 || cardIndex < 0) {
      throw new IllegalArgumentException("card does not exist");
    } else if (pile.get(pileNumber).size() == 0) {
      throw new IllegalArgumentException("empty pile");
    } else {
      c = pile.get(pileNumber).get(cardIndex);
    }
    return c;
  }


  /**
   * converts the pile to given string format.
   *
   * @param pile representing pile being converted
   * @return pile in string format
   */

  public String pileToString(ArrayList<Card> pile) {
    StringBuilder result = new StringBuilder();
    for (Card c : pile) {
      result.append(c.toString()).append((c == pile.get(pile.size() - 1)) ? "" : ", ");
    }
    return result.toString();
  }

  @Override
  public String getGameState() {
    StringBuilder result = new StringBuilder();
    if (!isStartGame) {
      return "";
    }

    for (int i = 0; i <= 3; i++) {
      result.append("F").append((i + 1)).append(":");
      result.append(pileToString(foundationPile.get(i)));
      result.append("\n");
    }

    for (int i = 0; i <= openPile.size() - 1; i++) {
      result.append("O").append((i + 1)).append(":");
      result.append(pileToString(openPile.get(i)));
      result.append("\n");
    }

    for (int i = 0; i <= cascadePile.size() - 1; i++) {
      result.append("C").append((i + 1)).append(": ");
      result.append(pileToString(cascadePile.get(i)));
      if (i != cascadePile.size() - 1) {
        result.append("\n");
      }
    }
    return result.toString();
  }


}
