package com.cafe.website.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Menu;

import jakarta.transaction.Transactional;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
	Optional<Menu> findMenuByProductId(Integer productId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Menu i WHERE i.product.id = :productId")
	void deleteAllMenuByProductId(Integer productId);

	List<Menu> findAllMenuByProductId(Integer productId);

}
