package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Rating;
import com.cafe.website.entity.Review;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	List<Review> findReviewByProductId(Long productId);

	default List<Review> findWithFilters(String name, Long productId, Long userId, Long ratingId, String createdAt,
			String updatedAt, Float ratingAverage, Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> cq = cb.createQuery(Review.class);

		Root<Review> review = cq.from(Review.class);
		List<Predicate> predicates = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(review.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			predicates.add(cb.like(cb.lower(review.get("createdAt")), "%" + createdAt.toLowerCase() + "%"));
		}
		if (updatedAt != null) {
			predicates.add(cb.like(cb.lower(review.get("updatedAt")), "%" + updatedAt.toLowerCase() + "%"));
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
		if (ratingAverage != null) {
			Join<Review, Rating> rating = review.join("rating");
			Expression<Float> sumOfRatings = cb
					.sum(cb.sum(cb.sum(cb.sum(rating.get("location"), rating.get("space")), rating.get("food")),
							rating.get("service")), rating.get("price"));

			Expression<Number> calculatedAverage = cb.quot(sumOfRatings, 5.0f);
			predicates.add(cb.equal(calculatedAverage, ratingAverage));
		}
		if (pageable != null && pageable.getSort() != null) {
			List<Order> orders = new ArrayList<>();
			for (Sort.Order order : pageable.getSort()) {
				orders.add(order.isAscending() ? cb.asc(review.get(order.getProperty()))
						: cb.desc(review.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		if (pageable != null)
			return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
					.setMaxResults(pageable.getPageSize()).getResultList();
		else
			return entityManager.createQuery(cq).setFirstResult(0).getResultList();
	}

	default List<Review> findAllByOrderByRatingAverageRating(Float ratingAverage, EntityManager entityManager) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> cq = cb.createQuery(Review.class);
		Root<Review> review = cq.from(Review.class);
		Join<Review, Rating> rating = review.join("rating");

		Expression<Float> sumOfRatings = cb
				.sum(cb.sum(cb.sum(cb.sum(rating.get("location"), rating.get("space")), rating.get("food")),
						rating.get("service")), rating.get("price"));

		Expression<Number> calculatedAverage = cb.quot(sumOfRatings, 5.0f);

		Predicate ratingPredicate = cb.equal(calculatedAverage, ratingAverage);

		cq.select(review).where(ratingPredicate);

		List<Review> results = entityManager.createQuery(cq).getResultList();
		return results;
	}

}
