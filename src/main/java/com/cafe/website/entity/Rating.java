package com.cafe.website.entity;


import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
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
