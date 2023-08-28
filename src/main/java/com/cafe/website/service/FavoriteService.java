package com.cafe.website.service;

import com.cafe.website.payload.FavoriteCreateDTO;

public interface FavoriteService {
	void toggleFavoriteReview(FavoriteCreateDTO favoriteCreate);

	Integer getAmountFavorite(Integer reviewId);

}
