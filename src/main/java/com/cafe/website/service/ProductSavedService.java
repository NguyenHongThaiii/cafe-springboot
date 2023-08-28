package com.cafe.website.service;

import java.util.List;

import com.cafe.website.entity.ProductSaved;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductSavedCreateDTO;
import com.cafe.website.payload.ProductSavedDTO;

public interface ProductSavedService {

	void toggleProductSaved(ProductSavedCreateDTO productSavedCreate);

	List<ProductDTO> getListProductSavedByUser(Integer userId);
}
