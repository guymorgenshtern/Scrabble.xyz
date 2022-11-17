import java.util.EventObject;

public class ScrabbleEvent extends EventObject {
    private Player currentPlayer;
    private ScrabbleModel.Status status;
    private ScrabbleModel.GameStatus gameStatus;
    private Board board;
    private ScrabbleMove move;

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
}
