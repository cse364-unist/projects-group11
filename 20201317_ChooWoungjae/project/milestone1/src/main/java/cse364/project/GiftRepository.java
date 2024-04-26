package cse364.project;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GiftRepository extends MongoRepository<Gift, Long> {
    Gift findByGiftId(String gift_id);
}