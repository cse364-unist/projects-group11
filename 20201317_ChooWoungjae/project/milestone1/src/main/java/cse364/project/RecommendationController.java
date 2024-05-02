package cse364.project;

import java.util.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.tuple.Pair;

@RestController
class RecommendationController {

    private final SimpleUserRepository repository;
    private final CosineSimilarityRepository similarityRepository;
    private final MovieRepository movieRepository;
    RecommendationController(SimpleUserRepository repository, CosineSimilarityRepository similarityRepository, MovieRepository movieRepository) {
        this.repository = repository;
        this.similarityRepository = similarityRepository;
        this.movieRepository = movieRepository;
    }


    @PutMapping("/recommendations")
    SimpleUser changeSimpleUser(@RequestBody SimpleUser newSimpleUser) {

        repository.save(newSimpleUser);

        return newSimpleUser;
    }

    @GetMapping("/recommendations")
    List<Movie> targetMovie(@RequestBody Recommendation newRecommendation) {
        // userList is the member of requested group, stringList is hate genre list.

        List<Movie> movieList = movieRepository.findAll();
        List<User> userList = newRecommendation.getUserList();
        List<String> stringList = newRecommendation.getGenreList();

        List<Pair<Double, Long>> predicationRatingList = new ArrayList<>();
        List<Movie> recommendation = new ArrayList<>();// Each movie's (predication rating, movieId) pair.
        Double[] weight = new Double[15];                                        // Each interval's weight(calculate by similarity and group member)
        int sz = userList.size();
        if(sz == 0){
            throw new CannotFoundException("SimpleUser", 0);
        }
        for(int i = 0 ; i < 14 ; i ++){                                           // Calculate each interval's similarity.
            double similarity = 0.0;
            int numOfPeople = 0;
            for(int j = 0 ; j < sz ; j ++){                                       // Calculate the interval with gender and age
                int interval = userList.get(j).calculateInterval();
                int num = userList.get(j).getNumOfPeople();
                double nowSimilarity;
                if(i == interval){                                              // if this interval's member is in requested group,
                    similarity = num;                                           // this interval's similarity is that interval's number of people
                    numOfPeople = 1;                                            // and this value will be use to flag.
                    break;
                }
                else if(i < interval){                                          // This if statement is because, there are only i < j pair in similarityRepository.
                    nowSimilarity = similarityRepository.findByTarget(Pair.of(i, interval)).get().getSimilarity();
                }
                else{
                    nowSimilarity = similarityRepository.findByTarget(Pair.of(interval, i)).get().getSimilarity();
                }                                                               // after above code, the similarity of i and interval is load in nowSimilarity.
                similarity += nowSimilarity * (double)num;
                numOfPeople += num;
            }
            weight[i] = Double.valueOf(similarity / (double)numOfPeople);    // The result of calculate, if interval_i is exist in group, weight[i] = numOfPeople of interval_i
        }                                                                       // if not exist, then weight[i] is weighted average of similarity of group.
        sz = movieList.size();
        int genreSize = stringList.size(), flag = 0;
        for(int i = 0 ; i < 3952 ; i ++){                                       // in each loop, we predicate the rating of requested group.
            Double estimatedScore = 0.0;
            Double sumOfSimilarity = 0.0;
            Movie nowMovie = movieList.get(i);
            String genre = nowMovie.getGenres();
            for(int j = 0 ; j < genreSize ; j ++){
                String nowGenre = stringList.get(j);
                if(genre.contains(nowGenre)){
                    flag = 1;
                    break;
                }
            }
            if(flag == 1) continue;
            for(int j = 0 ; j < 14 ; j ++){
                if(nowMovie.getIntervalPairList(j, 0) == 0) continue;
                Double nowAvgRating = Double.valueOf((double)nowMovie.getIntervalPairList(j, 0) / (double)nowMovie.getIntervalPairList(j, 1));
                estimatedScore += nowAvgRating * weight[j];
                sumOfSimilarity += weight[j];
            }
            predicationRatingList.add(Pair.of((estimatedScore / sumOfSimilarity), Long.valueOf(i)));
        }

        int predicationRatingListSize = predicationRatingList.size();
        if(predicationRatingListSize == 0){
            throw new NoSatisfactoryMovieException();
        }

        Collections.sort(predicationRatingList, Collections.reverseOrder());

        if(predicationRatingListSize >= 5) predicationRatingListSize = 5;
        for(int i = 0 ; i < predicationRatingListSize ; i ++){
            int nowMovieId = predicationRatingList.get(i).getRight().intValue();
            recommendation.add(movieList.get(nowMovieId));
        }


        return recommendation;
    }

    @PostMapping("/recommendations")
    SimpleUser addSimpleUser(@RequestBody SimpleUser newSimpleUser) {

        repository.save(newSimpleUser);

        return newSimpleUser;
    }

}