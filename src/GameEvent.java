import java.util.ArrayList;
import java.util.EventObject;

/**
 * A GameEvent for the scrabble game
 *
 * @author Alexander Hum 101180821
 */
public class GameEvent extends EventObject {
    /** An arraylist of players */
    private ArrayList<Player> playerList;

    /**
     *
     * @param model A model for the scrabble game
     * @param playerList A playerlist for players
     */
    public GameEvent(ScrabbleModel model, ArrayList<Player> playerList) {
        super(model);
        this.playerList = playerList;
    }

    /**
     *
     * @return the model
     */
    public ScrabbleModel getModel() {
        return (ScrabbleModel) getSource();
    }

    /**
     * gets an arraylist of the players
     * @return playerList for players
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }



}
