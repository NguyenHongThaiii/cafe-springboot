package com.cafe.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.ProductSchedule;

import jakarta.transaction.Transactional;

public interface ProductScheduleRepository extends JpaRepository<ProductSchedule, Integer> {
	@Transactional
	@Modifying
	@Query("DELETE FROM ProductSchedule i WHERE i.product.id = :productId")
	void deleteAllScheduleByProductId(Integer productId);

}
