package com.cafe.website.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.cafe.website.payload.ProductCreateDTO;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductUpdateDTO;

public interface ProductService {
	List<ProductDTO> getListProducts(int limit, int page, String name, String sortBy);

	ProductDTO createProduct(ProductCreateDTO productCreateDto) throws IOException;

	ProductDTO updateProduct(int id, ProductUpdateDTO productUpdateDto);

	ProductDTO getProductById(int id);

	String deleteProduct(int id);
}
