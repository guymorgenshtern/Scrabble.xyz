import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CustomizationFrame extends JFrame {

    /** An integer representing the minimum number of rows and columns the board can have. */
    private static final int MIN_NUM_ROWS_OR_COLS = 10;

    /** An integer representing the maximum number of rows and columns the board can have. */
    private static final int MAX_NUM_ROWS_OR_COLS = 20;

    /** A JFrame representing the welcome frame so users can navigate back if they press the "Cancel" button. */
    private final JFrame welcomeFrame;

    /** A 2D array of JButtons representing the board. */
    private final JButton[][] buttons;

    /** An integer representing the number of rows the board currently has. */
    private int numRows;

    /** An integer representing the number of columns the board currently has. */
    private int numCols;

    /** A JLabel to display the number of rows the board currently has. */
    private JLabel numRowsLabel;

    /** A JLabel to display the number of columns the board currently has. */
    private JLabel numColsLabel;

    /** A JPanel to display the current board. */
    private JPanel boardPanel;

    /**
     * Initializes a CustomizationFrame to allow user to design their own Scrabble board.
     * @author Emily Tang 101192604
     */
    public CustomizationFrame(JFrame welcomeFrame) {
        // initialize JFrame with a vertical box layout
        super("Customize your own Scrabble Board");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.welcomeFrame = welcomeFrame; // user can navigate back to the welcomeFrame using the "Cancel" button

        // initialize all the JButtons
        buttons = new JButton[MAX_NUM_ROWS_OR_COLS][MAX_NUM_ROWS_OR_COLS];
        for (int i = 0; i < MAX_NUM_ROWS_OR_COLS; i++) {
            for (int j = 0; j < MAX_NUM_ROWS_OR_COLS; j++) {
                JButton b = new JButton("");
                int x = i;
                int y = j;
                b.addActionListener(e -> {
                    askUserToInputMultiplier(x, y);
                });
                buttons[i][j] = b;
            }
        }

        // board customization starts with a 15x15 board
        numRows = 15;
        numCols = 15;

        // create JPanels to add to the JFrame
        createModPanel();
        createBoardPanel();
        createNavigationPanel();

        // finish initializing JFrame
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null); // displays the JFrame at the center of the screen
        setVisible(true);
    }

    /**
     * Asks user to add a multiplier to the square at the specified row and column.
     * @param row An integer representing the row of the JButton that was clicked.
     * @param col An integer representing the column of the JButton that was clicked.
     * @author Emily Tang 101192604
     */
    private void askUserToInputMultiplier(int row, int col) {
        JPanel multiplierPanel = new JPanel();
        multiplierPanel.setLayout(new GridLayout(2, 2, 10, 10));

        multiplierPanel.add(new JLabel("Select the type of multiplier: "));
        JComboBox<String> multiplierComboBox = new JComboBox<>();
        multiplierComboBox.addItem("Letter");
        multiplierComboBox.addItem("Word");
        multiplierPanel.add(multiplierComboBox);

        multiplierPanel.add(new JLabel("Enter a numeric value to represent the factor of the multiplier: "));
        JTextField factorField = new JTextField("");
        multiplierPanel.add(factorField);

        Object[] options = { "Remove Multiplier", "Done" };

        int result = JOptionPane.showOptionDialog(this, multiplierPanel, "Choose a Multiplier for the Square at " + row + " " + col,
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

        if (result == JOptionPane.YES_OPTION) { // remove button was pressed
            buttons[row][col].setText("");
        } else if (result == JOptionPane.NO_OPTION) { // done button was pressed
            String factorString = factorField.getText(); // get the inputted factor

            // factor can be a numeric value from 1 to 9 inclusive
            if (factorString.length() == 1 && !factorString.equals("0") && Character.isDigit(factorString.charAt(0))) {
                // set the multiplier
                String multiplier = "";
                multiplier += factorString;
                if (multiplierComboBox.getSelectedItem().equals("Letter")) {
                    multiplier += "L";
                } else {
                    multiplier += "W";
                }
                buttons[row][col].setText(multiplier);
            }
        }
    }

    /**
     * @param rowOrCol A String to represent the panel we're making.
     * @param rowOrColLabel A JLabel to display the number of rows or columns.
     * @param incrementButton A JButton to increment the number of rows or columns.
     * @param decrementButton A JButton to decrement the number of rows or columns.
     * @return A JPanel to modify the number of rows or columns.
     * @author Emily Tang 101192604
     */
    private JPanel createRowOrColModPanel(String rowOrCol, JLabel rowOrColLabel, JButton incrementButton, JButton decrementButton) {
        // create a JPanel with a center FlowLayout
        JPanel rowOrColPanel = new JPanel();
        rowOrColPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // add the components to the JPanel
        rowOrColPanel.add(new JLabel("# of " + rowOrCol));
        rowOrColPanel.add(decrementButton);
        rowOrColPanel.add(rowOrColLabel);
        rowOrColPanel.add(incrementButton);

        return rowOrColPanel;
    }

    /**
     * Initializes a JPanel to allow users to modify the number of rows and columns their board has.
     * @author Emily Tang 101192604
     */
    private void createModPanel() {
        // create a JPanel with a 1x2 GridLayout
        JPanel modPanel = new JPanel();
        modPanel.setLayout(new GridLayout(1, 2));

        // initialize the JLabel and JButtons for the row modifier
        numRowsLabel = new JLabel(numRows + "");
        JButton incrementRowsButton = new JButton("+");
        JButton decrementRowsButton = new JButton("-");

        // initialize the JLabel and JButtons for the column modifier
        numColsLabel = new JLabel(numCols + "");
        JButton incrementColsButton = new JButton("+");
        JButton decrementColsButton = new JButton("-");

        // ActionListener for when user removes a row from their board
        decrementRowsButton.addActionListener(e -> {
            if (numRows > MIN_NUM_ROWS_OR_COLS) {
                updateBoardPanel(true, false);
            }
        });

        // ActionListener for when user adds a row to their board
        incrementRowsButton.addActionListener(e -> {
            if (numRows < MAX_NUM_ROWS_OR_COLS) {
                updateBoardPanel(true, true);
            }
        });

        // ActionListener for when user removes a column from their board
        decrementColsButton.addActionListener(e -> {
            if (numCols > MIN_NUM_ROWS_OR_COLS) {
                updateBoardPanel(false, false);
            }
        });

        // ActionListener for when user adds a column to their board
        incrementColsButton.addActionListener(e -> {
            if (numCols < MAX_NUM_ROWS_OR_COLS) {
                updateBoardPanel(false, true);
            }
        });

        // create the row and column modifier panels and add them to the JPanel
        modPanel.add(createRowOrColModPanel("rows", numRowsLabel, incrementRowsButton, decrementRowsButton));
        modPanel.add(createRowOrColModPanel("columns", numColsLabel, incrementColsButton, decrementColsButton));

        add(modPanel); // add the modifier panel to the JFrame
    }

    /**
     * Adds the appropriate number of buttons to the boardPanel.
     * @author Emily Tang 101192604
     */
    private void addButtonsToBoardPanel() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                boardPanel.add(buttons[i][j]);
            }
        }
    }

    /**
     * Updates the JLabel displaying the number of rows and columns the board has, and updates the board.
     * @param isRow True, if the number of rows has been modified. False, if number of columns.
     * @param increment True, if the number of rows or columns has been incremented. False, if decremented.
     * @author Emily Tang 101192604
     */
    private void updateBoardPanel(boolean isRow, boolean increment) {
        // update the number of rows and columns
        if (isRow && increment) {
            numRows++;
        } else if (isRow && !increment) {
            numRows--;
        } else if (!isRow && increment) {
            numCols++;
        } else {
            numCols--;
        }
        numRowsLabel.setText(numRows + "");
        numColsLabel.setText(numCols + "");

        // update the board
        boardPanel.removeAll(); // remove all components currently on the board panel
        boardPanel.setLayout(new GridLayout(numRows, numCols)); // update layout to current number of rows and columns
        addButtonsToBoardPanel(); // add the appropriate number of buttons to the board panel
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    /**
     * Initializes a JPanel to display the squares of the Scrabble board.
     * @author Emily Tang 101192604
     */
    private void createBoardPanel() {
        // create a JPanel with a GridLayout based on the default number of rows and columns
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(numRows, numCols));

        addButtonsToBoardPanel(); // add the appropriate number of buttons to the boardPanel

        add(boardPanel); // add the board panel to the JFrame
    }

    /**
     * Initializes a JPanel for navigation purposes.
     * @author Emily Tang 101192604
     */
    private void createNavigationPanel() {
        // create a JPanel with a right-aligned FlowLayout
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        // initialize the JButtons and add them to the navigation panel
        JButton cancelButton = new JButton("Cancel");
        JButton doneButton = new JButton("Done");
        navigationPanel.add(cancelButton);
        navigationPanel.add(doneButton);

        // ActionListener for when user would like to go back to the StartupFrame
        cancelButton.addActionListener(e -> {
            welcomeFrame.setVisible(true); // navigate back to the welcomeFrame
            dispose(); // destroy the CustomizationFrame
        });

        // ActionListener for when user would like to start the game with their current customized board
        doneButton.addActionListener(e -> {
            // create a 2D String array representation of the board the user created
            String[][] customBoard = new String[numRows][numCols];
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    customBoard[i][j] = buttons[i][j].getText();
                }
            }

            try {
                new ScrabbleGameFrame(new ScrabbleModel(customBoard, numRows, numCols));
                dispose(); // destroy the CustomizationFrame
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        add(navigationPanel); // add the navigation panel to the JFrame
    }
}
