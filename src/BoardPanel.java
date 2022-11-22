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


    public BoardPanel(ScrabbleModel scrabbleModel) {
        super();

        this.setLayout(new GridLayout(15, 15));  // Need to define size within board
        this.setSize(900,900);
        this.buttons = new JButton[15][15];                 // Place board size in place. Call Board Size Directly

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
                    scrabbleModel.setSelectedLetter(""); //
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
        for (int i = 0; i <= 14; i += 7) {
            buttons[0][i].setBackground(TWRed);
            buttons[0][i].setForeground(Color.WHITE);
            buttons[0][i].setText("TW");
            buttons[0][i].setOpaque(true);
            buttons[0][i].setBorderPainted(false);
        }

        for (int j : new int[]{0, 14}) {
            buttons[7][j].setBackground(TWRed);
            buttons[7][j].setForeground(Color.WHITE);
            buttons[7][j].setText("TW");
            buttons[7][j].setOpaque(true);
            buttons[7][j].setBorderPainted(false);
        }

        for (int i = 0; i <= 14; i += 7) {
            buttons[14][i].setBackground(TWRed);
            buttons[14][i].setForeground(Color.WHITE);
            buttons[14][i].setText("TW");
            buttons[14][i].setOpaque(true);
            buttons[14][i].setBorderPainted(false);
        }

        //  Double Word Red Extract Method
        for (int i : new int[]{1, 13}) {
            buttons[1][i].setBackground(DWRed);
            buttons[1][i].setForeground(Color.WHITE);
            buttons[1][i].setText("DW");
            buttons[1][i].setOpaque(true);
            buttons[1][i].setBorderPainted(false);
        }

        for (int i : new int[]{2, 12}) {
            buttons[2][i].setBackground(DWRed);
            buttons[2][i].setForeground(Color.WHITE);
            buttons[2][i].setText("DW");
            buttons[2][i].setOpaque(true);
            buttons[2][i].setBorderPainted(false);
        }

        for (int i : new int[]{3, 11}) {
            buttons[3][i].setBackground(DWRed);
            buttons[3][i].setForeground(Color.WHITE);
            buttons[3][i].setText("DW");
            buttons[3][i].setOpaque(true);
            buttons[3][i].setBorderPainted(false);
        }

        for (int i : new int[]{4, 10}) {
            buttons[4][i].setBackground(DWRed);
            buttons[4][i].setForeground(Color.WHITE);
            buttons[4][i].setText("DW");
            buttons[4][i].setOpaque(true);
            buttons[4][i].setBorderPainted(false);
        }

        for (int i : new int[]{10, 4}) {
            buttons[10][i].setBackground(DWRed);
            buttons[10][i].setForeground(Color.WHITE);
            buttons[10][i].setText("DW");
            buttons[10][i].setOpaque(true);
            buttons[10][i].setBorderPainted(false);
        }

        for (int i : new int[]{11, 3}) {
            buttons[11][i].setBackground(DWRed);
            buttons[11][i].setForeground(Color.WHITE);
            buttons[11][i].setText("DW");
            buttons[11][i].setOpaque(true);
            buttons[11][i].setBorderPainted(false);
        }

        for (int i : new int[]{12, 2}) {
            buttons[12][i].setBackground(DWRed);
            buttons[12][i].setForeground(Color.WHITE);
            buttons[12][i].setText("DW");
            buttons[12][i].setOpaque(true);
            buttons[12][i].setBorderPainted(false);
        }

        for (int i : new int[]{1, 13}) {
            buttons[13][i].setBackground(DWRed);
            buttons[13][i].setForeground(Color.WHITE);
            buttons[13][i].setText("DW");
            buttons[13][i].setOpaque(true);
            buttons[13][i].setBorderPainted(false);
        }


        //  Triple Letter Blue Extract Method
        for (int i : new int[]{5, 9}) {
            buttons[1][i].setBackground(TLBlue);
            buttons[1][i].setForeground(Color.WHITE);
            buttons[1][i].setText("TL");
            buttons[1][i].setOpaque(true);
            buttons[1][i].setBorderPainted(false);
        }

        for (int i : new int[]{1, 5, 9, 13, 13}) {
            buttons[5][i].setBackground(TLBlue);
            buttons[5][i].setForeground(Color.WHITE);
            buttons[5][i].setText("TL");
            buttons[5][i].setOpaque(true);
            buttons[5][i].setBorderPainted(false);
        }

        for (int i = 1; i <= 13; i += 4) {
            buttons[9][i].setBackground(TLBlue);
            buttons[9][i].setForeground(Color.WHITE);
            buttons[9][i].setText("TL");
            buttons[9][i].setOpaque(true);
            buttons[9][i].setBorderPainted(false);
        }

        for (int i : new int[]{5, 9}) {
            buttons[13][i].setBackground(TLBlue);
            buttons[13][i].setForeground(Color.WHITE);
            buttons[13][i].setText("TL");
            buttons[13][i].setOpaque(true);
            buttons[13][i].setBorderPainted(false);
        }


        //  Double Letter Blue Extract Method
        for (int i : new int[]{3, 11}) {
            buttons[0][i].setBackground(DLBlue);
            buttons[0][i].setForeground(Color.WHITE);
            buttons[0][i].setText("DL");
            buttons[0][i].setOpaque(true);
            buttons[0][i].setBorderPainted(false);
        }

        for (int i : new int[]{6, 8}) {
            buttons[2][i].setBackground(DLBlue);
            buttons[2][i].setForeground(Color.WHITE);
            buttons[2][i].setText("DL");
            buttons[2][i].setOpaque(true);
            buttons[2][i].setBorderPainted(false);
        }

        for (int i = 0; i <= 14; i += 7) {
            buttons[3][i].setBackground(DLBlue);
            buttons[3][i].setForeground(Color.WHITE);
            buttons[3][i].setText("DL");
            buttons[3][i].setOpaque(true);
            buttons[3][i].setBorderPainted(false);
        }

        for (int i : new int[]{2, 6, 8, 12}) {
            buttons[6][i].setBackground(DLBlue);
            buttons[6][i].setForeground(Color.WHITE);
            buttons[6][i].setText("DL");
            buttons[6][i].setOpaque(true);
            buttons[6][i].setBorderPainted(false);
        }

        for (int j : new int[]{4, 11}) {
            buttons[7][j].setBackground(DLBlue);
            buttons[7][j].setForeground(Color.WHITE);
            buttons[7][j].setText("DL");
            buttons[7][j].setOpaque(true);
            buttons[7][j].setBorderPainted(false);
        }

        for (int i : new int[]{2, 6, 8}) {
            buttons[8][i].setBackground(DLBlue);
            buttons[8][i].setForeground(Color.WHITE);
            buttons[8][i].setText("DL");
            buttons[8][i].setOpaque(true);
            buttons[8][i].setBorderPainted(false);
        }

        for (int i = 0; i <= 14; i += 7) {
            buttons[11][i].setBackground(DLBlue);
            buttons[11][i].setForeground(Color.WHITE);
            buttons[11][i].setText("DL");
            buttons[11][i].setOpaque(true);
            buttons[11][i].setBorderPainted(false);
        }

        for (int i : new int[]{6, 8}) {
            buttons[12][i].setBackground(DLBlue);
            buttons[12][i].setForeground(Color.WHITE);
            buttons[12][i].setText("DL");
            buttons[12][i].setOpaque(true);
            buttons[12][i].setBorderPainted(false);
        }

        for (int i : new int[]{3, 11}) {
            buttons[14][i].setBackground(DLBlue);
            buttons[14][i].setForeground(Color.WHITE);
            buttons[14][i].setText("DL");
            buttons[14][i].setOpaque(true);
            buttons[14][i].setBorderPainted(false);
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

    public void attachTextBoard(Board board) {
        this.textBoard = board;
    }

    @Override
    public void update(ScrabbleEvent event) { // Update Game Model
        if (event.getMove().isValid()) {
            for (int i = 0; i < event.getMove().getCoords().size(); i++) {
                int x = event.getMove().getCoords().get(i).getCoords()[0];
                int y = event.getMove().getCoords().get(i).getCoords()[1];
                updateButton(x, y, String.valueOf(event.getBoard().getTileOnBoard(x,y).getLetter()));
            }
        } else {
            for (int i = 0; i < event.getMove().getCoords().size(); i++) {
                int x = event.getMove().getCoords().get(i).getCoords()[0];
                int y = event.getMove().getCoords().get(i).getCoords()[1];
                updateButton(x, y, "");
            }
        }

    }
}
