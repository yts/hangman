/**
 * The CurrentWord class represents the word as the player knows it at any given point during the game.
 */
public class CurrentWord {
    private char[] word;
    private char nullChar = '\u0000';

    public CurrentWord(int length) {
        word = new char[length];
    }

    /**
     * Updates the word with a letter the user guessed correctly
     * @param indexes the indexes the letter occurs in in the answer
     * @param letter the letter to be inserted in at the indexes
     */
    public void setLetters(int[] indexes, char letter) {
        for (int i = 0; i < indexes.length; i++) {
            word[indexes[i]] = letter;
        }
    }

    /**
     * Gets the string representation of the current word. Unknown letters are shown as "*"
     * @return the representation string
     */
    public String toString() {
        String string = "";

        for (int i = 0; i < word.length; i++) {
            if (word[i] == nullChar) {
                string += "*";
            } else {
                string += word[i];
            }
        }

        return string;
    }

    /**
     * Checks if the current word has been fully filled in
     * @return true if every letter is filled in, false if there are still unknown letters
     */
    public boolean isComplete() {
        for (int i = 0; i < word.length; i++) {
            if (word[i] == nullChar) {
                return false;
            }
        }

        return true;
    }
}
