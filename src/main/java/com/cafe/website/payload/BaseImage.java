package com.cafe.website.payload;

public class BaseImage extends BaseEntityDTO {
	private String url;

	public BaseImage(int id, Integer status, Long createdAt, Long updatedAt, String url) {
		super(id, status, createdAt, updatedAt);
		this.url = url;
	}

	public BaseImage() {
		// TODO Auto-generated constructor stub
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "BaseImage [url=" + url + "]";
	}

}
