package com.cafe.website.repository;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.Area;
import com.cafe.website.entity.Kind;

public interface KindRepository extends JpaRepository<Kind, Integer> {

	Slice<Area> findByNameContainingIgnoreCase(String name, Pageable pageable);


}
