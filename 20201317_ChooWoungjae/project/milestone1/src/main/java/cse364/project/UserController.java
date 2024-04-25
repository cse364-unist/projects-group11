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
class UserController {

    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    List<User> all() {
        return userRepository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    // Single item

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new CannotFoundException("User", id));
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            Long NewUserID = newUser.getUserId();
            if(NewUserID.equals(id)) {
                return userRepository.save(newUser);
            } else{
                throw new CannotChangeIDException("User");
            }
        } else{
            throw new CannotFoundException("User", id);
        }
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {

        Optional<User> optional = userRepository.findById(id);
        if(optional.isPresent()){
            userRepository.deleteById(id);
        } else{
            throw new CannotFoundException("User", id);
        }
    }
}