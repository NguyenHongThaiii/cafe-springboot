package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Convenience;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface ConvenienceRepository extends JpaRepository<Convenience, Integer> {

	Boolean existsBySlugAndIdNot(String slug, Integer id);

	Boolean existsBySlug(String slug);

	Boolean existsByName(String name);

	Boolean existsByNameAndIdNot(String name, Integer id);

	Optional<Convenience> findByName(String name);

	Optional<Convenience> findBySlug(String slug);

	@Query
	default List<Convenience> findWithFilters(String name, String slug, Pageable pageable,
			EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Convenience> cq = cb.createQuery(Convenience.class);

		Root<Convenience> convenience = cq.from(Convenience.class);
		List<Predicate> predicates = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(convenience.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (slug != null) {
			predicates.add(cb.equal(convenience.get("slug"), slug));
		}
		if (pageable.getSort() != null) {
			List<Order> orders = new ArrayList<>();
			for (Sort.Order order : pageable.getSort()) {
				orders.add(order.isAscending() ? cb.asc(convenience.get(order.getProperty()))
						: cb.desc(convenience.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}
}
