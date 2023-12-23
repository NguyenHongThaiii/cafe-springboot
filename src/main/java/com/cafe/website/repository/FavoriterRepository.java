package com.cafe.website.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.Favorite;
import com.cafe.website.entity.Review;
import com.cafe.website.payload.FavoriteReviewCreateDTO;

public interface FavoriterRepository extends JpaRepository<Favorite, Integer> {

	Optional<Favorite> findFavoriteByUserIdAndReviewId(Integer userId, Integer reviewId);

	Optional<Favorite> findFavoriteByUserIdAndCommentId(Integer userId, Integer commentId);

	List<Favorite> findFavoriteByReviewId(Integer reviewId);

	List<Favorite> findFavoriteByCommentId(Integer reviewId);

	Boolean existsByReviewIdAndUserId(Integer reviewId, Integer userId);

	Boolean existsByCommentIdAndUserId(Integer commentId, Integer userId);

}
