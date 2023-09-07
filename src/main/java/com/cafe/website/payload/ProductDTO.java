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
	private List<PurposeDTO> purposesDto = new ArrayList<>();
	private List<AreaDTO> areasDto = new ArrayList<>();
	private List<KindDTO> kindsDto = new ArrayList<>();
	private List<ConvenienceDTO> conveniencesDto = new ArrayList<>();
	private List<ProductScheduleDTO> schedules = new ArrayList<>();
	private String name;
	private String slug;
	private String phone;
	private int priceMin;
	private int priceMax;
	private int outstanding;
	private Integer latitude;
	private Integer longitude;
	private String email;
	private String facebook;
	private String description;
	private String location;
	private List<ImageDTO> listImage = new ArrayList<>();
	private UserDTO owner;
	private Integer isWaitingDelete;
	private double avgRating;

	public ProductDTO(int id, Integer status, Long createdAt, Long updatedAt, List<PurposeDTO> purposesDto,
			List<AreaDTO> areasDto, List<KindDTO> kindsDto, List<ConvenienceDTO> conveniencesDto,
			List<ProductScheduleDTO> schedules, String name, String slug, String phone, int priceMin, int priceMax,
			int outstanding, Integer latitude, Integer longitude, String email, String facebook, String description,
			String location, List<ImageDTO> listImage, UserDTO owner, Integer isWaitingDelete, double avgRating) {
		super(id, status, createdAt, updatedAt);
		this.purposesDto = purposesDto;
		this.areasDto = areasDto;
		this.kindsDto = kindsDto;
		this.conveniencesDto = conveniencesDto;
		this.schedules = schedules;
		this.name = name;
		this.slug = slug;
		this.phone = phone;
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
		this.avgRating = avgRating;
	}

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
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

	public List<PurposeDTO> getPurposesDto() {
		return purposesDto;
	}

	public void setPurposesDto(List<PurposeDTO> purposesDto) {
		this.purposesDto = purposesDto;
	}

	public List<AreaDTO> getAreasDto() {
		return areasDto;
	}

	public void setAreasDto(List<AreaDTO> areasDto) {
		this.areasDto = areasDto;
	}

	public List<KindDTO> getKindsDto() {
		return kindsDto;
	}

	public void setKindsDto(List<KindDTO> kindsDto) {
		this.kindsDto = kindsDto;
	}

	public List<ConvenienceDTO> getConveniencesDto() {
		return conveniencesDto;
	}

	public void setConveniencesDto(List<ConvenienceDTO> conveniencesDto) {
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

	public Integer getLatitude() {
		return latitude;
	}

	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
	}

	public Integer getLongitude() {
		return longitude;
	}

	public void setLongitude(Integer longitude) {
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

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

}
