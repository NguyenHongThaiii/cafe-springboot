package com.cafe.website.payload;

public class CommentDTO extends BaseEntityDTO {
	private String name;

	public CommentDTO(int id, int status, Long createdAt, Long updatedAt, String name) {
		super(id, status, createdAt, updatedAt);
		this.name = name;
	}

	public CommentDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
