package cse364.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RatingController {

    private final MovieRepository repository;
    RatingController(MovieRepository repository) {
        this.repository = repository;
    }


    @PostMapping("/ratings")
    Rating newRating(@RequestBody Rating newRating) {

        Long UserID = newRating.getUser_id();
        Long MovieID = newRating.getMovie_id();
        Long Rate = newRating.getRate();
        Optional<Movie> optional = repository.findById(MovieID);
        if(optional.isPresent()){
            Movie NowMovie = optional.get();
            Long sz = NowMovie.getNumOfRating();
            List<Rating> RatingList = NowMovie.getRatingList();
            int flag = 0;
            for(int i = 0 ; i < sz ; i ++){
                Rating NowRating = RatingList.get(i);
                if(NowRating.getUser_id().equals(UserID)){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                NowMovie.addRatingList(newRating);
                NowMovie.plusNumOfRating(1L);
                NowMovie.plusSumOfRating(Rate);
                repository.save(NowMovie);
                return newRating;
            } else{
                throw new AlreadyExistException("This rating");
            }
        } else{
            throw new CannotFoundException("Movie_ID", MovieID);
        }
    }

    @GetMapping("/ratings/{id}")
    List<SimpleMovie> all(@PathVariable Long id) {

        if(id < 1 || id > 5){
            throw new NotInRangeException("Given Rating");
        }
        List<SimpleMovie> ANS = new ArrayList<>();
        List<Movie> MovieList = repository.findAll();
        int sz = MovieList.size();
        Long z = 0L;
        for(int i = 0 ; i < sz ; i ++){
            Movie NowMovie = MovieList.get(i);
            if(NowMovie.getNumOfRating().equals(z)){
                continue;
            }
            Long Average = NowMovie.getSumOfRating() / NowMovie.getNumOfRating();
            if(Average >= id){
                SimpleMovie nowSimpleMovie = new SimpleMovie(NowMovie.getTitle(), NowMovie.getGenres());
                ANS.add(nowSimpleMovie);
            }
        }

        int ans_size = ANS.size();
        if(ans_size == 0){
            throw new NoSatisfactoryMovieException();
        } else{
            return ANS;
        }
    }

    @PutMapping("/ratings")
    Rating replaceRating(@RequestBody Rating newRating) {

        Long UserID = newRating.getUser_id();
        Long MovieID = newRating.getMovie_id();
        Long Rate = newRating.getRate();
        Optional<Movie> optional = repository.findById(MovieID);
        if(optional.isPresent()){
            Movie NowMovie = optional.get();
            Long sz = NowMovie.getNumOfRating();
            List<Rating> RatingList = NowMovie.getRatingList();
            int flag = 0;
            for(int i = 0 ; i < sz ; i ++){
                Rating NowRating = RatingList.get(i);
                if(UserID.equals(NowRating.getUser_id())){
                    flag = 1;
                    NowMovie.plusSumOfRating(Rate - NowRating.getRate());
                    NowMovie.setRatingList(i, newRating);
                    break;
                }
            }
            if(flag == 0){
                throw new CannotFoundException("User_ID", UserID);
            } else {
                repository.save(NowMovie);
                return newRating;
            }
        } else{
            throw new CannotFoundException("Movie_ID", MovieID);
        }
    }

    @DeleteMapping("/ratings/{id}")
    void deleteEmployee(@PathVariable Long id) {

        Optional<Movie> optional = repository.findById(id);
        if(optional.isPresent()){
            Movie TargetMovie = optional.get();
            repository.delete(TargetMovie);
        } else{
            throw new CannotFoundException("Movie_ID", id);
        }
    }
}