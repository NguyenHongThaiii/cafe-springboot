package com.cafe.website.serviceImp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.SortField;
import com.cafe.website.entity.Area;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.repository.AreaRepository;
import com.cafe.website.service.AreaService;
import com.cafe.website.util.MapperUtils;

import io.micrometer.common.util.StringUtils;

@Service
public class AreaServiceImp implements AreaService {

	private AreaRepository areaRepository;

	public AreaServiceImp(AreaRepository areaRepository) {
		this.areaRepository = areaRepository;
	}

	@Override
	public List<AreaDTO> getListAreas(int limit, int page, String name, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<AreaDTO> listAreaDto;
		List<Area> listArea;
		if (!StringUtils.isEmpty(sortBy))
			sortByList = Arrays.asList(sortBy.split(","));

		List<Sort.Order> sortOrders = new ArrayList<>();

		for (String sb : sortByList) {
			boolean isDescending = sb.endsWith("Desc");

			if (isDescending && !StringUtils.isEmpty(sortBy))
				sb = sb.substring(0, sb.length() - 4).trim();

			for (SortField sortField : validSortFields) {
				if (sortField.toString().equals(sb)) {
					sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
					break;
				}
			}
		}

		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));

		if (name != null && !name.isEmpty())
			listArea = areaRepository.findByNameContainingIgnoreCase(name, pageable).getContent();
		else
			listArea = areaRepository.findAll(pageable).getContent();

		listAreaDto = listArea.stream().map(area -> MapperUtils.mapToDTO(area, AreaDTO.class))
				.collect(Collectors.toList());

		return listAreaDto;
	}

	@Override
	public AreaDTO getAreaById(int id) {
		Area area = areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area", "area", id));
		AreaDTO areaDto = MapperUtils.mapToDTO(area, AreaDTO.class);
		return areaDto;
	}

	@Override
	public AreaDTO createArea(AreaDTO areaDto) {
		Area area = MapperUtils.mapToEntity(areaDto, Area.class);
		area.setStatus(1);

		Area newArea = areaRepository.save(area);

		AreaDTO newAreaDto = MapperUtils.mapToDTO(newArea, AreaDTO.class);

		return newAreaDto;
	}

	@Override
	public AreaDTO updateArea(int id, AreaDTO areaDto) {

		AreaDTO newAreaDto = this.getAreaById(id);
		areaDto.setId(newAreaDto.getId());

		Area area = MapperUtils.mapToEntity(areaDto, Area.class);
		areaRepository.save(area);

		AreaDTO res = MapperUtils.mapToDTO(area, AreaDTO.class);
		return res;
	}

	@Override
	public void deleteArea(int id) {
		AreaDTO newAreaDto = this.getAreaById(id);
		Area area = MapperUtils.mapToEntity(newAreaDto, Area.class);
		areaRepository.delete(area);
	}

}
