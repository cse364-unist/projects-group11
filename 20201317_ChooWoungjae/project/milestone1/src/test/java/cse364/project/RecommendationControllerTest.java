package cse364.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RecommendationControllerTest {

    @InjectMocks
    RecommendationController recommendationController;

    @Mock
    CosineSimilarityRepository similarityRepository;
    @Mock
    MovieRepository movieRepository;

    CosineSimilarity[] similarityList = {
        new CosineSimilarity(Integer.valueOf(0), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(1), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(2), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(3), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(4), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(5), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(6), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(7), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(8), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(9), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(10), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(11), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(12), Double.valueOf(3.3)),
        new CosineSimilarity(Integer.valueOf(13), Double.valueOf(3.3)),
    };

    @Test
    public void testTargetMovie() {
        
        User userSample1 = new User(Long.valueOf(1), "M", 1, "1", "40000");
        List<User> userList = List.of(userSample1);

        String genreSample1 = new String("2");
        List<String> genreList = List.of(genreSample1);

        Recommendation newRecommendation = new Recommendation(userList, genreList);

        for (int i = 1; i < 14; i++) {
            when(similarityRepository.findById(Integer.valueOf(i))).thenReturn(Optional.of(similarityList[i]));
        }

        Movie movieSample1 = new Movie(Long.valueOf(1), "title1 (2000)", "1");
        Movie movieSample2 = new Movie(Long.valueOf(2), "title2 (2001)", "2");
        for(int i = 0 ; i < 15 ; i ++){
            movieSample1.setIntervalPairList(i, 0, 20);
            movieSample1.setIntervalPairList(i, 1, 5);
            movieSample1.setIntervalPairList(i, 0, 30);
            movieSample1.setIntervalPairList(i, 1, 6);
        }
        List<Movie> movieList = List.of(movieSample1, movieSample2);
        when(movieRepository.findAll()).thenReturn(movieList);

        assertTrue(recommendationController.targetMovie(newRecommendation) instanceof List<Movie>);
    }

    @Test
    public void testEmptyUserList() {
        Movie movieSample1 = new Movie(Long.valueOf(1), "title1 (2000)", "1");
        Movie movieSample2 = new Movie(Long.valueOf(2), "title2 (2001)", "2");
        for(int i = 0 ; i < 15 ; i ++){
            movieSample1.setIntervalPairList(i, 0, 20);
            movieSample1.setIntervalPairList(i, 1, 5);
            movieSample1.setIntervalPairList(i, 0, 30);
            movieSample1.setIntervalPairList(i, 1, 6);
        }
        List<Movie> movieList = List.of(movieSample1, movieSample2);
        when(movieRepository.findAll()).thenReturn(movieList);

        List<User> userList = List.of();

        String genreSample1 = new String();
        List<String> genreList = List.of(genreSample1);

        Recommendation newRecommendation = new Recommendation(userList, genreList);
        assertThrows(CannotFoundException.class ,() -> recommendationController.targetMovie(newRecommendation));
    }

    @Test
    public void testNoRecommendation() {
        User userSample1 = new User(Long.valueOf(1), "M", 1, "1", "40000");
        List<User> userList = List.of(userSample1);

        String genreSample1 = new String("2");
        List<String> genreList = List.of(genreSample1);

        Recommendation newRecommendation = new Recommendation(userList, genreList);

        for (int i = 1; i < 14; i++) {
            when(similarityRepository.findById(Integer.valueOf(i))).thenReturn(Optional.of(similarityList[i]));
        }
        
        // test empty movie list
        List<Movie> movieList = List.of();
        when(movieRepository.findAll()).thenReturn(movieList);
        assertThrows(NoSatisfactoryMovieException.class ,() -> recommendationController.targetMovie(newRecommendation));
    }
    
}
