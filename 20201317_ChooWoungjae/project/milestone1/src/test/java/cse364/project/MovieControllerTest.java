package cse364.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {
    @InjectMocks
    MovieController movieController;

    @Mock
    MovieRepository movieRepository;
    @Mock
    MongoTemplate mongoTemplate;

    @Test
    public void testGetOne() {
        // test whether the controller throws exception properly if target movie doesn't exists
        assertThrows(CannotFoundException.class, () -> {movieController.one(Long.valueOf(1));});
    }
    
    @Test
    public void testDelete() {
        // test whether movieController.deleteMovie() calls movieRepository.delete() correctly when the target movie exists
        Movie sample1 = new Movie(Long.valueOf(1), "title", "genres");
        when(movieRepository.findById(sample1.getMovieId())).thenReturn(Optional.of(sample1));
        movieController.deleteMovie(sample1.getMovieId());
        verify(movieRepository).delete(sample1);

        // test whether movieController.deleteMovie() throws proper exception when the target movie doesn't exist
        Optional<Movie> sample2 = Optional.empty();
        when(movieRepository.findById(Long.valueOf(2))).thenReturn(sample2);
        assertThrows(CannotFoundException.class, () -> movieController.deleteMovie(Long.valueOf(2)));
    }
}