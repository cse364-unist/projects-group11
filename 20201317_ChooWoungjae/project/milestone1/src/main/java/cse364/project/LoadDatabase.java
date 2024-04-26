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
    CommandLineRunner initDatabase( MovieRepository movieRepository, UserRepository userRepository, GiftRepository giftRepository) {

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

            //레이팅 파싱해서 저장하는것 해야함
        };
    }
}