/**
 * A Multiplier can be added to a Square to multiply how many points a letter/word is worth. A Multiplier counts only on
 * the turn in which it is played.
 */
public abstract class Multiplier {

    /** An integer to represent the size of the multiplier. */
    private final int MULTIPLIER;

    /** A boolean to represent whether the multiplier has been used, or not. */
    private boolean used;

    /**
     * Creates a Multiplier with the specified size.
     * @param multiplier An integer to represent the size of the multiplier.
     */
    public Multiplier(int multiplier) {
        MULTIPLIER = multiplier;
        used = false;
    }

    /**
     * @param score An integer representing the score of the letter/word.
     * @return An integer representing the score of the letter/word after applying the multiplier.
     */
    public int calculateScore(int score) {
        // determine if the multiplier has not been used already
        if (!used) {
            used = true;
            return MULTIPLIER * score;
        }
        return score; // multiplier has been used already, do not multiply the score
    }

}
