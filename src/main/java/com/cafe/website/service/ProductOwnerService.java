package com.cafe.website.service;

public interface ProductOwnerService {
	Boolean isOwnerByProductIdAndUserId(Long productId, Long userId);
}
