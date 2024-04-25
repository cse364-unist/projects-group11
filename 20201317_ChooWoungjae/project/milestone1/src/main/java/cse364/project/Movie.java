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
    Movie() {};
    Movie(Long Movie_id, String Title, String Genres){
        this.movie_id = Movie_id;
        this.title = Title;
        this.genres = Genres;
    }

    public Long getMovieId() { return this.movie_id; }
    public String getTitle() { return this.title; }
    public String getGenres() { return this.genres; }
    public void setMovieId(Long Movie_id) { this.movie_id = Movie_id; }
    public void setTitle(String Title) { this.title = Title; }
    public void setGenres(String Genres) { this.genres = Genres; }

}