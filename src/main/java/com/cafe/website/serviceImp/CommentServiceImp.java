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
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import com.cafe.website.constant.SortField;
import com.cafe.website.constant.StatusLog;
import com.cafe.website.entity.Comment;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.User;
import com.cafe.website.exception.CafeAPIException;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.CommentCreateDTO;
import com.cafe.website.payload.CommentDTO;
import com.cafe.website.payload.CommentUpdateDTO;
import com.cafe.website.repository.CommentRepository;
import com.cafe.website.repository.ReviewRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.service.AuthService;
import com.cafe.website.service.CommentService;
import com.cafe.website.service.LogService;
import com.cafe.website.util.JsonConverter;
import com.cafe.website.util.MapperUtils;
import com.cafe.website.util.MethodUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class CommentServiceImp implements CommentService {
	@PersistenceContext
	private EntityManager entityManager;
	private CommentRepository commentRepository;
	private UserRepository userRepository;
	private ReviewRepository reviewRepository;
	private LogService logService;
	private AuthService authService;
	private ObjectMapper objectMapper;

	public CommentServiceImp(EntityManager entityManager, CommentRepository commentRepository,
			UserRepository userRepository, ReviewRepository reviewRepository, LogService logService,
			AuthService authService, ObjectMapper objectMapper) {
		super();
		this.entityManager = entityManager;
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
		this.reviewRepository = reviewRepository;
		this.logService = logService;
		this.authService = authService;
		this.objectMapper = objectMapper;
	}

	@Override
	public List<CommentDTO> getListComments(int limit, int page, String name, Integer userId, Integer reviewId,
			String createdAt, String updatedAt, String sortBy) {
		List<SortField> validSortFields = Arrays.asList(SortField.ID, SortField.NAME, SortField.UPDATEDAT,
				SortField.CREATEDAT, SortField.IDDESC, SortField.NAMEDESC, SortField.UPDATEDATDESC,
				SortField.CREATEDATDESC);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<String> sortByList = new ArrayList<String>();
		List<CommentDTO> listCommentDto;
		List<Comment> listComment;
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
		listComment = commentRepository.findWithFilters(name, reviewId, userId, createdAt, updatedAt, pageable,
				entityManager);

		listCommentDto = listComment.stream().map(comment -> {
			CommentDTO commentDto = MapperUtils.mapToDTO(comment, CommentDTO.class);
			commentDto.setReivewId(comment.getReview().getId());
			return commentDto;
		}).collect(Collectors.toList());

		return listCommentDto;
	}

	@Override
	public CommentDTO getCommentById(int id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

		return MapperUtils.mapToDTO(comment, CommentDTO.class);
	}

	@Override
	public CommentDTO createComment(CommentCreateDTO commentCreateDto, SimpMessageHeaderAccessor headerAccessor) {

		Review review = reviewRepository.findById(commentCreateDto.getReviewId())
				.orElseThrow(() -> new ResourceNotFoundException("Review", "id", commentCreateDto.getReviewId()));
		User user = userRepository.findById(commentCreateDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", commentCreateDto.getUserId()));
		String userAgent = (String) headerAccessor.getSessionAttributes().get("userAgent");
		String endpoint = (String) headerAccessor.getSessionAttributes().get("endpoint");

		Comment comment = new Comment();
		comment.setReview(review);
		comment.setUser(user);
		comment.setName(commentCreateDto.getName());
		commentRepository.save(comment);
		CommentDTO commentDto = MapperUtils.mapToDTO(comment, CommentDTO.class);
		commentDto.setReivewId(comment.getReview().getId());
		try {
			logService.createLog(user, "Create Comment SUCCESSFULLY", StatusLog.SUCCESSFULLY.toString(),
					objectMapper.writeValueAsString(commentCreateDto), "Websocket", endpoint, "GET", userAgent);
			return commentDto;
		} catch (IOException e) {

			try {
				logService.createLog(user, MethodUtil.handleSubstringMessage(e.getMessage()),
						StatusLog.FAILED.toString(), objectMapper.writeValueAsString(commentCreateDto), "Websocket",
						endpoint, "GET", userAgent);
				throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
						e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
			} catch (JsonProcessingException e1) {
				throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
						e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
			}
		}
	}

	@Override
	public CommentDTO updateComment(Integer id, CommentUpdateDTO commentUpdateDto,
			SimpMessageHeaderAccessor headerAccessor) {
		reviewRepository.findById(commentUpdateDto.getReviewId())
				.orElseThrow(() -> new ResourceNotFoundException("Review", "id", commentUpdateDto.getReviewId()));
		User user = userRepository.findById(commentUpdateDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", commentUpdateDto.getUserId()));
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
		String userAgent = (String) headerAccessor.getSessionAttributes().get("userAgent");
		String endpoint = (String) headerAccessor.getSessionAttributes().get("endpoint");
		if (commentUpdateDto.getStatus() != null)
			comment.setStatus(commentUpdateDto.getStatus());

		comment.setName(commentUpdateDto.getName());
		commentRepository.save(comment);

		CommentDTO commentDto = MapperUtils.mapToDTO(comment, CommentDTO.class);
		commentDto.setReivewId(comment.getReview().getId());

		try {
			logService.createLog(user, "Update Comment SUCCESSFULLY", StatusLog.SUCCESSFULLY.toString(),
					objectMapper.writeValueAsString(commentUpdateDto), "Websocket", endpoint, "POST", userAgent);
			return commentDto;
		} catch (IOException e) {

			try {
				logService.createLog(user, MethodUtil.handleSubstringMessage(e.getMessage()),
						StatusLog.FAILED.toString(), objectMapper.writeValueAsString(commentUpdateDto), "Websocket",
						endpoint, "POST", userAgent);
				throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
						e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
						e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
			}
		}

	}

	@Override
	public void deleteComment(int id, SimpMessageHeaderAccessor headerAccessor) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
		commentRepository.delete(comment);
		String userAgent = (String) headerAccessor.getSessionAttributes().get("userAgent");
		String endpoint = (String) headerAccessor.getSessionAttributes().get("endpoint");
		try {
			logService.createLog(comment.getUser(), "Delete Comment SUCCESSFULLY", StatusLog.SUCCESSFULLY.toString(),
					JsonConverter.convertToJSON("id", id), "Websocket", endpoint, "POST", userAgent);
		} catch (IOException e) {

			try {
				logService.createLog(comment.getUser(), MethodUtil.handleSubstringMessage(e.getMessage()),
						StatusLog.FAILED.toString(), JsonConverter.convertToJSON("id", id), "Websocket", endpoint,
						"POST", userAgent);
				throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
						e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());
			} catch (JsonProcessingException e1) {
				throw new CafeAPIException(HttpStatus.INTERNAL_SERVER_ERROR,
						e.getMessage().length() > 100 ? e.getMessage().substring(0, 100) : e.getMessage());

			}
		}

	}

	@Override
	public List<CommentDTO> getListCommentsByReviewId(int limit, int page, Integer reviewId, String sortBy) {
//		List<CommentDTO> list = this.getListComments(limit, page, sortBy, null, reviewId, sortBy);
		return null;
//		return list;
	}

}
