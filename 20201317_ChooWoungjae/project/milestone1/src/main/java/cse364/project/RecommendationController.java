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
    private final RatingRepository ratingRepository;
    RecommendationController(SimpleUserRepository repository, CosineSimilarityRepository similarityRepository, RatingRepository ratingRepository) {
        this.repository = repository;
        this.similarityRepository = similarityRepository;
        this.ratingRepository = ratingRepository;
    }


    @PostMapping("/recommendations")
    SimpleUser changeSimpleUser(@RequestBody SimpleUser newSimpleUser) {

        Optional<SimpleUser> optional = repository.findByGenderAndAge(newSimpleUser.getGenderAndAge());
        if(optional.isPresent()){
            repository.save(newSimpleUser);
        } else{
            throw new CannotFoundException("SimpleUser", 0L);
        }

        return newSimpleUser;
    }

    @GetMapping("/recommendations")
    List<Pair<Double, Long>> targetMovie() {

        List<SimpleUser> simpleUserList = repository.findAll();
        List<MovieRatingByDemographic> averageOfRating = ratingRepository.getAverageRatingsByMovieIdGenderAndAge();

        Comparator<MovieRatingByDemographic> comparator = new Comparator<MovieRatingByDemographic>() {
            @Override
            public int compare(MovieRatingByDemographic a, MovieRatingByDemographic b){
                if(a.getMovieId().equals(b.getMovieId())){
                    if(a.getGender().equals(b.getGender())){
                        return a.getAge() - b.getAge();
                    }
                    else{
                        if(a.getGender().equals("F")){
                            return 1;
                        }
                        else{
                            return -1;
                        }
                    }
                }
                else{
                    Long ans = a.getMovieId() - b.getMovieId();
                    return ans.intValue();
                }
            }
        };

        Collections.sort(averageOfRating, comparator);


        List<Pair<Double, Long>> expectRatingList = new ArrayList<>();
        Double[] weight = new Double[15];
        int sz = simpleUserList.size();
        if(sz == 0){
            throw new CannotFoundException("SimpleUser", 0);
        }
        for(int i = 0 ; i < 14 ; i ++){
            double similarity = 0.0;
            int numOfPeople = 0;
            for(int j = 0 ; j < sz ; j ++){
                int interval = simpleUserList.get(j).getCorrespondingNumber();
                int num = simpleUserList.get(j).getNumOfPeople();
                double nowSimilarity = 0.0;
                if(i == interval){
                    similarity = num;
                    numOfPeople = 0;
                    break;
                }
                if(i < j){
                    nowSimilarity = similarityRepository.findByTarget(Pair.of(i, interval)).get().getSimilarity();
                }
                else{
                    nowSimilarity = similarityRepository.findByTarget(Pair.of(interval, i)).get().getSimilarity();
                }
                similarity += nowSimilarity * (double)num;
                numOfPeople += num;
            }
            if(numOfPeople == 0){
                weight[i] = Double.valueOf(similarity);
            }
            else{
                weight[i] = Double.valueOf(similarity / (double)numOfPeople);
            }
        }
        for(int i = 0 ; i < 3952 ; i ++){
            Double estimatedScore = 0.0;
            Double sumOfSimilarity = 0.0;
            for(int j = 0 ; j < 14 ; j ++){
                Double nowAvgRating = averageOfRating.get(i * 14 + j).getAvgRating();
                if(nowAvgRating.equals(0.0)) continue;
                estimatedScore += nowAvgRating * weight[j];
                sumOfSimilarity += weight[j];
            }
            expectRatingList.add(Pair.of((estimatedScore / sumOfSimilarity), Long.valueOf(i)));
        }

        Collections.sort(expectRatingList, Collections.reverseOrder());

        return expectRatingList;
    }

    @PutMapping("/recommendations")
    SimpleUser addSimpleUser(@RequestBody SimpleUser newSimpleUser) {

        Optional<SimpleUser> optional = repository.findByGenderAndAge(newSimpleUser.getGenderAndAge());
        if(optional.isPresent()){
            throw new AlreadyExistException("SimpleUser");
        } else{
            repository.save(newSimpleUser);
        }

        return newSimpleUser;
    }

}