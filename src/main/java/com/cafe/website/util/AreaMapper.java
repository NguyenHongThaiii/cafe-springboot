package com.cafe.website.util;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.cafe.website.entity.Area;
import com.cafe.website.payload.AreaDTO;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AreaMapper {
	public AreaMapper INSTANCE = Mappers.getMapper(AreaMapper.class);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateAreaFromDto(AreaDTO areaDto, @MappingTarget Area area);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, ignoreByDefault = true)
	void updateAreaDTOFromArea(Area entity, @MappingTarget AreaDTO dto);

	AreaDTO entityToDto(Area area);

	Area dtoToEntity(AreaDTO areaDto);

}
