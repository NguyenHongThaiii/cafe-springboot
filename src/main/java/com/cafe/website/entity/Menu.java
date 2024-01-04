package com.cafe.website.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "menus")
public class Menu extends BaseEntity {
	@OneToOne(mappedBy = "menu", cascade = CascadeType.ALL)
	private Image image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	public Menu() {
		// TODO Auto-generated constructor stub
	}

	public Menu(Long id, int status, String createdAt, String updatedAt, Image image, Product product) {
		super(id, status, createdAt, updatedAt);
		this.image = image;
		this.product = product;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
