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

        /*

            Initialize Button Colors here after mapping board in Figma
            @Francisco

            - No need to add any updates to view as we are accessing the buttons directly,
            after the fact that we are accessing the "formed" buttons on the board once the for loop is done
            formatting them.

            - Board is set as [Y, X], this is fine as long as associated buttons are mapped with proper ui + multipliers

            - Tried implementing conditional statements to save time -> did not work

            - Need to clean code smell (Duplicated Code)
                - After general code modules are created refactor
                - Group everything in terms of "i" and create generalized for-loops
                - have less than 15 or exactly 15 for loops for each row/column

         */


        Color TWRed = new Color(179,0, 0);      // Color; Triple Word Squares WebSafe #B30000
        Color DWRed = new Color(255,128, 128);  // Color; Double Word Squares WebSafe #FF8080

        Color TLBlue = new Color(77, 77, 255);   // Color; Triple Letter Squares WebSafe #4D4DFF
        Color DLBlue = new Color(204, 204, 255); // Color; Double Letter Squares WebSafe #CCCCFF

        /*
            In this scenario row and column can be interchanged since the board is symmetric about the scrabble
            board axis.

         */

        // 0 th Row Grouping

        for (int i = 0; i <= 14; i += 7) {
            buttons[0][i].setBackground(TWRed);
            buttons[0][i].setForeground(Color.WHITE);
            buttons[0][i].setText("TW");
            buttons[0][i].setOpaque(true);
            buttons[0][i].setBorderPainted(false);
        }

        for (int i : new int[]{3, 11}) {
            buttons[0][i].setBackground(DLBlue);
            buttons[0][i].setForeground(Color.WHITE);
            buttons[0][i].setText("DL");
            buttons[0][i].setOpaque(true);
            buttons[0][i].setBorderPainted(false);
        }


        // 1 st Row Grouping
        for (int i : new int[]{1, 13}) {
            buttons[1][i].setBackground(DWRed);
            buttons[1][i].setForeground(Color.WHITE);
            buttons[1][i].setText("DW");
            buttons[1][i].setOpaque(true);
            buttons[1][i].setBorderPainted(false);
        }

        for (int i : new int[]{5, 9}) {
            buttons[1][i].setBackground(TLBlue);
            buttons[1][i].setForeground(Color.WHITE);
            buttons[1][i].setText("TL");
            buttons[1][i].setOpaque(true);
            buttons[1][i].setBorderPainted(false);
        }


        // 2 nd Row Grouping

        // 3 rd Row Grouping

        // 4 th Row Grouping

        // 5 th Row Grouping

        // 6 th Row Grouping

        // 7 th Row Grouping

        // 8 th Row Grouping

        // 9 th Row Grouping

        // 10 th Row Grouping

        // 11 th Row Grouping

        // 12 th Row Grouping

        // 13 th Row Grouping

        // 14 th Row Grouping

        // TRIPLE WORD SQUARES

        /*
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
        */


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



            // Double Word Squares -

                // Fill order. First Quadrant, Second Quadrant, Third Quadrant, Fourth Quadrant

        /*
        buttons[1][1].setBackground(DWRed);
        buttons[1][1].setForeground(Color.WHITE);
        buttons[1][1].setText("DW");
        buttons[1][1].setOpaque(true);
        buttons[1][1].setBorderPainted(false);
        */


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

        /*
        buttons[1][13].setBackground(DWRed);
        buttons[1][13].setForeground(Color.WHITE);
        buttons[1][13].setText("DW");
        buttons[1][13].setOpaque(true);
        buttons[1][13].setBorderPainted(false);
         */

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

        /*
        buttons[1][5].setBackground(TLBlue);
        buttons[1][5].setForeground(Color.WHITE);
        buttons[1][5].setText("TL");
        buttons[1][5].setOpaque(true);
        buttons[1][5].setBorderPainted(false);

        buttons[1][9].setBackground(TLBlue);
        buttons[1][9].setForeground(Color.WHITE);
        buttons[1][9].setText("TL");
        buttons[1][9].setOpaque(true);
        buttons[1][9].setBorderPainted(false);

         */

        buttons[5][1].setBackground(TLBlue);
        buttons[5][1].setForeground(Color.WHITE);
        buttons[5][1].setText("TL");
        buttons[5][1].setOpaque(true);
        buttons[5][1].setBorderPainted(false);

        buttons[5][5].setBackground(TLBlue);
        buttons[5][5].setForeground(Color.WHITE);
        buttons[5][5].setText("TL");
        buttons[5][5].setOpaque(true);
        buttons[5][5].setBorderPainted(false);

        buttons[5][9].setBackground(TLBlue);
        buttons[5][9].setForeground(Color.WHITE);
        buttons[5][9].setText("TL");
        buttons[5][9].setOpaque(true);
        buttons[5][9].setBorderPainted(false);

        buttons[5][13].setBackground(TLBlue);
        buttons[5][13].setForeground(Color.WHITE);
        buttons[5][13].setText("TL");
        buttons[5][13].setOpaque(true);
        buttons[5][13].setBorderPainted(false);

        buttons[5][13].setBackground(TLBlue);
        buttons[5][13].setForeground(Color.WHITE);
        buttons[5][13].setText("TL");
        buttons[5][13].setOpaque(true);
        buttons[5][13].setBorderPainted(false);

        buttons[9][1].setBackground(TLBlue);
        buttons[9][1].setForeground(Color.WHITE);
        buttons[9][1].setText("TL");
        buttons[9][1].setOpaque(true);
        buttons[9][1].setBorderPainted(false);

        buttons[9][5].setBackground(TLBlue);
        buttons[9][5].setForeground(Color.WHITE);
        buttons[9][5].setText("TL");
        buttons[9][5].setOpaque(true);
        buttons[9][5].setBorderPainted(false);

        buttons[9][9].setBackground(TLBlue);
        buttons[9][9].setForeground(Color.WHITE);
        buttons[9][9].setText("TL");
        buttons[9][9].setOpaque(true);
        buttons[9][9].setBorderPainted(false);

        buttons[9][13].setBackground(TLBlue);
        buttons[9][13].setForeground(Color.WHITE);
        buttons[9][13].setText("TL");
        buttons[9][13].setOpaque(true);
        buttons[9][13].setBorderPainted(false);

        buttons[13][5].setBackground(TLBlue);
        buttons[13][5].setForeground(Color.WHITE);
        buttons[13][5].setText("TL");
        buttons[13][5].setOpaque(true);
        buttons[13][5].setBorderPainted(false);

        buttons[13][9].setBackground(TLBlue);
        buttons[13][9].setForeground(Color.WHITE);
        buttons[13][9].setText("TL");
        buttons[13][9].setOpaque(true);
        buttons[13][9].setBorderPainted(false);

        // Double Letter Score Squares
