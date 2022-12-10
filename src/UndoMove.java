import java.io.Serializable;
import java.util.ArrayList;

/**
 * UndoMove stores a ScrabbleMove that was undone by a user.
 * @author Alexander Hum 101180821
 */
public class UndoMove implements Serializable {

    /** A ScrabbleMove representing the move that was undone. */
    private final ScrabbleMove move;

    /** An integer representing the previous score. */
    private final int score;

    /**
     * Initializes an UndoMove with the specified ScrabbleMove, score, and index of used letters list.
     * @param move A ScrabbleMove representing the move that was undone.
     * @param score An integer representing the previous score.
     * @author Alexander Hum 101180821
     */
    public UndoMove(ScrabbleMove move, int score) {
        this.move = move;
        this.score = score;
    }

    /**
     * @return An integer representing the previous score.
     * @author Alexander Hum 101180821
     */
    public int getScore() {
        return score;
    }

    /**
     * @return A ScrabbleMove representing the move that was undone.
     * @author Alexander Hum 101180821
     */
    public ScrabbleMove getMove() {
        return move;
    }

}
