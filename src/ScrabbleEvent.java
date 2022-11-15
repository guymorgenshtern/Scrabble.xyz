import java.util.EventObject;

public class ScrabbleEvent extends EventObject {
    private Player currentPlayer;
    private ScrabbleModel.Status status;
    private Board board;
    private int coords[][];

    public ScrabbleEvent(ScrabbleModel scrabbleModel, int coords[][], Player currentPlayer, Board board, ScrabbleModel.Status status) {
        super(scrabbleModel);
        this.status = status;
        this.currentPlayer = currentPlayer;
        this.board = board;
        this.coords = coords;
    }

    public Board getBoard() {
        return board;
    }

    public int[][] getCoords() {
        return coords;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ScrabbleModel.Status getStatus() {
        return status;
    }
}
