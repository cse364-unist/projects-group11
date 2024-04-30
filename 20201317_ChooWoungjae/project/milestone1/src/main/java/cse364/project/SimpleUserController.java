package cse364.project;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SimpleUserController {

    private final SimpleUserRepository simpleUserRepository;

    SimpleUserController(SimpleUserRepository simpleUserRepository) {
        this.simpleUserRepository = simpleUserRepository;
    }

    @GetMapping("/simpleUsers")
    List<SimpleUser> all() {
        return simpleUserRepository.findAll();
    }

    @PostMapping("/simpleUsers")
    SimpleUser addSimpleUser(@RequestBody SimpleUser newSimpleUser) {
        return simpleUserRepository.save(newSimpleUser);
    }

    @PutMapping("/simpleUsers")
    SimpleUser replaceSimpleUser(@RequestBody SimpleUser newSimpleUser) {
        return simpleUserRepository.save(newSimpleUser);

    }
}