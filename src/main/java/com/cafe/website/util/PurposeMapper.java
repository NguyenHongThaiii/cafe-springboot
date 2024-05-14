package com.cafe.website.util;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.cafe.website.entity.Purpose;
import com.cafe.website.payload.PurposeDTO;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PurposeMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updatePurposeFromDto(PurposeDTO purposeDto, @MappingTarget Purpose purpose);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
	void updatePurposeDTOFromArea(Purpose entity, @MappingTarget PurposeDTO purposeDto);

	PurposeDTO entityToDto(Purpose purpose);

	Purpose dtoToEntity(PurposeDTO purposeDTO);
}
