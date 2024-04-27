package cse364.project;

import org.hibernate.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Optional;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private static final ReadFile readFile = new ReadFile();

    @Bean
    CommandLineRunner initDatabase( MovieRepository movieRepository, UserRepository userRepository, RatingRepository ratingRepository, GiftRepository giftRepository, CosineSimilarityRepository cosineSimilarityRepository) {

        return args -> {
            List<List<String>> MovieData = readFile.readDAT("/root/project/milestone1/data/movies.dat");   // Load Movie Data
            int sz = MovieData.size();
            for(int i = 0 ; i < sz ; i ++) {
                Long MovieID = Long.parseLong(MovieData.get(i).get(0));
                Movie NewMovie = new Movie(MovieID, MovieData.get(i).get(1), MovieData.get(i).get(2));

                movieRepository.save(NewMovie);
            }

            List<List<String>> UserData = readFile.readDAT("/root/project/milestone1/data/users.dat");   // Load User Data
            sz = UserData.size();
            for(int i = 0 ; i < sz ; i ++) {
                Long UserID = Long.parseLong(UserData.get(i).get(0));
                int UserAge = Integer.parseInt(UserData.get(i).get(2));
                User NewUser = new User(UserID, UserData.get(i).get(1), UserAge, UserData.get(i).get(3), UserData.get(i).get(4));

                userRepository.save(NewUser);
            }

            List<List<String>> RatingData = readFile.readDAT("/root/project/milestone1/data/ratings.dat");   // Load Rating Data
            sz = RatingData.size();
            for(int i = 0 ; i < sz ; i ++){
                Long userId = Long.parseLong(RatingData.get(i).get(0));
                Long movieId = Long.parseLong(RatingData.get(i).get(1));
                Long rating = Long.parseLong(RatingData.get(i).get(2));
                Rating newRating = new Rating(userId, movieId, rating);

                ratingRepository.save(newRating);
            }

            List<List<Double>> AverageOfRating = new ArrayList<>(); // interval's average data store here
                                                                        // I don't know how to insert the average (Why no korean?)
            sz = MovieData.size();
            for(int i = 0 ; i < 14 ; i ++) {
                Integer leftTarget = Integer.valueOf(i);
                for (int j = i + 1; j < 14; j++) {
                    Double DotProduct = 0.0;
                    Double LengthOfI = 0.0;
                    Double LengthOfJ = 0.0;
                    for (int k = 0; k < sz; k++) {
                        DotProduct += AverageOfRating.get(i).get(k) * AverageOfRating.get(j).get(k);
                        LengthOfI += AverageOfRating.get(i).get(k) * AverageOfRating.get(i).get(k);
                        LengthOfJ += AverageOfRating.get(j).get(k) * AverageOfRating.get(j).get(k);
                    }
                    LengthOfI = Math.sqrt(LengthOfI);
                    LengthOfJ = Math.sqrt(LengthOfJ);
                    CosineSimilarity newCosineSimilarity = new CosineSimilarity(Pair.of(Integer.valueOf(i), Integer.valueOf(j)), (DotProduct / (LengthOfI * LengthOfJ)));
                    cosineSimilarityRepository.save(newCosineSimilarity);
                }
            }
        };
    }
}