package com.cafe.website.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
//    Optional<User> findByUsernameOrEmail(String username, String email);
//    Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
}
