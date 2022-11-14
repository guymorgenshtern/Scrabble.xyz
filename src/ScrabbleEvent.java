import java.util.EventObject;

public class ScrabbleEvent extends EventObject {
    private Player currentPlayer;
    private Game.Status status;
    private Board board;
    private int coords[][];

    public ScrabbleEvent(Game game, int coords[][], Player currentPlayer, Board board, Game.Status status) {
        super(game);
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

    public Game.Status getStatus() {
        return status;
    }
}
