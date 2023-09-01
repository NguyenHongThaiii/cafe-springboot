package com.cafe.website.serviceImp;

import org.springframework.stereotype.Service;

import com.cafe.website.entity.Product;
import com.cafe.website.entity.User;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.repository.ProductOwnerRepository;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.service.ProductOwnerService;

@Service
public class ProductOwnerServiceImp implements ProductOwnerService {
	private ProductOwnerRepository productOwnerRepository;
	private ProductRepository productRepository;
	private UserRepository userRepository;

	public ProductOwnerServiceImp(ProductOwnerRepository productOwnerRepository) {
		this.productOwnerRepository = productOwnerRepository;
	}

	@Override
	public Boolean isOwnerByProductIdAndUserId(Integer productId, Integer userId) {
		productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
		userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		Boolean isExist = productOwnerRepository.existsByUserIdAndProductId(userId, productId);

		return isExist;
	}

}
