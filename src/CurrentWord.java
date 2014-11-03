public class CurrentWord {
    private char[] word;
    private char nullChar = '\u0000';

    public CurrentWord(int length) {
        word = new char[length];
    }

    public void setLetters(int[] indexes, char letter) {
        for (int i = 0; i < indexes.length; i++) {
            word[indexes[i]] = letter;
        }
    }

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

    public boolean isComplete() {
        boolean complete = true;

        for (int i = 0; i < word.length; i++) {
            if (word[i] == nullChar) {
                complete = false;
            }
        }

        return complete;
    }

    public char[] getArray() {
        char[] array = new char[word.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = word[i];
        }

        return array;
    }
}
