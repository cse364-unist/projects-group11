package cse364.project;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "gifts")
public class Gift {

    private String gift_id;
    private String message;
    private Long movie_id;
    private String expire_date;
    Gift() {};
    Gift(String Message, Long Movie_id){
        this.message = Message;
        this.movie_id = Movie_id;
        // setGiftId(this.gift_id);
        // setExpireDate(this.expire_date);
    }

    public String getGiftId() { return this.gift_id; }
    public String getMessage() { return this.message; }
    public Long getMovieId() { return this.movie_id; }
    public String getExpireDate() { return this.expire_date; }
    public void setMessage(String Message) { this.message = Message; }
    public void setMovieId(Long Movie_id) { this.movie_id = Movie_id; }
    public void setGiftId(String Gift_id) { this.gift_id = UUID.randomUUID().toString(); }
    public void setExpireDate(String Expire_date) { 
        LocalDate today = LocalDate.now();
        LocalDate threeMonthsFromNow = today.plusMonths(3);
        LocalDateTime expireDateTime = LocalDateTime.of(threeMonthsFromNow, LocalTime.MAX);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = expireDateTime.format(formatter);

        this.expire_date = dateTimeString;
    }
    public void updateInfo() {
        setGiftId(this.gift_id);
        setExpireDate(this.expire_date);
    }

}