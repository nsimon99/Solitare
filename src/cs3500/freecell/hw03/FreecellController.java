package cs3500.freecell.hw03;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellOperations;
import cs3500.freecell.hw02.PileType;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


/**
 * Controller of the Freecell game. Implements from {@link cs3500.freecell.hw03.IFreecellController}
 */

public class FreecellController implements IFreecellController<Card> {

  private Readable rd;
  private Appendable ap;


  /**
   * constructor for FreecellController.
   *
   * @throws IllegalArgumentException if fields are null
   */

  public FreecellController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("readable or appendable null");
    }
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * returns correct pile based on users input.
   *
   * @param pile representing string input
   * @return correct pileType accoridng to input
   * @throws IllegalArgumentException if input is incorect
   */
  private PileType pileSource(String pile) throws IllegalArgumentException {
    PileType p;
    if (pile.charAt(0) == 'C') {
      p = PileType.CASCADE;
    } else if (pile.charAt(0) == 'O') {
      p = PileType.OPEN;
    } else if (pile.charAt(0) == 'F') {
      p = PileType.FOUNDATION;
    } else {
      throw new IllegalArgumentException("Invalid pile. Try again.");
    }

    return p;

  }

  @Override
  public void playGame(List<Card> deck, FreecellOperations<Card> model, int numCascades,
      int numOpens, boolean shuffle) {
    boolean validSource = false;
    boolean validCardIndex = false;
    boolean validDest = false;
    PileType source = null;
    Integer sourceNum = null;
    Integer cardIndex = null;
    Integer destNum = null;
    PileType dest = null;
    Scanner scanner = new Scanner(rd);
    String in;

    if (deck == null || model == null) {
      throw new IllegalArgumentException("null deck or model");
    } else {

      try {
        model.startGame(deck, numCascades, numOpens, shuffle);
      } catch (Exception e) {
        try {
          ap.append("Could not start game.");
        } catch (IOException ex) {
          throw new IllegalStateException("appendable");
        }
        return;
      }
      try {
        ap.append(model.getGameState()).append("\n");
      } catch (IOException e) {
        throw new IllegalStateException("appendable");
      }
    }

    while (!model.isGameOver()) {

      if (validDest) {
        try {
          model.move(source, sourceNum,
              cardIndex, dest,
              destNum);
        } catch (Exception e) {
          try {
            ap.append("Invalid move. Try again\n");
          } catch (IOException ex) {
            throw new IllegalStateException("appendable");
          }
        }
        try {
          ap.append(model.getGameState()).append("\n");
        } catch (IOException e) {
          throw new IllegalStateException("appendable");
        }
        if (model.isGameOver()) {
          try {
            ap.append(model.getGameState()).append("\nGame over.");
          } catch (IOException e) {
            throw new IllegalStateException("appendable");
          }
          return;
        }
        validSource = false;
        validCardIndex = false;
        validDest = false;
        source = null;
        sourceNum = null;
        cardIndex = null;
        destNum = null;
        dest = null;
      }

      try {
        in = scanner.next();
      } catch (Exception e) {
        throw new IllegalStateException("can not read from Readable");
      }

      if (in.equals("Q") || in.equals("q")) {
        try {
          ap.append("Game quit prematurely.");
        } catch (IOException e) {
          throw new IllegalStateException("appendable");
        }
        break;
      }

      if (!validSource && in.length() > 1) {

        try {
          source = this.pileSource(in);
          sourceNum = Integer.parseInt(in.substring(1)) - 1;
          validSource = true;
        } catch (Exception e) {
          try {
            ap.append("Try different source pile or source number.\n");
          } catch (IOException ex) {
            throw new IllegalStateException("appendable");
          }
          source = null;
          validSource = false;
        }

      } else if (!validCardIndex) {
        try {
          cardIndex = Integer.parseInt(in) - 1;
          validCardIndex = true;
        } catch (Exception e) {
          try {
            ap.append("Try different card index.\n");
          } catch (IOException ex) {
            throw new IllegalStateException("appendable");
          }
          cardIndex = null;
          validCardIndex = false;
        }


      } else if (!validDest && in.length() > 1) {
        try {
          dest = this.pileSource(in);
          destNum = Integer.parseInt(in.substring(1)) - 1;
          validDest = true;
        } catch (Exception e) {
          try {
            ap.append("Try different destination pile or destination number.\n");
          } catch (IOException ex) {
            throw new IllegalStateException("appendable");
          }
          dest = null;
          validDest = false;
        }
      }
    }
  }

}
