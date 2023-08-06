package com.cafe.website.constant;

public enum SortField {
//	NAME, NAMEDESC, ID, IDDESC, PRICES, PRICEDESC, CREATEDAT, CREATEDATDESC, UPDATEDAT, UPDATEDATDESC;
	NAME("name"), NAMEDESC("nameDesc"), ID("id"), IDDESC("idDesc"), PRICEMIN("priceMin"),PRICEMAX("priceMax"), PRICEMINDESC("priceMinDesc"),PRICEMAXDESC("priceMaxDesc"),
	CREATEDAT("createdAt"), CREATEDATDESC("createdAtDesc"), UPDATEDAT("updatedAt"), UPDATEDATDESC("updatedAtDesc");

	private final String value;

	SortField(String value) {
		this.value = value;
	}
//
//	public String getValue() {
//		return value;
//	}

	@Override
	public String toString() {
		return value;
	}
}
