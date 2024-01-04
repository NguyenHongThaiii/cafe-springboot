package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Log;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public interface LogRepository extends JpaRepository<Log, Long> {
	@Query
	default List<Log> findWithFilters(Integer status, String method, Long userId, String message, String agent,
			String result, String params, String body, String endpoint, String action, String createdAt,
			String updatedAt, Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Log> cq = cb.createQuery(Log.class);
		Root<Log> logger = cq.from(Log.class);
		List<Predicate> predicates = new ArrayList<>();
		List<Order> orders = new ArrayList<>();

		if (method != null) {
			predicates.add(cb.like(cb.lower(logger.get("method")), "%" + method.toLowerCase() + "%"));
		}
		if (action != null) {
			predicates.add(cb.like(cb.lower(logger.get("action")), "%" + action.toLowerCase() + "%"));
		}
		if (message != null) {
			predicates.add(cb.like(cb.lower(logger.get("message")), "%" + message.toLowerCase() + "%"));
		}
		if (agent != null) {
			predicates.add(cb.like(cb.lower(logger.get("agent")), "%" + agent.toLowerCase() + "%"));
		}
		if (result != null) {
			predicates.add(cb.like(cb.lower(logger.get("result")), "%" + result.toLowerCase() + "%"));
		}
		if (params != null) {
			predicates.add(cb.like(cb.lower(logger.get("params")), "%" + params.toLowerCase() + "%"));
		}
		if (body != null) {
			predicates.add(cb.like(cb.lower(logger.get("body")), "%" + body.toLowerCase() + "%"));
		}
		if (endpoint != null) {
			predicates.add(cb.like(cb.lower(logger.get("endpoint")), "%" + endpoint.toLowerCase() + "%"));
		}

		if (status != null) {
			predicates.add(cb.equal(logger.get("status"), status));
		}
		if (userId != null) {
			predicates.add(cb.equal(logger.get("user").get("id"), userId));
		}

		if (createdAt != null) {
			predicates.add(cb.like(cb.lower(logger.get("createdAt")), "%" + createdAt.toLowerCase() + "%"));

		}
		if (updatedAt != null) {
			predicates.add(cb.like(cb.lower(logger.get("updatedAt")), "%" + updatedAt.toLowerCase() + "%"));

		}
		if (pageable.getSort() != null) {
			for (Sort.Order order : pageable.getSort()) {

				orders.add(order.isAscending() ? cb.asc(logger.get(order.getProperty()))
						: cb.desc(logger.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}
}
