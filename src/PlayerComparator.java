import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

    /**
     * Overriden compare method for player
     * @author Guy Morgenshtern - 101151430
     */
    @Override
    public int compare(Player p1, Player p2) {
        return Integer.compare(p1.getScore(), p2.getScore());
    }

}

