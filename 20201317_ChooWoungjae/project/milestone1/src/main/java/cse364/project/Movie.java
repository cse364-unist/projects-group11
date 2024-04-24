package cse364.project;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "movies")
public class Movie {

    private @Id Long movie_id;
    private String title;
    private String genres;
    private List<Rating> ratingList;
    private Long numOfRating = 0L;
    private Long sumOfRating = 0L;
    Movie() {};
    Movie(Long Movie_id, String Title, String Genres){
        this.movie_id = Movie_id;
        this.title = Title;
        this.genres = Genres;
        this.ratingList = new ArrayList();
        this.numOfRating = 0L;
        this.sumOfRating = 0L;
    }

    public Long getMovie_id() { return this.movie_id; }
    public String getTitle() { return this.title; }
    public String getGenres() { return this.genres; }
    public List<Rating> getRatingList() {return this.ratingList; }
    public Long getNumOfRating() { return this.numOfRating; }
    public Long getSumOfRating() { return this.sumOfRating; }
    public void setMovie_id(Long Movie_id) { this.movie_id = Movie_id; }
    public void setTitle(String Title) { this.title = Title; }
    public void setGenres(String Genres) { this.genres = Genres; }
    public void addRatingList(Rating Rate) { this.ratingList.add(Rate); }
    public void plusNumOfRating(Long diff) { this.numOfRating += diff; }
    public void plusSumOfRating(Long diff) { this.sumOfRating += diff; }

    public void setRatingList(int index, Rating Rate) { this.ratingList.set(index, Rate); }

}