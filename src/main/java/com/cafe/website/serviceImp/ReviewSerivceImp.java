package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.SortField;
import com.cafe.website.entity.Image;
import com.cafe.website.entity.Product;
import com.cafe.website.entity.Rating;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.User;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ReviewCreateDTO;
import com.cafe.website.payload.ReviewDTO;
import com.cafe.website.payload.ReviewUpdateDTO;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.RatingRepository;
import com.cafe.website.repository.ReviewRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.ReviewService;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.ReviewMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.util.StringUtils;

@Service
public class ReviewSerivceImp implements ReviewService {
	private ReviewRepository reviewRepository;
	private CloudinaryService cloudinaryService;
	private RatingRepository ratingRepository;
	private ProductRepository productRepository;
	private UserRepository userRepository;
	private ImageRepository imageRepository;

	private ReviewMapper reviewMapper;
	ObjectMapper objMapper = new ObjectMapper();
	String path_reviews = "cafe-springboot/reviews/";

	public ReviewSerivceImp(ReviewRepository reviewRepository, CloudinaryService cloudinaryService,
			RatingRepository ratingRepository, ProductRepository productRepository, UserRepository userRepository,
			ReviewMapper reviewMapper, ImageRepository imageRepository) {
		super();
		this.reviewRepository = reviewRepository;
		this.cloudinaryService = cloudinaryService;
		this.ratingRepository = ratingRepository;
		this.productRepository = productRepository;
		this.userRepository = userRepository;
		this.reviewMapper = reviewMapper;
		this.imageRepository = imageRepository;
	}

	@Override
	public List<ReviewDTO> getListReviews(int limit, int page, String name, Integer productId, Integer userId,
			Integer ratingId, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<ReviewDTO> listReviewDto;
		List<Review> listReview;
		List<Sort.Order> sortOrders = new ArrayList<>();

		// sort
		if (!StringUtils.isEmpty(sortBy))
			sortByList = Arrays.asList(sortBy.split(","));

		for (String sb : sortByList) {
			boolean isDescending = sb.endsWith("Desc");

			if (isDescending && !StringUtils.isEmpty(sortBy))
				sb = sb.substring(0, sb.length() - 4).trim();

			for (SortField sortField : validSortFields) {
				if (sortField.toString().equals(sb)) {
					sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
					break;
				}
			}
		}

		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));

		if (name != null && productId != null && userId != null && ratingId != null) {
			listReview = reviewRepository.findByNameContainingIgnoreCaseAndProductIdAndUserIdAndRatingId(name,
					productId, userId, ratingId, pageable).getContent();
		} else if (name != null && productId != null && userId != null) {
			listReview = reviewRepository
					.findByNameContainingIgnoreCaseAndProductIdAndUserId(name, productId, userId, pageable)
					.getContent();
		} else if (name != null && productId != null && ratingId != null) {
			listReview = reviewRepository
					.findByNameContainingIgnoreCaseAndProductIdAndRatingId(name, productId, ratingId, pageable)
					.getContent();
		} else if (name != null && userId != null && ratingId != null) {
			listReview = reviewRepository
					.findByNameContainingIgnoreCaseAndUserIdAndRatingId(name, userId, ratingId, pageable).getContent();
		} else if (name != null && productId != null) {
			listReview = reviewRepository.findByNameContainingIgnoreCaseAndProductId(name, productId, pageable)
					.getContent();
		} else if (name != null && userId != null) {
			listReview = reviewRepository.findByNameContainingIgnoreCaseAndUserId(name, userId, pageable).getContent();
		} else if (name != null && ratingId != null) {
			listReview = reviewRepository.findByNameContainingIgnoreCaseAndRatingId(name, ratingId, pageable)
					.getContent();
		} else if (name != null) {
			listReview = reviewRepository.findByNameContainingIgnoreCase(name, pageable).getContent();
		} else {
			listReview = reviewRepository.findAll(pageable).getContent();
		}

		listReviewDto = listReview.stream().map(review -> MapperUtils.mapToDTO(review, ReviewDTO.class))
				.collect(Collectors.toList());

		return listReviewDto;
	}

	@Override
	public ReviewDTO getReviewById(int id) {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
		ReviewDTO reviewDto = MapperUtils.mapToDTO(review, ReviewDTO.class);

		return reviewDto;
	}

	@Override
	public ReviewDTO createReview(ReviewCreateDTO reviewCreateDto) throws IOException {
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
		rating.setStatus(1);

		Product product = productRepository.findById(reviewCreateDto.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", reviewCreateDto.getProductId()));
		User user = userRepository.findById(reviewCreateDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", reviewCreateDto.getUserId()));

		cloudinaryService.uploadImages(images, reviewCreateDto.getlistImageFiles(), "cafe-springboot/blogs", "image");
		images.forEach(image -> {
			Image imageItem = new Image();
			imageItem.setImage(image);
			imageItem.setReview(review);
			listImages.add(imageItem);
		});

		review.setName(reviewCreateDto.getName());
		review.setProduct(product);
		review.setUser(user);
		review.setStatus(1);
		review.setRating(rating);

		reviewRepository.save(review);
		imageRepository.saveAll(listImages);

		return MapperUtils.mapToDTO(review, ReviewDTO.class);
	}

	@Override
	public ReviewDTO updateReview(int id, ReviewUpdateDTO reviewUpdateDto) throws IOException {
		Review review = reviewRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
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
		}

		reviewMapper.updateReviewFromDto(reviewDto, review);
		reviewRepository.save(review);

		return reviewDto;
	}

	@Override
	public String deleteReview(int id) throws IOException {
		ReviewDTO reviewDto = this.getReviewById(id);
		String path_reviews = "cafe-springboot/reviews";
		List<Image> listEntityImages = imageRepository.findAllImageByReviewId(id);

		if (listEntityImages != null)
			for (Image temp : listEntityImages)
				cloudinaryService.removeImageFromCloudinary(temp.getImage(), path_reviews);

		reviewRepository.deleteById(id);

		return "Delete successfully";

	}

	@Override
	public Integer getRatingByReviewId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
