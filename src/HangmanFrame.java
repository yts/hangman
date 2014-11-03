import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class HangmanFrame extends JFrame {
    HangmanGame game;

    JLabel currentWord;
    JLabel guessesRemaining;
    JLabel lettersGuessed;
    JTextField guessField;
    JLabel guessLabel;


    public HangmanFrame() {
        setTitle("Hangman");
        setSize(300, 200);
        setLocation(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentWord = new JLabel("H _ N G M _ N");
        currentWord.setFont(new Font("Arial", Font.BOLD, 25));
        currentWord.setHorizontalAlignment(JLabel.CENTER);
        currentWord.setBorder(new EtchedBorder());

        getContentPane().add(currentWord, BorderLayout.NORTH);
        getContentPane().add(createStatusPanel(), BorderLayout.CENTER);
        getContentPane().add(createGuessPanel(), BorderLayout.SOUTH);

        setVisible(true);

        startNewGame();
    }

    private Component createGuessPanel() {
        JPanel panel = new JPanel();

        guessLabel = new JLabel("Letter to guess: ");
        guessField = new JTextField(1);
        panel.add(guessLabel);
        panel.add(guessField);
        panel.add(createGuessButton());

        return panel;
    }

    private JButton createGuessButton() {
        JButton button = new JButton("Guess");

        class GuessButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                int response = game.testLetter(guessField.getText().charAt(0));

                processResponse(response);
            }

        }

        button.addActionListener(new GuessButtonListener());

        return button;
    }

    public void processResponse(int response) {
        if (response == HangmanGame.NOT_FOUND) {
            JOptionPane.showMessageDialog(this, "INCORRECT!");

//			JOptionPane.showMessageDialog(this, "<html><strong>INCORRECT!</strong></html>");
            updateStatus();
        } else if (response == HangmanGame.FOUND) {
            JOptionPane.showMessageDialog(this, "CORRECT!");

            updateStatus();
        } else if (response == HangmanGame.LOST) {

        }

    }

    private void updateStatus() {
        currentWord.setText(currentWordString());
        guessesRemaining.setText("Guesses Remaining: " + game.guessesRemaining());
        lettersGuessed.setText("Letters Guessed: " + Arrays.toString(game.getGuessed()));
    }

    private void startNewGame() {
        boolean validInput = false;
        while (!validInput) {
            String newWord = JOptionPane.showInputDialog(this, "Enter a word to be guessed. It should be 4 letters or more.");
            if (newWord == null) {
                JOptionPane.showMessageDialog(this, "Goodbye");
                dispose();
                return;
            }
            if (newWord.matches("[A-Za-z]+") && newWord.length() > 4) {
                validInput = true;
                game = new HangmanGame(newWord);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid word. Please try again.");
            }
        }

        updateStatus();

    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));


        guessesRemaining = new JLabel("   Guesses Remaining: ");
        guessesRemaining.setFont(new Font("Arial", Font.BOLD, 17));

        lettersGuessed = new JLabel("   Letters Guessed: ");
        lettersGuessed.setFont(new Font("Arial", Font.BOLD, 17));

        panel.add(guessesRemaining);
        panel.add(lettersGuessed);

        return panel;
    }

    private String currentWordString() {
        String current = "";

        char[] currentArray = game.getCurrentArray();
        for (int i = 0; i < currentArray.length; i++) {
            if (currentArray[i] == '\u0000') {
                current += "_ ";
            } else {
                current += currentArray[i];
            }
        }

        return current;
    }

    class textOnlyVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent arg0) {
            // TODO Auto-generated method stub
            return false;
        }

    }
}
