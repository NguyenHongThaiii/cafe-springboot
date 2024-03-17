package com.cafe.website.payload;

public class CommentDTO extends BaseEntityDTO {
	private String name;
	private Long reivewId;

	public CommentDTO(Long id, Integer status, String createdAt, String updatedAt, String name, Long reivewId) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
		this.reivewId = reivewId;
	}

	public CommentDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getReivewId() {
		return reivewId;
	}

	public void setReivewId(Long reivewId) {
		this.reivewId = reivewId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
