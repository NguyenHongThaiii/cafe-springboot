package com.cafe.website.service;

import java.util.List;

import com.cafe.website.entity.Product;

public interface ProductService {
	List<Product> getListProducts(int limit, int page, String name,String sortBy);
}
