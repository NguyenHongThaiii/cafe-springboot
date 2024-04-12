package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import com.cafe.website.entity.BaseEntity;
import com.cafe.website.entity.Rating;

public class ReviewDTO extends BaseEntityDTO {

	private Rating rating;

	private String name;
	private List<ImageDTO> listImage = new ArrayList<>();

	private int favorite;
	private Long productId;
	private UserDTO userDto;

	public ReviewDTO() {
		// TODO Auto-generated constructor stub
	}

	public ReviewDTO(Rating rating, String name, List<ImageDTO> listImage, int favorite, Long productId,
			UserDTO userDto) {
		super();
		this.rating = rating;
		this.name = name;
		this.listImage = listImage;
		this.favorite = favorite;
		this.productId = productId;
		this.userDto = userDto;
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

	public List<ImageDTO> getListImage() {
		return listImage;
	}

	public void setListImage(List<ImageDTO> listImage) {
		this.listImage = listImage;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public UserDTO getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}

	

}
