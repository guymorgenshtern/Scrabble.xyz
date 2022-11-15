import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class BoardPanel extends JPanel implements ScrabbleView {

    private Board textBoard;
    private JButton [][] buttons;

    private ScrabbleModel scrabbleModel;

    public BoardPanel(ScrabbleModel scrabbleModel) {
        super();

        this.setLayout(new GridLayout(15, 15)); // Need to define size within board
        this.setSize(900,900);
        this.scrabbleModel = scrabbleModel;
        this.buttons = new JButton[15][15]; // Place board size in place. Call Board Size Directly

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
                    scrabbleModel.getCurrentMove().getCoords().add(new BoardClick(l, selectedLetter));
                    buttons[x][y].setText(selectedLetter);
                    this.textBoard.getScrabbleBoard()[x][y].setLetter(selectedLetter.charAt(0));
                    scrabbleModel.setSelectedLetter("");
                });
                this.add(buttons[i][j]);


            }
        }

        this.setVisible(true);

    }

    private void updateButton(int x, int y, String letter) {
        this.buttons[x][y].setText(letter);
        buttons[x][y].setBackground(Color.GREEN);
        buttons[x][y].setBorderPainted(false);
    }

    public void attachTextBoard(Board board) {
        this.textBoard = board;
    }

    @Override
    public void update(ScrabbleEvent event) { // Update Game Model
        for (int i = 0; i < event.getCoords().length; i++) {
            int x = event.getCoords()[i][0];
            int y = event.getCoords()[i][1];
            updateButton(x, y, String.valueOf(event.getBoard().getTileOnBoard(x,y).getLetter()));
        }
    }
}
