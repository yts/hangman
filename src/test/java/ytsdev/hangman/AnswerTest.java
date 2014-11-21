package ytsdev.hangman;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Admin on 11/20/2014.
 */
public class AnswerTest {

    @Test
    public void shouldFindCorrectIndexes() {
        Answer answer = new Answer("nabana");

        int[] result = answer.checkFor('a');
        assertArrayEquals(result, new int[]{1, 3, 5});
    }

    @Test
    public void shouldReturnEmptyArrayIfNoneFound() {
        Answer answer = new Answer("happy");

        assertArrayEquals(answer.checkFor('b'), new int[]{});
    }
}
