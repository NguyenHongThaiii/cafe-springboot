package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.ConvenienceCreateDTO;
import com.cafe.website.payload.ConvenienceDTO;
import com.cafe.website.payload.ConvenienceUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface ConvenienceService {
	List<ConvenienceDTO> getListConveniences(int limit, int page, String name, String slug, String createdAt,
			String updatedAt, String sortBy);

	ConvenienceDTO getConvenienceById(Long id);

	ConvenienceDTO getConvenienceBySlug(String slug);

	ConvenienceDTO createConvenience(ConvenienceCreateDTO convenienceCreateDto, HttpServletRequest request)
			throws IOException;

	ConvenienceDTO updateConvenience(Long id, ConvenienceUpdateDTO convenienceDto, HttpServletRequest request)
			throws IOException;

	void deleteConvenience(Long id, HttpServletRequest request) throws IOException;
}
