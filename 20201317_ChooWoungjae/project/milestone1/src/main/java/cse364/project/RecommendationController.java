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

    private final CosineSimilarityRepository similarityRepository;
    private final MovieRepository movieRepository;
    RecommendationController(CosineSimilarityRepository similarityRepository, MovieRepository movieRepository) {
        this.similarityRepository = similarityRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/recommendations")
    List<Movie> targetMovie(@RequestBody Recommendation newRecommendation) {
        // userList is the member of requested group, stringList is hate genre list.

        List<Movie> movieList = movieRepository.findAll();
        List<User> userList = newRecommendation.getUserList();
        List<String> stringList = newRecommendation.getGenreList();

        List<Pair<Double, Long>> predicationRatingList = new ArrayList<>();
        List<Movie> recommendation = new ArrayList<>();                          // Each movie's (predication rating, movieId) pair.
        Double[] weight = new Double[15];                                        // Each interval's weight(calculate by similarity and group member)
        for(int i = 0 ; i < 15 ; i ++){
            weight[i] = Double.valueOf(0.0);
        }
        int userListSize = userList.size();
        if(userListSize == 0){
            throw new CannotFoundException("SimpleUser", 0);
        }
        for(int i = 0 ; i < 14 ; i ++){                                           // Calculate each interval's similarity.
            double similarity = 0.0;
            int numOfPeople = 0;
            for(int j = 0 ; j < userListSize ; j ++) {                                       // Calculate the interval with gender and age
                int interval = userList.get(j).calculateInterval();
                int num = userList.get(j).getNumOfPeople();
                double nowSimilarity;
                if (i == interval) {                                              // if this interval's member is in requested group,
                    similarity = (double)num * 10.0;                                  // this interval's similarity is that interval's number of people
                                                                                    // data is so biased, therefore we give additional weight to the maching group.
                    numOfPeople = 1;                                            // and this value will be use to flag.
                    break;
                } else if (i < interval) {                                          // This if statement is because, there are only i < j pair in similarityRepository.
                    nowSimilarity = similarityRepository.findById(Integer.valueOf(i * 14 + interval)).get().getSimilarity().doubleValue();
                } else {
                    nowSimilarity = similarityRepository.findById(Integer.valueOf(interval * 14 + i)).get().getSimilarity().doubleValue();
                }
                similarity += nowSimilarity * (double) num;
                numOfPeople += num;
            }
            weight[i] = Double.valueOf(similarity / (double)numOfPeople);    // The result of calculate, if interval_i is exist in group, weight[i] = numOfPeople of interval_i
        }                                                                       // if not exist, then weight[i] is weighted average of similarity of group.
        int movieListSize = movieList.size();
        int genreSize = stringList.size(), flag = 0;
        for(int i = 0 ; i < movieListSize ; i ++){                                       // in each loop, we predicate the rating of requested group.
            Double estimatedScore = 0.0;
            Double sumOfSimilarity = 0.0;
            flag = 0;
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
                Double nowAvgRating = nowMovie.getAverageOfInterval(j);
                estimatedScore += nowAvgRating * weight[j];
                sumOfSimilarity += weight[j];
            }
            int isZero = sumOfSimilarity.compareTo(0.0);
            if(isZero <= 0){
                predicationRatingList.add(Pair.of(Double.valueOf(0.0), Long.valueOf(i)));
            }
            else{
                predicationRatingList.add(Pair.of((estimatedScore / sumOfSimilarity), nowMovie.getMovieId()));
            }

        }

        int predicationRatingListSize = predicationRatingList.size();
        if(predicationRatingListSize == 0){
            throw new NoSatisfactoryMovieException();
        }

        Comparator<Pair<Double, Long>> comparator = new Comparator<Pair<Double, Long>>(){
            @Override
            public int compare(Pair<Double, Long> a, Pair<Double, Long> b){
                if(a.getLeft().isNaN()) return 1;
                if(b.getLeft().isNaN()) return -1;
                if(a.getLeft().doubleValue() == b.getLeft().doubleValue()){
                    return a.getRight().intValue() - b.getRight().intValue();
                }
                else{
                    Double diff = b.getLeft() - a.getLeft();
                    return diff.compareTo(0.0);
                }
            }
        };

        //Collections.sort(predicationRatingList, comparator);
        Collections.sort(predicationRatingList, comparator);

        int count = 0;
        for(int i = 0 ; i < predicationRatingListSize ; i ++){
            int isPerfect = predicationRatingList.get(i).getLeft().compareTo(4.99999);
            if(isPerfect >= 0){
                continue;
            }
            if(predicationRatingList.get(i).getLeft().isNaN()){
                break;
            }
            Long nowMovieId = predicationRatingList.get(i).getRight();
            recommendation.add(movieRepository.findById(nowMovieId).get());
            count ++;
            if(count == 10){
                break;
            }
        }


        return recommendation;
    }

}