package com.cafe.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.website.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
