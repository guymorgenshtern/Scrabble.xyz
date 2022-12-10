import java.io.Serializable;

/**
 * A BoardClick holds a coordinate on the Board, and a Letter to place at that coordinate.
 * @author Guy Morgenshtern 101151430
 */
public class BoardClick implements Serializable {

    /** An array of integers representing the coordinates to place a letter at. The integer at index 0 represents the
     * x-coordinate. The integer at index 1 represents the y-coordinate.
     */
    private final int[] coords;

    /** A String representing the letter to place on the board. */
    private final String letter;

    /**
     * @param coords An array of integers to represent a coordinate on the Board.
     * @param letter A String to represent a letter to place at coords on the Board.
     * @author Guy Morgenshtern 101151430
     */
    public BoardClick(int[] coords, String letter) {
        this.coords = coords;
        this.letter = letter;
    }

    /**
     * @return An array of integers to represent a coordinate on the Board.
     * @author Guy Morgenshtern 101151430
     */
    public int[] getCoords() {
        return coords;
    }

    /**
     * @return A String to represent a letter to place at coords on the Board.
     * @author Guy Morgenshtern 101151430
     */
    public String getLetter() {
        return letter;
    }

}
