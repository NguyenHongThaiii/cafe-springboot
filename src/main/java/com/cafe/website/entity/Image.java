package com.cafe.website.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
public class Image extends BaseEntity {
	private String image;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menu;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	private Area area;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "kind_id")
	private Kind kind;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "convenience_id")
	private Convenience convenience;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "purpose_id")
	private Purpose purpose;

	public Image() {
		// TODO Auto-generated constructor stub
	}

	public Image(int id, int status, String createdAt, String updatedAt, String image, User user, Menu menu,
			Product product, Review review, Area area, Kind kind, Convenience convenience, Purpose purpose) {
		super(id, status, createdAt, updatedAt);
		this.image = image;
		this.user = user;
		this.menu = menu;
		this.product = product;
		this.review = review;
		this.area = area;
		this.kind = kind;
		this.convenience = convenience;
		this.purpose = purpose;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public Convenience getConvenience() {
		return convenience;
	}

	public void setConvenience(Convenience convenience) {
		this.convenience = convenience;
	}

	public Purpose getPurpose() {
		return purpose;
	}

	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}

}
