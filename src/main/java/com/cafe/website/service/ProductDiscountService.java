package com.cafe.website.service;

import java.util.List;

import com.cafe.website.payload.ProductDiscountCreateDTO;
import com.cafe.website.payload.ProductDiscountDTO;
import com.cafe.website.payload.ProductDiscountUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface ProductDiscountService {

	ProductDiscountDTO getProductDiscountByProductId(Long productId);

	ProductDiscountDTO createProductDiscount(ProductDiscountCreateDTO productDiscountCreateDto,
			HttpServletRequest request);

	ProductDiscountDTO updateProductDiscount(Long productId, ProductDiscountUpdateDTO productDiscountUpdateDto,
			HttpServletRequest request);

	void deleteProductDiscountById(Long id, HttpServletRequest request);

	List<ProductDiscountDTO> getListProductDiscount(int limit, int page, String name, Boolean expriteDays,
			Integer percent, String createdAt, String updatedAt, String sortBy);

}
