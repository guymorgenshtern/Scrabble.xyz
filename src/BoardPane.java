import javax.swing.*;
import java.awt.*;


public class BoardPane extends JPanel implements ScrabbleView {

    private JButton [][] buttons;

    public BoardPane(Game game) {
        super();

        this.setLayout(new GridLayout(Board.SIZE, Board.SIZE)); // Need to define size within board
        this.setSize(300,300);

        buttons = new JButton[][] // Place board size in place

        // Once size is defined we can insert a for loop
        // i = 0; i<Board.size; i++ -> goes up to 15 x 15


    }


    @Override
    public void update(Game game) {

    }
}
