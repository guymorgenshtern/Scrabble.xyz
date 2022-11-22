import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel implements ScrabbleView {

    // FIXME: don't need two Boards
    private Board textBoard;
    private JButton [][] buttons;

    // Set Colors for Multipliers On Board

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

        /*

            Initialize Button Colors here after mapping board in Figma
            @Francisco

            - No need to add any updates to view as we are accessing the buttons directly,
            after the fact that we are accessing the "formed" buttons on the board once the for loop is done
            formatting them.

            - Board is set as [Y, X], this is fine as long as associated buttons are mapped with proper ui + multipliers

            - Tried implementing conditional statements to save time -> did not work

         */

        Color TWRed = new Color(179,0, 0);      // Color; Triple Word Squares WebSafe #B30000
        Color DWRed = new Color(255,128, 128);  // Color; Double Word Squares WebSafe #FF8080
        Color TLBlue = new Color(77, 77, 255);   // Color; Triple Word Squares WebSafe #4D4DFF

        // TRIPLE WORD SQUARES
        buttons[0][0].setBackground(TWRed);
        buttons[0][0].setForeground(Color.WHITE);
        buttons[0][0].setText("TW");
        buttons[0][0].setOpaque(true);
        buttons[0][0].setBorderPainted(false);

        buttons[0][7].setBackground(TWRed);
        buttons[0][7].setForeground(Color.WHITE);
        buttons[0][7].setText("TW");
        buttons[0][7].setOpaque(true);
        buttons[0][7].setBorderPainted(false);

        buttons[0][14].setBackground(TWRed);
        buttons[0][14].setForeground(Color.WHITE);
        buttons[0][14].setText("TW");
        buttons[0][14].setOpaque(true);
        buttons[0][14].setBorderPainted(false);

        buttons[7][0].setBackground(TWRed);
        buttons[7][0].setForeground(Color.WHITE);
        buttons[7][0].setText("TW");
        buttons[7][0].setOpaque(true);
        buttons[7][0].setBorderPainted(false);

        buttons[7][14].setBackground(TWRed);
        buttons[7][14].setForeground(Color.WHITE);
        buttons[7][14].setText("TW");
        buttons[7][14].setOpaque(true);
        buttons[7][14].setBorderPainted(false);

        buttons[14][0].setBackground(TWRed);
        buttons[14][0].setForeground(Color.WHITE);
        buttons[14][0].setText("TW");
        buttons[14][0].setOpaque(true);
        buttons[14][0].setBorderPainted(false);

        buttons[14][7].setBackground(TWRed);
        buttons[14][7].setForeground(Color.WHITE);
        buttons[14][7].setText("TW");
        buttons[14][7].setOpaque(true);
        buttons[14][7].setBorderPainted(false);

        buttons[14][14].setBackground(TWRed);
        buttons[14][14].setForeground(Color.WHITE);
        buttons[14][14].setText("TW");
        buttons[14][14].setOpaque(true);
        buttons[14][14].setBorderPainted(false);


        /*
            Double Word Squares -

                Fill order. First Quadrant, Second Quadrant, Third Quadrant, Fourth Quadrant
         */

        buttons[1][1].setBackground(DWRed);
        buttons[1][1].setForeground(Color.WHITE);
        buttons[1][1].setText("DW");
        buttons[1][1].setOpaque(true);
        buttons[1][1].setBorderPainted(false);

        buttons[2][2].setBackground(DWRed);
        buttons[2][2].setForeground(Color.WHITE);
        buttons[2][2].setText("DW");
        buttons[2][2].setOpaque(true);
        buttons[2][2].setBorderPainted(false);

        buttons[3][3].setBackground(DWRed);
        buttons[3][3].setForeground(Color.WHITE);
        buttons[3][3].setText("DW");
        buttons[3][3].setOpaque(true);
        buttons[3][3].setBorderPainted(false);

        buttons[4][4].setBackground(DWRed);
        buttons[4][4].setForeground(Color.WHITE);
        buttons[4][4].setText("DW");
        buttons[4][4].setOpaque(true);
        buttons[4][4].setBorderPainted(false);

        // SECOND QUADRANT
        buttons[1][13].setBackground(DWRed);
        buttons[1][13].setForeground(Color.WHITE);
        buttons[1][13].setText("DW");
        buttons[1][13].setOpaque(true);
        buttons[1][13].setBorderPainted(false);

        buttons[2][12].setBackground(DWRed);
        buttons[2][12].setForeground(Color.WHITE);
        buttons[2][12].setText("DW");
        buttons[2][12].setOpaque(true);
        buttons[2][12].setBorderPainted(false);

        buttons[3][11].setBackground(DWRed);
        buttons[3][11].setForeground(Color.WHITE);
        buttons[3][11].setText("DW");
        buttons[3][11].setOpaque(true);
        buttons[3][11].setBorderPainted(false);

        buttons[4][10].setBackground(DWRed);
        buttons[4][10].setForeground(Color.WHITE);
        buttons[4][10].setText("DW");
        buttons[4][10].setOpaque(true);
        buttons[4][10].setBorderPainted(false);

        //THIRD QUADRANT
        buttons[13][13].setBackground(DWRed);
        buttons[13][13].setForeground(Color.WHITE);
        buttons[13][13].setText("DW");
        buttons[13][13].setOpaque(true);
        buttons[13][13].setBorderPainted(false);

        buttons[12][12].setBackground(DWRed);
        buttons[12][12].setForeground(Color.WHITE);
        buttons[12][12].setText("DW");
        buttons[12][12].setOpaque(true);
        buttons[12][12].setBorderPainted(false);

        buttons[11][11].setBackground(DWRed);
        buttons[11][11].setForeground(Color.WHITE);
        buttons[11][11].setText("DW");
        buttons[11][11].setOpaque(true);
        buttons[11][11].setBorderPainted(false);

        buttons[10][10].setBackground(DWRed);
        buttons[10][10].setForeground(Color.WHITE);
        buttons[10][10].setText("DW");
        buttons[10][10].setOpaque(true);
        buttons[10][10].setBorderPainted(false);

        //FOURTH QUADRANT
        buttons[13][1].setBackground(DWRed);
        buttons[13][1].setForeground(Color.WHITE);
        buttons[13][1].setText("DW");
        buttons[13][1].setOpaque(true);
        buttons[13][1].setBorderPainted(false);

        buttons[12][2].setBackground(DWRed);
        buttons[12][2].setForeground(Color.WHITE);
        buttons[12][2].setText("DW");
        buttons[12][2].setOpaque(true);
        buttons[12][2].setBorderPainted(false);

        buttons[11][3].setBackground(DWRed);
        buttons[11][3].setForeground(Color.WHITE);
        buttons[11][3].setText("DW");
        buttons[11][3].setOpaque(true);
        buttons[11][3].setBorderPainted(false);

        buttons[11][3].setBackground(DWRed);
        buttons[11][3].setForeground(Color.WHITE);
        buttons[11][3].setText("DW");
        buttons[11][3].setOpaque(true);
        buttons[11][3].setBorderPainted(false);

        buttons[10][4].setBackground(DWRed);
        buttons[10][4].setForeground(Color.WHITE);
        buttons[10][4].setText("DW");
        buttons[10][4].setOpaque(true);
        buttons[10][4].setBorderPainted(false);


            //  Triple Letter Score Squares

        buttons[1][5].setBackground(TLBlue);
        buttons[1][5].setForeground(Color.WHITE);
        buttons[1][5].setText("TL");
        buttons[1][5].setOpaque(true);
        buttons[1][5].setBorderPainted(false);






        this.setVisible(true);

    }

    private void updateButton(int x, int y, String letter) {
        if (!letter.equals("")) {
            buttons[x][y].setBackground(Color.RED);
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
