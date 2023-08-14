package com.cafe.website.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "owners")
public class ProductOwner extends BaseEntity {
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	private String name;

	public ProductOwner(int id, int status, Long createdAt, Long updatedAt, Product product, String name) {
		super(id, status, createdAt, updatedAt);
		this.product = product;
		this.name = name;
	}

	public ProductOwner() {
		// TODO Auto-generated constructor stub
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
