/**
 * Class ScrableMove allows the user
 * @author Guy Morgenshtern 101151430
 */
public class ScrabbleMove {
    // initializes the word, row, column and direction
    private String word;
    private int coords[][];
    private Game.Direction direction;
    private boolean isValid;
    private Player p;

    /**
     * creates ScrabbleMove
     * @param word A string representation for the word
     * @param direction A Game.Direction representation for the direction
     * @author Guy Morgenshtern 101151430
     */
    public ScrabbleMove(String word, int coords[][], Game.Direction direction) {
        this.word = word;
        this.direction = direction;
        this.isValid = false;
    }

    public ScrabbleMove(int coords[][], Game.Direction direction, Player player) {
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

    public void setCoords(int[][] coords) {
        this.coords = coords;
    }

    public void setDirection(Game.Direction direction) {
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

    public int[][] getCoords() {
        return coords;
    }

    /**
     * gets the direction
     * @return direction as a direction in the game
     * @author Guy Morgenshtern 101151430
     */
    public Game.Direction getDirection() {
        return direction;
    }
}
