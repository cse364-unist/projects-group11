package cse364.project;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "allMovies")
public class AllMovies {

    private Long movie_id;
    private String title;
    private String genres;
    AllMovies() {};
    AllMovies(Long Movie_id, String Title, String Genres){
        this.movie_id = Movie_id;
        this.title = Title;
        this.genres = Genres;
    }

    public Long getMovie_id() { return this.movie_id; }
    public String getTitle() { return this.title; }
    public String getGenres() { return this.genres; }
    public void setMovie_id(Long Movie_id) { this.movie_id = Movie_id; }
    public void setTitle(String Title) { this.title = Title; }
    public void setGenres(String Genres) { this.genres = Genres; }

}