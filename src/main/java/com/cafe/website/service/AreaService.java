package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.AreaCreateDTO;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.AreaUpdateDTO;

public interface AreaService {
	List<AreaDTO> getListAreas(int limit, int page, String name, String sortBy);

	AreaDTO getAreaById(int id);

	AreaDTO createArea(AreaCreateDTO areaCreateDto) throws IOException;

	AreaDTO updateArea(int id,  AreaUpdateDTO areaDto) throws IOException;

	void deleteArea(int id);
}
