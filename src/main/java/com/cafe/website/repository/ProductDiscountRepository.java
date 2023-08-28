package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.ProductDiscount;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface ProductDiscountRepository extends JpaRepository<ProductDiscount, Integer> {
	Boolean existsByProductId(Integer productId);

	Optional<ProductDiscount> findByProductId(Integer productId);

	@Query
	default List<ProductDiscount> findWithFilters(String name, Boolean isExpired, Integer percent, Pageable pageable,
			EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductDiscount> cq = cb.createQuery(ProductDiscount.class);

		Root<ProductDiscount> productDiscount = cq.from(ProductDiscount.class);
		List<Predicate> predicates = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(productDiscount.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (percent != null) {
			predicates.add(cb.equal(productDiscount.get("percent"), percent));
		}
		if (isExpired == true) {
			predicates.add(cb.greaterThan(productDiscount.get("expireDays"), new Date().getTime()));
		}
		if (isExpired == false) {
			predicates.add(cb.lessThan(productDiscount.get("expireDays"), new Date().getTime()));
		}

		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}
}
