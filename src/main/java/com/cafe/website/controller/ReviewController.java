package com.cafe.website.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.ReviewCreateDTO;
import com.cafe.website.payload.ReviewDTO;
import com.cafe.website.payload.ReviewUpdateDTO;
import com.cafe.website.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

	private ReviewService reviewService;
	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@GetMapping("")
	public ResponseEntity<List<ReviewDTO>> getListReviews(@RequestParam(defaultValue = "5") int limit,
			@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String name,
			@RequestParam(required = false) Integer ratingId, @RequestParam(required = false) Integer productId,
			@RequestParam(required = false) Integer userId,
			@RequestParam(required = false, defaultValue = "null") String sortBy) {
		List<ReviewDTO> listAreasDto = reviewService.getListReviews(limit, page, name, productId, userId, ratingId,
				sortBy);
		return new ResponseEntity<>(listAreasDto, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReviewDTO> getReviewById(@PathVariable(name = "id") int id) throws IOException {
		ReviewDTO reviewDto = reviewService.getReviewById(id);
		return new ResponseEntity<>(reviewDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PostMapping("")
	public ResponseEntity<ReviewDTO> createReview(@Valid @ModelAttribute ReviewCreateDTO review) throws IOException {
		ReviewDTO reviewDto = reviewService.createReview(review);
		return new ResponseEntity<>(reviewDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD','USER')")
	@PatchMapping("/{id}")
	public ResponseEntity<ReviewDTO> updateReviewById(@PathVariable(name = "id") int id,
			@Valid @ModelAttribute ReviewUpdateDTO review) throws IOException {
		ReviewDTO reviewDto = reviewService.updateReview(id, review);
		return new ResponseEntity<>(reviewDto, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delelteReviewById(@PathVariable(name = "id") int id) throws IOException {
		reviewService.deleteReview(id);
		return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
	}

	@GetMapping("/products")
	public ResponseEntity<List<ReviewDTO>> getListReviewsByProductId(@RequestParam(defaultValue = "5") int limit,
			@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) Integer productId,
			@RequestParam(required = false, defaultValue = "null") String sortBy) {

		List<ReviewDTO> listAreasDto = reviewService.getListReviewsByProductId(limit, page, productId, sortBy);
		return new ResponseEntity<>(listAreasDto, HttpStatus.OK);
	}

//	@GetMapping("/{id}/ratings")
//	public ResponseEntity<Float> getRatingByReviewId(@PathVariable(name = "id") int id) throws IOException {
//		Float number = reviewService.getRatingByReviewId(id);
//		return new ResponseEntity<>(number, HttpStatus.OK);
//	}

}
