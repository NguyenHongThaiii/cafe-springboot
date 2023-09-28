package com.cafe.website.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Optional<Product> findBySlugOrName(String slug, String name);

	Optional<Product> findByIdAndUserId(Integer productId, Integer userId);

	Boolean existsBySlugAndIdNot(String slug, Integer id);

	Boolean existsByNameAndIdNot(String name, Integer id);

	Boolean existsByIdAndUserId(Integer productId, Integer userId);

	Boolean existsBySlug(String slug);

	Boolean existsByName(String name);

	@Transactional
	@Query
	default List<Product> findWithFilters(String name, int status, String slugArea, String slugConvenience,
			String slugKind, String slugPurpose, Boolean isWatingDelete, Double latitude, Double longitude,
			Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> product = cq.from(Product.class);
		List<Predicate> predicates = new ArrayList<>();
		List<Order> orders = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(product.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (isWatingDelete != null) {
			predicates.add(cb.equal(product.get("isWaitingDelete"), isWatingDelete));
		}
		if (slugArea != null) {
			predicates.add(cb.equal(product.get("areas").get("slug"), slugArea));
		}
		if (slugConvenience != null) {
			predicates.add(cb.equal(product.get("conveniences").get("slug"), slugArea));
		}
		if (slugKind != null) {
			predicates.add(cb.equal(product.get("kinds").get("slug"), slugArea));
		}
		if (slugPurpose != null) {
			predicates.add(cb.equal(product.get("purposes").get("slug"), slugArea));
		}
		if (latitude != null && longitude != null && latitude instanceof Double && longitude instanceof Double) {
			Expression<Double> distanceSort = cb.sqrt(cb.sum(
					cb.prod(cb.diff(product.get("latitude"), latitude), cb.diff(product.get("latitude"), latitude)),
					cb.prod(cb.diff(product.get("longitude"), longitude),
							cb.diff(product.get("longitude"), longitude))));
			orders.add(cb.desc(distanceSort));
		}
		predicates.add(cb.equal(product.get("status"), status));

		if (pageable.getSort() != null) {
			for (Sort.Order order : pageable.getSort()) {

				orders.add(order.isAscending() ? cb.asc(product.get(order.getProperty()))
						: cb.desc(product.get(order.getProperty())));
			}
			cq.orderBy(orders);
		}
		cq.where(predicates.toArray(new Predicate[0]));

		return entityManager.createQuery(cq).setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize()).getResultList();
	}
}
