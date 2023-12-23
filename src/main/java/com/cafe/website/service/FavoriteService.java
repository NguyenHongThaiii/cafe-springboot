package com.cafe.website.service;

import com.cafe.website.payload.FavoriteCommentCreateDTO;
import com.cafe.website.payload.FavoriteReviewCreateDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface FavoriteService {
	void toggleFavoriteReview(FavoriteReviewCreateDTO favoriteCreate, HttpServletRequest request);

	void toggleFavoriteComment(FavoriteCommentCreateDTO favoriteCreate, HttpServletRequest request);

	Integer getAmountFavoriteReview(Integer reviewId);

	Integer getAmountFavoriteComment(Integer commentId);

}
