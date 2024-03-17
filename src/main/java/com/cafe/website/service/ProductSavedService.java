package com.cafe.website.service;

import java.util.List;

import com.cafe.website.entity.ProductSaved;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductSavedCreateDTO;
import com.cafe.website.payload.ProductSavedDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface ProductSavedService {

	void toggleProductSaved(ProductSavedCreateDTO productSavedCreate, HttpServletRequest request);

	List<ProductDTO> getListProductSavedByUser(Long userId);
}
