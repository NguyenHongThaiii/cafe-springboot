package com.cafe.website.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductSavedCreateDTO;
import com.cafe.website.service.ProductSavedService;

import jakarta.servlet.http.HttpServletRequest;
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
	public ResponseEntity<String> toggleProductSaved(@Valid @RequestBody ProductSavedCreateDTO productSavedCreate,
			HttpServletRequest request) {
		productSavedService.toggleProductSaved(productSavedCreate, request);
		return new ResponseEntity<String>("ok", HttpStatus.CREATED);

	}

	@GetMapping("")
	public ResponseEntity<List<ProductDTO>> getListProductSavedByUserId(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) String slugArea, @RequestParam(required = false) String slugKind,
			@RequestParam(required = false) String slugConvenience, @RequestParam(required = false) String slugPurpose,
			@RequestParam(required = false) Long userId, @RequestParam(required = false) String createdAt,
			@RequestParam(required = false) String updatedAt,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<ProductDTO> list = productSavedService.getListProductSavedByUser(limit, page, status, slugArea,
				slugConvenience, slugKind, slugPurpose, userId, createdAt, updatedAt, sortBy);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/userId/{userId}/productId/{productId}")
	public ResponseEntity<Boolean> getListProductSavedByUserId(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "productId") Long productId) {
		Boolean isSaved = productSavedService.checkIsSavedByUserId(userId, productId);
		return ResponseEntity.ok(isSaved);
	}
}
