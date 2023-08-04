package com.cafe.website.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.entity.Product;
import com.cafe.website.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
	ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@RequestMapping("")
	public ResponseEntity<List<Product>> getListProducts(
			@RequestParam(defaultValue="5")  int limit,
			@RequestParam (defaultValue="1") int page,
			@RequestParam (required = false, defaultValue = "") String name,
			@RequestParam (required = false,defaultValue = "") String sortBy
			) {
		List<Product> listproducts = productService.getListProducts(limit,page,name,sortBy);
		return new ResponseEntity<>(listproducts, HttpStatus.OK);
	}
}
