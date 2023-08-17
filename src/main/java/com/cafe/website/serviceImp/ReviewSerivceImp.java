package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.SortField;
import com.cafe.website.entity.Area;
import com.cafe.website.entity.Review;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.AreaCreateDTO;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.AreaUpdateDTO;
import com.cafe.website.payload.ReviewDTO;
import com.cafe.website.payload.ReviewUpdateDTO;
import com.cafe.website.repository.ReviewRepository;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.ReviewService;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.ReviewMapper;

import io.micrometer.common.util.StringUtils;

@Service
public class ReviewSerivceImp implements ReviewService {
	private ReviewRepository reviewRepository;
	private CloudinaryService cloudinaryService;
	private ReviewMapper reviewMapper; 


	public ReviewSerivceImp(ReviewRepository reviewRepository, CloudinaryService cloudinaryService,
			ReviewMapper reviewMapper) {
		this.reviewRepository = reviewRepository;
		this.cloudinaryService = cloudinaryService;
		this.reviewMapper = reviewMapper;
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
	public ReviewDTO createReview(ReviewDTO areaCreateDto) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReviewDTO updateReview(int id, ReviewUpdateDTO reviewUpdateDto) throws IOException {
		ReviewDTO reviewDto = this.getReviewById(id);
		Review review = MapperUtils.mapToEntity(reviewDto, Review.class);
		
//		AreaDTO newdto = this.getAreaById(id);
//
//		if (areaRepository.existsBySlugAndIdNot(slugify.slugify(areaUpdateDto.getSlug()), newdto.getId()))
//			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
//		if (areaRepository.existsByNameAndIdNot(areaUpdateDto.getName(), newdto.getId()))
//			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");
//
//		Area area = areaMapper.dtoToEntity(newdto);
//		String image = cloudinaryService.uploadImage(areaUpdateDto.getImage(), "cafe-springboot/categories", "image");
//		AreaDTO areaDto = MapperUtils.mapToDTO(areaUpdateDto, AreaDTO.class);
//
//		areaDto.setId(id);
//		areaDto.setImage(image);
//		areaDto.setSlug(slugify.slugify(areaUpdateDto.getSlug()));
//
//		areaMapper.updateAreaFromDto(areaDto, area);
//		areaRepository.save(area);

//		return areaMapper.entityToDto(area);
		return null;
	}

	@Override
	public void deleteReview(int id) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getRatingByReviewId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
