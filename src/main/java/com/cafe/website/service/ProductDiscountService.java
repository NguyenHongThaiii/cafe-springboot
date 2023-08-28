package com.cafe.website.service;

import java.util.List;

import com.cafe.website.payload.ProductDiscountCreateDTO;
import com.cafe.website.payload.ProductDiscountDTO;

public interface ProductDiscountService {

	ProductDiscountDTO getProductDiscountByProductId(Integer productId);

	ProductDiscountDTO createProductDiscount(ProductDiscountCreateDTO productDiscountDto);

	void deleteProductDiscountById(Integer id);

	List<ProductDiscountDTO> getListProductDiscount(int limit, int page, String name, Boolean expriteDays,
			Integer percent, String sortBy);

}
