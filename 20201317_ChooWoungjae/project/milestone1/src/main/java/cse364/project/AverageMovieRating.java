package com.example.movie;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
public class AverageMovieRating {
    Long _id;
    float averageRating;

    public Long getId() {
		return this._id;
	}
	public void setId(Long _id) {
		this._id = _id;
	}
    public float getAverageRating() {
		return this.averageRating;
	}
	public void setAverageRating(float rating) {
		this.averageRating = averageRating;
	}
}