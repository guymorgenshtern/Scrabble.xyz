import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel implements ScrabbleView {

    // FIXME: don't need two Boards
    private Board textBoard;
    private JButton [][] buttons;

    // Set Colors for Multipliers On Board
    Color TWRed = new Color(179,0, 0);      // Color; Triple Word Squares WebSafe #B30000
    Color DWRed = new Color(255,128, 128);  // Color; Double Word Squares WebSafe #FF8080

    Color TLBlue = new Color(77, 77, 255);   // Color; Triple Letter Squares WebSafe #4D4DFF
    Color DLBlue = new Color(204, 204, 255); // Color; Double Letter Squares WebSafe #CCCCFF

    Color BorderColour = new Color(211,211,211);


    public BoardPanel(ScrabbleModel scrabbleModel) {
        super();

        this.setLayout(new GridLayout(15, 15));  // Need to define size within board
        this.setSize(900,900);
        this.buttons = new JButton[15][15];                 // Place board size in place. Call Board Size Directly
        this.textBoard = scrabbleModel.getBoard();

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
                    scrabbleModel.setSelectedLetter("");
                });
                this.add(buttons[i][j]);


            }
        }

        changeButtonColor(TWRed, DWRed, TLBlue, DLBlue);

        this.setVisible(true);

    }

    /**
     * Method Extract, changes color of buttons on board to coordinate with designated multiplier.
     *
     * @param TWRed     rgb value for Triple Word Score Squares on board
     * @param DWRed     rgb value for Double Word Score Squares on Board
     * @param TLBlue    rgb value for Triple Letter Score Squares on Board
     * @param DLBlue    rgb value for Double Letter Score Squares on Board
     *
     * @author Created + Refactored By: Francisco De Grano, 101147447
     */
    private void changeButtonColor(Color TWRed, Color DWRed, Color TLBlue, Color DLBlue) {
        //  Triple Word Red Extract Method
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons.length; j++) {
                Square tile = textBoard.getTileOnBoard(i,j);
                buttons[i][j].setBorder(BorderFactory.createLineBorder(BorderColour, 1, true));
                buttons[i][j].setBorderPainted(true);

                if (tile.isPremiumSquare()) {
//                    buttons[i][j].setContentAreaFilled(true);
                    buttons[i][j].setOpaque(true);
                    //buttons[i][j].setBorderPainted(false);
                    buttons[i][j].setForeground(Color.BLACK);
                    switch (tile.getMultiplier().getType()) {
                        case WORD -> {
                            if (tile.getMultiplier().getMultiplier() == 3) {
                                buttons[i][j].setBackground(TWRed);

                            } else {
                                buttons[i][j].setBackground(DWRed);
                            }
                        }
                        case LETTER -> {
                            if (tile.getMultiplier().getMultiplier() == 3) {
                                buttons[i][j].setBackground(TLBlue);
                            } else {
                                buttons[i][j].setBackground(DLBlue);
                            }
                        }
                    }
                }
            }
        }
    }

    private void updateButton(int x, int y, String letter) {
        if (!letter.equals("")) {
            buttons[x][y].setBackground(Color.BLUE);
            buttons[x][y].setBorderPainted(false);
            buttons[x][y].setEnabled(false);
        }
        this.buttons[x][y].setText(letter);

    }

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
