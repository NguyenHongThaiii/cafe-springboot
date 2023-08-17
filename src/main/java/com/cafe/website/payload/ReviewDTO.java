package com.cafe.website.payload;

import java.util.List;

import com.cafe.website.entity.BaseEntity;
import com.cafe.website.entity.Comment;
import com.cafe.website.entity.Product;
import com.cafe.website.entity.Rating;
import com.cafe.website.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

public class ReviewDTO extends BaseEntity {

	private Rating rating;

	private String listImages;
	private String name;

	public ReviewDTO(int id, int status, Long createdAt, Long updatedAt, Rating rating, String listImages,
			String name) {
		super(id, status, createdAt, updatedAt);
		this.rating = rating;
		this.listImages = listImages;
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

	public String getListImages() {
		return listImages;
	}

	public void setListImages(String listImages) {
		this.listImages = listImages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
