package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class CommentDeleteDTO {
	@NotNull
	private Long id;
	@NotNull
	private Long reviewId;

	public CommentDeleteDTO() {
		// TODO Auto-generated constructor stub
	}

	public CommentDeleteDTO(@NotNull Long id, @NotNull Long reviewId) {
		super();
		this.id = id;
		this.reviewId = reviewId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

}
