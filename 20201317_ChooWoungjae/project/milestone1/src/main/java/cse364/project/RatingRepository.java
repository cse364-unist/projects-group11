package cse364.project;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Aggregation;

public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByMovieId(Long movieId);

    @Aggregation(pipeline = {
        "{ $lookup: { from: 'users', localField: 'userId', foreignField: 'userId', as: 'userDetails' } }",
        "{ $unwind: '$userDetails' }",
        "{ $group: { _id: { movieId: '$movieId', gender: '$userDetails.gender', age: '$userDetails.age' }, avgRating: { $avg: '$rating' } } }",
        "{ $sort: { '_id.movieId': 1, '_id.gender': 1, '_id.age': 1 } }"
    })
    List<MovieRatingByDemographic> getAverageRatingsByMovieIdGenderAndAge();

}