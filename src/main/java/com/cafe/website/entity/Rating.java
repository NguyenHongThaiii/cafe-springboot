package com.cafe.website.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "ratings")
@Entity
public class Rating extends BaseEntity {
	private int location;
	private int space;
	private int food;
	private int service;
	private int price;

	public Rating(int id, int status, String createdAt, String updatedAt, int location, int space, int food,
			int service, int price) {
		super(id, status, createdAt, updatedAt);
		this.location = location;
		this.space = space;
		this.food = food;
		this.service = service;
		this.price = price;
	}

	public Rating() {
		// TODO Auto-generated constructor stub
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getSpace() {
		return space;
	}

	public void setSpace(int space) {
		this.space = space;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getService() {
		return service;
	}

	public void setService(int service) {
		this.service = service;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public float getAverageRating() {
		return (float) (location + space + food + service + price) / 5;
	}

	@Override
	public String toString() {
		return "Rating [location=" + location + ", space=" + space + ", food=" + food + ", service=" + service
				+ ", price=" + price + "]";
	}

}
