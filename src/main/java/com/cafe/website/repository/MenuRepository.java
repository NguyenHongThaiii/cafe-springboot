package com.cafe.website.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Menu;

import jakarta.transaction.Transactional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	Optional<Menu> findMenuByProductId(Long productId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Menu i WHERE i.product.id = :productId")
	void deleteAllMenuByProductId(Long productId);

	List<Menu> findAllMenuByProductId(Long productId);

}
