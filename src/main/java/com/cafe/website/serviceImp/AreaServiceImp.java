package com.cafe.website.serviceImp;

import java.io.IOException;
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
import com.cafe.website.payload.AreaCreateDTO;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.AreaUpdateDTO;
import com.cafe.website.repository.AreaRepository;
import com.cafe.website.service.AreaService;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.util.AreaMapper;
import com.cafe.website.util.MapperUtils;

import io.micrometer.common.util.StringUtils;

@Service
public class AreaServiceImp implements AreaService {
	private AreaMapper areaMapper;
	CloudinaryService cloudinaryService;
	private AreaRepository areaRepository;

	public AreaServiceImp(AreaRepository areaRepository, AreaMapper areaMapper, CloudinaryService cloudinaryService) {
		this.areaRepository = areaRepository;
		this.areaMapper = areaMapper;
		this.cloudinaryService = cloudinaryService;
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
		List<Sort.Order> sortOrders = new ArrayList<>();

		if (!StringUtils.isEmpty(sortBy))
			sortByList = Arrays.asList(sortBy.split(","));

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
		Area area = areaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Area", "id", id + ""));
		AreaDTO areaDto = MapperUtils.mapToDTO(area, AreaDTO.class);
		return areaDto;
	}

	@Override
	public AreaDTO createArea(AreaCreateDTO areaCreateDto) throws IOException {
		Area area = MapperUtils.mapToEntity(areaCreateDto, Area.class);
		String image = cloudinaryService.uploadImage(areaCreateDto.getImage(), "cafe-springboot/categories", "image");
		area.setImage(image);
		Area newArea = areaRepository.save(area);

		AreaDTO newAreaDto = MapperUtils.mapToDTO(newArea, AreaDTO.class);

		return newAreaDto;
	}

	@Override
	public AreaDTO updateArea(int id, AreaUpdateDTO areaUpdateDto) throws IOException {
		AreaDTO newdto = this.getAreaById(id);
		Area area = areaMapper.dtoToEntity(newdto);
		String image = cloudinaryService.uploadImage(areaUpdateDto.getImage(), "cafe-springboot/categories", "image");
		AreaDTO areaDto = MapperUtils.mapToDTO(areaUpdateDto, AreaDTO.class);

		areaDto.setId(id);
		areaDto.setImage(image);

		areaMapper.updateAreaFromDto(areaDto, area);
		this.deleteArea(area.getId());
		areaRepository.save(area);

		return areaMapper.entityToDto(area);
	}

	@Override
	public void deleteArea(int id) throws IOException {
		AreaDTO areaDto = this.getAreaById(id);
		String path_category = "cafe-springboot/categories/";
		String listMenusDb = areaDto.getImage();

		this.cloudinaryService.removeImageFromCloudinary(listMenusDb, path_category);

		areaRepository.deleteById(id);
	}

}
