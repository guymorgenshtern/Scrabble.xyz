public class ScrabbleMove {
    private String word;
    private int row, column;
    private Game.Direction direction;

    public ScrabbleMove(String word, int row, int column, Game.Direction direction) {
        this.word = word;
        this.row = row;
        this.column = column;
        this.direction = direction;
    }

    public String getWord() {
        return word;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Game.Direction getDirection() {
        return direction;
    }
}
