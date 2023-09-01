package com.cafe.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.ProductOwner;

import jakarta.transaction.Transactional;

public interface ProductOwnerRepository extends JpaRepository<ProductOwner, Integer> {
	Boolean existsByUserIdAndProductId(Integer userId, Integer productId);

	@Transactional
	@Modifying
	@Query("DELETE FROM ProductOwner i WHERE i.product.id = :productId AND i.user.id = :userId")
	void deleteAllOwnerByProductIdAndUserId(Integer productId, Integer userId);

	Optional<ProductOwner> findByProductIdAndUserId(Integer productId, Integer userId);

}
