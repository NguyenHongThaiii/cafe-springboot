package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.AreaCreateDTO;
import com.cafe.website.payload.AreaUpdateDTO;
import com.cafe.website.payload.ReviewCreateDTO;
import com.cafe.website.payload.ReviewDTO;
import com.cafe.website.payload.ReviewUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface ReviewService {
	List<ReviewDTO> getListReviews(int limit, int page, String name, Long productId, Long userId, Long ratingId,
			String createdAt, String updatedAt, Float ratingAverage, String sortBy);

	List<ReviewDTO> getListReviewsByProductId(int limit, int page, Long productId, String sortBy);

	List<ReviewDTO> findAllByOrderByRatingAverageRating(Float ratingAverage);

	ReviewDTO getReviewById(Long id);

	ReviewDTO createReview(ReviewCreateDTO areaCreateDto, HttpServletRequest request) throws IOException;

	ReviewDTO updateReview(Long id, ReviewUpdateDTO areaUpdateDto, HttpServletRequest request) throws IOException;

	void deleteReview(Long id, HttpServletRequest request) throws IOException;

	Float getRatingByReviewId(Long id);

}
