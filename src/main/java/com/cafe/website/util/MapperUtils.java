package com.cafe.website.util;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cafe.website.serviceImp.ProductServiceImp;

public class MapperUtils {
	private static final ModelMapper mapper = new ModelMapper();
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public static <T, U> T mapToDTO(U entity, Class<T> dtoClass) {
		return mapper.map(entity, dtoClass);
	}

	public static <T, U> U mapToEntity(T dto, Class<U> entityClass) {
		return mapper.map(dto, entityClass);
	}

	public static <T, U> void mapToEntity(T dto, U entity) {
		mapper.map(dto, entity);
	}
}