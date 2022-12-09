import java.io.Serializable;
import java.util.ArrayList;

/**
 * ScrabbleMove represents a move a player made in Scrabble.
 * @author Guy Morgenshtern 101151430
 */
public class ScrabbleMove implements Serializable {

    /** A String representing a word. */
    private String word;

    /** An ArrayList of BoardClicks representing the coordinates of the word. */
    private ArrayList<BoardClick> coords;

    /** A Direction representing the orientation of the word the player placed on the board. */
    private ScrabbleModel.Direction direction;

    /** A boolean representing if the word is valid or not. */
    private boolean isValid;

    /** A Player representing the player that placed the word. */
    private Player p;

    private boolean isRedo;

    private boolean isUndo;

    public enum MoveType {
        UNDO,
        REDO,
        DEFAULT,
        INIT
    }

    private MoveType moveType;

    /**
     * Initializes a ScrabbleMove with no coordinates.
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleMove() {
        coords = new ArrayList<>();
    }

    /**
     * Initializes a ScrabbleMove with the specified coordinates, direction, and player.
     * @param coords An ArrayList of BoardClicks representing the coordinates of the word.
     * @param direction A Direction representing the orientation of the word the player placed on the board.
     * @param player A Player representing the player that placed the word.
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleMove(ArrayList<BoardClick> coords, ScrabbleModel.Direction direction, Player player) {
        this.coords = coords;
        this.direction = direction;
        isValid = false;
        this.p = player;
        isRedo = false;
        isUndo = false;
        this.moveType = MoveType.DEFAULT;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    /**
     * @return A Player representing the player that placed the word.
     * @author Guy Morgenshtern 101151430
     */
    public Player getPlayer() {
        return p;
    }

    /**
     * Sets the player to the specified player.
     * @param p A Player representing the player that placed the word.
     * @author Guy Morgenshtern 101151430
     */
    public void setPlayer(Player p) {
        this.p = p;
    }

    /**
     * @return A boolean representing if the word is valid or not.
     * @author Guy Morgenshtern 101151430
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Sets the word validity to the specified boolean.
     * @param valid A boolean representing the validity of the word.
     * @author Guy Morgenshtern 101151430
     */
    public void setValid(boolean valid) {
        isValid = valid;
    }

    /**
     * Sets the word to the specified string.
     * @param word A String representing the word to set.
     * @author Guy Morgenshtern 101151430
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Sets the coordinates to be the specified coordinates in the ArrayList of BoardClicks.
     * @param coords An ArrayList of BoardClicks representing the coordinates of the ScrabbleMove.
     * @author Guy Morgenshtern 101151430
     */
    public void setCoords(ArrayList<BoardClick> coords) {
        this.coords = coords;
    }

    /**
     * Sets the direction of the ScrabbleMove to the specified direction.
     * @param direction A Direction to set orientation in the ScrabbleMove.
     * @author Guy Morgenshtern 101151430
     */
    public void setDirection(ScrabbleModel.Direction direction) {
        this.direction = direction;
    }

    /**
     * @return A String representing the word.
     * @author Guy Morgenshtern 101151430
     */
    public String getWord() {
        return word;
    }

    /**
     * @return An ArrayList of BoardClicks representing the coordinates of the ScrabbleMove.
     * @author Guy Morgenshtern 101151430
     */
    public ArrayList<BoardClick> getCoords() {
        return coords;
    }

    /**
     * @return A Direction representing the direction of the orientation of the word in ScrabbleMove.
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleModel.Direction getDirection() {
        return direction;
    }

}
