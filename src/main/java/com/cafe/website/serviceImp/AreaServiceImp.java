package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.SortField;
import com.cafe.website.constant.StatusLog;
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
import com.cafe.website.service.AuthService;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.LogService;
import com.cafe.website.util.AreaMapper;
import com.cafe.website.util.JsonConverter;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.MethodUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class AreaServiceImp implements AreaService {
	@PersistenceContext
	private EntityManager entityManager;
	private AreaMapper areaMapper;
	private CloudinaryService cloudinaryService;
	private AreaRepository areaRepository;
	private ImageRepository imageRepository;
	private LogService logService;
	private AuthService authService;
	private ObjectMapper objectMapper;

	private Slugify slugify = Slugify.builder().build();
	private static final Logger logger = LoggerFactory.getLogger(AreaServiceImp.class);
	@Value("${app.path-category-area}")
	private String path_category;

	public AreaServiceImp(EntityManager entityManager, AreaMapper areaMapper, CloudinaryService cloudinaryService,
			AreaRepository areaRepository, ImageRepository imageRepository, LogService logService,
			AuthService authService, ObjectMapper objectMapper) {
		super();
		this.entityManager = entityManager;
		this.areaMapper = areaMapper;
		this.cloudinaryService = cloudinaryService;
		this.areaRepository = areaRepository;
		this.imageRepository = imageRepository;
		this.logService = logService;
		this.authService = authService;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<AreaDTO> getListAreas(int limit, int page, Integer status, String name, String slug, String createdAt,
			String updatedAt, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		List<String> sortByList = new ArrayList<String>();
		List<AreaDTO> listAreaDto;
		List<Area> listArea;
		List<Sort.Order> sortOrders = new ArrayList<>();

		Pageable pageable = null;

		if (page != 0) {
			pageable = PageRequest.of(page - 1, limit);
			if (!StringUtils.isEmpty(sortBy))
				sortByList = Arrays.asList(sortBy.split(","));

			for (String sb : sortByList) {
				boolean isDescending = sb.endsWith("Desc");

				if (isDescending && !StringUtils.isEmpty(sortBy))
					sb = sb.substring(0, sb.length() - 4).trim();

				for (SortField sortField : validSortFields) {
					if (sortField.toString().equals(sb.trim())) {
						sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
						break;
					}
				}
			}

		}
		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));

		listArea = areaRepository.findWithFilters(status, name, slug, createdAt, updatedAt, pageable, entityManager);

		listAreaDto = listArea.stream().map(area -> {
			AreaDTO areaDto = MapperUtils.mapToDTO(area, AreaDTO.class);
			areaDto.setImage(ImageDTO.generateImageDTO(area.getImage()));
			return areaDto;
		}).collect(Collectors.toList());

		return listAreaDto;
	}

	@Override
	public AreaDTO getAreaById(Long id) {
		Area area = areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area", "id", id + ""));
		AreaDTO areaDto = MapperUtils.mapToDTO(area, AreaDTO.class);
		areaDto.setImage(ImageDTO.generateImageDTO(area.getImage()));
		return areaDto;
	}

	@Override
	public AreaDTO getAreaBySlug(String slug) {
		Area area = areaRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("Area", "slug", slug));
		AreaDTO areaDto = MapperUtils.mapToDTO(area, AreaDTO.class);
		areaDto.setImage(ImageDTO.generateImageDTO(area.getImage()));
		return areaDto;
	}

	@Override
	public AreaDTO createArea(AreaCreateDTO areaCreateDto, HttpServletRequest request) throws IOException {

		if (areaRepository.existsBySlug(slugify.slugify(areaCreateDto.getSlug())))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (areaRepository.existsByName(areaCreateDto.getName()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		Area area = MapperUtils.mapToEntity(areaCreateDto, Area.class);
		area.setSlug(slugify.slugify(areaCreateDto.getName()));
		String url = cloudinaryService.uploadImage(areaCreateDto.getImageFile(), path_category, "image");
		Image image = new Image();
		image.setArea(area);
		image.setImage(url);
		area.setImage(image);

		Area newArea = areaRepository.save(area);

		AreaDTO newAreaDto = MapperUtils.mapToDTO(newArea, AreaDTO.class);
		newAreaDto.setImage(ImageDTO.generateImageDTO(newArea.getImage()));
		areaCreateDto.setDataToLogging(areaCreateDto.getImageFile().getOriginalFilename(),
				areaCreateDto.getImageFile().getContentType(), areaCreateDto.getImageFile().getSize(), () -> {
					areaCreateDto.setImageFile(null);
				});
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Create Area SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(areaCreateDto), "Create Area");
			return newAreaDto;
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					objectMapper.writeValueAsString(areaCreateDto), "Create Area");
			cloudinaryService.removeImageFromCloudinary(url, path_category);
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());

		}
	}

	@Override
	public AreaDTO updateArea(Long id, AreaUpdateDTO areaUpdateDto, HttpServletRequest request) throws IOException {
		AreaDTO newdto = this.getAreaById(id);

		if (areaRepository.existsBySlugAndIdNot(slugify.slugify(areaUpdateDto.getSlug()), newdto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (areaRepository.existsByNameAndIdNot(areaUpdateDto.getName(), newdto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");
		Map<String, Object> logData = new HashMap<>();

		Area area = areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area", "id", id + ""));
		AreaDTO areaDto = MapperUtils.mapToDTO(areaUpdateDto, AreaDTO.class);
		if (areaUpdateDto.getImageFile() != null && !areaUpdateDto.getImageFile().isEmpty()) {
			String url = cloudinaryService.uploadImage(areaUpdateDto.getImageFile(), path_category, "image");
			Image image = new Image();
			image.setArea(area);
			image.setImage(url);
			area.setImage(image);
			areaUpdateDto.setDataToLogging(areaUpdateDto.getImageFile().getOriginalFilename(),
					areaUpdateDto.getImageFile().getContentType(), areaUpdateDto.getImageFile().getSize(), () -> {
						areaUpdateDto.setImageFile(null);
					});
		}
		areaDto.setId(id);
		if (areaUpdateDto.getSlug() != null)
			areaDto.setSlug(slugify.slugify(areaUpdateDto.getSlug()));
		logData.put("id", id);
		logData.put("areaUpdateDto", areaUpdateDto);
		areaMapper.updateAreaFromDto(areaDto, area);
		areaRepository.save(area);

		AreaDTO newAreaDto = MapperUtils.mapToDTO(area, AreaDTO.class);
		newAreaDto.setImage(ImageDTO.generateImageDTO(area.getImage()));
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Update Area SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(logData), "Update Area");
			return newAreaDto;

		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(), "Create Area");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public void deleteArea(Long id, HttpServletRequest request) throws IOException {
		areaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Area", "id", id + ""));

		Image image = imageRepository.findImageByAreaId(id).orElse(null);
		if (image != null) {
			this.cloudinaryService.removeImageFromCloudinary(image.getImage(), path_category);

		}
		areaRepository.deleteById(id);
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Delete Area SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), JsonConverter.convertToJSON("id", id), "Delete Area");
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(), "Create Area");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public Integer getCountAreas(int limit, int page, Integer status, String name, String slug, String createdAt,
			String updatedAt, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		List<String> sortByList = new ArrayList<String>();
		List<AreaDTO> listAreaDto;
		List<Area> listArea;
		List<Sort.Order> sortOrders = new ArrayList<>();

		Pageable pageable = null;

		if (page != 0) {
			pageable = PageRequest.of(page - 1, limit);
			if (!StringUtils.isEmpty(sortBy))
				sortByList = Arrays.asList(sortBy.split(","));

			for (String sb : sortByList) {
				boolean isDescending = sb.endsWith("Desc");

				if (isDescending && !StringUtils.isEmpty(sortBy))
					sb = sb.substring(0, sb.length() - 4).trim();

				for (SortField sortField : validSortFields) {
					if (sortField.toString().equals(sb.trim())) {
						sortOrders.add(isDescending ? Sort.Order.desc(sb) : Sort.Order.asc(sb));
						break;
					}
				}
			}

		}
		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));

		listArea = areaRepository.findWithFilters(status, name, slug, createdAt, updatedAt, pageable, entityManager);
		return listArea.size();
	}

}
