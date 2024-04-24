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

@RestController
class MovieController {

    private final MovieRepository repository;

    MovieController(MovieRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/movies")
    List<AllMovies> all() {

        List<AllMovies> ANS = new ArrayList<>();

        List<Movie> movies = repository.findAll();
        int sz = movies.size();
        for(int i = 0 ; i < sz ; i ++){
            Movie nowMovie = movies.get(i);
            Long nowMovieID = nowMovie.getMovie_id();
            String nowMovieTitle = nowMovie.getTitle();
            String nowMovieGenres = nowMovie.getGenres();
            AllMovies newAllMovies = new AllMovies(nowMovieID, nowMovieTitle, nowMovieGenres);

            ANS.add(newAllMovies);
        }

        return ANS;
    }

    @PostMapping("/movies")
    Movie newMovie(@RequestBody Movie newMovie) {

        Long id = newMovie.getMovie_id();
        Optional<Movie> optional = repository.findById(id);
        if(optional.isPresent()){
            throw new AlreadyExistException("Movie");
        } else {
            return repository.save(newMovie);
        }
    }

    // Single item

    @GetMapping("/movies/{id}")
    Movie one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new CannotFoundException("Movie_ID", id));
    }

    @PutMapping("/movies/{id}")
    Movie replaceMovie(@RequestBody Movie newMovie, @PathVariable Long id) {

        Optional<Movie> optional = repository.findById(id);
        if(optional.isPresent()){
            Long NewMovieID = newMovie.getMovie_id();
            if(NewMovieID.equals(id)) {
                return repository.save(newMovie);
            } else{
                throw new CannotChangeIDException("Movie");
            }
        } else{
            throw new CannotFoundException("Movie_ID", id);
        }
    }

    @DeleteMapping("/Movies/{id}")
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