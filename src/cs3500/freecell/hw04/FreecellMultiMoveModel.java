package cs3500.freecell.hw04;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.Card.Suits;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.FreecellOperations;
import cs3500.freecell.hw02.PileType;
import java.util.ArrayList;


/**
 * Builds the freecell card game that can move multiple cards at once implementing the methods from
 * the {@link FreecellOperations}.
 */

public class FreecellMultiMoveModel extends FreecellModel {

  public FreecellMultiMoveModel() {
    super();
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException {
    this.moveMulti(source, pileNumber, cardIndex, destination, destPileNumber);
  }


  /**
   * Move a card or multiple cards from the given source pile to the given destination pile, if the
   * move is valid.
   *
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      starting index of cards needed to be moved
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalArgumentException if the move is not possible {@link PileType})
   */
  private void moveMulti(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException {
    ArrayList<Card> movedCards;
    //can only move multpile cards from and to cascade pile
    if (destination != PileType.CASCADE || source != PileType.CASCADE) {
      try {
        super.move(source, pileNumber, cardIndex, destination, destPileNumber);
      } catch (IllegalArgumentException | IllegalStateException e) {
        throw new IllegalArgumentException("Invalid single card move");
      }
    }
    if (source == PileType.CASCADE && destination == PileType.CASCADE) {
      if (!isStartGame) {
        throw new IllegalStateException("game has not started");
      }
      if (pileNumber > cascadePile.size() - 1 || pileNumber < 0) {
        throw new IllegalArgumentException("source pile out of bounds");
      }
      int lastCardInMovedPile = cascadePile.get(pileNumber).size() - 1;
      if (cardIndex == cascadePile.get(pileNumber).size() - 1) {
        try {
          super.move(source, pileNumber, cardIndex, destination, destPileNumber);
        } catch (IllegalArgumentException | IllegalStateException e) {
          throw new IllegalArgumentException("Invalid single card move to cascade pile");
        }
      }
      if (cardIndex < 0 || cardIndex > lastCardInMovedPile) {
        throw new IllegalArgumentException("card out of index");
      }

      if (destPileNumber > cascadePile.size() - 1 || destPileNumber < 0) {
        throw new IllegalArgumentException("destintion out of index");
      }

      if (cardIndex != lastCardInMovedPile) {
        movedCards = movedCards(cascadePile.get(pileNumber), cardIndex);
        if (movedCards.size() > ((this.numFreeOpen() + 1) * Math.pow(2, this.numFreeCascade()))) {
          throw new IllegalArgumentException("Moving too many cards");
        } else if (cascadePile.get(pileNumber).get(cardIndex).getValue()
            != cascadePile.get(destPileNumber).get(cascadePile.get(destPileNumber).size()
            - 1).getValue() - 1) {
          throw new IllegalArgumentException("must be one less then card on top");
        } else if ((cascadePile.get(destPileNumber).get(cascadePile.get(destPileNumber).size()
            - 1).getSuit() == Suits.spades
            || cascadePile.get(destPileNumber).get(cascadePile.get(destPileNumber).size()
            - 1).getSuit() == Suits.clubs) && (!(
            cascadePile.get(pileNumber).get(cardIndex).getSuit() == Suits.hearts
                || cascadePile.get(pileNumber).get(cardIndex).getSuit() == Suits.diamonds))) {
          throw new IllegalArgumentException("colors must alternate");
        }
        if ((cascadePile.get(destPileNumber).get(cascadePile.get(destPileNumber).size()
            - 1).getSuit() == Suits.hearts
            || cascadePile.get(destPileNumber).get(cascadePile.get(destPileNumber).size()
            - 1).getSuit() == Suits.diamonds) && (!(
            cascadePile.get(pileNumber).get(cardIndex).getSuit() == Suits.clubs
                || cascadePile.get(pileNumber).get(cardIndex).getSuit() == Suits.spades))) {
          throw new IllegalArgumentException("colors must alternate");
        }
        if (!this.isAlternatingColor(movedCards) || !this.isDescValues(movedCards)) {
          throw new IllegalArgumentException("either colors arent alternating or values are not"
              + "descending");
        } else {
          cascadePile.get(pileNumber).removeAll(movedCards);
          cascadePile.get(destPileNumber).addAll(movedCards);

        }
      }
    }
  }

  /**
   * retrieves array list items wanted from given array list.
   *
   * @param sourceDeck    arraylist wanting to retrieve items from
   * @param startingIndex representing started index of new arraylist
   * @return new arraylist with items
   */

  private ArrayList<Card> movedCards(ArrayList<Card> sourceDeck, int startingIndex) {
    ArrayList<Card> result = new ArrayList<>();
    for (int i = startingIndex; i < sourceDeck.size(); i++) {
      Card c;
      c = sourceDeck.get(i);
      result.add(c);
    }
    return result;

  }

  /**
   * determines the number empty cascade piles.
   *
   * @return number cascade piles
   */

  private int numFreeCascade() {
    int numCascade = 0;
    for (ArrayList<Card> pile : cascadePile) {
      if (pile.size() == 0) {
        numCascade++;
      }
    }
    return numCascade;
  }

  /**
   * determines the number empty open piles.
   *
   * @return number open piles
   */
  private int numFreeOpen() {
    int numOpen = 0;
    for (ArrayList<Card> pile : openPile) {
      if (pile.size() == 0) {
        numOpen++;
      }
    }
    return numOpen;
  }

  /**
   * answers if cards are alternating colors.
   *
   * @param cards given lists of cards
   * @return a boolean
   */

  private boolean isAlternatingColor(ArrayList<Card> cards) {
    boolean result = true;
    int i = 0;
    while (i <= cards.size()) {
      Card firstCard = cards.get(i);
      Card secondCard = cards.get(i + 1);
      if ((firstCard.getSuit() == Suits.spades
          || firstCard.getSuit() == Suits.clubs) && (!(secondCard.getSuit() == Suits.hearts
          || secondCard.getSuit() == Suits.diamonds))) {
        result = false;
      } else if ((firstCard.getSuit() == Suits.hearts
          || firstCard.getSuit() == Suits.diamonds) && (!(secondCard.getSuit() == Suits.clubs
          || secondCard.getSuit() == Suits.spades))) {
        result = false;
      }
      i = i + 1;
      if (i == cards.size() - 1) {
        break;
      }
    }
    return result;
  }

  /**
   * answers if cards are in consecutive descending values.
   *
   * @param cards given lists of cards
   * @return a boolean
   */

  private boolean isDescValues(ArrayList<Card> cards) {
    boolean result = true;
    int i = 0;
    while (i <= cards.size()) {
      Card firstCard = cards.get(i);
      Card secondCard = cards.get(i + 1);
      if (secondCard.getValue() != firstCard.getValue() - 1) {
        result = false;
      }
      i = i + 1;
      if (i == cards.size() - 1) {
        break;
      }
    }
    return result;
  }

}
