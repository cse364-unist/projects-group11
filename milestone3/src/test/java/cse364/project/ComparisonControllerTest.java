package cse364.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ComparisonControllerTest {
    @InjectMocks
    ComparisonController comparisonController;

    @Mock
    MovieRepository movieRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    MovieService movieService;
    
    @Test
    public void testInvalidMovieId() {
        Long validMovieId = Long.valueOf(1);
        Long invalidMovieId = Long.valueOf(2);
        when(movieRepository.findById(invalidMovieId)).thenReturn(Optional.empty());
        
        assertThrows(CannotFoundException.class, () -> comparisonController.compareMovies(invalidMovieId, validMovieId));
        assertThrows(CannotFoundException.class, () -> comparisonController.compareMovies(validMovieId, invalidMovieId));
    }


    @Test
    public void testInvalidMovieTitle() {
        // test whether comparisonController.compareMovies() throw proper exception when the target Movie's title doesn't contain its release year
        Movie sample1 = new Movie(Long.valueOf(1), "title (2020)", "genres");
        Movie sample2 = new Movie(Long.valueOf(2), "title", "genres");
        Movie sample3 = new Movie(Long.valueOf(3), "title (year)", "genres");
        Movie sample4 = new Movie(Long.valueOf(4), "title (2020 ", "genres");
        when(movieRepository.findById(sample1.getMovieId())).thenReturn(Optional.of(sample1));
        when(movieRepository.findById(sample2.getMovieId())).thenReturn(Optional.of(sample2));
        when(movieRepository.findById(sample3.getMovieId())).thenReturn(Optional.of(sample3));
        when(movieRepository.findById(sample4.getMovieId())).thenReturn(Optional.of(sample4));
        
        assertThrows(CannotFoundException.class, () -> comparisonController.compareMovies(sample1.getMovieId(), sample2.getMovieId()));
        assertThrows(CannotFoundException.class, () -> comparisonController.compareMovies(sample2.getMovieId(), sample1.getMovieId()));
        assertThrows(CannotFoundException.class, () -> comparisonController.compareMovies(sample3.getMovieId(), sample1.getMovieId()));
        assertThrows(CannotFoundException.class, () -> comparisonController.compareMovies(sample1.getMovieId(), sample3.getMovieId()));
        assertThrows(CannotFoundException.class, () -> comparisonController.compareMovies(sample4.getMovieId(), sample1.getMovieId()));
        assertThrows(CannotFoundException.class, () -> comparisonController.compareMovies(sample1.getMovieId(), sample4.getMovieId()));
    }

    @Test
    public void testCompareMovies() {
        // behavior of movieRepository
        Movie sample1 = new Movie(Long.valueOf(1), "First Movie (2000)", "genres");
        Movie sample2 = new Movie(Long.valueOf(2), "Second Movie (1995)", "genres");
        when(movieRepository.findById(sample1.getMovieId())).thenReturn(Optional.of(sample1));
        when(movieRepository.findById(sample2.getMovieId())).thenReturn(Optional.of(sample2));

        // behavior of movieService
        List<Long> movieStatistics1 = List.of(Long.valueOf(1), Long.valueOf(2), Long.valueOf(3), Long.valueOf(4), Long.valueOf(5), Long.valueOf(6), Long.valueOf(7), Long.valueOf(8));
        List<Long> movieStatistics2 = List.of(Long.valueOf(2), Long.valueOf(3), Long.valueOf(4), Long.valueOf(5), Long.valueOf(6), Long.valueOf(7), Long.valueOf(8), Long.valueOf(9));
        when(movieService.getMovieStats(sample1.getMovieId())).thenReturn(movieStatistics1);
        when(movieService.getMovieStats(sample2.getMovieId())).thenReturn(movieStatistics2);

        // behavior of userRepository
        User user1 = new User(Long.valueOf(1), "M", 1, "1", "40000");
        User user2 = new User(Long.valueOf(2), "F", 25, "2", "40001");
        User user3 = new User(Long.valueOf(3), "F", 35, "3", "40002");
        User user4 = new User(Long.valueOf(4), "F", 50, "4", "40003");
        
        List<User> userList = List.of(user1, user2, user3, user4);
        when(userRepository.findAll()).thenReturn(userList);
        
        // test whether comparisonController.compareMovies() returns a List of Longs
        assertTrue(comparisonController.compareMovies(sample1.getMovieId(), sample2.getMovieId()) instanceof List<Long>);
        assertTrue(comparisonController.compareMovies(sample2.getMovieId(), sample1.getMovieId()) instanceof List<Long>);
        
        // invalid user cases
        List<User> invalidUserList = List.of();

        User user5 = new User(Long.valueOf(5), "Female", 50, "5", "40004");
        invalidUserList = List.of(user5);
        when(userRepository.findAll()).thenReturn(invalidUserList);
        assertThrows(InvalidUserException.class, () -> comparisonController.compareMovies(sample1.getMovieId(), sample2.getMovieId()));

        User user6 = new User(Long.valueOf(6), null, 50, "6", "40005");
        invalidUserList = List.of(user6);
        when(userRepository.findAll()).thenReturn(invalidUserList);
        assertThrows(InvalidUserException.class, () -> comparisonController.compareMovies(sample1.getMovieId(), sample2.getMovieId()));

        User user7 = new User(Long.valueOf(7), "F", 0, "7", "40006");
        invalidUserList = List.of(user7);
        when(userRepository.findAll()).thenReturn(invalidUserList);
        assertThrows(InvalidUserException.class, () -> comparisonController.compareMovies(sample1.getMovieId(), sample2.getMovieId()));
    }
}