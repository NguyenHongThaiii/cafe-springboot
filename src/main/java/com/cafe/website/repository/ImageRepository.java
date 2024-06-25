package com.cafe.website.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Image;

import jakarta.transaction.Transactional;

public interface ImageRepository extends JpaRepository<Image, Long> {
	Optional<Image> findImageByProductId(Long productId);

	Optional<Image> findImageByUserId(Long userId);

	Optional<Image> findImageByReviewId(Long reviewId);

	Optional<Image> findImageByAreaId(Long areaId);

	Optional<Image> findImageByKindId(Long kindId);

	Optional<Image> findImageByPurposeId(Long purposeId);

	Optional<Image> findImageByConvenienceId(Long conId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Image i WHERE i.product.id = :productId")
	void deleteAllImageByProductId(Long productId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Image i WHERE i.menu.id = :menuId")
	void deleteImageByMenuId(Long menuId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Image i WHERE i.review.id = :reviewId")
	void deleteAllImageByReviewId(Long reviewId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Image i WHERE i.user.id = :userId")
	void deleteAllImageByUserId(Long userId);

	List<Image> findAllImageByProductId(Long productId);

	List<Image> findAllImageByReviewId(Long productId);

	List<Image> findAllImageByUserId(Long productId);

}
