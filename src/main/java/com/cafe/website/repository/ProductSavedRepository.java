package com.cafe.website.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.ProductSaved;

import jakarta.transaction.Transactional;

public interface ProductSavedRepository extends JpaRepository<ProductSaved, Long> {
	Boolean existsByUserIdAndProductId(Long userId, Long productId);

	@Transactional
	@Modifying
	@Query("DELETE FROM ProductSaved p WHERE p.product.id = :productId AND p.user.id = :userId")
	void deleteByUserIdAndProductId(Long userId, Long productId);

	List<ProductSaved> findAllByUserId(Long userId);
}
