package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cafe.website.payload.ProductScheduleCreateDTO;
import com.cafe.website.payload.ProductScheduleDTO;
import com.cafe.website.payload.ProductScheduleUpdateDTO;
import com.cafe.website.service.ProductScheduleService;

@Service
public class ProductScheduleServiceImp implements ProductScheduleService {

	@Override
	public List<ProductScheduleDTO> getListSchedules(int limit, int page, String sortBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductScheduleDTO getAreaById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductScheduleDTO getScheduleBySlug(String slug) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductScheduleDTO createSchedule(ProductScheduleCreateDTO scheduleCreateDto) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductScheduleDTO updateSchedule(Long id, ProductScheduleUpdateDTO scheduleUpdateDto) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteScheduleById(Long id) throws IOException {
		// TODO Auto-generated method stub

	}

}
