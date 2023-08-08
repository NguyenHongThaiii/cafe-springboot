package com.cafe.website.payload;

import java.util.List;

public class ProductUpdateDTO {
	private int id;

	private List<Integer> area_id;
	private List<Integer> kind_id;
	private List<Integer> convenience_id;
	private List<Integer> purpose_id;
	private String name;
	private String slug;
	private String phone;
	private int status;
	private int priceMin;
	private int priceMax;
	private int outstanding;
	private String listMenu;
	private String coordinates;
	private String email;
	private String facebook;

	public ProductUpdateDTO(int id, List<Integer> area_id, List<Integer> kind_id, List<Integer> convenience_id,
			List<Integer> purpose_id, String name, String slug, String phone, int status, int priceMin, int priceMax,
			int outstanding, String listMenu, String coordinates, String email, String facebook) {
		super();
		this.id = id;
		this.area_id = area_id;
		this.kind_id = kind_id;
		this.convenience_id = convenience_id;
		this.purpose_id = purpose_id;
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

	public ProductUpdateDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Integer> getArea_id() {
		return area_id;
	}

	public void setArea_id(List<Integer> area_id) {
		this.area_id = area_id;
	}

	public List<Integer> getKind_id() {
		return kind_id;
	}

	public void setKind_id(List<Integer> kind_id) {
		this.kind_id = kind_id;
	}

	public List<Integer> getConvenience_id() {
		return convenience_id;
	}

	public void setConvenience_id(List<Integer> convenience_id) {
		this.convenience_id = convenience_id;
	}

	public List<Integer> getPurpose_id() {
		return purpose_id;
	}

	public void setPurpose_id(List<Integer> purpose_id) {
		this.purpose_id = purpose_id;
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

}
