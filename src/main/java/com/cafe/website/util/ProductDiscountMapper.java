package com.cafe.website.util;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.cafe.website.entity.Area;
import com.cafe.website.entity.ProductDiscount;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.ProductDiscountDTO;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ProductDiscountMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateProductDiscountFromDto(ProductDiscountDTO productDiscountDto, @MappingTarget ProductDiscount productDiscount);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
	void updateProductDiscountDTOFromProductDiscount(ProductDiscount productDiscount, @MappingTarget ProductDiscountDTO productDiscountDto);
}
