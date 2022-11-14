import javax.swing.*;
import java.awt.*;

/*
    Following Class will represent the users Hand/Rack
 */
public class HandPane extends JPanel implements ScrabbleView{
    private JButton [] buttons;

    public HandPane(){
        super();

        GridLayout buttonGrid = new GridLayout(1, Game.___); // Place handsize
        this.setLayout(buttonGrid);

        buttons = new JButton[Game.];   // Hand Size Again (7)

        for(int i; i < Game.){  // Game.HandSize value (7){

            buttons[i] = new JButton();
            buttons[i].setActionCommand("" + i);
            buttons[i].addActionListener(); // insert controller
            this.add(buttons[i]);
        }

        this.setVisible(true);

    }

    /*
    Use following update section to update the Scrabble Game Model
        -   missing players rack size is 0
     */
    @Override
    public void update(Game){ // Use this section to update the Scrabble game model

    }
}
