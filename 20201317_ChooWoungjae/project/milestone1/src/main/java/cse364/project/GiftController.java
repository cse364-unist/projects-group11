package cse364.project;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


@RestController
class GiftController {

    private final GiftRepository giftRepository;
    private final MongoTemplate mongoTemplate;

    GiftController(GiftRepository giftRepository, MongoTemplate mongoTemplate) {
        this.giftRepository = giftRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/gifts")
    List<Gift> all() {
        // return giftRepository.findAll();
        return null;
    }

    @PostMapping("/gifts")
    Gift newGift(@RequestBody Gift newGift) {
        newGift.updateInfo();
        return giftRepository.save(newGift);
    }

    @GetMapping("/gifts/{giftId}")
    Gift one(@PathVariable String giftId) {
        return giftRepository.findByGiftId(giftId);
    }

    // @PutMapping("/gifts/{id}")
    // Gift replaceGift(@RequestBody Gift newGift, @PathVariable Long id) {
    //     return giftRepository.findById(id)
    //         .map(gift -> {
    //             gift.setTitle(newGift.getTitle());
    //             gift.setGenres(newGift.getGenres());
    //             return giftRepository.save(gift);
    //         })
    //         .orElseGet(() -> {
    //             newGift.setGiftId(id);
    //             return giftRepository.save(newGift);
    //         });
    // }

    // @DeleteMapping("/gifts/{id}")
    // void deleteGift(@PathVariable Long id) {
    //     Optional<Gift> optional = giftRepository.findById(id);
    //     if(optional.isPresent()){
    //         Gift TargetGift = optional.get();
    //         giftRepository.delete(TargetGift);
    //     } else{
    //         throw new CannotFoundException("Gift_ID", id);
    //     }
    // }
}