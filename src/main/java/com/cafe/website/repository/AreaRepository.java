package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Area;
import com.cafe.website.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface AreaRepository extends JpaRepository<Area, Long> {

	@Query
	default List<Area> findWithFilters(Integer status, String name, String slug, String createdAt, String updatedAt,
			Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Area> cq = cb.createQuery(Area.class);
		List<Order> orders = new ArrayList<>();
		Root<Area> area = cq.from(Area.class);
		List<Predicate> predicates = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(area.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			predicates.add(cb.like(cb.lower(area.get("createdAt")), "%" + createdAt.toLowerCase() + "%"));
		}
		if (updatedAt != null) {
			predicates.add(cb.like(cb.lower(area.get("updatedAt")), "%" + updatedAt.toLowerCase() + "%"));
		}
		if (slug != null) {
			predicates.add(cb.equal(area.get("slug"), slug));
		}
		if (status != null) {
			predicates.add(cb.equal(area.get("status"), status));
		}

		if (pageable != null && pageable.getSort() != null) {
			for (Sort.Order order : pageable.getSort()) {

				orders.add(order.isAscending() ? cb.asc(area.get(order.getProperty()))
						: cb.desc(area.get(order.getProperty())));
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

	Boolean existsBySlugAndIdNot(String slug, Long id);

	Boolean existsBySlug(String slug);

	Boolean existsByName(String name);

	Boolean existsByNameAndIdNot(String name, Long id);

	Optional<Area> findByName(String name);

	Optional<Area> findBySlug(String slug);

}
