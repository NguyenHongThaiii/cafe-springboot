package com.cafe.website.payload;

import java.util.ArrayList;
import java.util.List;

import com.cafe.website.entity.Area;
import com.cafe.website.entity.BaseEntity;
import com.cafe.website.entity.Convenience;
import com.cafe.website.entity.Kind;
import com.cafe.website.entity.ProductSchedule;
import com.cafe.website.entity.Purpose;

public class ProductDTO extends BaseEntityDTO {
	private List<Purpose> purposesDto;
	private List<AreaDTO> areasDto;
	private List<Kind> kindsDto;
	private List<Convenience> conveniencesDto;
	private List<ProductSchedule> schedules;
	private String name;
	private String slug;
	private String phone;
	private int status;
	private int priceMin;
	private int priceMax;
	private int outstanding;
	private String coordinates;
	private String email;
	private String facebook;
	private String description;
	private String location;
	private List<ImageDTO> listImage = new ArrayList<>();

	public ProductDTO(int id, int status, Long createdAt, Long updatedAt, List<Purpose> purposesDto,
			List<AreaDTO> areasDto, List<Kind> kindsDto, List<Convenience> conveniencesDto,
			List<ProductSchedule> schedules, String name, String slug, String phone, int status2, int priceMin,
			int priceMax, int outstanding, String coordinates, String email, String facebook, String description,
			String location, List<ImageDTO> listImage) {
		super(id, status, createdAt, updatedAt);
		this.purposesDto = purposesDto;
		this.areasDto = areasDto;
		this.kindsDto = kindsDto;
		this.conveniencesDto = conveniencesDto;
		this.schedules = schedules;
		this.name = name;
		this.slug = slug;
		this.phone = phone;
		status = status2;
		this.priceMin = priceMin;
		this.priceMax = priceMax;
		this.outstanding = outstanding;
		this.coordinates = coordinates;
		this.email = email;
		this.facebook = facebook;
		this.description = description;
		this.location = location;
		this.listImage = listImage;
	}

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	public List<Purpose> getPurposesDto() {
		return purposesDto;
	}

	public void setPurposesDto(List<Purpose> purposesDto) {
		this.purposesDto = purposesDto;
	}

	public List<AreaDTO> getAreasDto() {
		return areasDto;
	}

	public void setAreasDto(List<AreaDTO> areasDto) {
		this.areasDto = areasDto;
	}

	public List<Kind> getKindsDto() {
		return kindsDto;
	}

	public void setKindsDto(List<Kind> kindsDto) {
		this.kindsDto = kindsDto;
	}

	public List<Convenience> getConveniencesDto() {
		return conveniencesDto;
	}

	public void setConveniencesDto(List<Convenience> conveniencesDto) {
		this.conveniencesDto = conveniencesDto;
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

	public List<ImageDTO> getListImage() {
		return listImage;
	}

	public void setListImage(List<ImageDTO> listImage) {
		this.listImage = listImage;
	}
}
