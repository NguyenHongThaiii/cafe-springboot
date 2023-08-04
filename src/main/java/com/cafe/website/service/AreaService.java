package com.cafe.website.service;

import java.util.List;
import java.util.Optional;

import com.cafe.website.entity.Area;

public interface AreaService {
	List<Area> getListAreas();

	Optional<Area> getAreaById(int id);
}
