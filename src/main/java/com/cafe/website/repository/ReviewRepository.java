package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Review;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findReviewByProductId(Integer productId);

	@Query
	default List<Review> findWithFilters(String name, Integer productId, Integer userId, Integer ratingId,
			Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> cq = cb.createQuery(Review.class);

		Root<Review> review = cq.from(Review.class);
		List<Predicate> predicates = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(review.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (productId != null) {
			predicates.add(cb.equal(review.get("product").get("id"), productId));
		}
		if (userId != null) {
			predicates.add(cb.equal(review.get("user").get("id"), userId));
		}
		if (ratingId != null) {
			predicates.add(cb.equal(review.get("rating").get("id"), ratingId));
		}

		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}
}
