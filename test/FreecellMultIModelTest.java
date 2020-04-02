import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.Card.Suits;
import cs3500.freecell.hw02.Card.Value;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw04.FreecellModelCreator;
import cs3500.freecell.hw04.FreecellModelCreator.GameType;
import cs3500.freecell.hw04.FreecellMultiMoveModel;
import cs3500.freecell.hw02.PileType;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * tests for model that can handle moving multple cards at once.
 */
public class FreecellMultIModelTest {


  @Test
  public void testFreecellModelCreatorSingle() {
    FreecellModelCreator fcm = new FreecellModelCreator();
    assertTrue(fcm.create(GameType.SINGLEMOVE) instanceof FreecellModel);
  }

  @Test
  public void testFreecellModelCreatorMulti() {
    FreecellModelCreator fcm = new FreecellModelCreator();
    assertTrue(fcm.create(GameType.MULTIMOVE) instanceof FreecellMultiMoveModel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFreecellModelCreatorNull() {
    FreecellModelCreator fcm = new FreecellModelCreator();
    fcm.create(null);
  }


  @Test(expected = IllegalStateException.class)
  public void testMoveGameNotStarted() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    fm.move(PileType.CASCADE, 2, 3, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeSourceOutOfBounds() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 3, 20, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeCardOutOfBounds() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 3, 90, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNullSource() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(null, 3, 90, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveBothNull() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(null, 3, 90, null, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNullDestination() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 3, 90, null, 2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenSourcePileOutOfBounds() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 9, 0, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenSourceOutOfBounds() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 0, 20, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationMove() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationWrongSuit() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 22, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationWrongValue() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeSourcePileOutOfBounds() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 9, 7, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNotLastCard() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 0, 1, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveWrongSuitCascadingPile() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveWrongValueCascadingPile() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 1, 12, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenPileEmpty() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.OPEN, 0, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenPileOccupied() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    fm.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationFirstCardNotAce() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
  }

  @Test
  public void testGoodMoveToCascade() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    assertEquals(new Card(Suits.hearts, Value.A), fm.getCard(PileType.CASCADE, 1,
        1));
  }

  @Test
  public void testGoodMoveToOpen() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 4, 0, PileType.OPEN, 0);
    assertEquals(new Card(Suits.spades, Value.Five), fm.getCard(PileType.OPEN, 0,
        0));
  }

  @Test
  public void testMoveFoundation() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(new Card(Suits.spades, Value.Two), fm.getCard(PileType.FOUNDATION, 0, 1));
  }

