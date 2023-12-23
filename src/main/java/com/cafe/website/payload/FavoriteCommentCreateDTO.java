package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class FavoriteCommentCreateDTO {
	@NotNull
	private Integer userId;

	@NotNull
	private Integer commentId;

	public FavoriteCommentCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public FavoriteCommentCreateDTO(@NotNull Integer userId, @NotNull Integer commentId) {
		super();
		this.userId = userId;
		this.commentId = commentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

}
