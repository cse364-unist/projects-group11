package cse364.project;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MovieTest {
    /*
    ========== nullPointerException 오류 나는 부분 ==========
    ****** movieRepository가 null이 되지 않도록 해야함 ******

    @Autowired
    MovieController movieController;

    @MockBean
    MovieRepository movieRepository;
    @MockBean
    MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() {
        movieController = new MovieController(movieRepository, mongoTemplate);
    }
    
    @Test
    public void testMovieControllerGetOne() {
        // test whether the controller throws exception properly if target movie doesn't exists
        assertThrows(CannotFoundException.class, () -> {movieController.one(Long.valueOf(1));});
    
        // test whether the controller returns target Movie correctly
        Movie sample = new Movie(Long.valueOf(1), "title", "genres");
        movieRepository.save(sample);
        Movie result = movieController.one(Long.valueOf(1));
        assertEquals(sample, result);
    }
    */

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
}
