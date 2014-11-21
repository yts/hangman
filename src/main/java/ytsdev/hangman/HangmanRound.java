package ytsdev.hangman;

import java.util.ArrayList;


public class HangmanRound {
    public static final int NOT_FOUND = 0;
    public static final int FOUND = 1;
    public static final int LOST = 2;
    public static final int WON = 3;
    public static final int GUESSED = 4;
    public static final int OVER = 5;

    private final int GUESSES = 6;

    private ArrayList<Character> guessedLetters; //to hold the incorrectly guessed words by the player
    private Answer answer;
    private CurrentWord current;
    private int guessesRemaining;

    /**
     * Creates a round to be played with a given word
     *
     * @param word the word to play with
     */
    public HangmanRound(String word) {
        guessedLetters = new ArrayList<>();

        guessesRemaining = GUESSES;

        answer = new Answer(word);
        current = new CurrentWord(word.length());
    }

    /**
     * Takes a letter guessed by the player and tests it against the secret word
     *
     * @param letter the guessed letter
     * @return an int value representing the game's response to the letter
     */
    public int testLetter(char letter) {
        if (guessesRemaining <= 0) return OVER; //game is already over

        if (guessedLetters.contains(letter)) return GUESSED; //letter was already guessed incorrectly

        int[] indexes = answer.checkFor(letter);
        if (indexes.length > 0) {//if letter was found at least once in the word
            return found(indexes, letter);
        }

        return notFound(letter);
    }

    /**
     * @param indexes array with where the letter was found in the word
     * @param letter the letter
     * @return int HangmanGame.FOUND if the letter was found in the word, HangmanGame.NOT_FOUND if it wasn't.
     */
    private int found(int[] indexes, char letter) {
        current.setLetters(indexes, letter); //update the currently guessed word with the correct letter

        if (current.isComplete()) return WON; //if the word is spelled out, player has won

        return FOUND; //found but not won yet
    }

    /**
     * Gives appropriate response for a letter that wasn't found in the word
     *
     * @param letter the incorrect letter
     * @return value as response
     */
    private int notFound(char letter) {
        guessedLetters.add(letter);
        guessesRemaining--;

        if (guessesRemaining <= 0) {
            return LOST;
        }

        return NOT_FOUND;
    }

    public String getCurrent() {
        return current.toString();
    }

    /**
     * Get the number of remaining guesses
     * @return an integer with the number of guesses left
     */
    public int guessesRemaining() {
        return guessesRemaining;
    }

    /**
     * Get the incorrectly guessed letters
     * @return an array containing the letters
     */
    public char[] getGuessed() {
        //copy ArrayList to an array
        char[] guessedArr = new char[guessedLetters.size()];
        for (int i = 0; i < guessedArr.length; i++) {
            guessedArr[i] = guessedLetters.get(i);
        }

        return guessedArr;
    }

    /**
     * Determines if game was already lost
     * @return true if the game was lost, false if the player still has a chance
     */
    public boolean isLost() {
        return guessesRemaining <= 0;
    }

    /**
     * Find out the answer to the game. If the game is not already won, this will lose the game.
     * @return the secret word
     */
    public String getAnswer() {
        if (!current.isComplete()) {
            guessesRemaining = 0;
        }
        return answer.toString();
    }
}
