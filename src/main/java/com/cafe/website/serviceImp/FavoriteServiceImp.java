package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.StatusLog;
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
import com.cafe.website.service.AuthService;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.FavoriteService;
import com.cafe.website.service.LogService;
import com.cafe.website.util.ReviewMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FavoriteServiceImp implements FavoriteService {
	private ReviewRepository reviewRepository;
	private UserRepository userRepository;
	private FavoriterRepository favoriteRepository;
	private LogService logService;
	private AuthService authService;
	private ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public FavoriteServiceImp(ReviewRepository reviewRepository, UserRepository userRepository,
			FavoriterRepository favoriteRepository, LogService logService, AuthService authService,
			ObjectMapper objectMapper) {
		super();
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
		this.favoriteRepository = favoriteRepository;
		this.logService = logService;
		this.authService = authService;
		this.objectMapper = objectMapper;
	}

	@Override
	public void toggleFavoriteReview(FavoriteCreateDTO favoriteCreate, HttpServletRequest request) {
		Favorite favor = null;
		User user = null;
		Review review;
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
		try {
			logService.createLog(request, user, "Toggle Favorite SUCCESSFULY", StatusLog.SUCCESSFULLY.toString(),
					objectMapper.writeValueAsString(favoriteCreate), "Toggle Favorite");
		} catch (IOException e) {
			logService.createLog(request, user, e.getMessage().substring(0, 255), StatusLog.FAILED.toString(),
					"Toggle Favorite");
			e.printStackTrace();
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
