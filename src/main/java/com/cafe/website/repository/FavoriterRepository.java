package com.cafe.website.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.Favorite;
import com.cafe.website.entity.Review;
import com.cafe.website.payload.FavoriteReviewCreateDTO;

public interface FavoriterRepository extends JpaRepository<Favorite, Long> {

	Optional<Favorite> findFavoriteByUserIdAndReviewId(Long userId, Long reviewId);

	Optional<Favorite> findFavoriteByUserIdAndCommentId(Long userId, Long commentId);

	List<Favorite> findFavoriteByReviewId(Long reviewId);

	List<Favorite> findFavoriteByCommentId(Long reviewId);

	Boolean existsByReviewIdAndUserId(Long reviewId, Long userId);

	Boolean existsByCommentIdAndUserId(Long commentId, Long userId);

}
