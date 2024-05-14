package com.cafe.website.constant;

public enum SortField {
	NAME("name"), NAMEDESC("nameDesc"), ID("id"), IDDESC("idDesc"), PRICEMIN("priceMin"), PRICEMAX("priceMax"),
	PRICEMINDESC("priceMinDesc"), PRICEMAXDESC("priceMaxDesc"), CREATEDAT("createdAt"), CREATEDATDESC("createdAtDesc"),
	UPDATEDAT("updatedAt"), UPDATEDATDESC("updatedAtDesc"), RATING("rating"), RATINGDESC("ratingDesc");

	private final String value;

	SortField(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
