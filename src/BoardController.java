import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/**
 * Handles user interactions with the BoardPanel.
 * @author Guy Morgenshtern 101151430
 */
public class BoardController implements ActionListener, Serializable {

    /** A ScrabbleModel to update. */
    private final ScrabbleModel model;

    /** A BoardPanel to update. */
    private final BoardPanel boardPanel;

    /** A 2D array of JButtons representing the board in BoardPanel. */
    private JButton[][] buttons;

    /**
     * Creates a BoardController with the specified ScrabbleModel and the BoardPanel.
     * @param model A ScrabbleModel to update.
     * @param boardPanel A BoardPanel to update.
     * @author Guy Morgenshtern 101151430
     */
    public BoardController(ScrabbleModel model, BoardPanel boardPanel) {
        this.model = model;
        this.boardPanel = boardPanel;
    }

    /**
     * Updates the JButtons to reflect the current state of the JButtons in the BoardPanel.
     * @author Guy Morgenshtern 101151430
     */
    public void updateButtons() {
        buttons = boardPanel.getButtons();
    }

    /**
     * Handles user interactions with the BoardPanel.
     * @param e The event to be processed when the user interacts with the BoardPanel.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        // determine which JButton was pressed and which letter was selected
        String[] clickedLocation = actionCommand.split(",");
        int[] coords = new int[] { Integer.parseInt(clickedLocation[0]), Integer.parseInt(clickedLocation[1]) };
        int x = coords[0];
        int y = coords[1];
        String selectedLetter = model.getSelectedLetter();
        buttons[x][y].setForeground(Color.BLACK);
        buttons[x][y].setFont(new Font("Helvetica", Font.PLAIN, 12));

        // support for blank tile
        if (selectedLetter.equals(" ")) {
            String blankTileInput = "";
            while (blankTileInput.equals("")) {
                blankTileInput = JOptionPane.showInputDialog("Please enter a valid alphabetical character.");
                // if user enters nothing, must set blankTileInput to be a String before calling matches()
                if (blankTileInput == null) {
                    blankTileInput = "";
                }
                if (blankTileInput.matches("[a-zA-Z+]") && blankTileInput.length() == 1) {
                    selectedLetter = blankTileInput.toUpperCase();
                } else {
                    blankTileInput = "";
                }
            }
        }

        model.getCurrentMove().getCoords().add(new BoardClick(coords, selectedLetter));
        buttons[x][y].setText(selectedLetter);

        // gets the state of the Scrabble board and sets the letter at the clicked location
        model.getBoard().getScrabbleBoard()[x][y].setLetter(selectedLetter.charAt(0));
        model.setSelectedLetter("");
    }
}

