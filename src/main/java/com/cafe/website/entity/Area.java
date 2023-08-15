package com.cafe.website.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "areas")
@Entity
public class Area extends BaseCategory {

    public Area() {
        // TODO Auto-generated constructor stub
    }

   
	@Override
	public String toString() {
		return "Area [getName()=" + getName() + ", getSlug()=" + getSlug() + ", getImage()=" + getImage() + ", getId()="
				+ getId() + ", getStatus()=" + getStatus() + ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()="
				+ getUpdatedAt() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
    
}
