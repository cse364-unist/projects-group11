package cse364.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class UserTest {

    @Test
    @DisplayName("testing User default constructor & set methods")
    public void testUserDefaultConstructor() {
        // test default constructor
        User sample = new User();
        assertNotNull(sample);

        // test set methods
        sample.setUserId(Long.valueOf(9999999));
        sample.setGender("F");
        sample.setAge(18);
        sample.setOccupation("Student");
        sample.setZipCode("999999");
        assertEquals(sample.getUserId(), 9999999);
        assertEquals(sample.getGender(), "F");
        assertEquals(sample.getAge(), 18);
        assertEquals(sample.getOccupation(), "Student");
        assertEquals(sample.getZipCode(), "999999");
    }

    @Test
    @DisplayName("testing User constructor")
    public void testMovieConstructor() {
        // test movie constructor
        Long user_id = 9999985L;
        User sample = new User(user_id, "F", 18, "Student", "999999");

        // test whether a Movie is initialized correctly when the object is created
        assertEquals(sample.getUserId(), 9999985);
        assertEquals(sample.getGender(), "F");
        assertEquals(sample.getAge(), 18);
        assertEquals(sample.getOccupation(), "Student");
        assertEquals(sample.getZipCode(), "999999");
        assertEquals(sample.getNumOfPeople(), 0);
    }

    @Test
    @DisplayName("testing User calculateInterval")
    public void testUserCalculateInterval() {
        // test default constructor
        User[] user_list = {
            new User(9999986L, "F", 1, "Student", "999999"),
            new User(9999987L, "F", 18, "Student", "999999"),
            new User(9999988L, "F", 25, "Student", "999999"),
            new User(9999989L, "F", 35, "Student", "999999"),
            new User(9999990L, "F", 45, "Student", "999999"),
            new User(9999991L, "F", 50, "Student", "999999"),
            new User(9999992L, "F", 56, "Student", "999999"),
            new User(9999993L, "M", 1, "Student", "999999"),
            new User(9999994L, "M", 18, "Student", "999999"),
            new User(9999995L, "M", 25, "Student", "999999"),
            new User(9999996L, "M", 35, "Student", "999999"),
            new User(9999997L, "M", 45, "Student", "999999"),
            new User(9999998L, "M", 50, "Student", "999999"),
            new User(9999999L, "M", 56, "Student", "999999"),
        };
        int[] interval_list = {
            7, 8, 9, 10, 11, 12, 13, 0, 1, 2, 3, 4, 5, 6
        };
        for (int i=0; i<14; i++) {
            assertEquals(user_list[i].calculateInterval(), interval_list[i]);
        }
    }

    @Test
    @DisplayName("testing User Simple version calculateInterval")
    public void testSimpleUserConstructorCalculateInterval() {
        // test default constructor
        User[] user_list = {
            new User("F", 1, 10),
            new User("F", 18, 10),
            new User("F", 25, 10),
            new User("F", 35, 10),
            new User("F", 45, 10),
            new User("F", 50, 10),
            new User("F", 56, 10),
            new User("M", 1, 10),
            new User("M", 18, 10),
            new User("M", 25, 10),
            new User("M", 35, 10),
            new User("M", 45, 10),
            new User("M", 50, 10),
            new User("M", 56, 10),
            new User("F", -1, 10),
        };
        int[] interval_list = {
            7, 8, 9, 10, 11, 12, 13, 0, 1, 2, 3, 4, 5, 6
        };
        for (int i=0; i<14; i++) {
            assertEquals(user_list[i].calculateInterval(), interval_list[i]);
        }
        // System.out.println(user_list[11].calculateInterval());
        assertThrows(InvalidUserException.class, () -> {user_list[14].calculateInterval();} );
    }
}
