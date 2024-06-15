package cse364.project;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    public MovieServiceImpl(MovieRepository movieRepository, RatingRepository ratingRepository,
            UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Long> getMovieStats(Long movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isEmpty()) {
            throw new CannotFoundException("movie", movieId);
        }

        List<Rating> ratings = ratingRepository.findByMovieId(movieId);

        long sumOfRatings = 0;
        long numOfRatings = ratings.size();
        long numMaleOver3 = 0;
        long numFemaleOver3 = 0;
        long num1Over3 = 0;
        long num25Over3 = 0;
        long num35Over3 = 0;
        long num50Over3 = 0;

        for (Rating rating : ratings) {
            long ratingValue = rating.getRate();
            if (ratingValue >= 3) {
                sumOfRatings += ratingValue;

                Long userId = rating.getUser_id();
                Optional<User> userOptional = userRepository.findById(userId);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    String gender = user.getGender();
                    int age = user.getAge();

                    if (gender != null) {
                        if (gender.equals("M")) {
                            numMaleOver3++;
                        } else if (gender.equals("F")) {
                            numFemaleOver3++;
                        } else {
                            throw new InvalidUserException("gender", gender);
                        }
                    } else {
                        throw new InvalidUserException("gender", gender);
                    }

                    if (age >= 50) {
                        num50Over3++;
                    } else if (age >= 35) {
                        num35Over3++;
                    } else if (age >= 25) {
                        num25Over3++;
                    } else if (age >= 1) {
                        num1Over3++;
                    } else {
                        throw new InvalidUserException("age", String.valueOf(age));
                    }
                } else {
                    throw new InvalidUserException("userId", String.valueOf(userId));
                }
            }
        }

        List<Long> movieStats = new ArrayList<>();
        movieStats.add(numMaleOver3);
        movieStats.add(numFemaleOver3);
        movieStats.add(num1Over3);
        movieStats.add(num25Over3);
        movieStats.add(num35Over3);
        movieStats.add(num50Over3);
        movieStats.add(sumOfRatings);
        movieStats.add(numOfRatings);

        return movieStats;
    }
}
