package com.cafe.website.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.User;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Slice<Product> findByNameContainingIgnoreCase(String searchBy, Pageable pageable);
	Optional<Product> findBySlugOrName(String slug,String name);

	Boolean existsBySlugAndIdNot(String slug, Integer id);

	Boolean existsByNameAndIdNot(String name, Integer id);

}
