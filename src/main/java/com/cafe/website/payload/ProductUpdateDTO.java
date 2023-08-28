package com.cafe.website.payload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductUpdateDTO {
	private int id;

	// @NotNull
	private List<Integer> area_id;

	// @NotNull
	private List<Integer> kind_id;

	// @NotNull
	private List<Integer> convenience_id;

	// @NotNull
	private List<Integer> purpose_id;

	// @NotNull
	private String name;

	// @NotNull
	private String slug;

	private String phone;

	@Min(value = 0, message = "status should not be less than 0")
	@Max(value = 1, message = "status should not be greater than 1")
	private int status;

	@Min(value = 0, message = "priceMin should not be less than 0")
	private int priceMin;

	@Min(value = 0, message = "priceMax should not be less than 0")
	private int priceMax;

	@Min(value = 0, message = "status should not be less than 0")
	@Max(value = 1, message = "status should not be greater than 1")
	private int outstanding;

	private List<MultipartFile> listMenuFile;
	private List<MultipartFile> listImageFile;

	// @NotNull
	private String coordinates;

	// @NotNull
	private String description;

	// @NotNull
	private String location;

	private String email;
	private String facebook;

	

	public ProductUpdateDTO(int id, List<Integer> area_id, List<Integer> kind_id, List<Integer> convenience_id,
			List<Integer> purpose_id, String name, String slug, String phone,
			@Min(value = 0, message = "status should not be less than 0") @Max(value = 1, message = "status should not be greater than 1") int status,
			@Min(value = 0, message = "priceMin should not be less than 0") int priceMin,
			@Min(value = 0, message = "priceMax should not be less than 0") int priceMax,
			@Min(value = 0, message = "status should not be less than 0") @Max(value = 1, message = "status should not be greater than 1") int outstanding,
			List<MultipartFile> listMenuFile, List<MultipartFile> listImageFile, String coordinates, String description,
			String location, String email, String facebook) {
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
		this.listMenuFile = listMenuFile;
		this.listImageFile = listImageFile;
		this.coordinates = coordinates;
		this.description = description;
		this.location = location;
		this.email = email;
		this.facebook = facebook;
	}

	public ProductUpdateDTO() {
		// TODO Auto-generated constructor stub
		this.status = 1;

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

	public List<MultipartFile> getListMenuFile() {
		return listMenuFile;
	}

	public void setListMenuFile(List<MultipartFile> listMenuFile) {
		this.listMenuFile = listMenuFile;
	}

	public List<MultipartFile> getListImageFile() {
		return listImageFile;
	}

	public void setListImageFile(List<MultipartFile> listImageFile) {
		this.listImageFile = listImageFile;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
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
