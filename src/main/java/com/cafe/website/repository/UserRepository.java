package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cafe.website.entity.Area;
import com.cafe.website.entity.Product;
import com.cafe.website.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query
	default List<User> findWithFilters(Integer status, String name, String email, String slug, String createdAt,
			String updatedAt, Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);

		Root<User> user = cq.from(User.class);
		List<Predicate> predicates = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(user.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			predicates.add(cb.like(cb.lower(user.get("createdAt")), "%" + createdAt.toLowerCase() + "%"));
		}
		if (updatedAt != null) {
			predicates.add(cb.like(cb.lower(user.get("updatedAt")), "%" + updatedAt.toLowerCase() + "%"));
		}

		if (email != null) {
			predicates.add(cb.like(cb.lower(user.get("email")), "%" + email.toLowerCase() + "%"));
		}
		if (slug != null) {
			predicates.add(cb.like(cb.lower(user.get("slug")), slug.toLowerCase()));
		}
		if (status != null) {
			predicates.add(cb.equal(user.get("status"), status));
		}
		if (pageable.getSort() != null) {
			List<Order> orders = new ArrayList<>();
			for (Sort.Order order : pageable.getSort()) {
				orders.add(order.isAscending() ? cb.asc(user.get(order.getProperty()))
						: cb.desc(user.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}

	Slice<Area> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	Boolean existsBySlug(String slug);

	Optional<User> findByName(String name);

	Optional<User> findBySlug(String slug);

	Boolean existsByName(String email);

	Boolean existsBySlugAndIdNot(String slug, Long id);

	Boolean existsByNameAndIdNot(String name, Long id);

	void deleteUserBySlug(String slug);

}
