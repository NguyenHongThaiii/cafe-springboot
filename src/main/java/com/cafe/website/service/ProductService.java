package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.ProductCreateDTO;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductDeleteDTO;
import com.cafe.website.payload.ProductUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface ProductService {

	ProductDTO createProduct(ProductCreateDTO productCreateDto, HttpServletRequest request) throws IOException;

	ProductDTO updateProduct(Long id, ProductUpdateDTO productUpdateDto, HttpServletRequest request) throws IOException;

	ProductDTO getProductById(Long id);

	ProductDTO getProductBySlug(String slug);

	String setIsWaitingDeleteProduct(ProductDeleteDTO productDeleteDto, HttpServletRequest request) throws IOException;

	Float getRateReviewByProduct(Long productId);

	Long getCountProduct(Integer status);

	List<ProductDTO> getListProducts(int limit, int page, Integer status, String rating, Boolean isWatingDelete,
			String name, String slugArea, String slugConvenience, String slugKind, String slugPurpose, Double latitude,
			Double longitude, Long userId, Float ratingsAverage, Integer outstanding, String createdAt,
			String updatedAt, String timeStatus, Integer priceMax, String sortBy);

	void deleteProduct(ProductDeleteDTO productDeleteDto, HttpServletRequest request) throws IOException;
}
