import java.io.Serializable;

/**
 * A Square in the game of Scrabble. A Letter can be placed on a Square, and a Square can have a Multiplier.
 * @author Emily Tang 101192604
 */
public class Square implements Serializable {

    /** A Letter that is placed on the Square. */
    private char letter;

    /** A Letter/Word Multiplier. */
    private final Multiplier multiplier;

    /**
     * Creates a Square with a blank letter.
     * @author Emily Tang 101192604
     */
    public Square() {
        letter = ' ';
        multiplier = null;
    }

    /**
     * Creates a Premium Letter/Word Square with a blank letter and the specified multiplier.
     * @param multiplier A Multiplier to add to the Square.
     * @author Emily Tang 101192604
     */
    public Square(Multiplier multiplier) {
        letter = ' ';
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
     * @author Emily Tang 101192604
     */
    public void setLetter(char letter) {
        this.letter = letter;
    }

    /**
     * @return True, if the Square is a Premium Square. False, if not.
     * @author Emily Tang 101192604
     */
    public boolean isPremiumSquare() {
        return multiplier != null;
    }

    /**
     * @return A Letter/Word Multiplier that is associated with the Square.
     * @author Emily Tang 101192604
     */
    public Multiplier getMultiplier() {
        return multiplier;
    }

}
