package cse364.project;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "simpleMovie")
public class SimpleMovie {

    private String title;
    private String genres;
    SimpleMovie() {};
    SimpleMovie(String Title, String Genres){
        this.title = Title;
        this.genres = Genres;
    }

    public String getTitle() { return this.title; }
    public String getGenres() { return this.genres; }
    public void setTitle(String Title) { this.title = Title; }
    public void setGenres(String Genres) { this.genres = Genres; }

}