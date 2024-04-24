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
    User() {};
    User(Long User_id, String Gender, int Age, String Occupation, String Zip_code){
        this.user_id = User_id;
        this.gender = Gender;
        this.age = Age;
        this.occupation = Occupation;
        this.zip_code = Zip_code;
    }

    public Long getUser_id() { return this.user_id; }
    public String getGender() { return this.gender; }
    public int getAge() { return this.age; }
    public String getOccupation() { return this.occupation; }
    public String getZip_code() { return this.zip_code; }

    public void setUser_id(Long User_id) { this.user_id = User_id; }
    public void setGender(String Gender) { this.gender = Gender; }
    public void setAge(int Age) { this.age = Age; }
    public void setOccupation(String Occupation) { this.occupation = Occupation; }
    public void setZip_code(String Zip_code) { this.zip_code = Zip_code; }

}