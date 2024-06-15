package cse364.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.List;
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
        // necessary for user1
        new CosineSimilarity(Integer.valueOf(0), Double.valueOf(0.8)),
        new CosineSimilarity(Integer.valueOf(1), Double.valueOf(0.7)),
        new CosineSimilarity(Integer.valueOf(2), Double.valueOf(0.9)),
        new CosineSimilarity(Integer.valueOf(3), Double.valueOf(0.6)),
        new CosineSimilarity(Integer.valueOf(4), Double.valueOf(1.0)),
        new CosineSimilarity(Integer.valueOf(5), Double.valueOf(0.8)),
        new CosineSimilarity(Integer.valueOf(6), Double.valueOf(0.7)),
        new CosineSimilarity(Integer.valueOf(7), Double.valueOf(1.1)),
        new CosineSimilarity(Integer.valueOf(8), Double.valueOf(0.9)),
        new CosineSimilarity(Integer.valueOf(9), Double.valueOf(0.7)),
        new CosineSimilarity(Integer.valueOf(10), Double.valueOf(0.8)),
        new CosineSimilarity(Integer.valueOf(11), Double.valueOf(0.8)),
        new CosineSimilarity(Integer.valueOf(12), Double.valueOf(0.6)),
        new CosineSimilarity(Integer.valueOf(13), Double.valueOf(1.0)),
        // necessary for user2
        new CosineSimilarity(Integer.valueOf(21), Double.valueOf(0.7)),
        new CosineSimilarity(Integer.valueOf(35), Double.valueOf(0.8)),
        new CosineSimilarity(Integer.valueOf(49), Double.valueOf(0.8)),
        new CosineSimilarity(Integer.valueOf(63), Double.valueOf(0.6)),
        new CosineSimilarity(Integer.valueOf(77), Double.valueOf(1.0)),
        new CosineSimilarity(Integer.valueOf(91), Double.valueOf(0.7)),
        new CosineSimilarity(Integer.valueOf(106), Double.valueOf(0.8)),
        new CosineSimilarity(Integer.valueOf(107), Double.valueOf(0.8)),
        new CosineSimilarity(Integer.valueOf(108), Double.valueOf(0.8)),
        new CosineSimilarity(Integer.valueOf(109), Double.valueOf(0.6)),
        new CosineSimilarity(Integer.valueOf(110), Double.valueOf(1.0))
    };

    @Test
    public void testTargetMovie() {
        List<Integer> recomList = List.of(0, 1, -1, 3, 4);

        for (int i = 1; i < 14; i++) {
            when(similarityRepository.findById(similarityList[i].getTarget())).thenReturn(Optional.of(similarityList[i]));
        }

        // Creating sample Movie List
        List<Movie> movieList = List.of(
            new Movie(Long.valueOf(1), "title1 (2000)", "1"),
            new Movie(Long.valueOf(2), "title2 (2001)", "2"),
            new Movie(Long.valueOf(3), "title3 (2002)", "3"),
            new Movie(Long.valueOf(4), "title4 (2003)", "2")
        );
        for (int i = 0; i < 15; i++) {
            movieList.get(0).setIntervalPairList(i, 0, 18);
            movieList.get(0).setIntervalPairList(i, 1, 5);
            movieList.get(1).setIntervalPairList(i, 0, 30);
            movieList.get(1).setIntervalPairList(i, 1, 6);
            movieList.get(2).setIntervalPairList(i, 0, 28);
            movieList.get(2).setIntervalPairList(i, 1, 6);
            movieList.get(3).setIntervalPairList(i, 0, 0);
            movieList.get(3).setIntervalPairList(i, 1, 0);
        }

        when(movieRepository.findAll()).thenReturn(movieList);
        for (Movie movie : movieList) {
            lenient().when(movieRepository.findById(movie.getMovieId())).thenReturn(Optional.of(movie));
        }

        List<Movie> recommendations = recommendationController.targetMovie(recomList);
        assertTrue(recommendations instanceof List<Movie>);
    }
    
}
