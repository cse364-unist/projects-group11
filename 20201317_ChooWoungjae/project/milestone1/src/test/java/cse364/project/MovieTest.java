package cse364.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class MovieTest {

    @Test
    @DisplayName("testing Movie constructor & get methods")
    public void testMovieConstructor() {
        // test movie constructor
        Movie sample = new Movie(Long.valueOf(5), "title", "genres");

        // test whether a Movie is initialized correctly when the object is created
        assertEquals(sample.getMovieId(), Long.valueOf(5));
        assertEquals(sample.getTitle(), "title");
        assertEquals(sample.getGenres(), "genres");
    }

    @Test
    @DisplayName("testing Movie default constructor & set methods")
    public void testMovieDefaultConstructor() {
        // test default constructor
        Movie defaultMovie = new Movie();
        assertNotNull(defaultMovie);

        // test set methods
        defaultMovie.setMovieId(Long.valueOf(1));
        defaultMovie.setTitle("Titanic");
        defaultMovie.setGenres("Romance");
        assertEquals(defaultMovie.getMovieId(), Long.valueOf(1));
        assertEquals(defaultMovie.getTitle(), "Titanic");
        assertEquals(defaultMovie.getGenres(), "Romance");
    }

    @Test
    public void testIntervalPastList() {
        Movie sample = new Movie(Long.valueOf(1), "title", "genres");
        assertTrue(sample.getIntervalPairList(0, 0) == 0);
        assertTrue(sample.getAverageOfInterval(0) == 0);

        sample.setIntervalPairList(1, 0, 15);
        sample.plusIntervalPairList(1, 1, 3);
        assertTrue(sample.getAverageOfInterval(1) == Double.valueOf(15) / Double.valueOf(3));
    }
}
