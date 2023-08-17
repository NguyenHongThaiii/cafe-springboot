package com.cafe.website.util;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.cafe.website.entity.Review;
import com.cafe.website.entity.User;
import com.cafe.website.payload.ReviewDTO;
import com.cafe.website.payload.UserDTO;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface ReviewMapper {
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateReviewFromDto(ReviewDTO reviewDto, @MappingTarget Review review);

}
