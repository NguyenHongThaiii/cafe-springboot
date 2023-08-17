package com.cafe.website.payload;

import java.util.List;

import com.cafe.website.entity.Area;
import com.cafe.website.entity.BaseEntity;
import com.cafe.website.entity.Convenience;
import com.cafe.website.entity.Kind;
import com.cafe.website.entity.ProductSchedule;
import com.cafe.website.entity.Purpose;
import com.cafe.website.entity.Review;

public class ProductDTO extends BaseEntity {
	private List<Purpose> purposes;
	private List<Area> areas;
	private List<Kind> kinds;
	private List<Convenience> conveniences;
	private List<ProductSchedule> schedules;
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
	private String description;
	private String location;

	public ProductDTO(int id, int status, Long createdAt, Long updatedAt, List<Purpose> purposes, List<Area> areas,
			List<Kind> kinds, List<Convenience> conveniences, List<Review> reviews, List<ProductSchedule> schedules,
			String name, String slug, String phone, int status2, int priceMin, int priceMax, int outstanding,
			String listMenu, String listImage, String coordinates, String email, String facebook, String description,
			String location) {
		super(id, status, createdAt, updatedAt);
		this.purposes = purposes;
		this.areas = areas;
		this.kinds = kinds;
		this.conveniences = conveniences;
		this.schedules = schedules;
		this.name = name;
		this.slug = slug;
		this.phone = phone;
		status = status2;
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

	public ProductDTO() {
		// TODO Auto-generated constructor stub
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

	public List<ProductSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ProductSchedule> schedules) {
		this.schedules = schedules;
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
