package cse364.project;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComparisonController {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final MovieService movieService;

    public ComparisonController(MovieRepository movieRepository, UserRepository userRepository, MovieService movieService) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.movieService = movieService;
    }

    // Input: curl -X GET "http://localhost:8080/comparisons/search?keyword=toy"
    @GetMapping("/comparisons/search")
    public Movie searchMovies(@RequestParam String keyword) {
        String regexPattern = ".*" + keyword + ".*(?=\\s\\([0-9]{4}\\))";
        List<Movie> movies = movieRepository.findByTitleRegex(regexPattern);
        if (movies.isEmpty()) {
            throw new CannotFoundException("movies with keyword", keyword);
        }
        return movies.get(0);
    }

    // Input: curl -X POST "http://localhost:8080/comparisons?id1=1&id2=2"
    @PostMapping("/comparisons")
    public List<Long> compareMovies(@RequestParam("id1") Long id1, @RequestParam("id2") Long id2) {
        long current_year = 2024;
        Movie movie1, movie2;

        Optional<Movie> optional1 = movieRepository.findById(id1);
        if (optional1.isPresent()) {
            movie1 = optional1.get();
        } else {
            throw new CannotFoundException("movie", id1);
        }

        Optional<Movie> optional2 = movieRepository.findById(id2);
        if (optional2.isPresent()) {
            movie2 = optional2.get();
        } else {
            throw new CannotFoundException("movie", id2);
        }
        
        // Check how much the movie is outdated.
        String title1 = movie1.getTitle();
        int lastOpenBracketIndex1 = title1.lastIndexOf('(');
        int closeBracketIndex1 = title1.indexOf(')', lastOpenBracketIndex1);

        double howmuchOutdated1;
        if (lastOpenBracketIndex1 != -1 && closeBracketIndex1 != -1) {
            String numberStr = title1.substring(lastOpenBracketIndex1 + 1, closeBracketIndex1);
            try {
                howmuchOutdated1 = current_year + 1 - Long.parseLong(numberStr);
            } catch (NumberFormatException e) {
                throw new CannotFoundException("Released year", id1);
            }
        } else {
            throw new CannotFoundException("Released year", id1);
        }

        String title2 = movie2.getTitle();
        int lastOpenBracketIndex2 = title2.lastIndexOf('(');
        int closeBracketIndex2 = title2.indexOf(')', lastOpenBracketIndex2);

        double howmuchOutdated2;
        if (lastOpenBracketIndex2 != -1 && closeBracketIndex2 != -1) {
            String numberStr = title2.substring(lastOpenBracketIndex2 + 1, closeBracketIndex2);
            try {
                howmuchOutdated2 = current_year + 1 - Long.parseLong(numberStr);
            } catch (NumberFormatException e) {
                throw new CannotFoundException("Released year", id2);
            }
        } else {
            throw new CannotFoundException("Released year", id2);
        }
        
        
        double weightOutdated1 = 1 + 1 / howmuchOutdated1;
        double weightOutdated2 = 1 + 1 / howmuchOutdated2;

        // Check how much prefered evenly according to gender, age
        List<Long> numList_over_3_1 = movieService.getMovieStats(id1);
        long num_male_1 = numList_over_3_1.get(0);
        long num_female_1 = numList_over_3_1.get(1);
        long num_1_1 = numList_over_3_1.get(2);
        long num_25_1 = numList_over_3_1.get(3);
        long num_35_1 = numList_over_3_1.get(4);
        long num_50_1 = numList_over_3_1.get(5);
        long sum_of_rating_1 = numList_over_3_1.get(6);
        long num_of_rating_1 = numList_over_3_1.get(7);

        List<Long> numList_over_3_2 = movieService.getMovieStats(id2);
        long num_male_2 = numList_over_3_2.get(0);
        long num_female_2 = numList_over_3_2.get(1);
        long num_1_2 = numList_over_3_2.get(2);
        long num_25_2 = numList_over_3_2.get(3);
        long num_35_2 = numList_over_3_2.get(4);
        long num_50_2 = numList_over_3_2.get(5);
        long sum_of_rating_2 = numList_over_3_2.get(6);
        long num_of_rating_2 = numList_over_3_2.get(7);
        
        double avgrating1 = (double) sum_of_rating_1 / (double) (num_of_rating_1 + 1);
        double avgrating2 = (double) sum_of_rating_2 / (double) (num_of_rating_2 + 1);

        long num_male = 0;
        long num_female = 0;
        long num_1 = 0;
        long num_25 = 0;
        long num_35 = 0;
        long num_50 = 0;

        List<User> users = userRepository.findAll();
        for (User user : users) {
            String gender = user.getGender();
            int age = user.getAge();

            if (gender != null) {
                if (gender.equals("M")) {
                    num_male++;
                } else if (gender.equals("F")) {
                    num_female++;
                } else {
                    // user post 때만 예외처리 잘하면 여기서 오류날 일은 없을 거 같긴 함
                    throw new InvalidUserException("Gender", gender);
                }
            } else {
                // user post 때만 예외처리 잘하면 여기서 오류날 일은 없을 거 같긴 함
                throw new InvalidUserException("Gender", gender);
            }

            if (age >= 50) {
                num_50++;
            } else if (age >= 35) {
                num_35++;
            } else if (age >= 25) {
                num_25++;
            } else if (age >= 1) {
                num_1++;
            } else {
                // user post 때만 예외처리 잘하면 여기서 오류날 일은 없을 거 같긴 함
                throw new InvalidUserException("Age", String.valueOf(age));
            }
        }

        Long num_total_user = num_male + num_female;

        double howmuchPopular1 = num_of_rating_1 / num_total_user;
        double howmuchPopular2 = num_of_rating_2 / num_total_user;

        double weightPopular1 = 1 + 1 / (howmuchPopular1 + 1);
        double weightPopular2 = 1 + 1 / (howmuchPopular2 + 1);

        double ratio_male_1 = (double) num_male_1 / (double) num_male;
        double ratio_female_1 = (double) num_female_1 / (double) num_female;
        double howmuchGender1 = Math.abs(ratio_male_1 - ratio_female_1);
        double ratio_male_2 = (double) num_male_2 / (double) num_male;
        double ratio_female_2 = (double) num_female_2 / (double) num_female;
        double howmuchGender2 = Math.abs(ratio_male_2 - ratio_female_2);

        double weightGender1 = 1 + 1 / (howmuchGender1 + 1);
        double weightGender2 = 1 + 1 / (howmuchGender2 + 1);


        double mean_multiple_4 = num_1 + num_25 + num_35 + num_50;
        double mean = mean_multiple_4 / 4;
        double var = (Math.pow(num_1 - mean, 2) +
                Math.pow(num_25 - mean, 2) +
                Math.pow(num_35 - mean, 2) +
                Math.pow(num_50 - mean, 2)) / 4;
        double std = Math.sqrt(var);

        double mean_multiple_4_1 = num_1_1 + num_25_1 + num_35_1 + num_50_1;
        double mean_1 = mean_multiple_4_1 / 4;
        double var_1 = (Math.pow(num_1_1 - mean_1, 2) +
                Math.pow(num_25_1 - mean_1, 2) +
                Math.pow(num_35_1 - mean_1, 2) +
                Math.pow(num_50_1 - mean_1, 2)) / 4;
        double std_1 = Math.sqrt(var_1);

        double mean_multiple_4_2 = num_1_2 + num_25_2 + num_35_2 + num_50_2;
        double mean_2 = mean_multiple_4_2 / 4;
        double var_2 = (Math.pow(num_1_2 - mean_2, 2) +
                Math.pow(num_25_2 - mean_2, 2) +
                Math.pow(num_35_2 - mean_2, 2) +
                Math.pow(num_50_2 - mean_2, 2)) / 4;
        double std_2 = Math.sqrt(var_2);
    

        double weightMean1 = 1 + (1 / (1 + Math.abs(mean_1 - mean) / mean));
        double weightStd1 = 1 + (1 / (1 + Math.abs(std_1 - std) / std));
        double weightMean2 = 1 + (1 / (1 + Math.abs(mean_2 - mean) / mean));
        double weightStd2 = 1 + (1 / (1 + Math.abs(std_2 - std) / std));

        double weightAge1 = (weightMean1 + weightStd1) / 2;
        double weightAge2 = (weightMean2 + weightStd2) / 2;

        double weightedRating1 = avgrating1 * weightOutdated1 * weightPopular1 * weightGender1 * weightAge1;
        double weightedRating2 = avgrating2 * weightOutdated2 * weightPopular2 * weightGender2 * weightAge2;
        

        long betterMovie = weightedRating1 > weightedRating2 ? id1 : id2;

        List<Long> comparisonResult = new ArrayList<>();

        comparisonResult.add(betterMovie);
        comparisonResult.add((long)-1);
        comparisonResult.addAll(numList_over_3_1);
        comparisonResult.add((long)-1);
        comparisonResult.addAll(numList_over_3_2);
        comparisonResult.add((long)-1);
        comparisonResult.add(num_male);
        comparisonResult.add(num_female);
        comparisonResult.add(num_1);
        comparisonResult.add(num_25);
        comparisonResult.add(num_35);
        comparisonResult.add(num_50);
        
        return comparisonResult;
    }
}
