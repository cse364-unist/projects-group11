package cse364.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    RecommendationController(SimpleUserRepository repository, CosineSimilarityRepository similarityRepository) {
        this.repository = repository;
        this.similarityRepository = similarityRepository;
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
    List<Pair<Double, Movie>> targetMovie() {

        List<SimpleUser> simpleUserList = repository.findAll();
        List<Pair<Double, Movie>> expectRatingList = new ArrayList<>();
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
            // I don't know how to get average of each interval of user....
        }

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