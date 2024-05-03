package cse364.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoadDatabaseTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RatingRepository ratingRepository;
    @Mock
    private CosineSimilarityRepository cosineSimilarityRepository;
    @Mock
    ReadFile readFile;

    @InjectMocks
    LoadDatabase loadDatabase;

    @AfterEach
    void setUp() {
        loadDatabase = new LoadDatabase();
    }

    @Test
    void testInitDatabase() throws Exception {
        List<List<String>> movieData = new ArrayList<>();
        movieData.add(List.of("1", "Toy Story (1995)", "Animation|Children's|Comedy"));
        movieData.add(List.of("2", "Jumanji (1995)", "Adventure|Children's|Fantasy"));
        movieData.add(List.of("3", "Grumpier Old Men (1995)", "Comedy|Romance"));

        List<List<String>> userData = new ArrayList<>();
        userData.add(List.of("1", "F", "1", "10", "48067"));
        userData.add(List.of("2", "M", "56", "16", "70072"));
        userData.add(List.of("3", "M", "25", "15", "55117"));

        List<List<String>> ratingData = new ArrayList<>();
        ratingData.add(List.of("1", "1", "5"));
        ratingData.add(List.of("1", "2", "3"));
        ratingData.add(List.of("2", "1", "3"));
        ratingData.add(List.of("2", "3", "4"));

        when(readFile.readDAT("/root/project/milestone2/data/movies.dat")).thenReturn(movieData);
        when(readFile.readDAT("/root/project/milestone2/data/users.dat")).thenReturn(userData);
        when(readFile.readDAT("/root/project/milestone2/data/ratings.dat")).thenReturn(ratingData);

        Movie movie1 = new Movie(1L, "Toy Story (1995)", "Animation|Children's|Comedy");
        Movie movie2 = new Movie(2L, "Jumanji (1995)", "Adventure|Children's|Fantasy");
        Movie movie3 = new Movie(3L, "Grumpier Old Men (1995)", "Comedy|Romance");
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie1));
        when(movieRepository.findById(2L)).thenReturn(Optional.of(movie2));
        when(movieRepository.findById(3L)).thenReturn(Optional.of(movie3));
        when(movieRepository.save(any(Movie.class))).thenAnswer(i -> i.getArguments()[0]);

        User user1 = new User(1L, "F", 1, "10", "48067");
        User user2 = new User(2L, "M", 56, "16", "70072");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);
        movieList.add(movie3);
        when(movieRepository.findAll()).thenReturn(movieList);

        CommandLineRunner runner = loadDatabase.initDatabase(movieRepository, userRepository, ratingRepository, cosineSimilarityRepository);
        runner.run();

    }
}