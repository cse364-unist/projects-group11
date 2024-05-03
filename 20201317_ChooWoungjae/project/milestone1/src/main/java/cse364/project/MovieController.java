package cse364.project;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


@RestController
class MovieController {

    private final MovieRepository movieRepository;
    private final MongoTemplate mongoTemplate;

    MovieController(MovieRepository movieRepository, MongoTemplate mongoTemplate) {
        this.movieRepository = movieRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/movies")
    List<Movie> all() {
        return movieRepository.findAll();
    }

    @PostMapping("/movies")
    Movie newMovie(@RequestBody Movie newMovie) {
        return movieRepository.save(newMovie);
    }

    @PostMapping("/movies/{id}")
    int[] getIntervalRating(@PathVariable Long id){
        Movie nowMovie = movieRepository.findById(id).get();
        int[] ans = new int[15];
        for(int i = 0 ; i < 14 ; i ++){
            ans[i] += nowMovie.getIntervalPairList(i, 1);
        }
        return ans;
    }

    // Single item

    @GetMapping("/movies/{id}")
    Movie one(@PathVariable Long id) {
        return movieRepository.findById(id)
            .orElseThrow(() -> new CannotFoundException("Movie_ID", id));
    }

    @PutMapping("/movies/{id}")
    Movie replaceMovie(@RequestBody Movie newMovie, @PathVariable Long id) {
        return movieRepository.findById(id)
            .map(movie -> {
                movie.setTitle(newMovie.getTitle());
                movie.setGenres(newMovie.getGenres());
                return movieRepository.save(movie);
            })
            .orElseGet(() -> {
                newMovie.setMovieId(id);
                return movieRepository.save(newMovie);
            });
    }

    @DeleteMapping("/movies/{id}")
    void deleteMovie(@PathVariable Long id) {
        Optional<Movie> optional = movieRepository.findById(id);
        if(optional.isPresent()){
            Movie TargetMovie = optional.get();
            movieRepository.delete(TargetMovie);
        } else{
            throw new CannotFoundException("Movie_ID", id);
        }
    }
}