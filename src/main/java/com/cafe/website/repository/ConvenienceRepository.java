package com.cafe.website.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.Area;
import com.cafe.website.entity.Convenience;
import com.cafe.website.entity.Kind;

public interface ConvenienceRepository extends JpaRepository<Convenience, Integer> {

	Slice<Area> findByNameContainingIgnoreCase(String name, Pageable pageable);


}
