package cse364.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleUserTest {
    @Test
    @DisplayName("testing SimpleUser constructor")
    public void testMovieConstructor() {
        // test movie constructor
        SimpleUser sample = new SimpleUser("F", 1, 1);

        // test whether a Movie is initialized correctly when the object is created
        assertEquals(sample.getGender(), "F");
        assertEquals(sample.getAge(), 1);
        assertEquals(sample.getNumOfPeople(), 1);
    }
}
