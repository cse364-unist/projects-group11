package cse364.project;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, Long> {
    @Query("{ 'title' : { $regex: ?0, $options: 'i' } }")
    List<Movie> findByTitleRegex(String regex);
}