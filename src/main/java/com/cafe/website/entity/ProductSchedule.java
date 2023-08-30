package com.cafe.website.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "schedules")
@Entity
public class ProductSchedule extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	private Long dayOfWeek;
	private Long startTime;
	private Long endTime;

	public ProductSchedule() {

	}

	public ProductSchedule(int id, int status, Long createdAt, Long updatedAt, Product product, Long dayOfWeek,
			Long startTime, Long endTime) {
		super(id, status, createdAt, updatedAt);
		this.product = product;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(Long dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

}
