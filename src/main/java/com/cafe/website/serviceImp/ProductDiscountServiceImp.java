package com.cafe.website.serviceImp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import com.cafe.website.constant.StatusLog;
import com.cafe.website.entity.Product;
import com.cafe.website.entity.ProductDiscount;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductDiscountCreateDTO;
import com.cafe.website.payload.ProductDiscountDTO;
import com.cafe.website.payload.ProductDiscountUpdateDTO;
import com.cafe.website.repository.ProductDiscountRepository;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.LogService;
import com.cafe.website.service.ProductDiscountService;
import com.cafe.website.util.JsonConverter;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.MethodUtil;
import com.cafe.website.util.ProductDiscountMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ProductDiscountServiceImp implements ProductDiscountService {
	@PersistenceContext
	private EntityManager entityManager;
	private ProductDiscountRepository productDiscountRepository;
	private ProductRepository productRepository;
	private ProductDiscountMapper productDiscountMapper;
	private LogService logService;
	private AuthService authService;
	private ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public ProductDiscountServiceImp(EntityManager entityManager, ProductDiscountRepository productDiscountRepository,
			ProductRepository productRepository, ProductDiscountMapper productDiscountMapper, LogService logService,
			AuthService authService, ObjectMapper objectMapper) {
		super();
		this.entityManager = entityManager;
		this.productDiscountRepository = productDiscountRepository;
		this.productRepository = productRepository;
		this.productDiscountMapper = productDiscountMapper;
		this.logService = logService;
		this.authService = authService;
		this.objectMapper = objectMapper;
	}

	@Override
	public ProductDiscountDTO createProductDiscount(ProductDiscountCreateDTO productDiscountCreateDto,
			HttpServletRequest request) {
		Product product = productRepository.findById(productDiscountCreateDto.getProductId()).orElseThrow(
				() -> new ResourceNotFoundException("Product", "id", productDiscountCreateDto.getProductId()));

		ProductDiscount productDiscountEntity = productDiscountRepository.findByProductId(product.getId()).orElse(null);
		if (productDiscountEntity != null && new Date().getTime() < productDiscountEntity.getExpiryDate())
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Product going on other events");
		if (productDiscountEntity != null) {
			productDiscountRepository.deleteById(productDiscountEntity.getId());
		}
		ProductDiscount productDiscount = new ProductDiscount();
		productDiscount.setName(productDiscountCreateDto.getName());
		productDiscount.setProduct(product);
		productDiscount.setPercent(productDiscountCreateDto.getPercent());
		productDiscount.setExpiryDate(new Date().getTime() + productDiscountCreateDto.getExpiryDate());
		productDiscountRepository.save(productDiscount);
		ProductDiscountDTO productDiscountDto = MapperUtils.mapToDTO(productDiscount, ProductDiscountDTO.class);
		productDiscountDto.setProductDto(MapperUtils.mapToDTO(product, ProductDTO.class));
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Create Product Discount SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), objectMapper.writeValueAsString(productDiscountCreateDto),
					"Create Product Discount");
			return productDiscountDto;
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Create Product Discount");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public void deleteProductDiscountById(Integer id, HttpServletRequest request) {
		ProductDiscount productDiscount = productDiscountRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ProductDiscount", "id", id));
		productDiscountRepository.delete(productDiscount);
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Delete Product Discount SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), JsonConverter.convertToJSON("id", id),
					"Delete Product Discount");
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Delete Product Discount");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}
	}

	@Override
	public ProductDiscountDTO getProductDiscountByProductId(Integer productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
		ProductDiscount productDiscount = productDiscountRepository.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("ProductDiscount", "id_product", productId));
		ProductDiscountDTO productDiscountDto = MapperUtils.mapToDTO(productDiscount, ProductDiscountDTO.class);

		productDiscountDto.setProductDto(MapperUtils.mapToDTO(product, ProductDTO.class));

		return productDiscountDto;
	}

	@Override
	public List<ProductDiscountDTO> getListProductDiscount(int limit, int page, String name, Boolean expriteDays,
			Integer percent, String createdAt, String updatedAt, String sortBy) {

		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<ProductDiscount> productDiscountList = null;
		List<Sort.Order> sortOrders = new ArrayList<>();
		List<ProductDiscountDTO> listProductDiscountDto;

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

		productDiscountList = productDiscountRepository.findWithFilters(name, expriteDays, percent, createdAt,
				updatedAt, pageable, entityManager);
		listProductDiscountDto = productDiscountList.stream().map(productDiscount -> {
			ProductDiscountDTO temp = MapperUtils.mapToDTO(productDiscount, ProductDiscountDTO.class);
			temp.setProductDto(MapperUtils.mapToDTO(productDiscount.getProduct(), ProductDTO.class));
			return temp;
		}).collect(Collectors.toList());

		return listProductDiscountDto;

	}

	@Override
	public ProductDiscountDTO updateProductDiscount(Integer productId,
			ProductDiscountUpdateDTO productDiscountUpdateDto, HttpServletRequest request) {
		ProductDiscount productDiscount = productDiscountRepository.findByProductId(productId)
				.orElseThrow(() -> new ResourceNotFoundException("ProductDiscount", "id_product", productId));
		ProductDiscountDTO productDiscountDto = MapperUtils.mapToDTO(productDiscountUpdateDto,
				ProductDiscountDTO.class);

		productDiscountDto.setId(productDiscount.getId());
		productDiscountMapper.updateProductDiscountFromDto(productDiscountDto, productDiscount);
		productDiscountRepository.save(productDiscount);

		ProductDiscountDTO res = MapperUtils.mapToDTO(productDiscount, ProductDiscountDTO.class);
		res.setProductDto(MapperUtils.mapToDTO(productDiscount.getProduct(), ProductDTO.class));
		try {
			logService.createLog(request, authService.getUserFromHeader(request), "Update Product Discount SUCCESSFULY",
					StatusLog.SUCCESSFULLY.toString(), JsonConverter.convertToJSON("productId", productId) + " "
							+ objectMapper.writeValueAsString(productDiscountUpdateDto),
					"Update Product Discount");
			return res;
		} catch (IOException e) {
			logService.createLog(request, authService.getUserFromHeader(request),
					MethodUtil.handleSubstringMessage(e.getMessage()), StatusLog.FAILED.toString(),
					"Update Product Discount");
			throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
		}

	}

}
