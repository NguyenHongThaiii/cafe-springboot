package com.cafe.website.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.Favorite;
import com.cafe.website.entity.Review;
import com.cafe.website.payload.FavoriteCreateDTO;

public interface FavoriterRepository extends JpaRepository<Favorite, Integer> {

	Optional<Favorite> findFavoriteByUserIdAndReviewId(Integer userId, Integer reviewId);

	List<Favorite> findFavoriteByReviewId(Integer reviewId);

	Boolean existsByReviewIdAndUserId(Integer reviewId, Integer userId);

	Boolean existsByCommentIdAndUserId(Integer commentId, Integer userId);

}
