/**
 * Class ScrableMove allows the user
 * @author Guy Morgenshtern 101151430
 */
public class ScrabbleMove {
    // initializes the word, row, column and direction
    private String word;
    private int row, column;
    private Game.Direction direction;

    /**
     * creates ScrabbleMove
     * @param word A string representation for the word
     * @param row An integer representation for the row
     * @param column An integer representation for the column
     * @param direction A Game.Direction representation for the direction
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleMove(String word, int row, int column, Game.Direction direction) {
        this.word = word;
        this.row = row;
        this.column = column;
        this.direction = direction;
    }

    /**
     * gets the word
     * @return word as a string
     * @author Guy Morgenshtern 101151430
     */
    public String getWord() {
        return word;
    }
    /**
     * gets the row
     * @return row as an integer
     * @author Guy Morgenshtern 101151430
     */
    public int getRow() {
        return row;
    }
    /**
     * gets the column
     * @return column as an integer
     * @author Guy Morgenshtern 101151430
     */
    public int getColumn() {
        return column;
    }
    /**
     * gets the direction
     * @return direction as a direction in the game
     * @author Guy Morgenshtern 101151430
     */
    public Game.Direction getDirection() {
        return direction;
    }
}
