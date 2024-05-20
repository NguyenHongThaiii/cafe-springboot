package com.cafe.website.serviceImp;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.SortField;
import com.cafe.website.constant.StatusLog;
import com.cafe.website.entity.Favorite;
import com.cafe.website.entity.Image;
import com.cafe.website.entity.Product;
import com.cafe.website.entity.Rating;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.User;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.payload.ReviewCreateDTO;
import com.cafe.website.payload.ReviewDTO;
import com.cafe.website.payload.ReviewUpdateDTO;
import com.cafe.website.payload.UserDTO;
import com.cafe.website.repository.FavoriterRepository;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.RatingRepository;
import com.cafe.website.repository.ReviewRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.LogService;
import com.cafe.website.service.ReviewService;
import com.cafe.website.util.JsonConverter;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.MethodUtil;
import com.cafe.website.util.ReviewMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ReviewSerivceImp implements ReviewService {
	@PersistenceContext
	private EntityManager entityManager;
	private ReviewRepository reviewRepository;
	private CloudinaryService cloudinaryService;
	private RatingRepository ratingRepository;
	private ProductRepository productRepository;
	private UserRepository userRepository;
	private ImageRepository imageRepository;
	private FavoriterRepository favoriteRepository;
	private ReviewMapper reviewMapper;
	private LogService logService;
	private AuthService authService;
	private ObjectMapper objectMapper;

	ObjectMapper objMapper = new ObjectMapper();
	@Value("${app.path-review}")
	String path_reviews;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public ReviewSerivceImp(EntityManager entityManager, ReviewRepository reviewRepository,
			CloudinaryService cloudinaryService, RatingRepository ratingRepository, ProductRepository productRepository,
			UserRepository userRepository, ImageRepository imageRepository, FavoriterRepository favoriteRepository,
			ReviewMapper reviewMapper, LogService logService, AuthService authService, ObjectMapper objectMapper) {
		super();
		this.entityManager = entityManager;
		this.reviewRepository = reviewRepository;
		this.cloudinaryService = cloudinaryService;
		this.ratingRepository = ratingRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.imageRepository = imageRepository;
		this.favoriteRepository = favoriteRepository;
		this.reviewMapper = reviewMapper;
		this.logService = logService;
		this.authService = authService;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<ReviewDTO> getListReviews(int limit, int page, String name, Long productId, Long userId, Long ratingId,
			Integer outstanding, String createdAt, String updatedAt, Float ratingAverage, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = null;

		List<String> sortByList = new ArrayList<String>();
		List<ReviewDTO> listReviewDto;
		List<Review> listReview;
		List<Sort.Order> sortOrders = new ArrayList<>();

		if (page != 0) {
			pageable = PageRequest.of(page - 1, limit);

			if (!StringUtils.isEmpty(sortBy))
				sortByList = Arrays.asList(sortBy.split(","));

			for (String sb : sortByList) {
				boolean isDescending = sb.endsWith("Desc");

				if (isDescending && !StringUtils.isEmpty(sortBy))
					sb = sb.substring(0, sb.length() - 4).trim();

				for (SortField sortField : validSortFields) {
					if (sortField.toString().equals(sb.trim())) {
						sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
						break;
					}
				}
			}
		}
		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));

		listReview = reviewRepository.findWithFilters(name, productId, userId, ratingId, outstanding, createdAt,
				updatedAt, ratingAverage, pageable, entityManager);
		listReviewDto = listReview.stream().map(review -> {
			User user = userRepository.findById(review.getUser().getId())
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", review.getUser().getId()));
			UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);
			if (user.getAvatar() != null)
				userDto.setImage(ImageDTO.generateImageDTO(user.getAvatar()));

			ReviewDTO reviewDto = MapperUtils.mapToDTO(review, ReviewDTO.class);
			reviewDto.setListImage(ImageDTO.generateListImageDTO(review.getListImages()));
			reviewDto.setProductId(review.getProduct().getId());
			reviewDto.setUserDto(userDto);

			return reviewDto;
		}).collect(Collectors.toList());

		return listReviewDto;
	}

	@Override
	public ReviewDTO getReviewById(Long id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
		ReviewDTO reviewDto = MapperUtils.mapToDTO(review, ReviewDTO.class);
		User user = userRepository.findById(review.getUser().getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", review.getUser().getId()));
		UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);
		if (user.getAvatar() != null)
			userDto.setImage(ImageDTO.generateImageDTO(user.getAvatar()));

		reviewDto.setListImage(ImageDTO.generateListImageDTO(review.getListImages()));
		reviewDto.setProductId(review.getProduct().getId());
		reviewDto.setUserDto(userDto);
		return reviewDto;
	}

	@Override
	public ReviewDTO createReview(ReviewCreateDTO reviewCreateDto, HttpServletRequest request) throws IOException {
		// TODO Auto-generated method stub
		List<Image> listImages = new ArrayList<>();
		List<String> images = new ArrayList<>();
		Review review = new Review();
		Rating rating = new Rating();

		rating.setFood(reviewCreateDto.getFood());
		rating.setLocation(reviewCreateDto.getLocation());
		rating.setPrice(reviewCreateDto.getPrice());
		rating.setService(reviewCreateDto.getService());
		rating.setSpace(reviewCreateDto.getSpace());

		Product product = productRepository.findById(reviewCreateDto.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", reviewCreateDto.getProductId()));
		User user = userRepository.findById(reviewCreateDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", reviewCreateDto.getUserId()));

		cloudinaryService.uploadImages(images, reviewCreateDto.getListImageFiles(), path_reviews, "image");
		images.forEach(image -> {
			Image imageItem = new Image();
			imageItem.setImage(image);
			imageItem.setReview(review);
			listImages.add(imageItem);
		});

		review.setName(reviewCreateDto.getName());
		review.setProduct(product);
		review.setUser(user);
		review.setRating(rating);
		if (reviewCreateDto.getOutstanding() == null)
			review.setOutstanding(0);
		reviewRepository.save(review);
		imageRepository.saveAll(listImages);

		ReviewDTO reviewDto = MapperUtils.mapToDTO(review, ReviewDTO.class);
		if (review.getListImages().size() > 0)
			reviewDto.setListImage(ImageDTO.generateListImageDTO(review.getListImages()));
		reviewDto.setProductId(review.getProduct().getId());
		MethodUtil.convertListFileImageToInfo(reviewCreateDto.getListFileMetadatas(),
				reviewCreateDto.getListImageFiles());
		reviewCreateDto.setListImageFiles(null);
		UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);
		reviewDto.setUserDto(userDto);
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Create Review SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(reviewCreateDto),
					"Create Review SUCCESSFULY");
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Create Review SUCCESSFULY");
			e.printStackTrace();
		}

		return reviewDto;
	}

	@Override
	public ReviewDTO updateReview(Long id, ReviewUpdateDTO reviewUpdateDto, HttpServletRequest request)
			throws IOException {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
		User user = userRepository.findById(reviewUpdateDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", reviewUpdateDto.getUserId()));
		String path_reviews = "cafe-springboot/reviews";
		ReviewDTO reviewDto = MapperUtils.mapToDTO(review, ReviewDTO.class);

		if (reviewUpdateDto.getListImageFiles() != null) {
			List<String> images = new ArrayList<>();
			List<Image> listImages = new ArrayList<>();

			List<Image> listEntityImages = imageRepository.findAllImageByReviewId(id);
			if (listEntityImages != null)
				for (Image temp : listEntityImages)
					cloudinaryService.removeImageFromCloudinary(temp.getImage(), path_reviews);

			cloudinaryService.uploadImages(images, reviewUpdateDto.getListImageFiles(), path_reviews, "image");
			images.forEach(imageTemp -> {
				Image imageItem = new Image();
				imageItem.setImage(imageTemp);
				imageItem.setReview(review);
				listImages.add(imageItem);
			});
			imageRepository.deleteAllImageByReviewId(id);
			review.setListImages(listImages);
			MethodUtil.convertListFileImageToInfo(reviewUpdateDto.getListFileMetadatas(),
					reviewUpdateDto.getListImageFiles());
			reviewUpdateDto.setListImageFiles(null);
		}

		reviewMapper.updateReviewFromDto(reviewDto, review);
		reviewRepository.save(review);

		reviewDto.setListImage(ImageDTO.generateListImageDTO(review.getListImages()));
		reviewDto.setProductId(review.getProduct().getId());
		Map<String, Object> logData = new HashMap<>();
		logData.put("id", id);
		logData.put("reviewUpdateDto", reviewUpdateDto);
		UserDTO userDto = MapperUtils.mapToDTO(user, UserDTO.class);
		reviewDto.setUserDto(userDto);
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Update Review SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(logData),
					"Update Review SUCCESSFULY");
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Update Review SUCCESSFULY");
			e.printStackTrace();
		}
		return reviewDto;
	}

	@Override
	public void deleteReview(Long id, HttpServletRequest request) throws IOException {
		this.getReviewById(id);
		String path_reviews = "cafe-springboot/reviews";
		List<Image> listEntityImages = imageRepository.findAllImageByReviewId(id);

		if (listEntityImages != null)
			for (Image temp : listEntityImages)
				cloudinaryService.removeImageFromCloudinary(temp.getImage(), path_reviews);

		reviewRepository.deleteById(id);
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Delete Review SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), JsonConverter.convertToJSON("id", id),
					"Delete Review SUCCESSFULY");
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Delete Review SUCCESSFULY");
			e.printStackTrace();
		}
	}

	@Override
	public Double getRatingByReviewId(Long productId) {
		Product product = productRepository.findById(productId).orElse(null);

		List<Review> listReviews = reviewRepository.findReviewByProductId(productId);
		if (listReviews == null || product == null || listReviews.size() == 0)
			return 0.0;
		Double total = 0.0;
		for (Review review : listReviews) {
			total += review.getAverageRating();
		}
		Double res = (total / listReviews.size());
		return res;
	}

	@Override
	public List<ReviewDTO> getListReviewsByProductId(int limit, int page, Long productId, String sortBy) {
		Product product = productRepository.findById(productId).orElse(null);
		if (product == null)
			return null;
//		List<ReviewDTO> listReviewsDto = this.getListReviews(limit, page, null, productId, null, null, sortBy);
//		// TODO Auto-generated method stub
//		return listReviewsDto;
		return null;
	}

	@Override
	public List<ReviewDTO> findAllByOrderByRatingAverageRating(Float ratingAverage) {
		List<Review> reviews = reviewRepository.findAllByOrderByRatingAverageRating(ratingAverage, entityManager);
		List<ReviewDTO> listReviewDto = new ArrayList<>();
		listReviewDto = reviews.stream().map(review -> {
			ReviewDTO reviewDto = MapperUtils.mapToDTO(review, ReviewDTO.class);
			reviewDto.setListImage(ImageDTO.generateListImageDTO(review.getListImages()));
			reviewDto.setProductId(review.getProduct().getId());
			return reviewDto;
		}).collect(Collectors.toList());

		return listReviewDto;
	}

}
