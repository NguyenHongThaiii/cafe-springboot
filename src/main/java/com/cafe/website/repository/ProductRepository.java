package com.cafe.website.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.ProductSchedule;
import com.cafe.website.entity.Rating;
import com.cafe.website.entity.Review;
import com.cafe.website.serviceImp.ProductServiceImp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {
	static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

	Optional<Product> findBySlugOrName(String slug, String name);

	Optional<Product> findByIdAndUserId(Long productId, Long userId);

	Boolean existsBySlugAndIdNot(String slug, Long id);

	Boolean existsByNameAndIdNot(String name, Long id);

	Boolean existsByIdAndUserId(Long productId, Long userId);

	Boolean existsBySlug(String slug);

	Boolean existsByName(String name);

	Long countByStatus(Integer status);

	@Query
	default List<Product> findWithFilters(String name, Integer status, String slugArea, String slugConvenience,
			String slugKind, String slugPurpose, Boolean isWatingDelete, Double latitude, Double longitude, Long userId,
			Float ratingsAverage, Integer outstanding, String createdAt, String updatedAt, String timeStatus,
			Pageable pageable, EntityManager entityManager) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> product = cq.from(Product.class);
		List<Predicate> predicates = new ArrayList<>();
		List<Order> orders = new ArrayList<>();

		if (name != null) {
			predicates.add(cb.like(cb.lower(product.get("name")), "%" + name.toLowerCase() + "%"));
		}
		if (createdAt != null) {
			predicates.add(cb.like(cb.lower(product.get("createdAt")), "%" + createdAt.toLowerCase() + "%"));
		}
		if (updatedAt != null) {
			predicates.add(cb.like(cb.lower(product.get("updatedAt")), "%" + updatedAt.toLowerCase() + "%"));
		}
		if (isWatingDelete != null) {
			predicates.add(cb.equal(product.get("isWaitingDelete"), isWatingDelete));
		}
		if (slugArea != null) {
			predicates.add(cb.equal(product.get("areas").get("slug"), slugArea));
		}
		if (slugConvenience != null) {
			predicates.add(cb.equal(product.get("conveniences").get("slug"), slugConvenience));
		}
		if (slugKind != null) {
			predicates.add(cb.equal(product.get("kinds").get("slug"), slugKind));
		}
		if (userId != null) {
			predicates.add(cb.equal(product.get("user").get("id"), userId));
		}
		if (slugPurpose != null) {
			predicates.add(cb.equal(product.get("purposes").get("slug"), slugPurpose));
		}
		if (latitude != null && longitude != null && latitude instanceof Double && longitude instanceof Double) {
			Expression<Double> distanceSort = cb.sqrt(cb.sum(
					cb.prod(cb.diff(product.get("latitude"), latitude), cb.diff(product.get("latitude"), latitude)),
					cb.prod(cb.diff(product.get("longitude"), longitude),
							cb.diff(product.get("longitude"), longitude))));
			orders.add(cb.desc(distanceSort));
		}
		if (status != null) {
			predicates.add(cb.equal(product.get("status"), status));

		}
		if (outstanding != null) {
			predicates.add(cb.equal(product.get("outstanding"), outstanding));

		}
		if (ratingsAverage != null) {
			Join<Product, Review> review = product.join("reviews");
			Join<Review, Rating> rating = review.join("rating");
			Expression<Float> sumOfRatings = cb
					.sum(cb.sum(cb.sum(cb.sum(rating.get("location"), rating.get("space")), rating.get("food")),
							rating.get("service")), rating.get("price"));
			Expression<Number> calculatedAverage = cb.quot(sumOfRatings, 5.0f);
			predicates.add(cb.equal(calculatedAverage, ratingsAverage));
		}
		if (timeStatus != null && !timeStatus.isEmpty()) {
			Join<Product, ProductSchedule> productScheduleJoin = product.join("schedules");

			int currentDayOfWeek = LocalDate.now().getDayOfWeek().getValue();
			int currentTimeInSeconds = LocalTime.now().toSecondOfDay();

			if ("open".equals(timeStatus)) {
				Predicate isOpenNow = cb.and(cb.equal(productScheduleJoin.get("dayOfWeek"), currentDayOfWeek),
						cb.lessThanOrEqualTo(productScheduleJoin.get("startTime").as(Integer.class),
								currentTimeInSeconds),
						cb.greaterThanOrEqualTo(productScheduleJoin.get("endTime").as(Integer.class),
								currentTimeInSeconds));
				predicates.add(isOpenNow);
			} else if ("close".equals(timeStatus)) {
				Predicate isClosedNow = cb.or(cb.notEqual(productScheduleJoin.get("dayOfWeek"), currentDayOfWeek),
						cb.lessThan(productScheduleJoin.get("endTime").as(Integer.class), currentTimeInSeconds),
						cb.greaterThan(productScheduleJoin.get("startTime").as(Integer.class), currentTimeInSeconds));
				predicates.add(isClosedNow);
				logger.info("timeStatus Time Seconds: " + currentTimeInSeconds);
				logger.info("timeStatus Day: " + currentDayOfWeek);
			}
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
