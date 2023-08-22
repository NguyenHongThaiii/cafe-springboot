package com.cafe.website.payload;

import java.util.List;

import com.cafe.website.entity.BaseEntity;
import com.cafe.website.entity.Rating;

public class ReviewDTO extends BaseEntity {

	private Rating rating;

	private String name;
	private List<ImageDTO> listImages;

	public ReviewDTO(int id, int status, Long createdAt, Long updatedAt, Rating rating, String name,
			List<ImageDTO> listImages) {
		super(id, status, createdAt, updatedAt);
		this.rating = rating;
		this.name = name;
		this.listImages = listImages;
	}

	public ReviewDTO() {
		// TODO Auto-generated constructor stub
	}

	public List<ImageDTO> getListImages() {
		return listImages;
	}

	public void setListImages(List<ImageDTO> listImages) {
		this.listImages = listImages;
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
