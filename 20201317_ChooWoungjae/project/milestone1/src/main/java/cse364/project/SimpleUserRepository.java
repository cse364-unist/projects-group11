package cse364.project;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

public interface SimpleUserRepository extends MongoRepository<SimpleUser, String> {
    Optional<SimpleUser> findByGenderAndAge(Pair<String, Integer> genderAndAge);
}