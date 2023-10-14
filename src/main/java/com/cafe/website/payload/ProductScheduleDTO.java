package com.cafe.website.payload;

public class ProductScheduleDTO extends BaseEntityDTO {
	private Long dayOfWeek;
	private Long startTime;
	private Long endTime;

	public ProductScheduleDTO(int id, Integer status, String createdAt, String updatedAt, Long dayOfWeek,
			Long startTime, Long endTime) {
		super(id, status, createdAt, updatedAt);
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public ProductScheduleDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(Long dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

}
