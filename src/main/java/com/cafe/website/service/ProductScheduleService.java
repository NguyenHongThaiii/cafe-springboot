package com.cafe.website.service;

import java.io.IOException;
import java.util.List;

import com.cafe.website.payload.AreaCreateDTO;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.AreaUpdateDTO;
import com.cafe.website.payload.ProductScheduleCreateDTO;
import com.cafe.website.payload.ProductScheduleDTO;
import com.cafe.website.payload.ProductScheduleUpdateDTO;

public interface ProductScheduleService {
	List<ProductScheduleDTO> getListSchedules(int limit, int page, String sortBy);

	ProductScheduleDTO getAreaById(int id);

	ProductScheduleDTO getScheduleBySlug(String slug);

	ProductScheduleDTO createSchedule(ProductScheduleCreateDTO scheduleCreateDto) throws IOException;

	ProductScheduleDTO updateSchedule(int id, ProductScheduleUpdateDTO scheduleUpdateDto) throws IOException;

	void deleteScheduleById(int id) throws IOException;
}
