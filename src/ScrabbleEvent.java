import java.util.EventObject;

/**
 * Triggers when a game event occurs in Scrabble.
 */
public class ScrabbleEvent extends EventObject {

    /** A Player representing the current player. */
    private final Player currentPlayer;

    /** A GameStatus to determine if the game is finished or not. */
    private final ScrabbleModel.GameStatus gameStatus;

    /** A Board representing the current game board. */
    private final Board board;

    /** A ScrabbleMove representing the ScrabbleMove that was just played. */
    private final ScrabbleMove move;

    /**
     * Initializes a ScrabbleEvent.
     * @param scrabbleModel A ScrabbleModel representing the current state of the model.
     * @param move A ScrabbleMove representing the ScrabbleMove that was just played.
     * @param currentPlayer A Player representing the current player.
     * @param board A Board representing the current game board.
     * @param gameStatus A GameStatus to determine if the game is finished or not.
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleEvent(ScrabbleModel scrabbleModel, ScrabbleMove move, Player currentPlayer, Board board, ScrabbleModel.GameStatus gameStatus) {
        super(scrabbleModel);
        this.currentPlayer = currentPlayer;
        this.board = board;
        this.move = move;
        this.gameStatus = gameStatus;
    }

    /**
     * @return A ScrabbleModel representing the current state of the model.
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleModel getScrabbleModel() {
        return (ScrabbleModel) getSource();
    }

    /**
     * @return A Board representing the current game board.
     * @author Guy Morgenshtern 101151430
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return A ScrabbleMove representing the ScrabbleMove that was just played.
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleMove getMove() {
        return move;
    }

    /**
     * @return A Player representing the current player.
     * @author Guy Morgenshtern 101151430
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return A GameStatus to determine if the game is finished or not.
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleModel.GameStatus getGameStatus() { return gameStatus; }

}
