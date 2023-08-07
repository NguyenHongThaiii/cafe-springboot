package com.cafe.website.service;

import java.util.List;
import java.util.Optional;

import com.cafe.website.payload.AreaDTO;

public interface AreaService {
	List<AreaDTO> getListAreas(int limit, int page, String name, String sortBy);

	AreaDTO getAreaById(int id);

	AreaDTO createArea(AreaDTO areaDto);

	AreaDTO updateArea(int id,  AreaDTO areaDto);

	void deleteArea(int id);
}
