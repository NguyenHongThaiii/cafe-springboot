package com.cafe.website.serviceImp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cafe.website.entity.Favorite;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.User;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.FavoriteCreateDTO;
import com.cafe.website.repository.FavoriterRepository;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.RatingRepository;
import com.cafe.website.repository.ReviewRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.FavoriteService;
import com.cafe.website.util.ReviewMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FavoriteServiceImp implements FavoriteService {
	private ReviewRepository reviewRepository;
	private UserRepository userRepository;
	private FavoriterRepository favoriteRepository;

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public FavoriteServiceImp(ReviewRepository reviewRepository, UserRepository userRepository,
			FavoriterRepository favoriteRepository) {
		super();
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
		this.favoriteRepository = favoriteRepository;
	}

	@Override
	public void toggleFavoriteReview(FavoriteCreateDTO favoriteCreate) {
		Favorite favor = null;
		User user;
		Review review;
		logger.info(favoriteCreate.getUserId() + "");
		if (!favoriteRepository.existsByReviewIdAndUserId(favoriteCreate.getReviewId(), favoriteCreate.getUserId())) {
			user = userRepository.findById(favoriteCreate.getUserId())
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", favoriteCreate.getUserId()));
			review = reviewRepository.findById(favoriteCreate.getReviewId())
					.orElseThrow(() -> new ResourceNotFoundException("Review", "id", favoriteCreate.getReviewId()));
			favor = new Favorite();
			favor.setReview(review);
			favor.setUser(user);
			favoriteRepository.save(favor);

		} else {
			favor = favoriteRepository
					.findFavoriteByUserIdAndReviewId(favoriteCreate.getUserId(), favoriteCreate.getReviewId())
					.orElseThrow(() -> new ResourceNotFoundException("Favorite", "id", "Not found"));

			favoriteRepository.delete(favor);
		}

	}

	@Override
	public Integer getAmountFavorite(Integer reviewId) {
		List<Favorite> favor = favoriteRepository.findFavoriteByReviewId(reviewId);
		if (favor == null)
			return 0;
		return favor.size();
	}
}
