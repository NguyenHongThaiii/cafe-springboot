package com.cafe.website.serviceImp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafe.website.constant.SortField;
import com.cafe.website.entity.Comment;
import com.cafe.website.entity.Review;
import com.cafe.website.entity.User;
import com.cafe.website.exception.ResourceNotFoundException;
import com.cafe.website.payload.CommentCreateDTO;
import com.cafe.website.payload.CommentDTO;
import com.cafe.website.payload.CommentUpdateDTO;
import com.cafe.website.repository.CommentRepository;
import com.cafe.website.repository.ReviewRepository;
import com.cafe.website.repository.UserRepository;
import com.cafe.website.service.CommentService;
import com.cafe.website.util.MapperUtils;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class CommentServiceImp implements CommentService {
	@PersistenceContext
	private EntityManager entityManager;
	private CommentRepository commentRepository;
	private UserRepository userRepository;
	private ReviewRepository reviewRepository;

	public CommentServiceImp(CommentRepository commentRepository, UserRepository userRepository,
			ReviewRepository reviewRepository) {
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
		this.reviewRepository = reviewRepository;
	}

	@Override
	public List<CommentDTO> getListComments(int limit, int page, String name, Integer userId, Integer reviewId,
			String sortBy) {
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
		listComment = commentRepository.findWithFilters(name, reviewId, userId, pageable, entityManager);

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
	public CommentDTO createComment(CommentCreateDTO commentCreateDto) {
		Review review = reviewRepository.findById(commentCreateDto.getReviewId())
				.orElseThrow(() -> new ResourceNotFoundException("Review", "id", commentCreateDto.getReviewId()));
		User user = userRepository.findById(commentCreateDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", commentCreateDto.getUserId()));

		Comment comment = new Comment();
		comment.setReview(review);
		comment.setUser(user);
		comment.setName(commentCreateDto.getName());
		commentRepository.save(comment);
		CommentDTO commentDto = MapperUtils.mapToDTO(comment, CommentDTO.class);
		commentDto.setReivewId(comment.getReview().getId());
		return commentDto;
	}

	@Override
	public CommentDTO updateComment(int id, CommentUpdateDTO commentUpdateDto) {
		reviewRepository.findById(commentUpdateDto.getReviewId())
				.orElseThrow(() -> new ResourceNotFoundException("Review", "id", commentUpdateDto.getReviewId()));
		userRepository.findById(commentUpdateDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", commentUpdateDto.getUserId()));
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

		if (commentUpdateDto.getStatus() != null)
			comment.setStatus(commentUpdateDto.getStatus());

		comment.setName(commentUpdateDto.getName());
		commentRepository.save(comment);

		CommentDTO commentDto = MapperUtils.mapToDTO(comment, CommentDTO.class);
		commentDto.setReivewId(comment.getReview().getId());
		return commentDto;
	}

	@Override
	public void deleteComment(int id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
		commentRepository.delete(comment);
	}

	@Override
	public List<CommentDTO> getListCommentsByReviewId(int limit, int page, Integer reviewId, String sortBy) {
		List<CommentDTO> list = this.getListComments(limit, page, sortBy, null, reviewId, sortBy);

		return list;
	}

}
