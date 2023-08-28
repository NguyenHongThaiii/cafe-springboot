package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class FavoriteCreateDTO {
	@NotNull
	private Integer userId;

	@NotNull
	private Integer reviewId;

	public FavoriteCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public FavoriteCreateDTO(@NotNull Integer userId, @NotNull Integer reviewId) {
		super();
		this.userId = userId;
		this.reviewId = reviewId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getReviewId() {
		return reviewId;
	}

	public void setReviewId(Integer reviewId) {
		this.reviewId = reviewId;
	}

}
