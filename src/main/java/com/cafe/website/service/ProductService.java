package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.ProductCreateDTO;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductDeleteDTO;
import com.cafe.website.payload.ProductUpdateDTO;

public interface ProductService {

	ProductDTO createProduct(ProductCreateDTO productCreateDto) throws IOException;

	ProductDTO updateProduct(int id, ProductUpdateDTO productUpdateDto) throws IOException;

	ProductDTO getProductById(int id);

	ProductDTO getProductBySlug(String slug);

	void deleteProduct(ProductDeleteDTO productDeleteDto) throws IOException;

	String setIsWaitingDeleteProduct(ProductDeleteDTO productDeleteDto) throws IOException;

	Float getRateReviewByProduct(Integer productId);

	List<ProductDTO> getListProducts(int limit, int page, int status, String rating, Integer isWatingDelete,
			String name, String slugArea, String slugConvenience, String slugKind, String slugPurpose, Double latitude,
			Double longitude, String sortBy);
}
