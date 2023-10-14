package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.AreaCreateDTO;
import com.cafe.website.payload.AreaUpdateDTO;
import com.cafe.website.payload.ReviewCreateDTO;
import com.cafe.website.payload.ReviewDTO;
import com.cafe.website.payload.ReviewUpdateDTO;

public interface ReviewService {
	List<ReviewDTO> getListReviews(int limit, int page, String name, Integer productId, Integer userId,
			Integer ratingId, String createdAt, String updatedAt, Float ratingAverage, String sortBy);

	List<ReviewDTO> getListReviewsByProductId(int limit, int page, Integer productId, String sortBy);

	List<ReviewDTO> findAllByOrderByRatingAverageRating(Float ratingAverage);

	ReviewDTO getReviewById(int id);

	ReviewDTO createReview(ReviewCreateDTO areaCreateDto) throws IOException;

	ReviewDTO updateReview(int id, ReviewUpdateDTO areaUpdateDto) throws IOException;

	void deleteReview(int id) throws IOException;

	Float getRatingByReviewId(int id);

}