  @Test
  public void testMoveSamePlace() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 0);
    assertEquals(new Card(Suits.spades, Value.A), fm.getCard(PileType.CASCADE, 0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveMultipleDestOutOfBounds() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    fm.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 80);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveMultipleSourcePileOutOfBounds() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    fm.move(PileType.CASCADE, 59, 0, PileType.CASCADE, 15);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveMultipleCardIndexOutOfBounds() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    fm.move(PileType.CASCADE, 1, 3, PileType.CASCADE, 15);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveMultiMoveDestWrongColor() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    fm.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveMultiMoveDestWrongValue() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    fm.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 14);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveMultiMoveMovingTooManyCardsEdgeCase() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 20, 1, false);
    fm.move(PileType.CASCADE, 0, 2, PileType.OPEN, 0);
    fm.move(PileType.CASCADE, 9, 2, PileType.CASCADE, 17);
    fm.move(PileType.CASCADE, 17, 1, PileType.CASCADE, 11);
  }

  @Test
  public void testMoveMultiMoveGoodMove() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 20, 1, false);
    fm.move(PileType.CASCADE, 9, 2, PileType.CASCADE, 17);
    fm.move(PileType.CASCADE, 17, 1, PileType.CASCADE, 11);
    assertEquals(new Card(Suits.clubs, Value.J), fm.getCard(PileType.CASCADE, 11, 4));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveWrongColors() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    fm.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveWrongValues() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    fm.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 16);
  }

  @Test
  public void testMultiMoveGoodMove() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    assertEquals(new Card(Suits.hearts, Value.A), fm.getCard(PileType.CASCADE, 1, 1));
    fm.move(PileType.CASCADE, 1, 0, PileType.CASCADE, 15);
    assertEquals(new Card(Suits.hearts, Value.A), fm.getCard(PileType.CASCADE, 15, 2));
    fm.move(PileType.CASCADE, 15, 0, PileType.CASCADE, 3);
    assertEquals(new Card(Suits.hearts, Value.A), fm.getCard(PileType.CASCADE, 3, 3));
  }

  @Test
  public void testMakeDeck() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    assertEquals(52, deck.size());
    for (Suits s : Suits.values()) {
      for (Value v : Value.values()) {
        assertTrue(deck.contains(new Card(s, v)));
      }
    }
  }

  @Test
  public void testShuffleDeck() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> notShuffledDeck = fm.getDeck();
    FreecellModel fm2 = new FreecellModel();
    List<Card> shuffledDeck = fm2.getDeck();
    fm2.shuffleDeck(shuffledDeck);
    assertNotEquals(shuffledDeck, notShuffledDeck);
  }

  //checks to see deals right card in right place
  @Test
  public void testStartGameCorrectPiles() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    assertEquals(new Card(Suits.spades, Value.A),
        fm.getCard(PileType.CASCADE, 0, 0));
    assertEquals(new Card(Suits.spades, Value.Five),
        fm.getCard(PileType.CASCADE, 0, 1));
    assertEquals(new Card(Suits.clubs, Value.K),
        fm.getCard(PileType.CASCADE, 3, 12));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testStartGameTooManyCards() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    deck.add(new Card(Suits.hearts, Value.Three));
    fm.startGame(deck, 5, 1, true);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughCards() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    deck.remove(new Card(Suits.hearts, Value.Three));
    fm.startGame(deck, 5, 1, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartInvalidCascadingPiles() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 3, 1, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartInvalidOpenPiles() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 0, true);
  }

  @Test
  public void testGetCard() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    assertEquals(new Card(Suits.spades, Value.A),
        fm.getCard(PileType.CASCADE, 0, 0));
    assertEquals(new Card(Suits.spades, Value.Two),
        fm.getCard(PileType.CASCADE, 1, 0));
    assertEquals(new Card(Suits.spades, Value.Three),
        fm.getCard(PileType.CASCADE, 2, 0));
    assertEquals(new Card(Suits.spades, Value.Four),
        fm.getCard(PileType.CASCADE, 3, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardInvalidPileNumber() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.getCard(PileType.CASCADE, 6, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardInvalidCardNumber() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.getCard(PileType.CASCADE, 1, 20);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardGameNotStarted() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.getCard(PileType.CASCADE, 1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardNull() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.getCard(null, 1, 2);
  }


  @Test
  public void testIsGameOverGameNotFinished() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, true);
    assertFalse(fm.isGameOver());
  }

  @Test
  public void testIsGameOverGameNotStarter() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    assertFalse(fm.isGameOver());
  }

  @Test
  public void testIsGameOverFinished() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 3, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 4, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 5, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 6, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 7, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 8, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 9, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 10, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 11, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 12, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 13, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 14, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 15, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 16, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 17, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 18, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 19, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 20, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 21, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 22, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 23, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 24, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 25, 0, PileType.FOUNDATION, 1);
    fm.move(PileType.CASCADE, 26, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 27, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 28, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 29, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 30, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 31, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 32, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 33, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 34, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 35, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 36, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 37, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 38, 0, PileType.FOUNDATION, 2);
    fm.move(PileType.CASCADE, 39, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 40, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 41, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 42, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 43, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 44, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 45, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 46, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 47, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 48, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 49, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 50, 0, PileType.FOUNDATION, 3);
    fm.move(PileType.CASCADE, 51, 0, PileType.FOUNDATION, 3);
    assertTrue(fm.isGameOver());
  }

  @Test
  public void testPileToStringEmptyPile() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    ArrayList<Card> foundationPile = new ArrayList<>();
    fm.startGame(deck, 4, 1, false);
    assertEquals("", fm.pileToString(foundationPile));
  }

  @Test
  public void testPileToString() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    ArrayList<Card> cascadePile = new ArrayList<>();
    cascadePile.add(new Card(Suits.hearts, Value.Two));
    cascadePile.add(new Card(Suits.hearts, Value.Three));
    cascadePile.add(new Card(Suits.hearts, Value.Four));
    fm.startGame(deck, 4, 1, false);
    assertEquals("2♥, 3♥, 4♥", fm.pileToString(cascadePile));
  }

  @Test
  public void testGetGameStateGameNotBegun() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    assertEquals("", fm.getGameState());

  }

  @Test
  public void testGetGameState() {
    FreecellMultiMoveModel fm = new FreecellMultiMoveModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "C1: A♠, 5♠, 9♠, K♠, 4♥, 8♥, Q♥, 3♦, 7♦, J♦, 2♣, 6♣, 10♣\n"
        + "C2: 2♠, 6♠, 10♠, A♥, 5♥, 9♥, K♥, 4♦, 8♦, Q♦, 3♣, 7♣, J♣\n"
        + "C3: 3♠, 7♠, J♠, 2♥, 6♥, 10♥, A♦, 5♦, 9♦, K♦, 4♣, 8♣, Q♣\n"
        + "C4: 4♠, 8♠, Q♠, 3♥, 7♥, J♥, 2♦, 6♦, 10♦, A♣, 5♣, 9♣, K♣", fm.getGameState());

  }


}
