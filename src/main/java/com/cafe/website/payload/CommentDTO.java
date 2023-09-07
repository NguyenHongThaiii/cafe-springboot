package com.cafe.website.payload;

public class CommentDTO extends BaseEntityDTO {
	private String name;
	private Integer reivewId;

	public CommentDTO(int id, Integer status, Long createdAt, Long updatedAt, String name, Integer reivewId) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.reivewId = reivewId;
	}

	public CommentDTO() {
		// TODO Auto-generated constructor stub
	}

	public Integer getReivewId() {
		return reivewId;
	}

	public void setReivewId(Integer reivewId) {
		this.reivewId = reivewId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
