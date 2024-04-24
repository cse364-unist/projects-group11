package cse364.project;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "ratings")
public class Rating {

    private @Id Long user_id;
    private Long movie_id;
    private Long rate;
    Rating() {};
    Rating(Long User_id, Long Movie_id, Long Rate){
        this.user_id = User_id;
        this.movie_id = Movie_id;
        this.rate = Rate;
    }

    public Long getUser_id() { return this.user_id; }
    public Long getMovie_id() { return this.movie_id; }
    public Long getRate() { return this.rate; }
    public void setUser_id(Long User_id) { this.user_id = User_id; }
    public void setMovie_id(Long Movie_id) { this.movie_id = Movie_id; }
    public void setRate(Long Rate) { this.rate = Rate; }

}