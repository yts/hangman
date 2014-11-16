import java.util.ArrayList;

public class Answer {
    private String word;

    public Answer(String word) {
        this.word = word;
    }

    public int[] checkFor(char attempt) {
        ArrayList<Integer> positions = new ArrayList<>(); //temp to store positions, if any

        //add the matching positions to the ArrayList
        int index = 0;
        while (index >= 0 /*letter not found*/ && index < word.length()) {
            index = word.indexOf(attempt, index);
            if (index >= 0) {  //letter found in string
                positions.add(index);
                index++;
            }
        }

        //copy values from ArrayList to array
        int[] pos = new int[positions.size()];
        for (int i = 0; i < positions.size(); i++) {
            pos[i] = positions.get(i);
        }

        return pos;
    }

    public String toString() {
        return word;
    }

}