/**
 * A BoardClick holds a coordinate on the Board, and a Letter to place at that coordinate.
 */
public class BoardClick {

    /** An array of integers to represent a coordinate on the Board. */
    private final int[] coords;

    /** A String to represent a letter to place at coords on the Board. */
    private final String letter;

    /**
     * Initializes a BoardClick with the specified coordinates and letter.
     * @param coords An integer array to represent a coordinate on the Board. The zeroth index should hold the
     *               x-coordinate, and the first index should hold the y-coordinate.
     * @param letter A String to represent a letter to place at coords on the Board.
     */
    public BoardClick(int[] coords, String letter) {
        this.coords = coords;
        this.letter = letter;
    }

    /**
     * @return An array of integers to represent a coordinate on the Board.
     */
    public int[] getCoords() {
        return coords;
    }

    /**
     * @return A String to represent a letter to place at coords on the Board.
     */
    public String getLetter() {
        return letter;
    }
}
