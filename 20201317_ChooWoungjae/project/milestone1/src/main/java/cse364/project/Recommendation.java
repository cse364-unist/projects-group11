package cse364.project;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Document(collection = "simpleUsers")
public class Recommendation {

    private List<User> userList;
    private List<String> genreList;
    Recommendation() {};
    Recommendation(List<User> userList, List<String> genreList){
        this.userList = userList;
        this.genreList = genreList;
    }

    public List<User> getUserList() { return this.userList; }
    public List<String> getGenreList() { return this.genreList; }
    public void setUserList(List<User> newUserList) { this.userList = newUserList; }
    public void setGenreList(List<String> newGenreList) { this.genreList = newGenreList; }
}