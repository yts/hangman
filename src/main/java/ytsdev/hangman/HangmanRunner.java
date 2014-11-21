package ytsdev.hangman;

import java.util.Scanner;

public class HangmanRunner {
    static HangmanRound game;
    static Scanner in;

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        in = new Scanner(System.in);

        startNewGame();
    }

    /**
     * Starts a new game of Hangman
     */
    private static void startNewGame() {
        String word = "";
        boolean validWord = false;

        //prompts for secret word until proper input is given
        while (!validWord) {
            System.out.println("Enter the word for the other player to guess.");
            word = in.nextLine();

            if (word.matches("^[A-Za-z]+$")) {
                validWord = true;
            } else {
                System.out.println("Invalid input.");
            }

        }

        //elementary way of hiding the secret word - scrolling down
        for (int i = 0; i < 500; i++) {
            System.out.println();
        }

        game = new HangmanRound(word.toUpperCase()); //create new game
        System.out.println("The secret word has been entered.");

        update();

        //		boolean playing = true;
        while (!game.isLost()) {
            newGuess();
        }
    }

    /**
     * Displays the current stats of the game
     */
    static void update() {
        System.out.println("You have " + game.guessesRemaining() + " more guesses.\n");
        System.out.println("The word you are trying to guess is " + game.getCurrent() + "\n");

        if (game.getGuessed().length >= 1) {
            System.out.print("You have already guessed the letters: ");
            //print the letters to the screen
            char[] guessed = game.getGuessed();
            for (char letter : guessed) {
                System.out.print(letter + " ");
            }
            System.out.println("\n");
        }


        System.out.println("-----------------------------------------------------------------------------------------");
    }

    /**
     * Prompt the user for a new guess and process it
     */
    static void newGuess() {
        boolean validInput = false;
        while (!validInput) {
            System.out.println("What letter would you like to guess now? ");
            String input = in.nextLine();

            if (!input.matches("^[A-Za-z]{1}$")) //must be one and only one letter
            {
                System.out.println("Invalid input.");
            } else {
                validInput = true;
                System.out.println("\n\n\n");
                input = input.toUpperCase();
                char letter = input.charAt(0);
                int response = game.testLetter(letter); //test letter
                processResponse(letter, response);
            }
        }
    }

    /**
     * Process the response from the HangmanGame to a letter guess
     *
     * @param letter   the letter that was guessed
     * @param response the response given for the letter
     */
    static void processResponse(char letter, int response) {
        if (response == HangmanRound.NOT_FOUND) {
            System.out.println("Nope!\n \"" + letter + "\" is not in the word.");
            update();
            newGuess();
        } else if (response == HangmanRound.FOUND) {
            System.out.println("Correct!\n \"" + letter + "\" is in the word.");
            update();
            newGuess();
        } else if (response == HangmanRound.LOST) {
            System.out.println("Nope! \"" + letter + "\" is not in the word.");
            System.out.println("You have run out of chances. GAME OVER!\nThe answer was \"" + game.getAnswer() + ".\"");
            newGamePrompt();
        } else if (response == HangmanRound.WON) {
            System.out.println("Correct! \"" + letter + "\" is in the word.");
            System.out.println("You completed the entire word! YOU WIN! \nThe word was \"" + game.getAnswer() + ".\"");
            newGamePrompt();
        } else if (response == HangmanRound.GUESSED) {
            System.out.println("You already guessed that letter.");
            newGuess();
        } else if (response == HangmanRound.OVER) {
            System.out.println("This game is already over.");
            newGamePrompt();
        }
    }

    /**
     * Prompts the user to start a new game or exit
     */
    static void newGamePrompt() {
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Would you like to start a new game? (Y/N)");
            String input = in.next();

            if (input.equalsIgnoreCase("Y")) {
                validInput = true;
                startNewGame();
            } else if (input.equalsIgnoreCase("N")) {
                validInput = true;
                System.out.println("Ok, thanks for playing!");

                try {
                    Thread.sleep(1000); //give player a chance to read message

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            } else {
                System.out.println("I'm sorry, I didn't get that.");
            }
        }
    }


}
