package cse364.project;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.apache.commons.lang3.tuple.Pair;

@Document(collection = "simpleUsers")
public class SimpleUser {

    private Pair<String, Integer> genderAndAge;
    private int numOfPeople;
    private String gender;
    private Integer age;
    SimpleUser() {};
    SimpleUser(String Gender, int Age, int numOfPeople){
        this.gender = Gender;
        this.age = Age;
        this.genderAndAge = Pair.of(Gender, Age);
        this.numOfPeople = numOfPeople;
    }

    public String getGender() { return this.gender; }
    public int getAge() { return this.age; }
    public Pair<String, Integer> getGenderAndAge() { return this.genderAndAge; }
    public int getNumOfPeople() { return this.numOfPeople; }
    public void setGender(String Gender) { 
        this.genderAndAge = Pair.of(Gender, genderAndAge.getRight());
        this.gender = Gender;
    }
    public void setGenderAndAge(Pair<String, Integer> newGenderAndAge) { this.genderAndAge = newGenderAndAge; }
    public void setAge(int Age) { 
        this.genderAndAge = Pair.of(genderAndAge.getLeft(), Age);
        this.age = Age;
    }
    public void setNumOfPeople(int numOfPeople) { this.numOfPeople = numOfPeople; }
    public int getCorrespondingNumber() {
        int gender = 0;
        if(genderAndAge.getLeft().equals("F")){
            gender = 7;
        }
        Integer age = genderAndAge.getRight();
        if(age.equals(Integer.valueOf(1))){
            return gender;
        }
        else if(age.equals(Integer.valueOf(18))){
            return gender + 1;
        }
        else if(age.equals(Integer.valueOf(25))){
            return gender + 2;
        }
        else if(age.equals(Integer.valueOf(35))){
            return gender + 3;
        }
        else if(age.equals(Integer.valueOf(45))){
            return gender + 4;
        }
        else if(age.equals(Integer.valueOf(50))){
            return gender + 5;
        }
        else return gender + 6;
    }
}