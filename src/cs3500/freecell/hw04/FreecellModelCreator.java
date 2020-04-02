package cs3500.freecell.hw04;

import cs3500.freecell.hw02.Card;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.FreecellOperations;

/**
 * define a public enum GameType with two possible values SINGLEMOVE and MULTIMOVE. It should offer
 * a static factory method create(GameType type) that returns either a FreecellModel or an object of
 * your multi-card-move model, depending on the value of the parameter.
 */

public class FreecellModelCreator {

  /**
   * Enums representing single move model and multi-move model.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * method that returns desired model depending on input.
   *
   * @param type representing desired model
   * @throws IllegalArgumentException if null is given
   */
  public static FreecellOperations<Card> create(GameType type)
      throws IllegalArgumentException {
    FreecellOperations<Card> fm;
    if (type == GameType.SINGLEMOVE) {
      fm = new FreecellModel();
    } else if (type == GameType.MULTIMOVE) {
      fm = new FreecellMultiMoveModel();
    } else {
      throw new IllegalArgumentException("null GameType");
    }
    return fm;
  }

}
