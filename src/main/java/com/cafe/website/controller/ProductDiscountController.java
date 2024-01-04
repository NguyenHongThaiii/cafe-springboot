package com.cafe.website.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.ProductDiscountCreateDTO;
import com.cafe.website.payload.ProductDiscountDTO;
import com.cafe.website.payload.ProductDiscountUpdateDTO;
import com.cafe.website.service.ProductDiscountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productDiscounts")
public class ProductDiscountController {
	private ProductDiscountService productDiscountService;

	public ProductDiscountController(ProductDiscountService productDiscountService) {
		super();
		this.productDiscountService = productDiscountService;
	}

	@GetMapping("")
	public ResponseEntity<List<ProductDiscountDTO>> getListProductDiscount(
			@RequestParam(defaultValue = "5") Integer limit, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(required = false) Integer percent, @RequestParam(required = false) String name,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) Boolean expireDays,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<ProductDiscountDTO> listProduct = productDiscountService.getListProductDiscount(limit, page, name,
				expireDays, percent, createdAt, updatedAt, sortBy);
		return ResponseEntity.ok(listProduct);
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDiscountDTO> getProductDiscountByProductId(@PathVariable(name = "id") Long id) {
		ProductDiscountDTO product = productDiscountService.getProductDiscountByProductId(id);
		return ResponseEntity.ok(product);
	}

	@PostMapping("")
	public ResponseEntity<ProductDiscountDTO> createProductDiscount(
			@Valid @RequestBody ProductDiscountCreateDTO productDiscountDto, HttpServletRequest request) {
		ProductDiscountDTO product = productDiscountService.createProductDiscount(productDiscountDto, request);

		return new ResponseEntity<ProductDiscountDTO>(product, HttpStatus.CREATED);
	}

	@PatchMapping("/products/{productId}")
	public ResponseEntity<ProductDiscountDTO> updateProductDiscount(
			@Valid @RequestBody ProductDiscountUpdateDTO productDiscountUpdateDto,
			@PathVariable(name = "productId") Long productId, HttpServletRequest request) {
		ProductDiscountDTO product = productDiscountService.updateProductDiscount(productId, productDiscountUpdateDto,
				request);

		return new ResponseEntity<ProductDiscountDTO>(product, HttpStatus.OK);
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<String> deleteProductDiscountById(@PathVariable(name = "id") Long id,
			HttpServletRequest request) {
		productDiscountService.deleteProductDiscountById(id, request);

		return ResponseEntity.ok("Ok");
	}
}
