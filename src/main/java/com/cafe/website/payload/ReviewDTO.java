package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import com.cafe.website.entity.BaseEntity;
import com.cafe.website.entity.Rating;

public class ReviewDTO extends BaseEntityDTO {

	private Rating rating;

	private String name;
	private List<ImageDTO> listImages = new ArrayList<>();

	private int favorite;
	private Integer productId;

	public ReviewDTO(int id, Integer status, String createdAt, String updatedAt, Rating rating, String name,
			List<ImageDTO> listImages, int favorite, Integer productId) {
		super(id, status, createdAt, updatedAt);
		this.rating = rating;
		this.name = name;
		this.listImages = listImages;
		this.favorite = favorite;
		this.productId = productId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
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

	public List<ImageDTO> getListImages() {
		return listImages;
	}

	public void setListImages(List<ImageDTO> listImages) {
		this.listImages = listImages;
	}

}
