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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.website.constant.SortField;
import com.cafe.website.entity.Area;
import com.cafe.website.entity.Convenience;
import com.cafe.website.entity.Kind;
import com.cafe.website.entity.Product;
import com.cafe.website.entity.Purpose;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ProductCreateDTO;
import com.cafe.website.payload.ProductDTO;
import com.cafe.website.payload.ProductUpdateDTO;
import com.cafe.website.repository.AreaRepository;
import com.cafe.website.repository.ConvenienceRepository;
import com.cafe.website.repository.KindRepository;
import com.cafe.website.repository.ProductRepository;
import com.cafe.website.repository.PurposeRepository;
import com.cafe.website.service.CloudinaryService;
import com.cafe.website.service.ProductService;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;

import io.micrometer.common.util.StringUtils;

@Service
public class ProductServiceImp implements ProductService {
	private ProductRepository productRepository;
	private AreaRepository areaRepository;
	private KindRepository kindRepository;
	private PurposeRepository purposeRepository;
	private ConvenienceRepository conveRepository;
	private ProductMapper productMapper;
	private CloudinaryService cloudinaryService;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);
	ObjectMapper objMapper = new ObjectMapper();
	private Slugify slugify = Slugify.builder().build();

	public ProductServiceImp(ProductRepository productRepository, AreaRepository areaRepository,
			KindRepository kindRepository, PurposeRepository purposeRepository, ConvenienceRepository conveRepository,
			ProductMapper productMapper, CloudinaryService cloudinaryService) {
		super();
		this.productRepository = productRepository;
		this.areaRepository = areaRepository;
		this.kindRepository = kindRepository;
		this.purposeRepository = purposeRepository;
		this.conveRepository = conveRepository;
		this.productMapper = productMapper;
		this.cloudinaryService = cloudinaryService;
	}

	@Override
	public List<ProductDTO> getListProducts(int limit, int page, String name, String sortBy) {

		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.PRICEMIN,
				SortField.PRICEMAX, SortField.UPDATEDAT, SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC,
				SortField.PRICEMINDESC, SortField.PRICEMAXDESC, SortField.UPDATEDATDESC, SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<Product> productList = null;
		List<Sort.Order> sortOrders = new ArrayList<>();
		List<ProductDTO> listProductDto;

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
			productList = productRepository.findByNameContainingIgnoreCase(name, pageable).getContent();
		else
			productList = productRepository.findAll(pageable).getContent();

		listProductDto = productList.stream().map(product -> MapperUtils.mapToDTO(product, ProductDTO.class))
				.collect(Collectors.toList());

		return listProductDto;
	}

	@Override
	public ProductDTO createProduct(ProductCreateDTO productCreateDto) throws IOException {
		List<String> listMenus = new ArrayList<>();

		ProductDTO pdto = MapperUtils.mapToEntity(productCreateDto, ProductDTO.class);
		Product product = new Product();

		List<Area> listAreas = this.getListFromIds(productCreateDto.getArea_id(), areaRepository, "area");
		List<Purpose> listPurposes = this.getListFromIds(productCreateDto.getPurpose_id(), purposeRepository,
				"purpose");
		List<Kind> listKinds = this.getListFromIds(productCreateDto.getArea_id(), kindRepository, "kind");
		List<Convenience> listCon = this.getListFromIds(productCreateDto.getArea_id(), conveRepository, "convenience");

		pdto.setAreas(listAreas);
		pdto.setKinds(listKinds);
		pdto.setPurposes(listPurposes);
		pdto.setConveniences(listCon);
		productMapper.updateProductFromDto(pdto, product);

		product.setId(0);
		cloudinaryService.uploadImages(listMenus, productCreateDto.getListMenuFile(), "cafe-springboot/menu", "image");
		String jsonListMenu = this.objMapper.writeValueAsString(listMenus);
		product.setListMenu(jsonListMenu);

		productRepository.save(product);

		return MapperUtils.mapToDTO(product, ProductDTO.class);
	}

	@Override
	public ProductDTO updateProduct(int id, ProductUpdateDTO productUpdateDto) throws IOException {
		ProductDTO pdto = this.getProductById(id);
		if (productRepository.existsBySlugAndIdNot(slugify.slugify(productUpdateDto.getSlug()), pdto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Slug is already exists!");
		if (productRepository.existsByNameAndIdNot(productUpdateDto.getName(), pdto.getId()))
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Name is already exists!");

		Product product = MapperUtils.mapToEntity(pdto, Product.class);
		productUpdateDto.setId(id);
		productMapper.updateProductDtoFromProductUpdateDto(pdto, productUpdateDto);

		List<Area> listAreas = this.getListFromIds(productUpdateDto.getArea_id(), areaRepository, "area");
		List<Purpose> listPurposes = this.getListFromIds(productUpdateDto.getPurpose_id(), purposeRepository,
				"purpose");
		List<Kind> listKinds = this.getListFromIds(productUpdateDto.getArea_id(), kindRepository, "kind");
		List<Convenience> listCon = this.getListFromIds(productUpdateDto.getArea_id(), conveRepository, "convenience");

		pdto.setAreas(listAreas);
		pdto.setKinds(listKinds);
		pdto.setPurposes(listPurposes);
		pdto.setConveniences(listCon);
		pdto.setSlug(slugify.slugify(productUpdateDto.getSlug()));

//		delete old images and add new images

		if (productUpdateDto.getListMenuFile() != null
				&& productUpdateDto.getListMenuFile() instanceof List<MultipartFile>) {
			List<String> listMenus = new ArrayList<>();
			cloudinaryService.uploadImages(listMenus, productUpdateDto.getListMenuFile(), "cafe-springboot/menu",
					"image");
			String urlImageMenu = this.objMapper.writeValueAsString(listMenus);
			pdto.setListMenu(urlImageMenu);
			this.deleteProduct(pdto.getId());
		}

		productMapper.updateProductFromDto(pdto, product);
		productRepository.save(product);

		return pdto;
	}

	@Override
	public ProductDTO getProductById(int id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id + ""));

		ProductDTO productDto = MapperUtils.mapToDTO(product, ProductDTO.class);

		return productDto;
	}

	@Override
	public String deleteProduct(int id) throws IOException {
		ProductDTO productDto = this.getProductById(id);
		String path_menu = "cafe-springboot/menu/";
		String path_blogs = "cafe-springboot/blogs/";
		String listMenusDb = productDto.getListMenu();

		this.cloudinaryService.removeListImageFromCloudinary(listMenusDb, path_menu);
		this.cloudinaryService.removeListImageFromCloudinary(listMenusDb, path_blogs);

		productRepository.deleteById(id);

		return "Delete successfully";
	}

	private <T> List<T> getListFromIds(List<Integer> ids, JpaRepository<T, Integer> repository, String entityName) {
		List<T> resultList = new ArrayList<>();
		if (ids != null)
			ids.forEach(id -> {
				T entity = repository.findById(id).orElse(null);
				if (entity != null)
					resultList.add(entity);
			});
		return resultList;
	}
}
