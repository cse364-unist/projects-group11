package cse364.project;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
class GiftController {

    private final GiftRepository giftRepository;
    private final MovieRepository movieRepository;

    GiftController(GiftRepository giftRepository, MovieRepository movieRepository) {
        this.giftRepository = giftRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/gifts")
    List<Gift> all() {
        return giftRepository.findAll();
    }

    @GetMapping("/gifts/findmovie")
    Movie findMovieById(@RequestParam("movieId") Long movieId) {
        Movie shouldbereturned;
        Optional<Movie> canbereturned = movieRepository.findById(movieId);
        if (canbereturned.isPresent()) {
            shouldbereturned = canbereturned.get();
            return shouldbereturned;
        } else {
            throw new CannotFoundException("movie", movieId);
        }
    }

    // Input: curl -X GET "http://localhost:8080/gifts/search?keyword=toy"
    @GetMapping("/gifts/search")
    public Movie searchMovies(@RequestParam String keyword) {
        String[] keywords = keyword.split("\\+");
        List<String> patterns = Arrays.stream(keywords)
            .map(k -> "(?=.*" + Pattern.quote(k.trim()) + ")")
            .collect(Collectors.toList());
        
        String regexPattern = String.join("", patterns) + ".*";
        List<Movie> movies = movieRepository.findByTitleRegex(regexPattern);
        if (movies.isEmpty()) {
            throw new CannotFoundException("movies with keyword", keyword);
        }
        return movies.get(0);
    }

    // Input: curl -X POST "http://localhost:8080/gifts?message="영화 선물"&movieId=5"
    @PostMapping("/gifts")
    Gift newGift(@RequestParam("message") String message, @RequestParam("movieId") Long movieId) {
        Gift newGift = new Gift(message, movieId);
        return giftRepository.save(newGift);
    }

    @GetMapping("/gifts/{giftId}")
    Gift one(@PathVariable String giftId) {
        Optional<Gift> optional = giftRepository.findById(giftId);
        if(optional.isPresent()){
            Gift TargetGift = optional.get();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime otherDateTime = LocalDateTime.parse(TargetGift.getExpireDate(), formatter);
            if (now.isAfter(otherDateTime)) {
                giftRepository.delete(TargetGift);
                throw new CannotFoundException("Gift_ID", giftId);
            } else {
                return TargetGift;
            }
        } else {
            throw new CannotFoundException("Gift_ID", giftId);
        }
    }

    //  The reason for commenting below is that it should not be directly update and delete with gifts' giftId
    //  Instead, when you approach giftId, if you connect the link after expireDate, that giftId should delete.

    @PutMapping("/gifts")
    Gift replaceGift(@RequestParam("giftId") String giftId) {
        Optional<Gift> canbereturned = giftRepository.findById(giftId)
                                                        .map(gift -> {
                                                            gift.setExpireDate("0000-00-00T00:00:00.000000000");
                                                            return giftRepository.save(gift);
                                                        });
        if (canbereturned.isPresent()) {
            Gift shouldbereturned = canbereturned.get();
            return shouldbereturned;
        } else {
            throw new CannotFoundException("gift", giftId);
        }
    }

    // @DeleteMapping("/gifts/{giftId}")
    // void deleteGift(@PathVariable String giftId) {
    //     Optional<Gift> optional = giftRepository.findById(giftId);
    //     if(optional.isPresent()){
    //         Gift TargetGift = optional.get();
    //         giftRepository.delete(TargetGift);
    //     } else{
    //         throw new CannotFoundException("Gift_ID", giftId);
    //     }
    // }
}