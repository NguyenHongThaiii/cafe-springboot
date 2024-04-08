package com.cafe.website.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.ProductSaved;
import com.cafe.website.entity.ProductSchedule;
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
import jakarta.transaction.Transactional;

public interface ProductSavedRepository extends JpaRepository<ProductSaved, Long> {
	Boolean existsByUserIdAndProductId(Long userId, Long productId);

	@Transactional
	@Modifying
	@Query("DELETE FROM ProductSaved p WHERE p.product.id = :productId AND p.user.id = :userId")
	void deleteByUserIdAndProductId(Long userId, Long productId);

	List<ProductSaved> findAllByUserId(Long userId);

	@Query
	default List<ProductSaved> findWithFilters(Integer status, String slugArea, String slugKind, String slugConvenience,
			String slugPurpose, Long userId, String createdAt, String updatedAt, Pageable pageable,
			EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProductSaved> cq = cb.createQuery(ProductSaved.class);
		Root<ProductSaved> product = cq.from(ProductSaved.class);
		List<Predicate> predicates = new ArrayList<>();
		List<Order> orders = new ArrayList<>();

		if (createdAt != null) {
			predicates.add(cb.like(cb.lower(product.get("createdAt")), "%" + createdAt.toLowerCase() + "%"));
		}
		if (updatedAt != null) {
			predicates.add(cb.like(cb.lower(product.get("updatedAt")), "%" + updatedAt.toLowerCase() + "%"));
		}

		if (userId != null) {
			predicates.add(cb.equal(product.get("user").get("id"), userId));
		}
		if (slugArea != null) {
			predicates.add(cb.equal(product.get("product").get("areas").get("slug"), slugArea));
		}
		if (slugConvenience != null) {
			predicates.add(cb.equal(product.get("product").get("conveniences").get("slug"), slugConvenience));
		}
		if (slugKind != null) {
			predicates.add(cb.equal(product.get("product").get("kinds").get("slug"), slugKind));
		}

		if (slugPurpose != null) {
			predicates.add(cb.equal(product.get("product").get("purposes").get("slug"), slugPurpose));
		}
		if (status != null) {
			predicates.add(cb.equal(product.get("status"), status));

		}

		if (pageable != null && pageable.getSort() != null) {
			for (Sort.Order order : pageable.getSort()) {

				orders.add(order.isAscending() ? cb.asc(product.get(order.getProperty()))
						: cb.desc(product.get(order.getProperty())));
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
