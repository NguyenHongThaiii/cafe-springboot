package com.cafe.website.util;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.cafe.website.entity.Area;
import com.cafe.website.entity.Product;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductUpdateDTO;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
	public ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateProductFromDto(ProductDTO productDto, @MappingTarget Product product);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
	void updateProductDtoFromProduct(Product entity, @MappingTarget ProductDTO dto);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateProductDtoFromProductUpdateDto(@MappingTarget ProductDTO productDto, ProductUpdateDTO productUpdateDto);
	
}
