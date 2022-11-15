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
     * creates ScrabbleMove
     * @param word A string representation for the word
     * @param direction A Game.Direction representation for the direction
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleMove(String word, ArrayList<int[]> coords[][], ScrabbleModel.Direction direction) {
        this.word = word;
        this.direction = direction;
        this.isValid = false;
    }



    public ScrabbleMove(ArrayList<BoardClick> coords, ScrabbleModel.Direction direction, Player player) {
        this.coords = coords;
        this.direction = direction;
        this.isValid = false;
        this.p = player;
    }

    public Player getPlayer() {
        return p;
    }

    public void setPlayer(Player p) {
        this.p = p;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setCoords(ArrayList<BoardClick> coords) {
        this.coords = coords;
    }

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
