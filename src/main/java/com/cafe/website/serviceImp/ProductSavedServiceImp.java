package com.cafe.website.serviceImp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.ProductSaved;
import com.cafe.website.entity.User;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductSavedCreateDTO;
import com.cafe.website.payload.ProductSavedDTO;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.ProductSavedRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.service.ProductSavedService;
import com.cafe.website.util.MapperUtils;

@Service
public class ProductSavedServiceImp implements ProductSavedService {
	private ProductSavedRepository productSavedRepository;
	private UserRepository userRepository;
	private ProductRepository productRepository;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public ProductSavedServiceImp(ProductSavedRepository productSavedRepository, UserRepository userRepository,
			ProductRepository productRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.productSavedRepository = productSavedRepository;
	}

	@Override
	public void toggleProductSaved(ProductSavedCreateDTO productSavedCreate) {
		Integer productId = productSavedCreate.getProductId();
		Integer userId = productSavedCreate.getUserId();
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		if (productSavedRepository.existsByUserIdAndProductId(userId, productId)) {
			productSavedRepository.deleteByUserIdAndProductId(userId, productId);
		} else {
			ProductSaved productSaved = new ProductSaved();
			productSaved.setProduct(product);
			productSaved.setUser(user);
			productSavedRepository.save(productSaved);
		}
	}

	@Override
	public List<ProductDTO> getListProductSavedByUser(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		List<ProductSaved> list = productSavedRepository.findAllByUserId(user.getId());
		List<ProductDTO> listProductDto = new ArrayList<>();
		for (ProductSaved ps : list) {
			listProductDto.add(MapperUtils.mapToDTO(ps.getProduct(), ProductDTO.class));
		}
		return listProductDto;
	}
}
