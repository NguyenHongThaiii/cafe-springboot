package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductUpdateDTO {
	@NotNull
	private List<Long> area_id = new ArrayList<>();

	@NotNull
	private List<Long> kind_id = new ArrayList<>();

	@NotNull
	private List<Long> convenience_id = new ArrayList<>();

	@NotNull
	private List<Long> purpose_id = new ArrayList<>();

	@NotNull
	private String name;

	private String slug;

	private String phone;

	@Min(value = 0, message = "status should not be less than 0")
	@Max(value = 1, message = "status should not be greater than 1")
	private Integer status;

	@Min(value = 0, message = "priceMin should not be less than 0")
	private int priceMin;

	@Min(value = 0, message = "priceMax should not be less than 0")
	private int priceMax;

	@Min(value = 0, message = "status should not be less than 0")
	@Max(value = 1, message = "status should not be greater than 1")
	private int outstanding;

	private List<MultipartFile> listMenuFile = new ArrayList<>();
	private List<MultipartFile> listImageFile = new ArrayList<>();

	@NotNull
	private String description;

	@NotNull
	private String location;
	private Double latitude;
	private Double longitude;
	private String email;
	private String facebook;
	private String listScheduleDto;
	@NotNull
	private Long userId;
	@JsonIgnoreProperties(ignoreUnknown = true)
	private List<FileMetadata> listFileMetadatas = new ArrayList<>();

	public ProductUpdateDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductUpdateDTO(@NotNull List<Long> area_id, @NotNull List<Long> kind_id,
			@NotNull List<Long> convenience_id, @NotNull List<Long> purpose_id, @NotNull String name,
			@NotNull String slug, String phone,
			@Min(value = 0, message = "status should not be less than 0") @Max(value = 1, message = "status should not be greater than 1") Integer status,
			@Min(value = 0, message = "priceMin should not be less than 0") int priceMin,
			@Min(value = 0, message = "priceMax should not be less than 0") int priceMax,
			@Min(value = 0, message = "status should not be less than 0") @Max(value = 1, message = "status should not be greater than 1") int outstanding,
			List<MultipartFile> listMenuFile, List<MultipartFile> listImageFile, @NotNull String description,
			@NotNull String location,Double latitude, Double longitude, String email, String facebook,
			String listScheduleDto, Long userId, List<FileMetadata> listFileMetadatas) {
		super();
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
		this.description = description;
		this.location = location;
		this.latitude = latitude;
		this.longitude = longitude;
		this.email = email;
		this.facebook = facebook;
		this.listScheduleDto = listScheduleDto;
		this.userId = userId;
		this.listFileMetadatas = listFileMetadatas;
	}

	public List<Long> getArea_id() {
		return area_id;
	}

	public void setArea_id(List<Long> area_id) {
		this.area_id = area_id;
	}

	public List<Long> getKind_id() {
		return kind_id;
	}

	public void setKind_id(List<Long> kind_id) {
		this.kind_id = kind_id;
	}

	public List<Long> getConvenience_id() {
		return convenience_id;
	}

	public void setConvenience_id(List<Long> convenience_id) {
		this.convenience_id = convenience_id;
	}

	public List<Long> getPurpose_id() {
		return purpose_id;
	}

	public void setPurpose_id(List<Long> purpose_id) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public String getListScheduleDto() {
		return listScheduleDto;
	}

	public void setListScheduleDto(String listScheduleDto) {
		this.listScheduleDto = listScheduleDto;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<FileMetadata> getListFileMetadatas() {
		return listFileMetadatas;
	}

	public void setListFileMetadatas(List<FileMetadata> listFileMetadatas) {
		this.listFileMetadatas = listFileMetadatas;
	}

}
