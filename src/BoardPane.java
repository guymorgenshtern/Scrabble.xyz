import javax.swing.*;
import java.awt.*;


public class BoardPane extends JPanel implements ScrabbleView {

    private JButton [][] buttons;

    public BoardPane(Game game) {
        super();

        this.setLayout(new GridLayout(Board.SIZE, Board.SIZE)); // Need to define size within board
        this.setSize(300,300);

        buttons = new JButton[Board.size][Board.size]; // Place board size in place. Call Board Size Directly

        for (int i < 0; i < Board.size; i++){          // Place board size.
            for (int j = 0; j < Board.size; j++){      // Place board size. 
                JButton b = new JButton("");
                b.setActionCommand(i + "" + j);
                b.addActionListener(); // Call to controller from Scrabble Controller
                buttons[i][j];
                this.add(b);
            }
        }

        this.setVisible(true);

    }


    @Override
    public void update(Game game) {
        Board board = getBoard;

        //TODO


    }
}
