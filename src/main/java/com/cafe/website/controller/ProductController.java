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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.website.payload.ProductCreateDTO;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductUpdateDTO;
import com.cafe.website.service.ProductService;
import com.cafe.website.serviceImp.ProductServiceImp;

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
	public ResponseEntity<List<ProductDTO>> getListProducts(@RequestParam(defaultValue = "5") int limit,
			@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String name,
			@RequestParam(required = false, defaultValue = "") String sortBy) {
		List<ProductDTO> listproducts = productService.getListProducts(limit, page, name, sortBy);
		return new ResponseEntity<>(listproducts, HttpStatus.OK);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") int id) {
		ProductDTO product = productService.getProductById(id);

		return new ResponseEntity<>(product, HttpStatus.OK);

	}

	@GetMapping("/{slug}")
	public ResponseEntity<ProductDTO> getProductBySlug(@PathVariable(name = "slug") String slug) {
		ProductDTO product = productService.getProductBySlug(slug);

		return new ResponseEntity<>(product, HttpStatus.OK);

	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@PostMapping("")
	public ResponseEntity<ProductDTO> createProduct(@ModelAttribute ProductCreateDTO productCreateDto)
			throws IOException {
		ProductDTO product = productService.createProduct(productCreateDto);
		return new ResponseEntity<ProductDTO>(product, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MOD')")
	@PatchMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable(name = "id") int id,
			@Valid @ModelAttribute ProductUpdateDTO productUpdateDto) throws IOException {
		ProductDTO productDto = productService.updateProduct(id, productUpdateDto);
		return new ResponseEntity<ProductDTO>(productDto, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") int id) throws IOException {
		productService.deleteProduct(id);
		return new ResponseEntity<String>("Delete successfully", HttpStatus.OK);
	}

	@GetMapping("/{id}/ratings")
	public ResponseEntity<Float> getRatingByReviewId(@PathVariable(name = "id") int id) throws IOException {
		Float number = productService.getRateReviewByProduct(id);
		return new ResponseEntity<>(number, HttpStatus.OK);
	}

}
