import javax.swing.*;
import java.awt.*;


public class BoardFrame extends JFrame implements ScrabbleView {

    private JButton [][] buttons;

    public BoardFrame(Game game) {
        super();

        this.setLayout(new GridLayout(15, 15)); // Need to define size within board
        this.setSize(900,900);

        this.buttons = new JButton[15][15]; // Place board size in place. Call Board Size Directly

        for (int i= 0; i < 15; i++){          // Place board size.
            for (int j = 0; j < 15; j++){      // Place board size.
                buttons[i][j] = new JButton();
                buttons[i][j].setActionCommand(i + "," + j);
                this.add(buttons[i][j]);


            }
        }

        this.setVisible(true);

    }

    private void updateButton(int x, int y, String letter) {
        this.buttons[x][y].setText(letter);
        buttons[x][y].setBackground(Color.GREEN);
        buttons[x][y].setOpaque(true);
        buttons[x][y].setBorderPainted(false);
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
