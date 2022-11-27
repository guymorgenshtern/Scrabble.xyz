import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * A JFrame to welcome the users to the game of Scrabble.
 */
public class WelcomeFrame extends JFrame {

    /** A JButton that users can press to start a default game of Scrabble. */
    private JButton playGameButton;

    /** A JButton that users can press to customize their own Scrabble board to play on. */
    private JButton customizeGameButton;

    /** A JButton that users can press to load a game that they saved. */
    private JButton loadGameButton;

    /**
     * Initializes a JFrame to welcome the users to the game of Scrabble.
     * @author Emily Tang 101192604
     */
    public WelcomeFrame() {
        // initializes a JFrame with a vertical BoxLayout
        super("Welcome");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // set the welcomePanel in the center of the JFrame
        add(Box.createVerticalGlue());
        createWelcomePanel();
        add(Box.createVerticalGlue());

        // ActionListener for when playGameButton is pressed
        playGameButton.addActionListener(e -> {
            try {
                new ScrabbleGameFrame(new ScrabbleModel()); // navigate to the default game of Scrabble
                dispose(); // destroy the WelcomeFrame
            } catch (IOException ex) { // in case of an I/O exception when reading files
                throw new RuntimeException(ex);
            }
        });

        // ActionListener for when customizeGameButton is pressed
        customizeGameButton.addActionListener(e -> {
            new CustomizationFrame(this); // navigate to CustomizationFrame
            // don't dispose of this JFrame because the user can navigate back by using the "Cancel" button in the
            // CustomizationFrame
            setVisible(false);
        });

        // ActionListener for when loadGameButton is pressed
        loadGameButton.addActionListener(e -> {
            // TODO: add load game functionality
            System.out.println("Load Game button was clicked!");
        });

        // finish initializing JFrame
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null); // displays the JFrame at the center of the screen
        setResizable(false);
        setVisible(true);
    }

    /**
     * Initializes a welcomePanel with JButtons for users to press.
     * @author Emily Tang 101192604
     */
    private void createWelcomePanel() {
        // initialize JPanel with a vertical BoxLayout
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));

        // initialize JLabel to display "Scrabble.xyz"
        JLabel scrabbleLabel = new JLabel("Scrabble.xyz");
        scrabbleLabel.setFont(new Font("Dialog", Font.BOLD, 20));

        // initialize JButtons to handle user input
        playGameButton = new JButton("Play Game");
        customizeGameButton = new JButton("Customize Game");
        loadGameButton = new JButton("Load Game");

        // add components to the center of the JFrame
        scrabbleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customizeGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(scrabbleLabel);
        welcomePanel.add(Box.createVerticalGlue());
        welcomePanel.add(playGameButton);
        welcomePanel.add(customizeGameButton);
        welcomePanel.add(loadGameButton);

        add(welcomePanel, BorderLayout.CENTER); // add the welcomePanel to the JFrame
    }

    public static void main(String[] args) {
        new WelcomeFrame();
    }

}
