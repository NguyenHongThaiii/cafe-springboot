package com.cafe.website.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.Area;

public interface AreaRepository extends JpaRepository<Area, Integer> {

	Slice<Area> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Boolean existsBySlugAndIdNot(String slug, Integer id);

	Boolean existsByNameAndIdNot(String name, Integer id);
	
	Optional<Area> findByName(String name);

}
