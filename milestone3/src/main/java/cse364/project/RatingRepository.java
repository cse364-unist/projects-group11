package cse364.project;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByMovieId(Long movieId);
}