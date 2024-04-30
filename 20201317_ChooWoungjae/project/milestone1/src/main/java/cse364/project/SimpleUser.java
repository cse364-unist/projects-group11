package cse364.project;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.apache.commons.lang3.tuple.Pair;

@Document(collection = "simpleUsers")
public class SimpleUser {

    private String gender;
    private int age;
    private int numOfPeople;
    SimpleUser() {};
    SimpleUser(String Gender, int Age, int numOfPeople){
        this.gender = Gender;
        this.age = Age;
        this.numOfPeople = numOfPeople;
    }

    public String getGender() { return this.gender; }
    public int getAge() { return this.age; }
    public int getNumOfPeople() { return this.numOfPeople; }
    public void setGender(String Gender) {
        this.gender = Gender;
    }
    public void setAge(int Age) {
        this.age = Age;
    }
    public void setNumOfPeople(int numOfPeople) { this.numOfPeople = numOfPeople; }
}