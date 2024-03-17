package com.cafe.website.payload;

import jakarta.validation.constraints.NotNull;

public class FavoriteCommentCreateDTO {
	@NotNull
	private Long userId;

	@NotNull
	private Long commentId;

	public FavoriteCommentCreateDTO() {
		// TODO Auto-generated constructor stub
	}

	public FavoriteCommentCreateDTO(@NotNull Long userId, @NotNull Long commentId) {
		super();
		this.userId = userId;
		this.commentId = commentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

}
