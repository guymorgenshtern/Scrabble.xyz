/**
 * A BoardClick holds a coordinate on the Board, and a Letter to place at that coordinate.
 *
 * @param coords An array of integers to represent a coordinate on the Board.
 * @param letter A String to represent a letter to place at coords on the Board.
 */
public record BoardClick(int[] coords, String letter) {

    /**
     * @return An array of integers to represent a coordinate on the Board.
     */
    @Override
    public int[] coords() {
        return coords;
    }

    /**
     * @return A String to represent a letter to place at coords on the Board.
     */
    @Override
    public String letter() {
        return letter;
    }
}
