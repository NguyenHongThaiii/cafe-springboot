package com.cafe.website.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Slice<Product> findByNameContainingIgnoreCase(String searchBy, Pageable pageable);

}
