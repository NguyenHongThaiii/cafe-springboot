package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.SortField;
import com.cafe.website.constant.StatusLog;
import com.cafe.website.entity.Product;
import com.cafe.website.entity.ProductSaved;
import com.cafe.website.entity.ProductSchedule;
import com.cafe.website.entity.User;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.payload.MenuDTO;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductSavedCreateDTO;
import com.cafe.website.payload.ProductSavedDTO;
import com.cafe.website.payload.ProductScheduleDTO;
import com.cafe.website.payload.UserDTO;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.ProductSavedRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.LogService;
import com.cafe.website.service.ProductSavedService;
import com.cafe.website.service.ReviewService;
import com.cafe.website.util.JsonConverter;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.MethodUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ProductSavedServiceImp implements ProductSavedService {
	@PersistenceContext
	private EntityManager entityManager;
	private ProductSavedRepository productSavedRepository;
	private UserRepository userRepository;
	private ProductRepository productRepository;
	private LogService logService;
	private AuthService authService;
	private ReviewService reviewService;
	private ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public ProductSavedServiceImp(EntityManager entityManager, ProductSavedRepository productSavedRepository,
			UserRepository userRepository, ProductRepository productRepository, LogService logService,
			AuthService authService, ReviewService reviewService, ObjectMapper objectMapper) {
		super();
		this.entityManager = entityManager;
		this.productSavedRepository = productSavedRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.logService = logService;
		this.authService = authService;
		this.reviewService = reviewService;
		this.objectMapper = objectMapper;
	}

	@Override
	public void toggleProductSaved(ProductSavedCreateDTO productSavedCreate, HttpServletRequest request) {
		Long productId = productSavedCreate.getProductId();
		Long userId = productSavedCreate.getUserId();
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		if (productSavedRepository.existsByUserIdAndProductId(userId, productId)) {
			productSavedRepository.deleteByUserIdAndProductId(userId, productId);
		} else {
			ProductSaved productSaved = new ProductSaved();
			productSaved.setProduct(product);
			productSaved.setUser(user);

			productSavedRepository.save(productSaved);
		}
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Toggle Product Saved SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(productSavedCreate),
					"Toggle Product Saved Discount");
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Toggle Product Saved Discount");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public List<ProductDTO> getListProductSavedByUser(Integer limit, Integer page, Integer status, String slugArea,
			String slugKind, String slugConvenience, String slugPurpose, Long userId, String createdAt,
			String updatedAt, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.PRICEMIN,
				SortField.PRICEMAX, SortField.UPDATEDAT, SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC,
				SortField.PRICEMINDESC, SortField.PRICEMAXDESC, SortField.UPDATEDATDESC, SortField.CREATEDATDESC);

		List<String> sortByList = new ArrayList<String>();
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

			if (!sortOrders.isEmpty())
				pageable = PageRequest.of(page - 1, limit, Sort.by(sortOrders));
		}

		List<ProductSaved> list = productSavedRepository.findWithFilters(status, slugArea, slugKind, slugConvenience, slugPurpose, userId, createdAt, updatedAt, pageable, entityManager);
		List<ProductDTO> listProductDto = new ArrayList<>();
		for (ProductSaved ps : list) {
			ProductDTO pdto = MapperUtils.mapToDTO(ps.getProduct(), ProductDTO.class);
			List<ProductScheduleDTO> listScheduleDto = new ArrayList<>();
			for (ProductSchedule schedule : ps.getProduct().getSchedules()) {
				ProductScheduleDTO scheduleDto = MapperUtils.mapToDTO(schedule, ProductScheduleDTO.class);
				listScheduleDto.add(scheduleDto);
			}
			pdto.setListImage(ImageDTO.generateListImageDTO(ps.getProduct().getListImages()));
			pdto.setListMenu(MenuDTO.generateListMenuDTO(ps.getProduct().getListMenus()));
			pdto.setSchedules(listScheduleDto);
			pdto.setOwner(MapperUtils.mapToDTO(ps.getUser(), UserDTO.class));
			pdto.setAvgRating(reviewService.getRatingByReviewId(ps.getId()));
			listProductDto.add(pdto);

		}
		return listProductDto;
	}

	@Override
	public Boolean checkIsSavedByUserId(Long userId, Long productId) {
		if (productSavedRepository.existsByUserIdAndProductId(userId, productId))
			return true;
		return false;
	}
}
