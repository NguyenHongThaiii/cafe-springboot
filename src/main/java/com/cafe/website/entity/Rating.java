package com.cafe.website.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ratings")
@Entity
public class Rating extends BaseEntity {
	private int location;
	private int space;
	private int food;
	private int service;
	private int price;
}
