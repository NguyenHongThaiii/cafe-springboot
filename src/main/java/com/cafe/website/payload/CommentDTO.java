package com.cafe.website.payload;

public class CommentDTO extends BaseEntityDTO {
	private String name;
	private Long reivewId;
	private UserDTO user;

	public CommentDTO() {
		// TODO Auto-generated constructor stub
	}

	public CommentDTO(String name, Long reivewId, UserDTO user) {
		super();
		this.name = name;
		this.reivewId = reivewId;
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getReivewId() {
		return reivewId;
	}

	public void setReivewId(Long reivewId) {
		this.reivewId = reivewId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
