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
import com.cafe.website.entity.Convenience;
import com.cafe.website.entity.Image;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ConvenienceCreateDTO;
import com.cafe.website.payload.ConvenienceDTO;
import com.cafe.website.payload.ConvenienceUpdateDTO;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.repository.ConvenienceRepository;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.ConvenienceService;
import com.cafe.website.service.LogService;
import com.cafe.website.util.ConvenienceMapper;
import com.cafe.website.util.JsonConverter;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.MethodUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ConvenienceServiceImp implements ConvenienceService {
	@PersistenceContext
	private EntityManager entityManager;
	private ConvenienceMapper convenienceMapper;
	private CloudinaryService cloudinaryService;
	private ConvenienceRepository convenienceRepository;
	private ImageRepository imageRepository;
	private LogService logService;
	private AuthService authService;
	private ObjectMapper objectMapper;

	private Slugify slugify = Slugify.builder().build();
	private static final Logger logger = LoggerFactory.getLogger(ConvenienceServiceImp.class);
	@Value("${app.path-category-convenience}")
	private String path_category;

	public ConvenienceServiceImp(EntityManager entityManager, ConvenienceMapper convenienceMapper,
			CloudinaryService cloudinaryService, ConvenienceRepository convenienceRepository,
			ImageRepository imageRepository, LogService logService, AuthService authService,
			ObjectMapper objectMapper) {
		super();
		this.entityManager = entityManager;
		this.convenienceMapper = convenienceMapper;
		this.cloudinaryService = cloudinaryService;
		this.convenienceRepository = convenienceRepository;
		this.imageRepository = imageRepository;
		this.logService = logService;
		this.authService = authService;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<ConvenienceDTO> getListConveniences(int limit, int page, String name, String slug, String createdAt,
			String updatedAt, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<ConvenienceDTO> listConvenienceDto;
		List<Convenience> listConvenience;
		List<Sort.Order> sortOrders = new ArrayList<>();
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

		if (!sortOrders.isEmpty())
			pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));

		listConvenience = convenienceRepository.findWithFilters(name, slug, createdAt, updatedAt, pageable,
				entityManager);

		listConvenienceDto = listConvenience.stream().map(convenience -> {
			ConvenienceDTO convenienceDto = MapperUtils.mapToDTO(convenience, ConvenienceDTO.class);
			convenienceDto.setImage(ImageDTO.generateImageDTO(convenience.getImage()));
			return convenienceDto;
		}).collect(Collectors.toList());

		return listConvenienceDto;
	}

	@Override
	public ConvenienceDTO getConvenienceById(Long id) {
		Convenience convenience = convenienceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Convenience", "id", id));
		ConvenienceDTO ConvenienceDto = MapperUtils.mapToDTO(convenience, ConvenienceDTO.class);
		ConvenienceDto.setImage(ImageDTO.generateImageDTO(convenience.getImage()));
		return ConvenienceDto;
	}

	@Override
	public ConvenienceDTO getConvenienceBySlug(String slug) {
		Convenience convenience = convenienceRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("Convenience", "slug", slug));
		ConvenienceDTO ConvenienceDto = MapperUtils.mapToDTO(convenience, ConvenienceDTO.class);
		ConvenienceDto.setImage(ImageDTO.generateImageDTO(convenience.getImage()));
		return ConvenienceDto;
	}

	@Override
	public ConvenienceDTO createConvenience(ConvenienceCreateDTO convenienceCreateDto, HttpServletRequest request)
			throws IOException {
		if (convenienceRepository.existsBySlug(slugify.slugify(convenienceCreateDto.getSlug())))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (convenienceRepository.existsByName(convenienceCreateDto.getName()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		Convenience convenience = MapperUtils.mapToEntity(convenienceCreateDto, Convenience.class);
		convenience.setSlug(slugify.slugify(convenienceCreateDto.getSlug()));

		String url = cloudinaryService.uploadImage(convenienceCreateDto.getImageFile(), path_category, "image");
		Image image = new Image();
		image.setConvenience(convenience);
		image.setImage(url);
		convenience.setImage(image);

		Convenience newConvenience = convenienceRepository.save(convenience);
		ConvenienceDTO newConvenienceDto = MapperUtils.mapToDTO(newConvenience, ConvenienceDTO.class);
		newConvenienceDto.setImage(ImageDTO.generateImageDTO(newConvenience.getImage()));
		convenienceCreateDto.setDataToLogging(convenienceCreateDto.getImageFile().getOriginalFilename(),
				convenienceCreateDto.getImageFile().getContentType(), convenienceCreateDto.getImageFile().getSize(),
				() -> {
					convenienceCreateDto.setImageFile(null);
				});
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Create Convenience SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(convenienceCreateDto),
					"Create Convenience");
			return newConvenienceDto;
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Create Convenience");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public ConvenienceDTO updateConvenience(Long id, ConvenienceUpdateDTO convenienceUpdateDto,
			HttpServletRequest request) throws IOException {
		ConvenienceDTO newDto = this.getConvenienceById(id);
		Image image = new Image();

		if (convenienceRepository.existsBySlugAndIdNot(slugify.slugify(convenienceUpdateDto.getSlug()), newDto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (convenienceRepository.existsByNameAndIdNot(convenienceUpdateDto.getName(), newDto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");
		Map<String, Object> logData = new HashMap<>();

		Convenience convenience = convenienceMapper.dtoToEntity(newDto);
		ConvenienceDTO convenienceDTO = MapperUtils.mapToDTO(convenienceUpdateDto, ConvenienceDTO.class);
		if (convenienceUpdateDto.getImageFile() != null) {
			String url = cloudinaryService.uploadImage(convenienceUpdateDto.getImageFile(), path_category, "image");
			image.setConvenience(convenience);
			image.setImage(url);
			convenience.setImage(image);
			convenienceUpdateDto.setDataToLogging(convenienceUpdateDto.getImageFile().getOriginalFilename(),
					convenienceUpdateDto.getImageFile().getContentType(), convenienceUpdateDto.getImageFile().getSize(),
					() -> {
						convenienceUpdateDto.setImageFile(null);
					});
		}

		convenienceDTO.setId(id);
		if (convenienceUpdateDto.getSlug() != null)
			convenienceDTO.setSlug(slugify.slugify(convenienceUpdateDto.getSlug()));
		convenienceMapper.updateConvenienceFromDto(convenienceDTO, convenience);
		convenienceRepository.save(convenience);
		logData.put("id", id);
		logData.put("convenienceUpdateDto", convenienceUpdateDto);
		ConvenienceDTO newConvenienceDTO = MapperUtils.mapToDTO(convenience, ConvenienceDTO.class);
		if (convenience.getImage() != null)
			newConvenienceDTO.setImage(ImageDTO.generateImageDTO(convenience.getImage()));
		else
			newConvenienceDTO.setImage(newDto.getImage());

		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Update Convenience SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(logData), "Update Convenience");
			return newConvenienceDTO;
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Update Convenience");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public void deleteConvenience(Long id, HttpServletRequest request) throws IOException {
		convenienceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Convenience", "id", id + ""));
		Image image = imageRepository.findImageByKindId(id).orElse(null);
		if (image != null)
			this.cloudinaryService.removeImageFromCloudinary(image.getImage(), path_category);

		convenienceRepository.deleteById(id);
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Delete Convenience SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), JsonConverter.convertToJSON("id", id), "Delete Convenience");
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Delete Convenience");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

}
