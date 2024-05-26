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
import com.cafe.website.entity.Kind;
import com.cafe.website.entity.Purpose;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface PurposeRepository extends JpaRepository<Purpose, Long> {

	Boolean existsBySlugAndIdNot(String slug, Long id);

	Boolean existsByNameAndIdNot(String name, Long id);

	Boolean existsBySlug(String slug);

	Boolean existsByName(String name);

	Optional<Purpose> findByName(String name);

	Optional<Purpose> findBySlug(String slug);

	@Query
	default List<Purpose> findWithFilters(String name, String slug, String createdAt, String updatedAt,
			Pageable pageable, EntityManager entityManager) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Purpose> cq = cb.createQuery(Purpose.class);
		List<Order> orders = new ArrayList<>();
		Root<Purpose> kind = cq.from(Purpose.class);
		List<Predicate> predicates = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(kind.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			predicates.add(cb.like(cb.lower(kind.get("createdAt")), "%" + createdAt.toLowerCase() + "%"));
		}
		if (updatedAt != null) {
			predicates.add(cb.like(cb.lower(kind.get("updatedAt")), "%" + updatedAt.toLowerCase() + "%"));
		}
		if (slug != null) {
			predicates.add(cb.equal(kind.get("slug"), slug));
		}
		if (pageable != null && pageable.getSort() != null) {
			for (Sort.Order order : pageable.getSort()) {

				orders.add(order.isAscending() ? cb.asc(kind.get(order.getProperty()))
						: cb.desc(kind.get(order.getProperty())));
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
