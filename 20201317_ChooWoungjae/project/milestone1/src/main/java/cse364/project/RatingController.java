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

        // 필요하면 다시 코드짜야함
        return null;
    }

    @GetMapping("/ratings/{id}")
    List<Movie> all(@PathVariable Long id) {

        //선재의 방식 사용
        return null;
    }

    @PutMapping("/ratings")
    Rating replaceRating(@RequestBody Rating newRating) {

        // 선재의 방식 사용
        return null;
    }

}