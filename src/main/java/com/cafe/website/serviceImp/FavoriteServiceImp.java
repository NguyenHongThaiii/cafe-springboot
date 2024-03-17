package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.StatusLog;
import com.cafe.website.entity.Comment;
import com.cafe.website.entity.Favorite;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.User;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.FavoriteCommentCreateDTO;
import com.cafe.website.payload.FavoriteReviewCreateDTO;
import com.cafe.website.repository.CommentRepository;
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
import com.cafe.website.util.MethodUtil;
import com.cafe.website.util.ReviewMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class FavoriteServiceImp implements FavoriteService {
	private ReviewRepository reviewRepository;
	private CommentRepository commentRepository;
	private UserRepository userRepository;
	private FavoriterRepository favoriteRepository;
	private LogService logService;
	private AuthService authService;
	private ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public FavoriteServiceImp(ReviewRepository reviewRepository, CommentRepository commentRepository,
			UserRepository userRepository, FavoriterRepository favoriteRepository, LogService logService,
			AuthService authService, ObjectMapper objectMapper) {
		super();
		this.reviewRepository = reviewRepository;
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
		this.favoriteRepository = favoriteRepository;
		this.logService = logService;
		this.authService = authService;
		this.objectMapper = objectMapper;
	}

	@Override
	public void toggleFavoriteReview(FavoriteReviewCreateDTO favoriteCreate, HttpServletRequest request) {
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
			logService.createLog(request, user, MethodUtil.handleSubstringMessage(e.getMessage()),
					StatusLog.FAILED.toString(), "Toggle Favorite");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}

	}

	@Override
	public void toggleFavoriteComment(FavoriteCommentCreateDTO favoriteCreate, HttpServletRequest request) {
		Favorite favor = null;
		User user = null;
		Comment comment;
		if (!favoriteRepository.existsByCommentIdAndUserId(favoriteCreate.getCommentId(), favoriteCreate.getUserId())) {
			user = userRepository.findById(favoriteCreate.getUserId())
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", favoriteCreate.getUserId()));
			comment = commentRepository.findById(favoriteCreate.getCommentId())
					.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", favoriteCreate.getCommentId()));
			favor = new Favorite();
			favor.setComment(comment);
			favor.setUser(user);
			favoriteRepository.save(favor);

		} else {
			favor = favoriteRepository
					.findFavoriteByUserIdAndCommentId(favoriteCreate.getUserId(), favoriteCreate.getCommentId())
					.orElseThrow(() -> new ResourceNotFoundException("Favorite", "id", "Not found"));
			favoriteRepository.delete(favor);
		}
		try {
			logService.createLog(request, user, "Toggle Favorite SUCCESSFULY", StatusLog.SUCCESSFULLY.toString(),
					objectMapper.writeValueAsString(favoriteCreate), "Toggle Favorite");
		} catch (IOException e) {
			logService.createLog(request, user, MethodUtil.handleSubstringMessage(e.getMessage()),
					StatusLog.FAILED.toString(), "Toggle Favorite");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}

	}

	@Override
	public Integer getAmountFavoriteReview(Long reviewId) {
		List<Favorite> favor = favoriteRepository.findFavoriteByReviewId(reviewId);
		if (favor == null)
			return 0;
		return favor.size();
	}

	@Override
	public Integer getAmountFavoriteComment(Long commentId) {
		List<Favorite> favor = favoriteRepository.findFavoriteByCommentId(commentId);
		if (favor == null)
			return 0;
		return favor.size();
	}

}
