package com.cafe.website.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductSavedCreateDTO;
import com.cafe.website.service.ProductSavedService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productSaved")
public class ProductSavedController {
	private ProductSavedService productSavedService;

	public ProductSavedController(ProductSavedService productSavedService) {
		super();
		this.productSavedService = productSavedService;
	}

	@PostMapping("/toggle")
	public ResponseEntity<String> toggleProductSaved(@Valid @RequestBody ProductSavedCreateDTO productSavedCreate) {
		productSavedService.toggleProductSaved(productSavedCreate);
		return new ResponseEntity<String>("ok", HttpStatus.CREATED);

	}

	@GetMapping("/{id}")
	public ResponseEntity<List<ProductDTO>> getListProductSavedByUserId(@PathVariable(name = "id") Integer id) {
		List<ProductDTO> list = productSavedService.getListProductSavedByUser(id);
		return ResponseEntity.ok(list);
	}
}
