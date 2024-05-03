package cse364.project;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    private @Id Long user_id;
    private String gender;
    private int age;
    private String occupation;
    private String zip_code;
    private int numOfPeople;
    User() {};
    User(Long User_id, String Gender, int Age, String Occupation, String Zip_code){
        this.user_id = User_id;
        this.gender = Gender;
        this.age = Age;
        this.occupation = Occupation;
        this.zip_code = Zip_code;
        this.numOfPeople = 0;
    }

    User(String gender, int age, int numOfPeople){
        this.gender = gender;
        this.age = age;
        this.numOfPeople = numOfPeople;
    }

    public Long getUserId() { return this.user_id; }
    public String getGender() { return this.gender; }
    public int getAge() { return this.age; }
    public String getOccupation() { return this.occupation; }
    public String getZipCode() { return this.zip_code; }
    public int getNumOfPeople() {return this.numOfPeople; }
    public int calculateInterval(){
        int interval = 0;
        if(this.gender.equals("F")){
            interval = 7;
        }
        if(this.age == 18) { interval += 1;} 
        else if(this.age == 25) { interval += 2; }
        else if(this.age == 35) { interval += 3; }
        else if(this.age == 45) { interval += 4; }
        else if(this.age == 50) { interval += 5; }
        else if(this.age == 56) { interval += 6; }
        else {
            throw new InvalidUserException("Age", String.valueOf(age));
        }

        return interval;
    }

    public void setUserId(Long User_id) { this.user_id = User_id; }
    public void setGender(String Gender) { this.gender = Gender; }
    public void setAge(int Age) { this.age = Age; }
    public void setOccupation(String Occupation) { this.occupation = Occupation; }
    public void setZipCode(String Zip_code) { this.zip_code = Zip_code; }

}