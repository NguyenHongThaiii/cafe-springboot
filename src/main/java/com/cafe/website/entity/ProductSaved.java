package com.cafe.website.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "product_saved", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "product_id" }))
public class ProductSaved extends BaseEntity {
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	public ProductSaved() {
		// TODO Auto-generated constructor stub
	}

	public ProductSaved(Long id, int status, String createdAt, String updatedAt, User user, Product product) {
		super(id, status, createdAt, updatedAt);
		this.user = user;
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
