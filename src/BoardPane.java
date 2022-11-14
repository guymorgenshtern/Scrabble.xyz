import javax.swing.*;
import java.awt.*;


public class BoardPane extends JPanel implements ScrabbleView {

    private JButton [][] buttons;

    public BoardPane(Game game) {
        super();

        this.setLayout(new GridLayout(Board.SIZE, Board.SIZE)); // Need to define size within board
        this.setSize(300,300);

        buttons = new JButton[Board.size][Board.size]; // Place board size in place. Call Board Size Directly?

        //TODO

        for (int i < 0; i < Board__; i++){
            for (int j = 0; j < Board.size)
        }


    }


    @Override
    public void update(Game game) {
        Board board = getBoard;

        //TODO


    }
}
