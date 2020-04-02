import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.Card.Suits;
import cs3500.freecell.hw02.Card.Value;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.PileType;
import cs3500.freecell.hw03.FreecellController;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Tests for the Freecell game.
 */
public class FreecellOperationsTest {

  String wonGame = "C1 1 F1 C2 1 F1 C3 1 F1 C4 1 F1 C5 1 F1 C6 1 F1 C7 1 F1 "
      + "C8 1 F1 C9 1 F1 C10 1 F1 C11 1 F1 C12 1 F1 C13 1 F1 C14 1 F2 C15 1 F2 C16 1 F2 "
      + "C17 1 F2 C18 1 F2 C19 1 F2 C20 1 F2 C21 1 F2 C22 1 F2 C23 1 F2 C24 1 F2 C25 1 F2 "
      + "C26 1 F2 C27 1 F3 C28 1 F3 C29 1 F3 C30 1 F3 C31 1 F3 C32 1 F3 C33 1 F3 C34 1 F3 "
      + "C35 1 F3 C36 1 F3 C37 1 F3 C38 1 F3 C39 1 F3 C40 1 F4 C41 1 F4 C42 1 F4 C43 1 F4 "
      + "C44 1 F4 C45 1 F4 C46 1 F4 C47 1 F4 C48 1 F4 C49 1 F4 C50 1 F4 C51 1 F4 C52 1 F4";

  @Test
  public void testMakeDeck() {
    FreecellModel fm = new FreecellModel();
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
    FreecellModel fm = new FreecellModel();
    List<Card> notShuffledDeck = fm.getDeck();
    FreecellModel fm2 = new FreecellModel();
    List<Card> shuffledDeck = fm2.getDeck();
    fm2.shuffleDeck(shuffledDeck);
    assertNotEquals(shuffledDeck, notShuffledDeck);
  }

  //checks to see deals right card in right place
  @Test
  public void testStartGameCorrectPiles() {
    FreecellModel fm = new FreecellModel();
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
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    deck.add(new Card(Suits.hearts, Value.Three));
    fm.startGame(deck, 5, 1, true);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testStartGameNotEnoughCards() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    deck.remove(new Card(Suits.hearts, Value.Three));
    fm.startGame(deck, 5, 1, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartInvalidCascadingPiles() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 3, 1, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartInvalidOpenPiles() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 0, true);
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveGameNotStarted() {
    FreecellModel fm = new FreecellModel();
    fm.move(PileType.CASCADE, 2, 3, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeSourceOutOfBounds() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 3, 20, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeCardOutOfBounds() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 3, 90, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNullSource() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(null, 3, 90, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveBothNull() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(null, 3, 90, null, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNullDestination() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 3, 90, null, 2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenSourcePileOutOfBounds() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 9, 0, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenSourceOutOfBounds() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 0, 20, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationMove() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.FOUNDATION, 0, 0, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationWrongSuit() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 22, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationWrongValue() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveCascadeSourcePileOutOfBounds() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 9, 7, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveNotLastCard() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 0, 1, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveWrongSuitCascadingPile() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 0, 12, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveWrongValueCascadingPile() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 1, 12, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenPileEmpty() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.OPEN, 0, 0, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveOpenPileOccupied() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    fm.move(PileType.CASCADE, 1, 12, PileType.OPEN, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveFoundationFirstCardNotAce() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
  }

  @Test
  public void testGoodMoveToCascade() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 13, 0, PileType.CASCADE, 1);
    assertEquals(new Card(Suits.hearts, Value.A), fm.getCard(PileType.CASCADE, 1,

        1));
  }

  @Test
  public void testGoodMoveToOpen() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 4, 0, PileType.OPEN, 0);
    assertEquals(new Card(Suits.spades, Value.Five), fm.getCard(PileType.OPEN, 0,
        0));
  }

  @Test
  public void testMoveFoundation() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    fm.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(new Card(Suits.spades, Value.Two), fm.getCard(PileType.FOUNDATION, 0, 1));
  }

