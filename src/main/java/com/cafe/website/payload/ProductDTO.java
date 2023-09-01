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
	private List<Purpose> purposesDto = new ArrayList<>();
	private List<AreaDTO> areasDto = new ArrayList<>();
	private List<Kind> kindsDto = new ArrayList<>();
	private List<Convenience> conveniencesDto = new ArrayList<>();
	private List<ProductScheduleDTO> schedules = new ArrayList<>();
	private String name;
	private String slug;
	private String phone;
	private int status;
	private int priceMin;
	private int priceMax;
	private int outstanding;
	private String latitude;
	private String longitude;

	private String email;
	private String facebook;
	private String description;
	private String location;
	private List<ImageDTO> listImage = new ArrayList<>();
	private UserDTO owner;
	private Integer isWaitingDelete;

	public ProductDTO(int id, Integer status, Long createdAt, Long updatedAt, List<Purpose> purposesDto,
			List<AreaDTO> areasDto, List<Kind> kindsDto, List<Convenience> conveniencesDto,
			List<ProductScheduleDTO> schedules, String name, String slug, String phone, int status2, int priceMin,
			int priceMax, int outstanding, String latitude, String longitude, String email, String facebook,
			String description, String location, List<ImageDTO> listImage, UserDTO owner, Integer isWaitingDelete) {
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
		this.latitude = latitude;
		this.longitude = longitude;
		this.email = email;
		this.facebook = facebook;
		this.description = description;
		this.location = location;
		this.listImage = listImage;
		this.owner = owner;
		this.isWaitingDelete = isWaitingDelete;
	}

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Integer getIsWaitingDelete() {
		return isWaitingDelete;
	}

	public void setIsWaitingDelete(Integer isWaitingDelete) {
		if (isWaitingDelete != 0 && isWaitingDelete != 1) {
			throw new IllegalArgumentException("Status can only be 1 or 0");
		}
		this.isWaitingDelete = isWaitingDelete;
	}

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public List<ProductScheduleDTO> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<ProductScheduleDTO> schedules) {
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
