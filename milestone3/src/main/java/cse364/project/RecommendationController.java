package cse364.project;

import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // Input: curl -X POST "http://localhost:8080/recommendations?recomList=0,1,0,3,1,2,-1,3,5,11,14"
    @GetMapping("/recommendations")
    List<Movie> targetMovie(@RequestParam List<Integer> recomList) {
        // userList is the member of requested group, stringList is hate genre list.
        
        String[] genderArr = {"M", "F"};
        int[] ageArr = {1, 18, 25, 35, 45, 50, 56};
        String[] genreArr = {"Action", "Adventure", "Animation", "Children's", "Comedy",
                                "Crime", "Documentary", "Drama", "Fantasy", "Film-Noir", "Horror",
                                "Musical", "Mystery", "Romance", "Sci-Fi", "Thriller", "War"};
        int[][] allCases = {{0,0,0,0,0,0}, {0,0,0,0,0,0}};
        
        List<Movie> movieList = movieRepository.findAll();
        List<User> userList = new ArrayList<User>();
        int jj = 0;
        while (recomList.get(jj) != -1) {
            allCases[recomList.get(jj)][recomList.get(jj+1)]++;
            jj += 2;
            if (jj >= recomList.size())
                throw new InvalidUserException("from recommendation", "invalid recomList");
        }
        for (int i = 0; i < 12; i++) {
            if (allCases[i/6][i%6] != 0) {
                User newUser = new User(genderArr[i/6], ageArr[i%6], allCases[i/6][i%6]);
                userList.add(newUser);
            } 
        }
        List<String> stringList = new ArrayList<String>();
        jj++;
        for (int i = jj; i < recomList.size(); i++) {
            stringList.add(genreArr[recomList.get(i)]);
        }

        List<Pair<Double, Long>> predicationRatingList = new ArrayList<>();
        List<Movie> recommendation = new ArrayList<>();                          // Each movie's (predication rating, movieId) pair.
        Double[] weight = new Double[15];                                        // Each interval's weight(calculate by similarity and group member)
        for(int i = 0 ; i < 15 ; i ++){
            weight[i] = Double.valueOf(0.0);
        }
        int userListSize = userList.size();
        if(userListSize == 0){
            throw new CannotFoundException("AnyUser", 0);
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
                Double diff = b.getLeft() - a.getLeft();
                return diff.compareTo(0.0);
            }
        };

        Collections.sort(predicationRatingList, comparator);

        int count = 0;
        for(int i = 0 ; i < predicationRatingListSize ; i ++){
            int isPerfect = predicationRatingList.get(i).getLeft().compareTo(4.99999);    ///  Some movie has all 5 rating, so remove that always highScore movie.
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