package com.cafe.website.util;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.cafe.website.entity.Kind;
import com.cafe.website.payload.KindDTO;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface KindMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateKindFromDto(KindDTO kindDto, @MappingTarget Kind kind);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
	void updateKindDTOFromArea(Kind entity, @MappingTarget KindDTO kindDto);

	KindDTO entityToDto(Kind kind);

	Kind dtoToEntity(KindDTO kindDto);
}
