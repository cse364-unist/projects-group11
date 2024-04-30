package cse364.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.MongoTemplate;

@RestController
class RatingController {

    private final MovieRepository repository;
    private final MongoTemplate mongoTemplate;
    RatingController(MovieRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }
    @PostMapping("/ratings")
    Rating newRating(@RequestBody Rating newRating) {

        // 필요하면 다시 코드짜야함
        return null;
    }

    @GetMapping("/ratings/{rating}")
    List<Movie> getMoviesWithAverageRatingGreaterThanOrEqual(@PathVariable("rating") int rating) {
        if (rating < 1 || rating > 5) {
            throw new NotInRangeException("rating");
        }
        GroupOperation groupByMovieId = Aggregation.group("movieId").avg("rate").as("averageRating");
        Aggregation aggregation = Aggregation.newAggregation(groupByMovieId);
        AggregationResults<AverageMovieRating> results = mongoTemplate.aggregate(aggregation, "ratings", AverageMovieRating.class);
        List<AverageMovieRating> ratings = results.getMappedResults();
        List<AverageMovieRating> filteredRatings = ratings.stream()
            .filter(ratingResult -> ratingResult.getAverageRating() >= rating)
            .collect(Collectors.toList());

        List<Long> movieIds = filteredRatings.stream()
            .map(AverageMovieRating::getId)
            .collect(Collectors.toList());
        List<Movie> movies = mongoTemplate.find(
            Query.query(Criteria.where("movie_id").in(movieIds)),
            Movie.class
        );
        return movies;
    }

    @PutMapping("/ratings")
    Rating replaceRating(@RequestBody Rating newRating) {

        // 선재의 방식 사용
        return null;
    }

}