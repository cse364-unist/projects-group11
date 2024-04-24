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

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {

        Long id = newUser.getUser_id();
        if(id < 1){
            throw new NotInRangeException("User_ID");
        }
        Optional<User> optional = repository.findById(id);
        if(optional.isPresent()){
            throw new AlreadyExistException("User");
        } else {
            return repository.save(newUser);
        }
    }

    // Single item

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new CannotFoundException("User", id));
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        Optional<User> optional = repository.findById(id);
        if(optional.isPresent()){
            Long NewUserID = newUser.getUser_id();
            if(NewUserID.equals(id)) {
                return repository.save(newUser);
            } else{
                throw new CannotChangeIDException("User");
            }
        } else{
            throw new CannotFoundException("User", id);
        }
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {

        Optional<User> optional = repository.findById(id);
        if(optional.isPresent()){
            repository.deleteById(id);
        } else{
            throw new CannotFoundException("User", id);
        }
    }
}