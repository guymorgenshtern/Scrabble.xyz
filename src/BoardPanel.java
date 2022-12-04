import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel implements ScrabbleView {

    private final Board board;
    private final JButton [][] buttons;

    /* A Color to represent the double letter multiplier. */
    private final Color doubleLetterColour;

    /* A Color to represent the triple letter multiplier. */
    private final Color tripleLetterColour;

    /* A Color to represent the border of the tiles. */
    private final Color borderColour;

    /* A Color to represent the double word multiplier. */
    private final Color doubleWordColour;

    /* A Color to represent the triple word multiplier. */
    private final Color tripleWordColour;

    /** A Color to represent the default multiplier colour. */
    private final Color defaultMultiplierColour;

    private ScrabbleModel scrabbleModel;

    private BoardController controller;

    /**
     * Initializes a JPanel to display the Scrabble board.
     * @param scrabbleModel A ScrabbleModel to model the board of.
     * @author Guy Morgenshtern 101151430. Edited by Emily Tang 101192604.
     */
    public BoardPanel(ScrabbleModel scrabbleModel) {
        super();

        // initialize the board
        this.scrabbleModel = scrabbleModel;
        this.controller = new BoardController(scrabbleModel, this);
        board = scrabbleModel.getBoard();

        int numRows = board.getNumRows();
        int numCols = board.getNumCols();
        buttons = new JButton[numRows][numCols];
        setLayout(new GridLayout(numRows, numCols));
        setSize(900,900);

        // initialize the colours to use on the board
        doubleLetterColour = new Color(186, 238, 254);
        tripleLetterColour = new Color(117, 213, 253);
        doubleWordColour = new Color(220, 186, 254);
        tripleWordColour = new Color(184, 117, 253);
        borderColour = new Color(211,211,211);
        defaultMultiplierColour = new Color(253, 239, 117); // for any multiplier that's not a DW DL TW TL

        // initialize the JButtons
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setActionCommand(i + "," + j);

                // ActionListener for when user presses a JButton
                buttons[i][j].addActionListener(controller);
                add(buttons[i][j]);
            }
        }
        controller.updateButtons();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                initializeButtonColor(i, j);
            }
        }
        setVisible(true);
    }

    public JButton[][] getButtons() {
        return buttons;
    }

    /**
     * Changes color and text of buttons on board to coordinate with the designated multiplier.
     * @param i An integer representing the row coordinate of the square on the board.
     * @param j An integer representing the column coordinate of the square on the board.
     * @author Francisco De Grano 101147447. Edited by Guy Morgenshtern 101151430 and Emily Tang 101192604.
     */
    private void initializeButtonColor(int i, int j) {
        Square tile = board.getTileOnBoard(i, j);
        buttons[i][j].setBorder(BorderFactory.createLineBorder(borderColour, 1, true));
        buttons[i][j].setBorderPainted(true);

        if (tile.isPremiumSquare()) {
            buttons[i][j].setOpaque(true);
            buttons[i][j].setForeground(Color.darkGray);
            String label = "";

            Color backgroundColour = defaultMultiplierColour;
            int multiplierFactor = tile.getMultiplier().getMultiplier();

            switch (tile.getMultiplier().getType()) {
                case WORD -> {
                    label += "W";
                    if (multiplierFactor == 3) {
                        backgroundColour = tripleWordColour;
                        label = "T" + label;

                    } else if (multiplierFactor == 2) {
                        backgroundColour = doubleWordColour;
                        label = "D" + label;
                    } else {
                        label = tile.getMultiplier().getMultiplier() + label;
                    }
                }
                case LETTER -> {
                    label += "L";
                    if (multiplierFactor == 3) {
                        backgroundColour = tripleLetterColour;
                        label = "T" + label;
                    } else if (multiplierFactor == 2) {
                        backgroundColour = doubleLetterColour;
                        label = "D" + label;
                    } else {
                        label = tile.getMultiplier().getMultiplier() + label;
                    }
                }
            }

            buttons[i][j].setBackground(backgroundColour);
            buttons[i][j].setText(label);
            buttons[i][j].setFont(new Font("Helvetica", Font.PLAIN, 8));
        }
    }

    /**
     * Update button that was clicked
     * @param x
     * @param y
     * @param letter
     * @author Guy Morgenshtern 101151430
     */
    private void updateButton(int x, int y, String letter, ScrabbleMove move) {
        if (!letter.equals("")) {
            buttons[x][y].setBorderPainted(false);
            buttons[x][y].setForeground(Color.BLACK);
            buttons[x][y].setFont(new Font("Helvetica", Font.BOLD, 12));
            if (move.getMoveType() == ScrabbleMove.MoveType.INIT) {
                buttons[x][y].setText(letter);
            }
            if (move.getMoveType() != ScrabbleMove.MoveType.UNDO && buttons[x][y].getActionListeners().length > 0) {
                buttons[x][y].removeActionListener(controller);
            } else if (buttons[x][y].getActionListeners().length == 0) {
                buttons[x][y].addActionListener(controller);
            }
        }

        if (!move.isValid()) {
            buttons[x][y].setText("");
            buttons[x][y].addActionListener(controller);
            initializeButtonColor(x,y);
        } else {
            buttons[x][y].setText(letter);
        }


        // repaint border
        buttons[x][y].setBorder(BorderFactory.createLineBorder(borderColour, 1, true));
        buttons[x][y].setBorderPainted(true);
    }

    /**
     * update board
     * @param event
     * @author Guy Morgenshtern 101151430
     */
    @Override
    public void update(ScrabbleEvent event) { // Update Game Model
        for (int i = 0; i < event.getMove().getCoords().size(); i++) {
            int x = event.getMove().getCoords().get(i).coords()[0];
            int y = event.getMove().getCoords().get(i).coords()[1];
            updateButton(x, y, String.valueOf(event.getBoard().getTileOnBoard(x,y).getLetter()), event.getMove());
        }
    }

}
