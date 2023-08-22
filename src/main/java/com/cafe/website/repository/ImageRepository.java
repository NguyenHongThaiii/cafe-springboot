package com.cafe.website.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Image;

import jakarta.transaction.Transactional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	Optional<Image> findImageByProductId(Integer productId);

	Optional<Image> findImageByUserId(Integer userId);


	Optional<Image> findImageByReviewId(Integer reviewId);

	Optional<Image> findImageByAreaId(Integer areaId);

	Optional<Image> findImageByKindId(Integer kindId);

	Optional<Image> findImageByPurposeId(Integer purposeId);

	Optional<Image> findImageByConvenienceId(Integer conId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Image i WHERE i.product.id = :productId")
	void deleteAllImageByProductId(Integer productId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Image i WHERE i.review.id = :reviewId")
	void deleteAllImageByReviewId(Integer reviewId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Image i WHERE i.user.id = :userId")
	void deleteAllImageByUserId(Integer userId);

	List<Image> findAllImageByProductId(Integer productId);

	List<Image> findAllImageByReviewId(Integer productId);

	List<Image> findAllImageByUserId(Integer productId);


	
}
