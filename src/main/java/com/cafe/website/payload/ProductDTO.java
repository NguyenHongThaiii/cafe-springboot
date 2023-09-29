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
	private List<PurposeDTO> purposes = new ArrayList<>();
	private List<AreaDTO> areas = new ArrayList<>();
	private List<KindDTO> kinds = new ArrayList<>();
	private List<ConvenienceDTO> conveniences = new ArrayList<>();
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
	private List<MenuDTO> listMenu = new ArrayList<>();
	private UserDTO owner;
	private Boolean isWaitingDelete;
	private float avgRating;

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	public ProductDTO(int id, Integer status, Long createdAt, Long updatedAt, List<PurposeDTO> purposes,
			List<AreaDTO> areas, List<KindDTO> kinds, List<ConvenienceDTO> conveniences,
			List<ProductScheduleDTO> schedules, String name, String slug, String phone, int priceMin, int priceMax,
			int outstanding, Integer latitude, Integer longitude, String email, String facebook, String description,
			String location, List<ImageDTO> listImage, List<MenuDTO> listMenu, UserDTO owner, Boolean isWaitingDelete,
			float avgRating) {
		super(id, status, createdAt, updatedAt);
		this.purposes = purposes;
		this.areas = areas;
		this.kinds = kinds;
		this.conveniences = conveniences;
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
		this.listMenu = listMenu;
		this.owner = owner;
		this.isWaitingDelete = isWaitingDelete;
		this.avgRating = avgRating;
	}

	public List<PurposeDTO> getPurposes() {
		return purposes;
	}

	public void setPurposes(List<PurposeDTO> purposes) {
		this.purposes = purposes;
	}

	public List<AreaDTO> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaDTO> areas) {
		this.areas = areas;
	}

	public List<KindDTO> getKinds() {
		return kinds;
	}

	public void setKinds(List<KindDTO> kinds) {
		this.kinds = kinds;
	}

	public List<ConvenienceDTO> getConveniences() {
		return conveniences;
	}

	public void setConveniences(List<ConvenienceDTO> conveniences) {
		this.conveniences = conveniences;
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

	public List<MenuDTO> getListMenu() {
		return listMenu;
	}

	public void setListMenu(List<MenuDTO> listMenu) {
		this.listMenu = listMenu;
	}

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public Boolean getIsWaitingDelete() {
		return isWaitingDelete;
	}

	public void setIsWaitingDelete(Boolean isWaitingDelete) {
		this.isWaitingDelete = isWaitingDelete;
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}

}
