package com.cafe.website.serviceImp;

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
import com.cafe.website.entity.Notification;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.User;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.ImageDTO;
import com.cafe.website.payload.NotificationCreateDTO;
import com.cafe.website.payload.NotificationDTO;
import com.cafe.website.payload.NotificationDeleteDTO;
import com.cafe.website.payload.NotificationUpdateDTO;
import com.cafe.website.payload.ReviewDTO;
import com.cafe.website.repository.NotificationRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.service.NotificationService;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.NotificationMapper;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class NotificationServiceImp implements NotificationService {

	@PersistenceContext
	private EntityManager entityManager;
	private NotificationRepository notificationRepository;
	private UserRepository userRepository;
	private NotificationMapper notificationMapper;
	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImp.class);

	public NotificationServiceImp(EntityManager entityManager, NotificationRepository notificationRepository,
			UserRepository userRepository, NotificationMapper notificationMapper) {
		super();
		this.entityManager = entityManager;
		this.notificationRepository = notificationRepository;
		this.userRepository = userRepository;
		this.notificationMapper = notificationMapper;
	}

	@Override
	public NotificationDTO createNotification(NotificationCreateDTO notificationCreateDto, HttpServletRequest request) {
		User sender = userRepository.findById(notificationCreateDto.getSenderId())
				.orElseThrow(() -> new ResourceNotFoundException("Sender", "id", notificationCreateDto.getSenderId()));
		Notification notifi = notificationMapper.dtoToEntity(notificationCreateDto);
		List<User> listUsers = new ArrayList<>();
		List<User> listSenders = new ArrayList<>();
		notificationCreateDto.getListUsers().forEach((id) -> {
			User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
			listUsers.add(user);
		});
		listSenders.add(sender);
		notifi.setUsers(listUsers);
		notifi.setSenders(listSenders);
		notificationRepository.save(notifi);
		NotificationDTO notifiDto = MapperUtils.mapToDTO(notifi, NotificationDTO.class);
		listUsers.forEach(user -> {
			notifiDto.getListUsersId().add(user.getId());
		});
		notifiDto.setSenderId(sender.getId());

		return notifiDto;
	}

	@Override
	public List<NotificationDTO> getListNotifications(Integer limit, Integer page, String url, String message,
			String original, Integer state, Integer status, Long userId, Long senderId, String createdAt,
			String updatedAt, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.UPDATEDAT, SortField.CREATEDAT,
				SortField.IDDESC, SortField.UPDATEDATDESC, SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<NotificationDTO> listNotifiDto;
		List<Notification> listNotifi;
		List<Sort.Order> sortOrders = new ArrayList<>();

		// sort
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

		listNotifi = notificationRepository.findWithFilters(message, original, state, userId, senderId, createdAt,
				updatedAt, pageable, entityManager);
		listNotifiDto = listNotifi.stream().map(notifi -> {
			NotificationDTO notifiDto = MapperUtils.mapToDTO(notifi, NotificationDTO.class);

			notifi.getUsers().forEach(user -> {
				notifiDto.getListUsersId().add(user.getId());
			});
			notifi.getSenders().forEach(sender -> {
				notifiDto.setSenderId(sender.getId());

			});
			return notifiDto;
		}).collect(Collectors.toList());

		return listNotifiDto;
	}

	@Override
	public NotificationDTO updateNotification(NotificationUpdateDTO notificationUpdateDto, HttpServletRequest request) {
		Notification notifi = notificationRepository.findById(notificationUpdateDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Notification", "id", notificationUpdateDto.getId()));
		notificationMapper.updateNotificationFromNotificationUpdateDTO(notificationUpdateDto, notifi);

		notificationRepository.save(notifi);

		NotificationDTO notifiDto = MapperUtils.mapToDTO(notifi, NotificationDTO.class);
		notifi.getUsers().forEach(user -> {
			notifiDto.getListUsersId().add(user.getId());
		});
		notifi.getSenders().forEach(sender -> {
			notifiDto.setSenderId(sender.getId());

		});
		return notifiDto;
	}

	@Override
	public void deleteNotification(NotificationDeleteDTO notificationDeleteDTO, HttpServletRequest request) {
		Notification notifi = notificationRepository.findById(notificationDeleteDTO.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Notification", "id", notificationDeleteDTO.getId()));
		if (notifi.getStatus() == 3)
			throw new CafeAPIException(HttpStatus.BAD_REQUEST, "Notification's already disabled");
		notifi.setStatus(3);
		notificationRepository.save(notifi);

	}

}
