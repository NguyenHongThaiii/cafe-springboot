package com.cafe.website.serviceImp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cafe.website.entity.Area;
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

}
