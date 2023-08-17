package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.AreaCreateDTO;
import com.cafe.website.payload.AreaUpdateDTO;
import com.cafe.website.payload.ReviewDTO;
import com.cafe.website.payload.ReviewUpdateDTO;

public interface ReviewService {
	List<ReviewDTO> getListReviews(int limit, int page, String name, Integer productId, Integer userId,
			Integer ratingId, String sortBy);

	ReviewDTO getReviewById(int id);

	ReviewDTO createReview(ReviewDTO areaCreateDto) throws IOException;

	ReviewDTO updateReview(int id, ReviewUpdateDTO areaUpdateDto) throws IOException;

	void deleteReview(int id) throws IOException;
	
	Integer getRatingByReviewId(int id);
}
