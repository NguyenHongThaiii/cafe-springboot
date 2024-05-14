package com.cafe.website.payload;

public class ProductScheduleUpdateDTO {
	private Long dayOfWeek;
	private Long startTime;
	private Long endTime;
	private Integer status;

	public ProductScheduleUpdateDTO(Long dayOfWeek, Long startTime, Long endTime, Integer status) {
		super();
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}

	public ProductScheduleUpdateDTO() {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
