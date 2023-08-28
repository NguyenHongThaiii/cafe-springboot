package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Area;
import com.cafe.website.entity.Product;
import com.cafe.website.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query
	default List<User> findWithFilters(String name, String email, Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);

		Root<User> user = cq.from(User.class);
		List<Predicate> predicates = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(user.get("name")), "%" + name.toLowerCase() + "%"));
		}

		if (email != null) {
			predicates.add(cb.like(cb.lower(user.get("email")), "%" + email.toLowerCase() + "%"));
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

	Boolean existsBySlugAndIdNot(String slug, Integer id);

	Boolean existsByNameAndIdNot(String name, Integer id);

	void deleteUserBySlug(String slug);

}
