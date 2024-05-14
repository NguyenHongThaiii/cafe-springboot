package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class FavoriteReviewCreateDTO {
	@NotNull
	private Long userId;

	@NotNull
	private Long reviewId;

	public FavoriteReviewCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public FavoriteReviewCreateDTO(@NotNull Long userId, @NotNull Long reviewId) {
		super();
		this.userId = userId;
		this.reviewId = reviewId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

}
