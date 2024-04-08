package com.cafe.website.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.cafe.website.entity.ProductSaved;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductSavedCreateDTO;
import com.cafe.website.payload.ProductSavedDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface ProductSavedService {

	void toggleProductSaved(ProductSavedCreateDTO productSavedCreate, HttpServletRequest request);

	List<ProductDTO> getListProductSavedByUser(Integer limit, Integer page, Integer status, String slugArea,
			String slugKind, String slugConvenience, String slugPurpose, Long userId, String createdAt,
			String updatedAt, String sortBy);

	Boolean checkIsSavedByUserId(Long userId, Long productId);
}
