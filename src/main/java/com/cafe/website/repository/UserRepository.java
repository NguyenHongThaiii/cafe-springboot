package com.cafe.website.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.Area;
import com.cafe.website.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Slice<Area> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	Boolean existsBySlug(String slug);

	Optional<User> findByName(String name);

	Optional<User> findBySlug(String slug);

	Boolean existsByName(String email);

	Boolean existsBySlugAndIdNot(String slug, Integer id);

	Boolean existsByNameAndIdNot(String name, Integer id);

}
