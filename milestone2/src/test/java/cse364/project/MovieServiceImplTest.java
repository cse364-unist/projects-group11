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
public class MovieServiceImplTest {
    @InjectMocks
    MovieServiceImpl movieServiceImpl;

    @Mock
    MovieRepository movieRepository;
    @Mock
    RatingRepository ratingRepository;
    @Mock
    UserRepository userRepository;

    @Test
    public void testGetMovieStats() {
        // test whether the method throws proper exception when the MovieId is invalid
        Long invalidId = Long.valueOf(0);
        Optional<Movie> emptySample = Optional.empty();
        when(movieRepository.findById(invalidId)).thenReturn(emptySample);
        assertThrows(CannotFoundException.class, () -> movieServiceImpl.getMovieStats(invalidId));

        // test whether the method returns proper type and size of List
        Movie sample1 = new Movie(Long.valueOf(1), "title (1900)", "genres");
        when(movieRepository.findById(sample1.getMovieId())).thenReturn(Optional.of(sample1));
        Rating rating1 = new Rating(Long.valueOf(1), Long.valueOf(1), Long.valueOf(5));
        Rating rating2 = new Rating(Long.valueOf(2), Long.valueOf(1), Long.valueOf(4));
        Rating rating3 = new Rating(Long.valueOf(3), Long.valueOf(1), Long.valueOf(3));
        Rating rating4 = new Rating(Long.valueOf(4), Long.valueOf(1), Long.valueOf(3));
        Rating rating5 = new Rating(Long.valueOf(5), Long.valueOf(1), Long.valueOf(2));
        List<Rating> ratings = List.of(rating1, rating2, rating3, rating4, rating5);
        when(ratingRepository.findByMovieId(sample1.getMovieId())).thenReturn(ratings);
        User user1 = new User(Long.valueOf(1), "F", 1, "1", "40000");
        User user2 = new User(Long.valueOf(2), "M", 25, "1", "40001");
        User user3 = new User(Long.valueOf(3), "F", 35, "1", "40002");
        User user4 = new User(Long.valueOf(4), "M", 50, "1", "40003");
        when(userRepository.findById(rating1.getUser_id())).thenReturn(Optional.of(user1));
        when(userRepository.findById(rating2.getUser_id())).thenReturn(Optional.of(user2));
        when(userRepository.findById(rating3.getUser_id())).thenReturn(Optional.of(user3));
        when(userRepository.findById(rating4.getUser_id())).thenReturn(Optional.of(user4));

        assertTrue(movieServiceImpl.getMovieStats(sample1.getMovieId()) instanceof List<Long> && movieServiceImpl.getMovieStats(sample1.getMovieId()).size() == 8);
    }

    @Test
    public void testException() {
        Movie sample1 = new Movie(Long.valueOf(1), "title (1900)", "genres");
        when(movieRepository.findById(sample1.getMovieId())).thenReturn(Optional.of(sample1));
        Rating rating1 = new Rating(Long.valueOf(1), Long.valueOf(1), Long.valueOf(5));
        Rating rating2 = new Rating(Long.valueOf(2), Long.valueOf(1), Long.valueOf(4));
        Rating rating3 = new Rating(Long.valueOf(3), Long.valueOf(1), Long.valueOf(3));
        Rating rating4 = new Rating(Long.valueOf(4), Long.valueOf(1), Long.valueOf(4));
        User invalidGenderUser1 = new User(Long.valueOf(1), null, 1, "1", "40000");
        User invalidGenderUser2 = new User(Long.valueOf(2), "Female", 1, "1", "40001");
        User invalidAgeUser = new User(Long.valueOf(3), "F", -1, "1", "40002");
        when(userRepository.findById(rating1.getUser_id())).thenReturn(Optional.of(invalidGenderUser1));
        when(userRepository.findById(rating2.getUser_id())).thenReturn(Optional.of(invalidGenderUser2));
        when(userRepository.findById(rating3.getUser_id())).thenReturn(Optional.empty());
        when(userRepository.findById(rating4.getUser_id())).thenReturn(Optional.of(invalidAgeUser));

        List<Rating> ratings = List.of(rating1);
        when(ratingRepository.findByMovieId(sample1.getMovieId())).thenReturn(ratings);
        assertThrows(InvalidUserException.class, () -> movieServiceImpl.getMovieStats(sample1.getMovieId()));
        ratings = List.of(rating2);
        when(ratingRepository.findByMovieId(sample1.getMovieId())).thenReturn(ratings);
        assertThrows(InvalidUserException.class, () -> movieServiceImpl.getMovieStats(sample1.getMovieId()));
        ratings = List.of(rating3);
        when(ratingRepository.findByMovieId(sample1.getMovieId())).thenReturn(ratings);
        assertThrows(InvalidUserException.class, () -> movieServiceImpl.getMovieStats(sample1.getMovieId()));
        ratings = List.of(rating4);
        when(ratingRepository.findByMovieId(sample1.getMovieId())).thenReturn(ratings);
        assertThrows(InvalidUserException.class, () -> movieServiceImpl.getMovieStats(sample1.getMovieId()));
    }
}