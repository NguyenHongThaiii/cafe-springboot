package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.AreaCreateDTO;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.AreaUpdateDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface AreaService {
	List<AreaDTO> getListAreas(int limit, int page, Integer status, String name, String slug, String createdAt,
			String updatedAt, String sortBy);

	AreaDTO getAreaById(Long id);

	AreaDTO getAreaBySlug(String slug);

	AreaDTO createArea(AreaCreateDTO areaCreateDto, HttpServletRequest request) throws IOException;

	AreaDTO updateArea(Long id, AreaUpdateDTO areaDto, HttpServletRequest request) throws IOException;

	void deleteArea(Long id, HttpServletRequest request) throws IOException;
}
