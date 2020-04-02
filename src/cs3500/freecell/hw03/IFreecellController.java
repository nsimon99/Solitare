package cs3500.freecell.hw03;

import cs3500.freecell.hw02.FreecellOperations;
import java.util.List;


/**
 * Interface for the Freecell controller.
 *
 * @param <Card> card type
 */
public interface IFreecellController<Card> {

  /**
   * This method should start a new game of Freecell using the provided model, number of cascade and
   * open piles, and the provided deck. If "shuffle" is set to false, the deck must be used as-is,
   * else the deck should be shuffled. It should throw an IllegalStateException if the controller
   * has not been initialized properly to receive input and transmit output.
   *
   * @param deck        represents deck of cards.
   * @param model       represents model representation.
   * @param numCascades represents the given number of cascasding piles.
   * @param numOpens    represents the given number of open piles.
   * @param shuffle     if deck is shuffled or not.
   * @throws IllegalArgumentException if the controller has not been initialized properly to receive
   *                                  input and transmit output.
   */

  void playGame(List<Card> deck, FreecellOperations<Card> model, int numCascades,
      int numOpens, boolean shuffle);

}
