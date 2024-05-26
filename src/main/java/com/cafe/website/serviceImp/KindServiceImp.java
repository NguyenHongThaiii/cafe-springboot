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
import com.cafe.website.entity.Kind;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.AreaDTO;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.payload.KindCreateDTO;
import com.cafe.website.payload.KindDTO;
import com.cafe.website.payload.KindUpdateDTO;
import com.cafe.website.repository.ImageRepository;
import com.cafe.website.repository.KindRepository;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.KindService;
import com.cafe.website.service.LogService;
import com.cafe.website.util.JsonConverter;
import com.cafe.website.util.KindMapper;
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
public class KindServiceImp implements KindService {
	@PersistenceContext
	private EntityManager entityManager;
	private KindMapper kindMapper;
	private CloudinaryService cloudinaryService;
	private KindRepository kindRepository;
	private ImageRepository imageRepository;
	private LogService logService;
	private AuthService authService;
	private ObjectMapper objectMapper;

	private Slugify slugify = Slugify.builder().build();
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);
	@Value("${app.path-category-kind}")
	private String path_category;

	public KindServiceImp(EntityManager entityManager, KindMapper kindMapper, CloudinaryService cloudinaryService,
			KindRepository kindRepository, ImageRepository imageRepository, LogService logService,
			AuthService authService, ObjectMapper objectMapper) {
		super();
		this.entityManager = entityManager;
		this.kindMapper = kindMapper;
		this.cloudinaryService = cloudinaryService;
		this.kindRepository = kindRepository;
		this.imageRepository = imageRepository;
		this.logService = logService;
		this.authService = authService;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<KindDTO> getListKinds(int limit, int page, String name, String slug, String createdAt, String updatedAt,
			String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		List<String> sortByList = new ArrayList<String>();
		List<KindDTO> listKindDto;
		List<Kind> listKind;
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

		listKind = kindRepository.findWithFilters(name, slug, createdAt, updatedAt, pageable, entityManager);

		listKindDto = listKind.stream().map(kind -> {
			KindDTO kindDto = MapperUtils.mapToDTO(kind, KindDTO.class);
			Image image = imageRepository.findImageByKindId(kind.getId()).orElse(null);
			kindDto.setImage(ImageDTO.generateImageDTO(image));
			return kindDto;
		}).collect(Collectors.toList());

		return listKindDto;
	}

	@Override
	public KindDTO getKindById(Long id) {
		Kind kind = kindRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("kind", "id", id + ""));
		KindDTO kindDto = MapperUtils.mapToDTO(kind, KindDTO.class);
		kindDto.setImage(ImageDTO.generateImageDTO(kind.getImage()));
		return kindDto;
	}

	@Override
	public KindDTO getKindBySlug(String slug) {
		Kind kind = kindRepository.findBySlug(slug)
				.orElseThrow(() -> new ResourceNotFoundException("Kind", "slug", slug));
		KindDTO kindDto = MapperUtils.mapToDTO(kind, KindDTO.class);
		kindDto.setImage(ImageDTO.generateImageDTO(kind.getImage()));
		return kindDto;
	}

	@Override
	public KindDTO createKind(KindCreateDTO kindCreateDto, HttpServletRequest request) throws IOException {
		if (kindRepository.existsBySlug(slugify.slugify(kindCreateDto.getSlug())))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (kindRepository.existsByName(kindCreateDto.getName()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		Kind kind = MapperUtils.mapToEntity(kindCreateDto, Kind.class);
		kind.setSlug(slugify.slugify(kindCreateDto.getName()));
		String url = cloudinaryService.uploadImage(kindCreateDto.getImageFile(), path_category, "image");

		Image image = new Image();
		image.setKind(kind);
		image.setImage(url);
		kind.setImage(image);

		Kind newKind = kindRepository.save(kind);

		KindDTO newKindDto = MapperUtils.mapToDTO(newKind, KindDTO.class);
		newKindDto.setImage(ImageDTO.generateImageDTO(newKind.getImage()));
		kindCreateDto.setDataToLogging(kindCreateDto.getImageFile().getOriginalFilename(),
				kindCreateDto.getImageFile().getContentType(), kindCreateDto.getImageFile().getSize(), () -> {
					kindCreateDto.setImageFile(null);
				});
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Create Kind SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(kindCreateDto), "Create Kind");
			return newKindDto;
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request), e.getMessage().substring(0, 255),
					StatusLog.FAILED.toString(), "Create Kind");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public KindDTO updateKind(Long id, KindUpdateDTO kindUpdateDto, HttpServletRequest request) throws IOException {
		KindDTO newDto = this.getKindById(id);

		if (kindRepository.existsBySlugAndIdNot(slugify.slugify(kindUpdateDto.getSlug()), newDto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (kindRepository.existsByNameAndIdNot(kindUpdateDto.getName(), newDto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");
		Map<String, Object> logData = new HashMap<>();

		Kind kind = kindRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("kind", "id", id + ""));
		KindDTO kindDto = MapperUtils.mapToDTO(kindUpdateDto, KindDTO.class);
		if (kindUpdateDto.getImageFile() != null) {
			String url = cloudinaryService.uploadImage(kindUpdateDto.getImageFile(), path_category, "image");
			Image image = new Image();
			image.setKind(kind);
			image.setImage(url);
			kind.setImage(image);
			kindUpdateDto.setDataToLogging(kindUpdateDto.getImageFile().getOriginalFilename(),
					kindUpdateDto.getImageFile().getContentType(), kindUpdateDto.getImageFile().getSize(), () -> {
						kindUpdateDto.setImageFile(null);
					});
		}
		kindDto.setId(id);
		if (kindUpdateDto.getSlug() != null)
			kindDto.setSlug(slugify.slugify(kindUpdateDto.getSlug()));

		kindMapper.updateKindFromDto(kindDto, kind);
		kindRepository.save(kind);
		logData.put("id", id);
		logData.put("kindUpdateDto", kindUpdateDto);
		KindDTO newKindDto = MapperUtils.mapToDTO(kind, KindDTO.class);
		newKindDto.setImage(ImageDTO.generateImageDTO(kind.getImage()));

		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Update Kind SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(logData), "Update Kind");
			return newKindDto;

		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(), "Update Kind");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public void deleteKind(Long id, HttpServletRequest request) throws IOException {
		kindRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("kind", "id", id + ""));
		Image image = imageRepository.findImageByKindId(id).orElse(null);
		if (image != null)
			this.cloudinaryService.removeImageFromCloudinary(image.getImage(), path_category);

		kindRepository.deleteById(id);
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Delete Kind SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), JsonConverter.convertToJSON("id", id), "Delete Kind");
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(), "Delete Kind");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public Integer getCountKinds(int limit, int page, String name, String slug, String createdAt, String updatedAt,
			String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		List<String> sortByList = new ArrayList<String>();
		List<Kind> listKind;
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

		listKind = kindRepository.findWithFilters(name, slug, createdAt, updatedAt, pageable, entityManager);
		return listKind.size();
	}

}
