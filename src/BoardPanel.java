import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel implements ScrabbleView {

    // FIXME: don't need two Boards
    private Board textBoard;
    private JButton [][] buttons;

    /* A Color to represent the double letter multiplier. */
    private final Color doubleLetterColour;

    /* A Color to represent the triple letter multiplier. */
    private final Color tripleLetterColour;

    /* Border colour of tiles */
    private final Color borderColour;

    /* A Color to represent the double word multiplier. */
    private final Color doubleWordColour;

    /* A Color to represent the triple word multiplier. */
    private final Color tripleWordColour;

    /**
     * Initializes a JPanel to hold the Scrabble board.
     * @param scrabbleModel A ScrabbleModel to model the board of.
     * @author Guy Morgenshtern - 101151430
     */
    public BoardPanel(ScrabbleModel scrabbleModel) {
        super();

        this.setLayout(new GridLayout(15, 15));  // Need to define size within board
        this.setSize(900,900);
        this.buttons = new JButton[15][15];                 // Place board size in place. Call Board Size Directly
        this.textBoard = scrabbleModel.getBoard();

        doubleLetterColour = new Color(186, 238, 254);
        tripleLetterColour = new Color(117, 213, 253);
        doubleWordColour = new Color(220, 186, 254);
        tripleWordColour = new Color(184, 117, 253);
        borderColour = new Color(211,211,211);

        for (int i= 0; i < 15; i++){          // Place board size.
            for (int j = 0; j < 15; j++){      // Place board size.
                buttons[i][j] = new JButton();
                buttons[i][j].setActionCommand(i + "," + j);
                int x = i;
                int y = j;
                buttons[i][j].addActionListener(e -> {
                    String[] clickedLocation = e.getActionCommand().split(",");
                    int[] l = new int[2];
                    System.out.println(e.getActionCommand());

                    String selectedLetter = scrabbleModel.getSelectedLetter();
                    l[0] = Integer.parseInt(clickedLocation[0]);
                    l[1] = Integer.parseInt(clickedLocation[1]);
                    buttons[x][y].setForeground(Color.BLACK);
                    buttons[x][y].setFont(new Font("Helvetica", Font.PLAIN, 12));

                    // Alternate Implementation for Squares Coloring

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

                    scrabbleModel.getCurrentMove().getCoords().add(new BoardClick(l, selectedLetter));
                    buttons[x][y].setText(selectedLetter);
                    /*
                    Gets the state of Scrabble board and sets letter at
                     */
                    this.textBoard.getScrabbleBoard()[x][y].setLetter(selectedLetter.charAt(0));
                    scrabbleModel.setSelectedLetter(""); //
                });
                this.add(buttons[i][j]);


            }
        }

        initializeButtonColor();

        this.setVisible(true);

    }

    /**
     * changes color of buttons on board to coordinate with designated multiplier.
     * @author Created + Refactored By: Francisco De Grano, 101147447
     */
    private void initializeButtonColor() {
        //  Triple Word Red Extract Method
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                Square tile = textBoard.getTileOnBoard(i,j);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(borderColour, 1, true));
                buttons[i][j].setBorderPainted(true);

                if (tile.isPremiumSquare()) {
                    buttons[i][j].setOpaque(true);
                    buttons[i][j].setForeground(Color.darkGray);
                    String label = "";
                    switch (tile.getMultiplier().getType()) {
                        case WORD -> {
                            label += "W";
                            if (tile.getMultiplier().getMultiplier() == 3) {
                                buttons[i][j].setBackground(tripleWordColour);
                                label = "T" + label;

                            } else {
                                buttons[i][j].setBackground(doubleWordColour);
                                label = "D" + label;
                            }
                        }
                        case LETTER -> {
                            label += "L";
                            if (tile.getMultiplier().getMultiplier() == 3) {
                                buttons[i][j].setBackground(tripleLetterColour);
                                label = "T" + label;
                            } else {
                                buttons[i][j].setBackground(doubleLetterColour);
                                label = "D" + label;
                            }
                        }
                    }
                    buttons[i][j].setText(label);
                    buttons[i][j].setFont(new Font("Helvetica", Font.PLAIN, 8));
                }
            }
        }
    }

    /**
     * Update button that was clicked
     * @param x
     * @param y
     * @param letter
     * @author Guy Morgenshtern - 101151430
     */
    private void updateButton(int x, int y, String letter) {
        if (!letter.equals("")) {
            buttons[x][y].setBorderPainted(false);
            buttons[x][y].setEnabled(false);
        }
        buttons[x][y].setText(letter);
        buttons[x][y].setForeground(Color.BLACK);
        buttons[x][y].setFont(new Font("Helvetica", Font.PLAIN, 12));
    }

    /**
     * update board
     * @param event
     * @author Guy Morgenshtern - 101151430
     */
    @Override
    public void update(ScrabbleEvent event) { // Update Game Model
        if (event.getMove().isValid()) {
            for (int i = 0; i < event.getMove().getCoords().size(); i++) {
                int x = event.getMove().getCoords().get(i).coords()[0];
                int y = event.getMove().getCoords().get(i).coords()[1];
                updateButton(x, y, String.valueOf(event.getBoard().getTileOnBoard(x,y).getLetter()));
            }
        } else {
            for (int i = 0; i < event.getMove().getCoords().size(); i++) {
                int x = event.getMove().getCoords().get(i).coords()[0];
                int y = event.getMove().getCoords().get(i).coords()[1];
                updateButton(x, y, "");
            }
        }

    }
}
