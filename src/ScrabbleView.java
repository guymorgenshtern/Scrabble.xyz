/*
Francisco De Grano

    ScrabbleView Interface

    Following MVC the following is an interface and holds the property of a
    "View" updating only.
 */
public interface ScrabbleView {

    /**
     * Updates the "look" of the GUI.
     * @param event A ScrabbleEvent that occurs.
     * @author Francisco DeGrano 101147447
     */
    void update(ScrabbleEvent event);
}
