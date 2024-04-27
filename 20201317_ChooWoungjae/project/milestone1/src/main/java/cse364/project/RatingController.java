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

    @GetMapping("/ratings/{rating}")
    List<Movie> getMoviesWithAverageRatingGreaterThanOrEqual(@PathVariable("rating") int rating) {
        if (rating < 1 || rating > 5) {
            throw new RatingInvalidException(rating);
        }
        GroupOperation groupByMovieId = Aggregation.group("movieId").avg("rating").as("averageRating");
        Aggregation aggregation = Aggregation.newAggregation(groupByMovieId);
        AggregationResults<AverageMovieRating> results = mongoTemplate.aggregate(aggregation, "rating", AverageMovieRating.class);
        List<AverageMovieRating> ratings = results.getMappedResults();
        logger.debug("Received {} ratings, first 5: {}", ratings.size(), ratings.subList(1, 5));
        List<AverageMovieRating> filteredRatings = ratings.stream()
            .filter(ratingResult -> ratingResult.getAverageRating() >= rating)
            .collect(Collectors.toList());
        logger.debug("Filtered {} ratings with rating greater than or equal to {}", filteredRatings.size(), rating);
        List<Long> movieIds = filteredRatings.stream()
            .map(AverageMovieRating::getId)
            .collect(Collectors.toList());
        logger.debug("Filtered movieIds: {}", movieIds);
        List<Movie> movies = mongoTemplate.find(
            Query.query(Criteria.where("movieId").in(movieIds)),
            Movie.class
        );
        logger.debug("Found {} movies", movies.size());
        return movies;
    }

    @PutMapping("/ratings")
    Rating replaceRating(@RequestBody Rating newRating) {

        // 선재의 방식 사용
        return null;
    }

}