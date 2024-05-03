package cse364.project;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface CosineSimilarityRepository extends MongoRepository<CosineSimilarity, Integer> {
}