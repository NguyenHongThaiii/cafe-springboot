package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cafe.website.entity.Notification;
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

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	default List<Notification> findWithFilters(String message, String original, Integer state, Long userId,
			Long senderId, String createdAt, String updatedAt, Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Notification> cq = cb.createQuery(Notification.class);

		Root<Notification> notifi = cq.from(Notification.class);
		List<Predicate> predicates = new ArrayList<>();
		if (message != null) {
			predicates.add(cb.like(cb.lower(notifi.get("message")), "%" + message.toLowerCase() + "%"));
		}
		if (original != null) {
			predicates.add(cb.like(cb.lower(notifi.get("original")), "%" + original.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			predicates.add(cb.like(cb.lower(notifi.get("createdAt")), "%" + createdAt.toLowerCase() + "%"));
		}
		if (updatedAt != null) {
			predicates.add(cb.like(cb.lower(notifi.get("updatedAt")), "%" + updatedAt.toLowerCase() + "%"));
		}

		if (userId != null) {
			predicates.add(cb.equal(notifi.get("users").get("id"), userId));
		}
		if (senderId != null) {
			predicates.add(cb.equal(notifi.get("senders").get("id"), senderId));
		}
		if (userId != null) {
			predicates.add(cb.equal(notifi.get("state"), state));
		}

		if (pageable.getSort() != null) {
			List<Order> orders = new ArrayList<>();
			for (Sort.Order order : pageable.getSort()) {
				orders.add(order.isAscending() ? cb.asc(notifi.get(order.getProperty()))
						: cb.desc(notifi.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}

}
