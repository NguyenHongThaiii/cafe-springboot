package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.ProductDiscount;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface ProductDiscountRepository extends JpaRepository<ProductDiscount, Long> {
	Boolean existsByProductId(Long productId);

	Optional<ProductDiscount> findByProductId(Long productId);

	@Query
	default List<ProductDiscount> findWithFilters(String name, Boolean isExpired, Integer percent, String createdAt,
			String updatedAt, Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductDiscount> cq = cb.createQuery(ProductDiscount.class);

		Root<ProductDiscount> productDiscount = cq.from(ProductDiscount.class);
		List<Predicate> predicates = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(productDiscount.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			predicates.add(cb.like(cb.lower(productDiscount.get("createdAt")), "%" + createdAt.toLowerCase() + "%"));
		}
		if (updatedAt != null) {
			predicates.add(cb.like(cb.lower(productDiscount.get("updatedAt")), "%" + updatedAt.toLowerCase() + "%"));
		}
		if (percent != null) {
			predicates.add(cb.equal(productDiscount.get("percent"), percent));
		}
		if (isExpired != null && isExpired == false) {
			predicates.add(cb.greaterThan(productDiscount.get("expiryDate"), new Date().getTime()));
		}
		if (isExpired != null && isExpired == true) {
			predicates.add(cb.lessThan(productDiscount.get("expiryDate"), new Date().getTime()));
		}
		if (pageable.getSort() != null) {
			List<Order> orders = new ArrayList<>();
			for (Sort.Order order : pageable.getSort()) {
				orders.add(order.isAscending() ? cb.asc(productDiscount.get(order.getProperty()))
						: cb.desc(productDiscount.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}
}
