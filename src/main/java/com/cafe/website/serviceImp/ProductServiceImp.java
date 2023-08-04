package com.cafe.website.serviceImp;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.SortField;
import com.cafe.website.entity.Product;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.service.ProductService;

import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductServiceImp implements ProductService {
	ProductRepository productRepository;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public ProductServiceImp(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getListProducts(int limit, int page, String name, String sortBy) {
		boolean isDescending = sortBy.endsWith("Desc");
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.PRICES,
				SortField.UPDATED_AT, SortField.CREATED_AT, SortField.IDDESC, SortField.NAMEDESC, SortField.PRICEDESC,
				SortField.UPDATED_ATDESC, SortField.CREATED_ATDESC);
		Sort sort;
		Pageable pageable = PageRequest.of(page - 1, limit);

		if (isDescending && !StringUtils.isEmpty(sortBy))
			sortBy = sortBy.substring(0, sortBy.length() - 4);

		if (!StringUtils.isEmpty(sortBy)) {
			for (SortField sortField : validSortFields) {
				if (sortField.toString().equalsIgnoreCase(sortBy)) {
					sort = isDescending ? Sort.by(sortBy).descending() : Sort.by(sortBy);
					pageable = PageRequest.of(page - 1, limit, sort);
					break;
				}
			}
		}

		if (name != null && !name.isEmpty()) {
			return productRepository.findByNameContainingIgnoreCase(name, pageable).getContent();
		} else {
			return productRepository.findAll(pageable).getContent();
		}
	}

}
