package com.cafe.website.serviceImp;

import java.util.ArrayList;
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

@Service
public class ProductServiceImp implements ProductService {
	ProductRepository productRepository;

	public ProductServiceImp(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getListProducts(int limit, int page, String name, String sortBy) {

		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.PRICEMIN,
				SortField.PRICEMAX, SortField.UPDATEDAT, SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC,
				SortField.PRICEMINDESC, SortField.PRICEMAXDESC, SortField.UPDATEDATDESC, SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		if (!StringUtils.isEmpty(sortBy))
			sortByList = Arrays.asList(sortBy.split(","));

		List<Sort.Order> sortOrders = new ArrayList<>();

		for (String sb : sortByList) {
			boolean isDescending = sb.endsWith("Desc");

			if (isDescending && !StringUtils.isEmpty(sortBy))
				sb = sb.substring(0, sb.length() - 4).trim();

			for (SortField sortField : validSortFields) {
				if (sortField.toString().equals(sb)) {
					sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
					break;
				}
			}
		}

		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));

		if (name != null && !name.isEmpty())
			return productRepository.findByNameContainingIgnoreCase(name, pageable).getContent();
		else
			return productRepository.findAll(pageable).getContent();
	}

}
