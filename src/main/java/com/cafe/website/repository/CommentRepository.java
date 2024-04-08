package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Comment;
import com.cafe.website.entity.Review;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query
	default List<Comment> findWithFilters(Integer status, String name, Long reviewId, Long userId, String createdAt,
			String updatedAt, Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);

		Root<Comment> comment = cq.from(Comment.class);
		List<Predicate> predicates = new ArrayList<>();
		if (status != null) {
			predicates.add(cb.equal(comment.get(" status"), status));
		}
		if (name != null) {
			predicates.add(cb.like(cb.lower(comment.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			predicates.add(cb.like(cb.lower(comment.get("createdAt")), "%" + createdAt.toLowerCase() + "%"));
		}
		if (updatedAt != null) {
			predicates.add(cb.like(cb.lower(comment.get("updatedAt")), "%" + updatedAt.toLowerCase() + "%"));
		}
		if (reviewId != null) {
			predicates.add(cb.equal(comment.get("review").get("id"), reviewId));
		}
		if (userId != null) {
			predicates.add(cb.equal(comment.get("user").get("id"), userId));
		}
		if (pageable != null && pageable.getSort() != null) {
			List<Order> orders = new ArrayList<>();
			for (Sort.Order order : pageable.getSort()) {
				orders.add(order.isAscending() ? cb.asc(comment.get(order.getProperty()))
						: cb.desc(comment.get(order.getProperty())));
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
}
