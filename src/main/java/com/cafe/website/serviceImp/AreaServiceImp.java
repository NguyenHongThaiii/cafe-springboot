package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.SortField;
import com.cafe.website.entity.Area;
import com.cafe.website.entity.Image;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.AreaCreateDTO;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.AreaUpdateDTO;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.repository.AreaRepository;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.service.AreaService;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.util.AreaMapper;
import com.cafe.website.util.MapperUtils;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class AreaServiceImp implements AreaService {
	@PersistenceContext
	private EntityManager entityManager;
	private AreaMapper areaMapper;
	private CloudinaryService cloudinaryService;
	private AreaRepository areaRepository;
	private ImageRepository imageRepository;

	private Slugify slugify = Slugify.builder().build();
	private static final Logger logger = LoggerFactory.getLogger(AreaServiceImp.class);
	String path_category = "cafe-springboot/categories/Area";

	public AreaServiceImp(AreaRepository areaRepository, AreaMapper areaMapper, CloudinaryService cloudinaryService,
			ImageRepository imageRepository) {
		this.areaRepository = areaRepository;
		this.areaMapper = areaMapper;
		this.cloudinaryService = cloudinaryService;
		this.imageRepository = imageRepository;
	}

	@Override
	public List<AreaDTO> getListAreas(int limit, int page, String name, String slug, String sortBy) {
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

		listArea = areaRepository.findWithFilters(name, slug, pageable, entityManager);

		listAreaDto = listArea.stream().map(area -> {
			AreaDTO areaDto = MapperUtils.mapToDTO(area, AreaDTO.class);
			Image image = imageRepository.findImageByAreaId(area.getId()).orElse(null);

			areaDto.setImage(ImageDTO.generateImageDTO(image));
			return areaDto;
		}).collect(Collectors.toList());

		return listAreaDto;
	}

	@Override
	public AreaDTO getAreaById(int id) {
		Area area = areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area", "id", id + ""));
		AreaDTO areaDto = MapperUtils.mapToDTO(area, AreaDTO.class);
		Image image = imageRepository.findImageByAreaId(area.getId()).orElse(null);
		areaDto.setImage(ImageDTO.generateImageDTO(image));
		return areaDto;
	}

	@Override
	public AreaDTO getAreaBySlug(String slug) {
		Area area = areaRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("Area", "slug", slug));
		AreaDTO areaDto = MapperUtils.mapToDTO(area, AreaDTO.class);
		Image image = imageRepository.findImageByAreaId(area.getId()).orElse(null);

		areaDto.setImage(ImageDTO.generateImageDTO(image));
		return areaDto;
	}

	@Override
	public AreaDTO createArea(AreaCreateDTO areaCreateDto) throws IOException {

		if (areaRepository.existsBySlug(slugify.slugify(areaCreateDto.getSlug())))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (areaRepository.existsByName(areaCreateDto.getName()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		Area area = MapperUtils.mapToEntity(areaCreateDto, Area.class);
		area.setSlug(slugify.slugify(areaCreateDto.getSlug()));
		String url = cloudinaryService.uploadImage(areaCreateDto.getImageFile(), path_category, "image");

		Image image = new Image();
		image.setArea(area);
		image.setImage(url);
		area.setImage(image);

		Area newArea = areaRepository.save(area);

		AreaDTO newAreaDto = MapperUtils.mapToDTO(newArea, AreaDTO.class);
		newAreaDto.setImage(ImageDTO.generateImageDTO(image));
		return newAreaDto;
	}

	@Override
	public AreaDTO updateArea(int id, AreaUpdateDTO areaUpdateDto) throws IOException {
		AreaDTO newdto = this.getAreaById(id);

		if (areaRepository.existsBySlugAndIdNot(slugify.slugify(areaUpdateDto.getSlug()), newdto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (areaRepository.existsByNameAndIdNot(areaUpdateDto.getName(), newdto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		Area area = areaMapper.dtoToEntity(newdto);
		AreaDTO areaDto = MapperUtils.mapToDTO(areaUpdateDto, AreaDTO.class);
		if (areaUpdateDto.getImageFile() != null) {
			String url = cloudinaryService.uploadImage(areaUpdateDto.getImageFile(), path_category, "image");
			Image image = new Image();
			image.setArea(area);
			image.setImage(url);
			area.setImage(image);
		}
		areaDto.setId(id);
		if (areaUpdateDto.getSlug() != null)
			areaDto.setSlug(slugify.slugify(areaUpdateDto.getSlug()));

		areaMapper.updateAreaFromDto(areaDto, area);
		areaRepository.save(area);

		AreaDTO newAreaDto = MapperUtils.mapToDTO(area, AreaDTO.class);
		newAreaDto.setImage(ImageDTO.generateImageDTO(area.getImage()));

		return newAreaDto;
	}

	@Override
	public void deleteArea(int id) throws IOException {
		areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area", "id", id + ""));

		Image image = imageRepository.findImageByAreaId(id).orElse(null);
		if (image != null)
			this.cloudinaryService.removeImageFromCloudinary(image.getImage(), path_category);

		areaRepository.deleteById(id);
	}

}
