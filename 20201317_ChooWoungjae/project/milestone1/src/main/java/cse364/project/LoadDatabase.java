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

            // LookupOperation lookup = LookupOperation.newLookup()
            //     .from("roomTypes")
            //     .localField("roomtypeId")
            //     .foreignField("_id")
            //     .as("roomType");

            List<MovieRatingByDemographic> AverageOfRating = ratingRepository.getAverageRatingsByMovieIdGenderAndAge();

            Comparator<MovieRatingByDemographic> comparator = new Comparator<MovieRatingByDemographic>() {    // define the sort algorithm of MovieRatingByDemographic
                @Override
                public int compare(MovieRatingByDemographic a, MovieRatingByDemographic b){
                    if(a.getGender().equals(b.getGender())){
                        if(a.getAge().equals(b.getAge())){
                            Long ans = a.getMovieId() - b.getMovieId();
                            return ans.intValue();
                        }
                        else{
                            return a.getAge() - b.getAge();
                        }
                    }
                    else{
                        if(a.getGender().equals("F")){
                            return 1;
                        }
                        else{
                            return -1;
                        }
                    }
                }
            };

            Collections.sort(AverageOfRating, comparator);

            sz = MovieData.size();                       // In below code, I will calculate cosineSimilarity with averageOfRating data.
                                                         // This cosineSimilarity means that similarity of two interval(i, j)
                                                         // The equation of similarity of two vector is "similarity = vector_i * vector_j / lengthOfVector_i * lengthOfVector_j"
            for(int i = 0 ; i < 14 ; i ++) {
                for (int j = i + 1; j < 14; j++) {
                    Double DotProduct = 0.0;
                    Double LengthOfI = 0.0;
                    Double LengthOfJ = 0.0;
                    for (int k = 0; k < sz; k++) {
                        DotProduct += AverageOfRating.get(i * sz + k).getAvgRating() * AverageOfRating.get(j * sz + k).getAvgRating();
                        LengthOfI += AverageOfRating.get(i * sz + k).getAvgRating() * AverageOfRating.get(i * sz + k).getAvgRating();
                        LengthOfJ += AverageOfRating.get(j * sz + k).getAvgRating() * AverageOfRating.get(j * sz + k).getAvgRating();
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