package com.cafe.website.serviceImp;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cafe.website.entity.Area;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.repository.AreaRepository;
import com.cafe.website.service.AreaService;

@Service
public class AreaServiceImp implements AreaService {

	private AreaRepository areaRepository;

	public AreaServiceImp(AreaRepository areaRepository) {
		this.areaRepository = areaRepository;
	}

	@Override
	public List<Area> getListAreas() {
		// TODO Auto-generated method stub
		List<Area> listAreas = areaRepository.findAll();
		return listAreas;
	}

	@Override
	public Optional<Area> getAreaById(int id) {
		// TODO Auto-generated method stub
		Area area = areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area", "area", id) );

		return Optional.ofNullable(area);
	}

}