/*
        buttons[0][3].setBackground(DLBlue);
        buttons[0][3].setForeground(Color.WHITE);
        buttons[0][3].setText("DL");
        buttons[0][3].setOpaque(true);
        buttons[0][3].setBorderPainted(false);

        buttons[0][11].setBackground(DLBlue);
        buttons[0][11].setForeground(Color.WHITE);
        buttons[0][11].setText("DL");
        buttons[0][11].setOpaque(true);
        buttons[0][11].setBorderPainted(false);
*/
        buttons[2][6].setBackground(DLBlue);
        buttons[2][6].setForeground(Color.WHITE);
        buttons[2][6].setText("DL");
        buttons[2][6].setOpaque(true);
        buttons[2][6].setBorderPainted(false);

        buttons[2][8].setBackground(DLBlue);
        buttons[2][8].setForeground(Color.WHITE);
        buttons[2][8].setText("DL");
        buttons[2][8].setOpaque(true);
        buttons[2][8].setBorderPainted(false);

        buttons[3][0].setBackground(DLBlue);
        buttons[3][0].setForeground(Color.WHITE);
        buttons[3][0].setText("DL");
        buttons[3][0].setOpaque(true);
        buttons[3][0].setBorderPainted(false);

        buttons[3][7].setBackground(DLBlue);
        buttons[3][7].setForeground(Color.WHITE);
        buttons[3][7].setText("DL");
        buttons[3][7].setOpaque(true);
        buttons[3][7].setBorderPainted(false);

        buttons[3][14].setBackground(DLBlue);
        buttons[3][14].setForeground(Color.WHITE);
        buttons[3][14].setText("DL");
        buttons[3][14].setOpaque(true);
        buttons[3][14].setBorderPainted(false);

        buttons[6][2].setBackground(DLBlue);
        buttons[6][2].setForeground(Color.WHITE);
        buttons[6][2].setText("DL");
        buttons[6][2].setOpaque(true);
        buttons[6][2].setBorderPainted(false);

        buttons[6][6].setBackground(DLBlue);
        buttons[6][6].setForeground(Color.WHITE);
        buttons[6][6].setText("DL");
        buttons[6][6].setOpaque(true);
        buttons[6][6].setBorderPainted(false);

        buttons[6][8].setBackground(DLBlue);
        buttons[6][8].setForeground(Color.WHITE);
        buttons[6][8].setText("DL");
        buttons[6][8].setOpaque(true);
        buttons[6][8].setBorderPainted(false);

        buttons[6][12].setBackground(DLBlue);
        buttons[6][12].setForeground(Color.WHITE);
        buttons[6][12].setText("DL");
        buttons[6][12].setOpaque(true);
        buttons[6][12].setBorderPainted(false);

        buttons[7][4].setBackground(DLBlue);
        buttons[7][4].setForeground(Color.WHITE);
        buttons[7][4].setText("DL");
        buttons[7][4].setOpaque(true);
        buttons[7][4].setBorderPainted(false);

        buttons[7][11].setBackground(DLBlue);
        buttons[7][11].setForeground(Color.WHITE);
        buttons[7][11].setText("DL");
        buttons[7][11].setOpaque(true);
        buttons[7][11].setBorderPainted(false);

        buttons[8][2].setBackground(DLBlue);
        buttons[8][2].setForeground(Color.WHITE);
        buttons[8][2].setText("DL");
        buttons[8][2].setOpaque(true);
        buttons[8][2].setBorderPainted(false);

        buttons[8][6].setBackground(DLBlue);
        buttons[8][6].setForeground(Color.WHITE);
        buttons[8][6].setText("DL");
        buttons[8][6].setOpaque(true);
        buttons[8][6].setBorderPainted(false);

        buttons[8][8].setBackground(DLBlue);
        buttons[8][8].setForeground(Color.WHITE);
        buttons[8][8].setText("DL");
        buttons[8][8].setOpaque(true);
        buttons[8][8].setBorderPainted(false);

        buttons[11][0].setBackground(DLBlue);
        buttons[11][0].setForeground(Color.WHITE);
        buttons[11][0].setText("DL");
        buttons[11][0].setOpaque(true);
        buttons[11][0].setBorderPainted(false);

        buttons[11][7].setBackground(DLBlue);
        buttons[11][7].setForeground(Color.WHITE);
        buttons[11][7].setText("DL");
        buttons[11][7].setOpaque(true);
        buttons[11][7].setBorderPainted(false);

        buttons[11][14].setBackground(DLBlue);
        buttons[11][14].setForeground(Color.WHITE);
        buttons[11][14].setText("DL");
        buttons[11][14].setOpaque(true);
        buttons[11][14].setBorderPainted(false);

        buttons[12][6].setBackground(DLBlue);
        buttons[12][6].setForeground(Color.WHITE);
        buttons[12][6].setText("DL");
        buttons[12][6].setOpaque(true);
        buttons[12][6].setBorderPainted(false);

        buttons[12][8].setBackground(DLBlue);
        buttons[12][8].setForeground(Color.WHITE);
        buttons[12][8].setText("DL");
        buttons[12][8].setOpaque(true);
        buttons[12][8].setBorderPainted(false);

        buttons[14][3].setBackground(DLBlue);
        buttons[14][3].setForeground(Color.WHITE);
        buttons[14][3].setText("DL");
        buttons[14][3].setOpaque(true);
        buttons[14][3].setBorderPainted(false);

        buttons[14][11].setBackground(DLBlue);
        buttons[14][11].setForeground(Color.WHITE);
        buttons[14][11].setText("DL");
        buttons[14][11].setOpaque(true);
        buttons[14][11].setBorderPainted(false);


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
