package cse364.project;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "movies")
public class Movie {

    private @Id Long movie_id;
    private String title;
    private String genres;
    private int[][] intervalPairList;
    Movie() {};
    Movie(Long Movie_id, String Title, String Genres){
        this.movie_id = Movie_id;
        this.title = Title;
        this.genres = Genres;
        this.intervalPairList = new int[15][2];
        for(int i = 0 ; i < 15 ; i ++){
            this.intervalPairList[i][0] = 0;
            this.intervalPairList[i][1] = 0;
        }
    }

    public Long getMovieId() { return this.movie_id; }
    public String getTitle() { return this.title; }
    public String getGenres() { return this.genres; }
    public int getIntervalPairList(int x, int y) { return this.intervalPairList[x][y]; }
    public Double getAverageOfInterval(int interval) {
        if(this.intervalPairList[interval][1] == 0){
            return Double.valueOf(0.0);
        }
        else return Double.valueOf((double)this.intervalPairList[interval][0] / (double)this.intervalPairList[interval][1]);
    }
    public void setMovieId(Long Movie_id) { this.movie_id = Movie_id; }
    public void setTitle(String Title) { this.title = Title; }
    public void setGenres(String Genres) { this.genres = Genres; }
    public void setIntervalPairList(int x, int y, int val) { this.intervalPairList[x][y] = val; }
    public void plusIntervalPairList(int x, int y, int diff) { this.intervalPairList[x][y] += diff; }

}