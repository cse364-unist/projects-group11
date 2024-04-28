package cse364.project;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


public class MovieRatingByDemographic {
    private String movieId;
    private String gender;
    private Integer age;
    private Double avgRating;

    MovieRatingByDemographic() {};
    MovieRatingByDemographic(String MovieId, String Gender, Integer Age, Double AvgRating){
        this.movieId = MovieId;
        this.gender = Gender;
        this.age = Age;
        this.avgRating = AvgRating;
    }

    public String getMovieId() { return this.movieId; }
    public String getGender() { return this.gender; }
    public Integer getAge() { return this.age; }
    public Double getAvgRating() { return this.avgRating; }

    public void setMovieId(String MovieId) { this.movieId = MovieId; }
    public void setGender(String Gender) { this.gender = Gender; }
    public void setAge(Integer Age) { this.age = Age; }
    public void setAvgRating(Double AvgRating) { this.avgRating = AvgRating; }

}
