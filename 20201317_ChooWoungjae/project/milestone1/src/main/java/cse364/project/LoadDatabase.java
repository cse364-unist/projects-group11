package cse364.project;

import org.hibernate.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

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



            // LookupOperation lookup = LookupOperation.newLookup()
            //     .from("roomTypes")
            //     .localField("roomtypeId")
            //     .foreignField("_id")
            //     .as("roomType");

            List<Movie> allMovie = movieRepository.findAll();
            sz = allMovie.size();                       // In below code, I will calculate cosineSimilarity with averageOfRating data.
                                                         // This cosineSimilarity means that similarity of two interval(i, j)
                                                         // The equation of similarity of two vector is "similarity = vector_i * vector_j / lengthOfVector_i * lengthOfVector_j"
            Double[] vectorLength = new Double[15];
            Double[][] dotProduct = new Double[15][15];

            for(int i = 0 ; i < 14 ; i ++){
                vectorLength[i] = Double.valueOf(0.0);
                for(int j = 0 ; j < 14 ; j ++){
                    dotProduct[i][j] = Double.valueOf(0.0);
                }
            }

            for(int k = 0 ; k < sz ; k ++){
                Movie nowMovie = allMovie.get(k);
                for(int i = 0 ; i < 14 ; i ++){
                    Double nowVectorCoordinate = nowMovie.getAverageOfInterval(i);
                    vectorLength[i] += nowVectorCoordinate * nowVectorCoordinate;
                    for(int j = i + 1 ; j < 14 ; j ++){
                        dotProduct[i][j] += nowMovie.getAverageOfInterval(i) * nowMovie.getAverageOfInterval(j);
                    }
                }
            }
            for(int i = 0 ; i < 14 ; i ++){
                vectorLength[i] = Math.sqrt(vectorLength[i]);
            }
            for(int i = 0 ; i < 14 ; i ++){
                for(int j = i + 1 ; j < 14 ; j ++){
                    CosineSimilarity newCosineSimilarity = new CosineSimilarity(Integer.valueOf(i * 14 + j), (dotProduct[i][j] / (vectorLength[i] * vectorLength[j])));
                    cosineSimilarityRepository.save(newCosineSimilarity);
                }
            }


            List<List<String>> RatingData = readFile.readDAT("/root/project/milestone1/data/ratings.dat");   // Load Rating Data
            sz = RatingData.size();
            for(int i = 0 ; i < sz ; i ++){
                Long userId = Long.parseLong(RatingData.get(i).get(0));
                Long movieId = Long.parseLong(RatingData.get(i).get(1));
                Long rating = Long.parseLong(RatingData.get(i).get(2));
                Rating newRating = new Rating(userId, movieId, rating);

                ratingRepository.save(newRating);

                Movie newMovie = movieRepository.findById(movieId).get();
                User newUser = userRepository.findById(userId).get();
                int interval = newUser.calculateInterval();
                newMovie.plusIntervalPairList(interval, 0, rating.intValue());
                newMovie.plusIntervalPairList(interval, 1, 1);
            }


            System.out.println("end parsing\n");
        };
    }
}