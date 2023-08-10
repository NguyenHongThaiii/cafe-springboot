package com.cafe.website.payload;

import java.util.List;

import com.cafe.website.entity.Area;
import com.cafe.website.entity.Convenience;
import com.cafe.website.entity.Kind;
import com.cafe.website.entity.ProductSchedule;
import com.cafe.website.entity.Purpose;
import com.cafe.website.entity.Review;

public class ProductDTO {
	private List<Purpose> purposes;
	private List<Area> areas;
	private List<Kind> kinds;
	private List<Convenience> conveniences;
	private List<Review> reviews;
	private List<ProductSchedule> schedules;
	private int id;
	private String name;
	private String slug;
	private String phone;
	private int status;
	private int priceMin;
	private int priceMax;
	private int outstanding;
	private String listMenu;
	private String listImage;
	private String coordinates;
	private String email;
	private String facebook;

	public ProductDTO(List<Purpose> purposes, List<Area> areas, List<Kind> kinds, List<Convenience> conveniences,
			List<Review> reviews, List<ProductSchedule> schedules, int id, String name, String slug, String phone,
			int status, int priceMin, int priceMax, int outstanding, String listMenu, String coordinates,
			String email, String facebook) {
		super();
		this.purposes = purposes;
		this.areas = areas;
		this.kinds = kinds;
		this.conveniences = conveniences;
		this.reviews = reviews;
		this.schedules = schedules;
		this.id = id;
		this.name = name;
		this.slug = slug;
		this.phone = phone;
		this.status = status;
		this.priceMin = priceMin;
		this.priceMax = priceMax;
		this.outstanding = outstanding;
		this.listMenu = listMenu;
		this.coordinates = coordinates;
		this.email = email;
		this.facebook = facebook;
	}

	public ProductDTO() {
		super();
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
		return "ProductDTO [purposes=" + purposes + ", areas=" + areas + ", kinds=" + kinds + ", conveniences="
				+ conveniences + ", reviews=" + reviews + ", schedules=" + schedules + ", id=" + id + ", name=" + name
				+ ", slug=" + slug + ", phone=" + phone + ", status=" + status + ", priceMin=" + priceMin
				+ ", priceMax=" + priceMax + ", outstanding=" + outstanding + ", listMenu=" + listMenu
				+ ", coordinates=" + coordinates + ", email=" + email + ", facebook=" + facebook + "]";
	}

}
