package cse364.project;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "recommendations")
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