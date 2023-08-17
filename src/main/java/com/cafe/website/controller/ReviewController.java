package com.cafe.website.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.ReviewDTO;
import com.cafe.website.service.AreaService;
import com.cafe.website.service.ReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

	private ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@RequestMapping("")
	public ResponseEntity<List<ReviewDTO>> getListReviews(@RequestParam(defaultValue = "5") int limit,
			@RequestParam(defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "") String name,
			@RequestParam(required = false) Integer ratingId, @RequestParam(required = false) Integer productId,
			@RequestParam(required = false	) Integer userId,
			@RequestParam(required = false, defaultValue = "null") String sortBy) {
		List<ReviewDTO> listAreasDto = reviewService.getListReviews(limit, page, name, productId, userId, ratingId,
				sortBy);
		return new ResponseEntity<>(listAreasDto, HttpStatus.OK);
	}

}
