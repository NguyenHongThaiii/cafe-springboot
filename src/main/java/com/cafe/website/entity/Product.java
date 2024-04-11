package com.cafe.website.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
	@Column(unique = true, nullable = false)
	private String name;
	@Column(unique = true, nullable = false)
	private String slug;

	private String phone;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "products_purposes", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "purpose_id", referencedColumnName = "id"))
	private List<Purpose> purposes = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "products_areas", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "area_id", referencedColumnName = "id"))
	private List<Area> areas = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "products_kinds", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "kind_id", referencedColumnName = "id"))
	private List<Kind> kinds = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "products_conveniences", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "convenience_id", referencedColumnName = "id"))
	private List<Convenience> conveniences = new ArrayList<>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ProductSchedule> schedules = new ArrayList<>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Menu> listMenus = new ArrayList<>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Image> listImages = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REFRESH })
	private User user;

	private int priceMin;
	private int priceMax;
	private int outstanding;

	private String email;
	private String facebook;
	private Double latitude;
	private Double longitude;
	private String description;
	private String location;

	@Column(nullable = false, columnDefinition = "int default 0")
	private Boolean isWaitingDelete;

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(String name, String slug, String phone, List<Purpose> purposes, List<Area> areas, List<Kind> kinds,
			List<Convenience> conveniences, List<Review> reviews, List<ProductSchedule> schedules, List<Menu> listMenus,
			List<Image> listImages, User user, int priceMin, int priceMax, int outstanding, String email,
			String facebook, Double latitude, Double longitude, String description, String location,
			Boolean isWaitingDelete) {
		super();
		this.name = name;
		this.slug = slug;
		this.phone = phone;
		this.purposes = purposes;
		this.areas = areas;
		this.kinds = kinds;
		this.conveniences = conveniences;
		this.reviews = reviews;
		this.schedules = schedules;
		this.listMenus = listMenus;
		this.listImages = listImages;
		this.user = user;
		this.priceMin = priceMin;
		this.priceMax = priceMax;
		this.outstanding = outstanding;
		this.email = email;
		this.facebook = facebook;
		this.latitude = latitude;
		this.longitude = longitude;
		this.description = description;
		this.location = location;
		this.isWaitingDelete = isWaitingDelete;
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

	public List<Menu> getListMenus() {
		return listMenus;
	}

	public void setListMenus(List<Menu> listMenus) {
		this.listMenus = listMenus;
	}

	public List<Image> getListImages() {
		return listImages;
	}

	public void setListImages(List<Image> listImages) {
		this.listImages = listImages;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
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

	public Boolean getIsWaitingDelete() {
		return isWaitingDelete;
	}

	public void setIsWaitingDelete(Boolean isWaitingDelete) {
		this.isWaitingDelete = isWaitingDelete;
	}

}
