package cse364.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private static final ReadFile readFile = new ReadFile();

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository repository, MovieRepository movieRepository, UserRepository userRepository) {

        return args -> {
            log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));   // Load Employee Data
            log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));

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
                Long UserID = Long.parseLong(RatingData.get(i).get(0));
                Long MovieID = Long.parseLong(RatingData.get(i).get(1));
                Long Rate = Long.parseLong(RatingData.get(i).get(2));
                Rating NewRating = new Rating(UserID, MovieID, Rate);

                Movie NewMovie = movieRepository.findById(MovieID).get();
                NewMovie.addRatingList(NewRating);
                NewMovie.plusSumOfRating(Rate);
                NewMovie.plusNumOfRating(1L);

                movieRepository.save(NewMovie);

                /*Optional<Movie> optional = movieRepository.findById(MovieID);
                if(optional.isPresent()){
                    Movie NewMovie = optional.get();
                    NewMovie.addRatingList(NewRating);
                    NewMovie.plusSumOfRating(Rate);
                    NewMovie.plusNumOfRating(1L);
                } else{
                    throw new CannotFoundException("Movie_ID", MovieID);
                }*/
            }
        };
    }
}