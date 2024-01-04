package com.cafe.website.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "discounts")
public class ProductDiscount extends BaseEntity {

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id", nullable = false, unique = true)
	private Product product;
	private Long expiryDate;
	private Integer percent;
	private String name;

	public ProductDiscount(Long id, int status, String createdAt, String updatedAt, Product product, Long expiryDate,
			Integer percent, String name) {
		super(id, status, createdAt, updatedAt);
		this.product = product;
		this.expiryDate = expiryDate;
		this.percent = percent;
		this.name = name;
	}

	public ProductDiscount() {
		// TODO Auto-generated constructor stub
	}

	public Long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
