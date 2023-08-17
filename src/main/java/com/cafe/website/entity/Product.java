package com.cafe.website.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

	@Column(unique = true, nullable = false)
	private String name;
	@Column(unique = true, nullable = false)
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

	private String description;
	private String location;

	public Product(int id, int status, Long createdAt, Long updatedAt, String name, String slug, String phone,
			List<Purpose> purposes, List<Area> areas, List<Kind> kinds, List<Convenience> conveniences,
			List<Review> reviews, List<ProductSchedule> schedules, int priceMin, int priceMax, int outstanding,
			String listMenu, String listImage, String coordinates, String email, String facebook, String description,
			String location) {
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
		this.listImage = listImage;
		this.coordinates = coordinates;
		this.email = email;
		this.facebook = facebook;
		this.description = description;
		this.location = location;
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

	public String getListImage() {
		return listImage;
	}

	public void setListImage(String listImage) {
		this.listImage = listImage;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
