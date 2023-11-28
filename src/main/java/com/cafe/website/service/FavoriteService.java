package com.cafe.website.service;

import com.cafe.website.payload.FavoriteCreateDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface FavoriteService {
	void toggleFavoriteReview(FavoriteCreateDTO favoriteCreate, HttpServletRequest request);

	Integer getAmountFavorite(Integer reviewId);

}
