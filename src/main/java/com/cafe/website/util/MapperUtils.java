package com.cafe.website.util;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cafe.website.payload.HasImage;
import com.cafe.website.payload.HasImageDTO;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.serviceImp.ProductServiceImp;

public class MapperUtils {
	private static final ModelMapper mapper = new ModelMapper();
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public static <T> List<T> loppMapToDTO(List<? extends HasImage> listEntities, Class<T> dtoClass) {
		if (listEntities == null)
			return null;

		List<T> list = new ArrayList<>();
		for (HasImage entity : listEntities) {
			T temp = mapper.map(entity, dtoClass);
			ImageDTO imageDto = ImageDTO.generateImageDTO(entity.getImage());
			((HasImageDTO) temp).setImage(imageDto);
			list.add((T) temp);
		}
		return list;
	}

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