import java.io.Serializable;

/**
 * A Multiplier can be added to a Square to multiply how many points a letter/word is worth. A Multiplier counts only on
 * the turn in which it is played.
 * @author Emily Tang 101192604. Edited by Guy Morgenshtern 101151430.
 */
public class Multiplier implements Serializable {

    /** An enumeration to represent the type of the multiplier. */
    public enum Type { LETTER, WORD }

    /** A Multiplier can either be a Letter, or a Word Multiplier. */
    private final Type type;

    /** An integer to represent the size of the multiplier. */
    private final int multiplier;

    /** A boolean to represent whether the multiplier has been used, or not. */
    private boolean used;

    /**
     * Creates a Multiplier with the specified size.
     * @param type A multiplier can either be a letter, or a word multiplier.
     * @param multiplier An integer to represent the size of the multiplier.
     * @author Emily Tang 101192604
     */
    public Multiplier(Type type, int multiplier) {
        this.type = type;
        this.multiplier = multiplier;
        used = false;
    }

    /**
     * @return The Type of Multiplier.
     * @author Emily Tang 101192604
     */
    public Type getType() {
        return type;
    }

    /**
     * @param score An integer representing the score of the letter/word.
     * @return An integer representing the score of the letter/word after applying the multiplier.
     * @author Emily Tang 101192604
     */
    public int calculateScore(int score) {
        // determine if the multiplier has not been used already
        if (!used) {
            used = true;
            return multiplier * score;
        }
        return score; // multiplier has been used already, do not multiply the score
    }

    /**
     * @return An integer representing the factor of the Multiplier.
     * @author Guy Morgenshtern 101151430
     */
    public int getMultiplier() {
        return multiplier;
    }

}
