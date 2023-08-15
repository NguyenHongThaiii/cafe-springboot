package com.cafe.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	Boolean existsBySlug(String slug);

	Optional<User> findByName(String name);

	Optional<User> findBySlug(String slug);

	Boolean existsByName(String email);

}
