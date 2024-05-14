package com.cafe.website.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.ProductCreateDTO;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductDeleteDTO;
import com.cafe.website.payload.ProductUpdateDTO;
import com.cafe.website.service.ProductService;
import com.cafe.website.serviceImp.ProductServiceImp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
	ProductService productService;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@GetMapping("")
	public ResponseEntity<List<ProductDTO>> getListProducts(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) Long userId, @RequestParam(required = false) String rating,
			@RequestParam(required = false) Boolean isWaitingDelete, @RequestParam(required = false) Double latitude,
			@RequestParam(required = false) Double longitude, @RequestParam(required = false) String name,
			@RequestParam(required = false) String slugArea, @RequestParam(required = false) String slugKind,
			@RequestParam(required = false) String slugConvenience, @RequestParam(required = false) String slugPurpose,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) Float ratingsAverage, @RequestParam(required = false) String timeStatus,
			@RequestParam(required = false) Integer outstanding, @RequestParam(required = false) Integer priceMax,

			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<ProductDTO> listproducts = productService.getListProducts(limit, page, status, rating, isWaitingDelete,
				name, slugArea, slugConvenience, slugKind, slugPurpose, latitude, longitude, userId, ratingsAverage,
				outstanding, createdAt, updatedAt, timeStatus, priceMax, sortBy);
		return new ResponseEntity<>(listproducts, HttpStatus.OK);
	}

	@GetMapping("/count")
	public ResponseEntity<Integer> getCountProducts(@RequestParam(defaultValue = "5") Integer limit,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) Integer status,
			@RequestParam(required = false) Long userId, @RequestParam(required = false) String rating,
			@RequestParam(required = false) Boolean isWaitingDelete, @RequestParam(required = false) Double latitude,
			@RequestParam(required = false) Double longitude, @RequestParam(required = false) String name,
			@RequestParam(required = false) String slugArea, @RequestParam(required = false) String slugKind,
			@RequestParam(required = false) String slugConvenience, @RequestParam(required = false) String slugPurpose,
			@RequestParam(required = false) String createdAt, @RequestParam(required = false) String updatedAt,
			@RequestParam(required = false) Float ratingsAverage, @RequestParam(required = false) String timeStatus,
			@RequestParam(required = false) Integer outstanding, @RequestParam(required = false) Integer priceMax,

			@RequestParam(required = false, defaultValue = "") String sortBy) {
		Integer listproducts = productService.getCountProducts(limit, page, status, rating, isWaitingDelete, name,
				slugArea, slugConvenience, slugKind, slugPurpose, latitude, longitude, userId, ratingsAverage,
				outstanding, createdAt, updatedAt, timeStatus, priceMax, sortBy);
		return new ResponseEntity<>(listproducts, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") Long id) {
		ProductDTO product = productService.getProductById(id);

		return new ResponseEntity<>(product, HttpStatus.OK);

	}

	@GetMapping("/count/{status}")
	public ResponseEntity<Long> getCountProductByStatus(@PathVariable(name = "status") Integer status) {
		Long count = productService.getCountProduct(status);

		return new ResponseEntity<Long>(count, HttpStatus.OK);

	}

	@GetMapping("/{slug}")
	public ResponseEntity<ProductDTO> getProductBySlug(@PathVariable(name = "slug") String slug) {
		ProductDTO product = productService.getProductBySlug(slug);

		return new ResponseEntity<>(product, HttpStatus.OK);

	}

	@PostMapping("")
	public ResponseEntity<ProductDTO> createProduct(@Valid @ModelAttribute ProductCreateDTO productCreateDto,
			HttpServletRequest request) throws IOException {
		ProductDTO product = productService.createProduct(productCreateDto, request);
		return new ResponseEntity<ProductDTO>(product, HttpStatus.CREATED);
	}

	@PatchMapping("/id/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable(name = "id") Long id,
			@Valid @ModelAttribute ProductUpdateDTO productUpdateDto, HttpServletRequest request) throws IOException {
		ProductDTO productDto = productService.updateProduct(id, productUpdateDto, request);
		return new ResponseEntity<ProductDTO>(productDto, HttpStatus.OK);
	}

	@DeleteMapping("")
	public ResponseEntity<String> deleteProduct(@Valid @RequestBody ProductDeleteDTO productDeleteDto,
			HttpServletRequest request) throws IOException {
		productService.deleteProduct(productDeleteDto, request);
		return new ResponseEntity<String>("Delete successfully", HttpStatus.OK);
	}

	@GetMapping("/{id}/ratings")
	public ResponseEntity<Float> getRatingByReviewId(@PathVariable(name = "id") Long id) throws IOException {
		Float number = productService.getRateReviewByProduct(id);
		return new ResponseEntity<>(number, HttpStatus.OK);
	}

	@DeleteMapping("/setDelete")
	public ResponseEntity<String> setIsWaitingDeleteProduct(@Valid @RequestBody ProductDeleteDTO productDeleteDto,
			HttpServletRequest request) throws IOException {
		String res = productService.setIsWaitingDeleteProduct(productDeleteDto, request);
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}

}