  @Test
  public void testMoveSamePlace() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 52, 1, false);
    fm.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 0);
    assertEquals(new Card(Suits.spades, Value.A), fm.getCard(PileType.CASCADE, 0, 0));
  }


  @Test
  public void testGetCard() {
    FreecellModel fm = new FreecellModel();
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
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.getCard(PileType.CASCADE, 6, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardInvalidCardNumber() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.getCard(PileType.CASCADE, 1, 20);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCardGameNotStarted() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.getCard(PileType.CASCADE, 1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetCardNull() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, false);
    fm.getCard(null, 1, 2);
  }


  @Test
  public void testIsGameOverGameNotFinished() {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fm.startGame(deck, 4, 1, true);
    assertFalse(fm.isGameOver());
  }

  @Test
  public void testIsGameOverGameNotStarter() {
    FreecellModel fm = new FreecellModel();
    assertFalse(fm.isGameOver());
  }

  @Test
  public void testIsGameOverFinished() {
    FreecellModel fm = new FreecellModel();
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
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    ArrayList<Card> foundationPile = new ArrayList<>();
    fm.startGame(deck, 4, 1, false);
    assertEquals("", fm.pileToString(foundationPile));
  }

  @Test
  public void testPileToString() {
    FreecellModel fm = new FreecellModel();
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
    FreecellModel fm = new FreecellModel();
    assertEquals("", fm.getGameState());

  }

  @Test
  public void testGetGameState() {
    FreecellModel fm = new FreecellModel();
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

  // tests for controller

  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullRD() {
    StringBuilder ap = new StringBuilder("");
    FreecellController fc = new FreecellController(null, ap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullAP() {
    StringReader rd = new StringReader("");
    FreecellController fc = new FreecellController(rd, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerNull() {
    FreecellController fc = new FreecellController(null, null);
  }

  @Test (expected = IllegalStateException.class)
  public void testUnreadableInput() throws IOException {
    Reader rd = new StringReader(" ");
    StringBuilder ap = new StringBuilder();
    FreecellController fc = new FreecellController(rd, ap);
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 4, 1, false);
  }

  @Test
  public void testPlayGameInvalidDeck() throws IOException {
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    deck.add(new Card(Suits.hearts, Value.Three));
    StringReader rd = new StringReader("");
    StringBuilder ap = new StringBuilder();
    FreecellController fc = new FreecellController(rd, ap);
    fc.playGame(deck, fm, 4, 1, true);
    assertEquals("Could not start game.", ap.toString());
  }

  @Test
  public void testPlayGameInvalidNumCascade() throws IOException {
    StringReader rd = new StringReader("");
    StringBuilder ap = new StringBuilder();
    FreecellController fc = new FreecellController(rd, ap);
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 1, 4, false);
    assertEquals("Could not start game.", ap.toString());
  }

  @Test
  public void testPlayGameInvalidNumOpen() throws IOException {
    StringReader rd = new StringReader("");
    StringBuilder ap = new StringBuilder();
    FreecellController fc = new FreecellController(rd, ap);
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 5, 0, false);
    assertEquals("Could not start game.", ap.toString());
  }

  @Test
  public void testPlayGameQuitGame() throws IOException {
    StringReader rd = new StringReader("Q");
    StringBuilder ap = new StringBuilder();
    FreecellModel fm = new FreecellModel();
    FreecellController fc = new FreecellController(rd, ap);
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 5, 1, false);
    assertEquals(true, ap.toString().contains("Game quit prematurely."));
  }

  @Test
  public void testPlayGameQuitGameRandom() throws IOException {
    StringReader rd = new StringReader("C1 Q C1");
    StringBuilder ap = new StringBuilder();
    FreecellModel fm = new FreecellModel();
    FreecellController fc = new FreecellController(rd, ap);
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 5, 1, false);
    assertEquals(true, ap.toString().contains("Game quit prematurely."));
  }

  @Test
  public void testPlayGameQuitGameRandomLowerCase() throws IOException {
    StringReader rd = new StringReader("C1 1 q");
    StringBuilder ap = new StringBuilder();
    FreecellModel fm = new FreecellModel();
    FreecellController fc = new FreecellController(rd, ap);
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 5, 1, false);
    assertEquals(true, ap.toString().contains("Game quit prematurely."));
  }

  @Test
  public void testPlayGameQuitGameLowerCase() throws IOException {
    StringReader rd = new StringReader("q");
    StringBuilder ap = new StringBuilder();
    FreecellModel fm = new FreecellModel();
    FreecellController fc = new FreecellController(rd, ap);
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 5, 1, false);
    assertEquals(true, ap.toString().contains("Game quit prematurely."));
  }

  @Test
  public void testPlayGamePileInvalidSource() throws IOException {
    StringReader rd = new StringReader("R1 " + wonGame);
    StringBuilder ap = new StringBuilder();
    FreecellController fc = new FreecellController(rd, ap);
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 52, 1, false);
    assertEquals(true, ap.toString().contains("Try different source pile or source number.\n"));
  }

  @Test
  public void testPlayGamePileInvalidDestination() throws IOException {
    StringReader rd = new StringReader("C1 1 G1 F1 C2 1 F1 C3 1 F1 C4 1 F1 C5 1 F1 C6 1 F1 C7 1 F1 "
        + "C8 1 F1 C9 1 F1 C10 1 F1 C11 1 F1 C12 1 F1 C13 1 F1 C14 1 F2 C15 1 F2 C16 1 F2 "
        + "C17 1 F2 C18 1 F2 C19 1 F2 C20 1 F2 C21 1 F2 C22 1 F2 C23 1 F2 C24 1 F2 C25 1 F2 "
        + "C26 1 F2 C27 1 F3 C28 1 F3 C29 1 F3 C30 1 F3 C31 1 F3 C32 1 F3 C33 1 F3 C34 1 F3 "
        + "C35 1 F3 C36 1 F3 C37 1 F3 C38 1 F3 C39 1 F3 C40 1 F4 C41 1 F4 C42 1 F4 C43 1 F4 "
        + "C44 1 F4 C45 1 F4 C46 1 F4 C47 1 F4 C48 1 F4 C49 1 F4 C50 1 F4 C51 1 F4 C52 1 F4");
    StringBuilder ap = new StringBuilder();
    FreecellController fc = new FreecellController(rd, ap);
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 52, 1, false);
    assertEquals(true,
        ap.toString().contains("Try different destination pile or destination number.\n"));
  }

  @Test
  public void testPlayGamePileInvalidCardIndex() throws IOException {
    Reader rd = new StringReader("C1 x 1 F1 C2 1 F1 C3 1 F1 C4 1 F1 C5 1 F1 C6 1 F1 C7 1 F1 "
        + "C8 1 F1 C9 1 F1 C10 1 F1 C11 1 F1 C12 1 F1 C13 1 F1 C14 1 F2 C15 1 F2 C16 1 F2 "
        + "C17 1 F2 C18 1 F2 C19 1 F2 C20 1 F2 C21 1 F2 C22 1 F2 C23 1 F2 C24 1 F2 C25 1 F2 "
        + "C26 1 F2 C27 1 F3 C28 1 F3 C29 1 F3 C30 1 F3 C31 1 F3 C32 1 F3 C33 1 F3 C34 1 F3 "
        + "C35 1 F3 C36 1 F3 C37 1 F3 C38 1 F3 C39 1 F3 C40 1 F4 C41 1 F4 C42 1 F4 C43 1 F4 "
        + "C44 1 F4 C45 1 F4 C46 1 F4 C47 1 F4 C48 1 F4 C49 1 F4 C50 1 F4 C51 1 F4 C52 1 F4");
    StringBuilder ap = new StringBuilder();
    FreecellController fc = new FreecellController(rd, ap);
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 52, 1, false);
    assertEquals(true, ap.toString().contains("Try different card index.\n"));
  }

  @Test
  public void testPlayGamePileInvalidCardNegativeIndex() throws IOException {
    Reader rd = new StringReader("C1 -x 1 F1 C2 1 F1 C3 1 F1 C4 1 F1 C5 1 F1 C6 1 F1 C7 1 F1 "
        + "C8 1 F1 C9 1 F1 C10 1 F1 C11 1 F1 C12 1 F1 C13 1 F1 C14 1 F2 C15 1 F2 C16 1 F2 "
        + "C17 1 F2 C18 1 F2 C19 1 F2 C20 1 F2 C21 1 F2 C22 1 F2 C23 1 F2 C24 1 F2 C25 1 F2 "
        + "C26 1 F2 C27 1 F3 C28 1 F3 C29 1 F3 C30 1 F3 C31 1 F3 C32 1 F3 C33 1 F3 C34 1 F3 "
        + "C35 1 F3 C36 1 F3 C37 1 F3 C38 1 F3 C39 1 F3 C40 1 F4 C41 1 F4 C42 1 F4 C43 1 F4 "
        + "C44 1 F4 C45 1 F4 C46 1 F4 C47 1 F4 C48 1 F4 C49 1 F4 C50 1 F4 C51 1 F4 C52 1 F4");
    StringBuilder ap = new StringBuilder();
    FreecellController fc = new FreecellController(rd, ap);
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 52, 1, false);
    assertEquals(true, ap.toString().contains("Try different card index.\n"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameNullDeck() throws IOException {
    Reader rd = new StringReader("");
    StringBuilder ap = new StringBuilder();
    FreecellController fc = new FreecellController(rd, ap);
    FreecellModel fm = new FreecellModel();
    fc.playGame(null, fm, 5, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameNullModel() throws IOException {
    Reader rd = new StringReader("");
    StringBuilder ap = new StringBuilder();
    FreecellController fc = new FreecellController(rd, ap);
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, null, 5, 1, false);
  }

  @Test
  public void testPlayGameGameWon() throws IOException {
    StringReader rd = new StringReader(wonGame);
    StringBuilder ap = new StringBuilder();
    FreecellController fc = new FreecellController(rd, ap);
    FreecellModel fm = new FreecellModel();
    List<Card> deck = fm.getDeck();
    fc.playGame(deck, fm, 52, 1, false);
    assertEquals(true, ap.toString().contains("F1:A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠,"
        + " 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F2:A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "F3:A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "F4:A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "O1:\n"
        + "C1: \n"
        + "C2: \n"
        + "C3: \n"
        + "C4: \n"
        + "C5: \n"
        + "C6: \n"
        + "C7: \n"
        + "C8: \n"
        + "C9: \n"
        + "C10: \n"
        + "C11: \n"
        + "C12: \n"
        + "C13: \n"
        + "C14: \n"
        + "C15: \n"
        + "C16: \n"
        + "C17: \n"
        + "C18: \n"
        + "C19: \n"
        + "C20: \n"
        + "C21: \n"
        + "C22: \n"
        + "C23: \n"
        + "C24: \n"
        + "C25: \n"
        + "C26: \n"
        + "C27: \n"
        + "C28: \n"
        + "C29: \n"
        + "C30: \n"
        + "C31: \n"
        + "C32: \n"
        + "C33: \n"
        + "C34: \n"
        + "C35: \n"
        + "C36: \n"
        + "C37: \n"
        + "C38: \n"
        + "C39: \n"
        + "C40: \n"
        + "C41: \n"
        + "C42: \n"
        + "C43: \n"
        + "C44: \n"
        + "C45: \n"
        + "C46: \n"
        + "C47: \n"
        + "C48: \n"
        + "C49: \n"
        + "C50: \n"
        + "C51: \n"
        + "C52: \n"
        + "Game over."));
  }



}










