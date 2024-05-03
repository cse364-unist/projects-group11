package cse364.project;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Aggregation;

public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByMovieId(Long movieId);
}