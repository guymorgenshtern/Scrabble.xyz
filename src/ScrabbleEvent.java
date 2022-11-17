import java.util.EventObject;

public class ScrabbleEvent extends EventObject {
    private Player currentPlayer;
    private ScrabbleModel.Status status;
    private Board board;
    private ScrabbleMove move;
    private boolean validMove;

    public ScrabbleEvent(ScrabbleModel scrabbleModel, ScrabbleMove move, Player currentPlayer, Board board, ScrabbleModel.Status status) {
        super(scrabbleModel);
        this.status = status;
        this.currentPlayer = currentPlayer;
        this.board = board;
        this.move = move;
        this.validMove = false;
    }

    public Board getBoard() {
        return board;
    }

    public ScrabbleMove getMove() {
        return move;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ScrabbleModel.Status getStatus() {
        return status;
    }

    public boolean isValidMove() {
        return validMove;
    }

    public void setValidMove(boolean validMove) {
        this.validMove = validMove;
    }
}
