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

	ProductDTO updateProduct(int id, ProductUpdateDTO productUpdateDto, HttpServletRequest request) throws IOException;

	ProductDTO getProductById(int id);

	ProductDTO getProductBySlug(String slug);

	String setIsWaitingDeleteProduct(ProductDeleteDTO productDeleteDto, HttpServletRequest request) throws IOException;

	Float getRateReviewByProduct(Integer productId);

	List<ProductDTO> getListProducts(int limit, int page, Integer status, String rating, Boolean isWatingDelete,
			String name, String slugArea, String slugConvenience, String slugKind, String slugPurpose, Double latitude,
			Double longitude, Integer userId, Float ratingsAverage, String createdAt, String updatedAt,
			String timeStatus, String sortBy);

	void deleteProduct(ProductDeleteDTO productDeleteDto, HttpServletRequest request) throws IOException;
}
