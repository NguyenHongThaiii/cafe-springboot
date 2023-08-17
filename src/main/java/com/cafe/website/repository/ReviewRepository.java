package com.cafe.website.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	Slice<Review> findByNameContainingIgnoreCaseAndProductIdAndUserIdAndRatingId(String name, Integer productId,
			Integer userId, Integer ratingId, Pageable pageable);

	Slice<Review> findByNameContainingIgnoreCaseAndProductIdAndUserId(String name, Integer productId, Integer userId,
			Pageable pageable);

	Slice<Review> findByNameContainingIgnoreCaseAndProductIdAndRatingId(String name, Integer productId,
			Integer ratingId, Pageable pageable);

	Slice<Review> findByNameContainingIgnoreCaseAndUserIdAndRatingId(String name, Integer userId, Integer ratingId,
			Pageable pageable);

	Slice<Review> findByNameContainingIgnoreCaseAndProductId(String name, Integer productId, Pageable pageable);

	Slice<Review> findByNameContainingIgnoreCaseAndUserId(String name, Integer userId, Pageable pageable);

	Slice<Review> findByNameContainingIgnoreCaseAndRatingId(String name, Integer ratingId, Pageable pageable);

	Slice<Review> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Optional<Review> findByName(String name);

}
