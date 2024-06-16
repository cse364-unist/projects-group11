package cse364.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class RecommendationTest {

    @Test
    @DisplayName("testing Recommendation default constructor && set method")
    public void testRecommendationDefaultConstructor(){
        // test default constructor
        Recommendation sample = new Recommendation();
        assertNotNull(sample);

        // test set method
        List<String> sampleGenreList = new ArrayList<>();
        sampleGenreList.add("Drama");
        sample.setGenreList(sampleGenreList);

        List<User> sampleUserList = new ArrayList<>();
        User sampleUser = new User("F", 1, 1);
        sampleUserList.add(sampleUser);
        sample.setUserList(sampleUserList);

        assertEquals(sample.getGenreList(), sampleGenreList);
        assertEquals(sample.getUserList(), sampleUserList);
    }

    @Test
    @DisplayName("testing Recommendation constructor")
    public void testRecommendationConstructor(){
        // test constructor
        List<String> sampleGenreList = new ArrayList<>();
        sampleGenreList.add("Comedy");

        List<User> sampleUserList = new ArrayList<>();
        User sampleUser = new User("M", 56, 2);
        sampleUserList.add(sampleUser);

        Recommendation sample = new Recommendation(sampleUserList, sampleGenreList);

        assertEquals(sample.getUserList(), sampleUserList);
        assertEquals(sample.getGenreList(), sampleGenreList);
    }
}