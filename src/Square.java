/**
 * A Square in the game of Scrabble. A Letter can be placed on a Square, and a Square can have a Multiplier.
 */
public class Square {

    /** A Letter that is placed on the Square. */
    private char letter;

    /** A Letter/Word Multiplier. */
    private Multiplier multiplier;

    /**
     * Creates a Square.
     * @author Emily Tang 101192604
     */
    public Square() { }

    /**
     * Creates a Premium Letter/Word Square with the specified multiplier.
     * @param multiplier A Multiplier to add to the Square.
     * @author Emily Tang 101192604
     */
    public Square(Multiplier multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * @return The Letter that is on the Square.
     * @author Emily Tang 101192604
     */
    public char getLetter() {
        return letter;
    }

    /**
     * @param letter A Letter to be placed on the Square.
     * @return True, if the Letter was placed on the Square successfully. False, if not.
     * @author Emily Tang 101192604
     */
    public boolean setLetter(char letter) {
        if (letter != ' ') {
            this.letter = letter;
            return true;
        }
        return false;
    }

    /**
     * @return True, if the Square is a Premium Square. False, if not.
     * @author Emily Tang 101192604
     */
    public boolean isPremiumSquare() {
        return multiplier != null;
    }

    public Multiplier getMultiplier() {
        return multiplier;
    }
}
