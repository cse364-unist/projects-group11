package cse364.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    @DisplayName("testing User constructor")
    public void testMovieConstructor() {
        // test movie constructor
        Long user_id = 9999999L;
        User sample = new User(user_id, "F", 18, "Student", "999999");

        // test whether a Movie is initialized correctly when the object is created
        assertEquals(sample.getUserId(), 9999999);
        assertEquals(sample.getGender(), "F");
        assertEquals(sample.getAge(), 18);
        assertEquals(sample.getOccupation(), "Student");
        assertEquals(sample.getZipCode(), "999999");
        assertEquals(sample.getNumOfPeople(), 0);
    }
}
