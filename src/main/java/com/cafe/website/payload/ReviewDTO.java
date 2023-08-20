package com.cafe.website.payload;

import com.cafe.website.entity.BaseEntity;
import com.cafe.website.entity.Rating;

public class ReviewDTO extends BaseEntity {

	private Rating rating;

	private String name;

	public ReviewDTO(int id, int status, Long createdAt, Long updatedAt, Rating rating, String name) {
		super(id, status, createdAt, updatedAt);
		this.rating = rating;
		this.name = name;
	}

	public ReviewDTO() {
		// TODO Auto-generated constructor stub
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
