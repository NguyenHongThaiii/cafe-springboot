package com.cafe.website.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

	private String name;
	private String slug;
	private String phone;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "products_purposes", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "purpose_id", referencedColumnName = "id"))
	private List<Purpose> purposes;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "products_areas", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "area_id", referencedColumnName = "id"))
	private List<Area> areas;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "products_kinds", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "kind_id", referencedColumnName = "id"))
	private List<Kind> kinds;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "products_conveniences", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "convenience_id", referencedColumnName = "id"))
	private List<Convenience> conveniences;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Review> reviews;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ProductSchedule> schedules;

	private int priceMin;
	private int priceMax;
	private int outstanding;
	private String listMenu;
	private String listImage;
	private String coordinates;
	private String email;
	private String facebook;

	public Product(int id, int status, Long createdAt, Long updatedAt, String name, String slug, String phone,
			List<Purpose> purposes, List<Area> areas, List<Kind> kinds, List<Convenience> conveniences,
			List<Review> reviews, List<ProductSchedule> schedules, int priceMin, int priceMax, int outstanding,
			String listMenu, String coordinates, String email, String facebook, String listImage) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.slug = slug;
		this.phone = phone;
		this.purposes = purposes;
		this.areas = areas;
		this.kinds = kinds;
		this.conveniences = conveniences;
		this.reviews = reviews;
		this.schedules = schedules;
		this.priceMin = priceMin;
		this.priceMax = priceMax;
		this.outstanding = outstanding;
		this.listMenu = listMenu;
		this.coordinates = coordinates;
		this.email = email;
		this.facebook = facebook;
		this.listImage = listImage;
	}

	public Product(int id, int status, Long createdAt, Long updatedAt) {
		super(id, status, createdAt, updatedAt);
	}

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Purpose> getPurposes() {
		return purposes;
	}

	public void setPurposes(List<Purpose> purposes) {
		this.purposes = purposes;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public List<Kind> getKinds() {
		return kinds;
	}

	public void setKinds(List<Kind> kinds) {
		this.kinds = kinds;
	}

	public List<Convenience> getConveniences() {
		return conveniences;
	}

	public void setConveniences(List<Convenience> conveniences) {
		this.conveniences = conveniences;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<ProductSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ProductSchedule> schedules) {
		this.schedules = schedules;
	}

	public int getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(int priceMin) {
		this.priceMin = priceMin;
	}

	public int getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(int priceMax) {
		this.priceMax = priceMax;
	}

	public int getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(int outstanding) {
		this.outstanding = outstanding;
	}

	public String getListMenu() {
		return listMenu;
	}

	public void setListMenu(String listMenu) {
		this.listMenu = listMenu;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getEmail() {
		return email;
	}

	public String getListImage() {
		return listImage;
	}

	public void setListImage(String listImage) {
		this.listImage = listImage;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", slug=" + slug + ", phone=" + phone + ", purposes=" + purposes + ", areas="
				+ areas + ", kinds=" + kinds + ", conveniences=" + conveniences + ", reviews=" + reviews
				+ ", schedules=" + schedules + ", priceMin=" + priceMin + ", priceMax=" + priceMax + ", outstanding="
				+ outstanding + ", listMenu=" + listMenu + ", coordinates=" + coordinates + ", email=" + email
				+ ", facebook=" + facebook + "]";
	}

}
