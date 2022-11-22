import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel implements ScrabbleView {

    // FIXME: don't need two Boards
    private Board textBoard;
    private JButton [][] buttons;

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

        - Y, X

         */


        Color TWRed = new Color(179,0, 0);
        buttons[8][6].setOpaque(true);
        buttons[8][6].setBackground(TWRed);
        buttons[8][6].setForeground(Color.WHITE);
        buttons[8][6].setText("TW");
        buttons[8][6].setBorderPainted(false); // Need to add to change entire button color

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
