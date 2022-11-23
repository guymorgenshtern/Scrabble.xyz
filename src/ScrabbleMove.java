import java.util.ArrayList;

/**
 * Class ScrableMove allows the user
 * @author Guy Morgenshtern 101151430
 */
public class ScrabbleMove {
    // initializes the word, row, column and direction
    private String word;
    private ArrayList<BoardClick> coords;
    private ScrabbleModel.Direction direction;
    private boolean isValid;
    private Player p;

    public ScrabbleMove() {
        this.coords = new ArrayList<>();
    }

    /**
     * Constructor
     * @param coords
     * @param direction
     * @param player
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleMove(ArrayList<BoardClick> coords, ScrabbleModel.Direction direction, Player player) {
        this.coords = coords;
        this.direction = direction;
        this.isValid = false;
        this.p = player;
    }

    /**
     * @return player of move
     *  @author Guy Morgenshtern 101151430
     */
    public Player getPlayer() {
        return p;
    }

    /**
     * sets player
     * @param p
     * @author Guy Morgenshtern 101151430
     */
    public void setPlayer(Player p) {
        this.p = p;
    }

    /**
     *
     * @return move validity
     * @author Guy Morgenshtern 101151430
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * set validity of move
     * @param valid
     * @author Guy Morgenshtern 101151430
     */
    public void setValid(boolean valid) {
        isValid = valid;
    }

    /**
     * set word
     * @param word
     * @author Guy Morgenshtern 101151430
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * set coordinates of move
     * @param coords of move
     * @author Guy Morgenshtern 101151430
     */
    public void setCoords(ArrayList<BoardClick> coords) {
        this.coords = coords;
    }

    /**
     * set direction of move
     * @param direction
     * @author Guy Morgenshtern 101151430
     */
    public void setDirection(ScrabbleModel.Direction direction) {
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
     * return move coordinates
     * @return arraylist of move coordinates
     * @author Guy Morgenshtern 101151430
     */
    public ArrayList<BoardClick> getCoords() {
        return coords;
    }

    /**
     * gets the direction
     * @return direction as a direction in the game
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleModel.Direction getDirection() {
        return direction;
    }
}
