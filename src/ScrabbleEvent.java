import java.util.EventObject;

public class ScrabbleEvent extends EventObject {
    private final Player currentPlayer;
    private final ScrabbleModel.Status status;
    private final ScrabbleModel.GameStatus gameStatus;
    private final Board board;
    private final ScrabbleMove move;
    private boolean validMove;

    public ScrabbleEvent(ScrabbleModel scrabbleModel, ScrabbleMove move, Player currentPlayer, Board board, ScrabbleModel.Status status, ScrabbleModel.GameStatus gameStatus) {
        super(scrabbleModel);
        this.status = status;
        this.currentPlayer = currentPlayer;
        this.board = board;
        this.move = move;
        this.gameStatus = gameStatus;
    }

    public ScrabbleModel getScrabbleModel() {
        return (ScrabbleModel) getSource();
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

    public ScrabbleModel.GameStatus getGameStatus() {return gameStatus; }

    public boolean isValidMove() {
        return validMove;
    }

    public void setValidMove(boolean validMove) {
        this.validMove = validMove;
    }
}
