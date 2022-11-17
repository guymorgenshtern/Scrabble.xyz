/**
 * A BotPlayer in the game of Scrabble.
 */
public class BotPlayer extends Player {

    /**
     * Creates a BotPlayer with the specified name. BotPlayer starts off with an empty hand and a score of zero.
     * @param name A String representing the name of the BotPlayer.
     * @author Emily Tang 101192604
     */
    public BotPlayer(String name) {
        super(name);
    }

    /*
    private boolean hasLettersNeededForWord(String input)
    - could be helpful to create legal words... would need to temporarily pass in an additional letter to my hand

    * start small, need to be able to fit bot moves in while board keeps expanding *

    1. find a spot to put words... scrabblemodel can pass in board
    2. find a word to put in... probably need a library
    3. plop in a word... what a daunting task
     */
}
