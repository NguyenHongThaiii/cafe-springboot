package com.cafe.website.service;

import java.util.List;

import com.cafe.website.constant.SortField;

public interface SortAndSearchService<T> {
	List<T> getAllEntities(int limit, int page, String name, String sortBy, List<SortField> validSortFields);

}
