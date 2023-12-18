package com.cafe.website.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cafe.website.payload.CommentCreateDTO;
import com.cafe.website.payload.CommentDTO;
import com.cafe.website.payload.CommentDeleteDTO;
import com.cafe.website.payload.CommentListDTO;
import com.cafe.website.payload.CommentUpdateDTO;
import com.cafe.website.service.CommentService;
import com.cafe.website.serviceImp.ProductServiceImp;

import jakarta.validation.Valid;

@Controller
public class CommentSocketController {
	private CommentService commentService;
	private final SimpMessagingTemplate messagingTemplate;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public CommentSocketController(CommentService commentService, SimpMessagingTemplate messagingTemplate) {
		super();
		this.commentService = commentService;
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/getComments")
	public List<CommentDTO> getListComment(@Payload CommentListDTO commentListDTO) {
		List<CommentDTO> list = commentService.getListComments(commentListDTO.getLimit(), commentListDTO.getPage(),
				commentListDTO.getName(), commentListDTO.getUserId(), commentListDTO.getReviewId(),
				commentListDTO.getCreatedAt(), commentListDTO.getUpdatedAt(), commentListDTO.getSortBy());
		if (commentListDTO.getReviewId() != null)
			messagingTemplate.convertAndSend("/api/v1/getComments/" + commentListDTO.getReviewId(), list);
		else
			messagingTemplate.convertAndSend("/api/v1/admin/getComments", list);
		return list;
	}

	@GetMapping("/api/v1/comments/id/{id}")
	public ResponseEntity<CommentDTO> getCommentById(@PathVariable(name = "id") Integer id) {
		CommentDTO comment = commentService.getCommentById(id);
		return new ResponseEntity<CommentDTO>(comment, HttpStatus.OK);
	}

	@MessageMapping("/createComment")
	public CommentDTO createComment(@Valid @Payload CommentCreateDTO commentCreateDto,
			SimpMessageHeaderAccessor headerAccessor) {
		CommentDTO comment = commentService.createComment(commentCreateDto, headerAccessor);
		CommentListDTO commentlist = new CommentListDTO();
		commentlist.setReviewId(commentCreateDto.getReviewId());
		this.getListComment(commentlist);
		return comment;
	}

	@MessageMapping("/admin/createComment")
	public CommentDTO createCommentAdmin(@Valid @Payload CommentCreateDTO commentCreateDto,
			SimpMessageHeaderAccessor headerAccessor) {
		CommentDTO comment = commentService.createComment(commentCreateDto, headerAccessor);
		CommentListDTO commentlist = new CommentListDTO();
		this.getListComment(commentlist);
		return comment;
	}

	@MessageMapping("/updateComment")
	public CommentDTO updateComment(@Valid @Payload CommentUpdateDTO commentUpdateDTO,
			SimpMessageHeaderAccessor headerAccessor) {
		CommentDTO comment = commentService.updateComment(commentUpdateDTO.getId(), commentUpdateDTO, headerAccessor);
		CommentListDTO commentlist = new CommentListDTO();
		commentlist.setReviewId(commentUpdateDTO.getReviewId());
		this.getListComment(commentlist);
		return comment;
	}

	@MessageMapping("/admin/updateComment")
	public CommentDTO updateCommentAdmin(@Valid @Payload CommentUpdateDTO commentUpdateDTO,
			SimpMessageHeaderAccessor headerAccessor) {
		CommentDTO comment = commentService.updateComment(commentUpdateDTO.getId(), commentUpdateDTO, headerAccessor);
		CommentListDTO commentlist = new CommentListDTO();
		this.getListComment(commentlist);
		return comment;
	}

	@MessageMapping("/deleteComment")
	public String delteCommentById(@Payload CommentDeleteDTO deleteDTO, SimpMessageHeaderAccessor headerAccessor) {
		commentService.deleteComment(deleteDTO.getId(), headerAccessor);
		CommentListDTO commentlist = new CommentListDTO();
		commentlist.setReviewId(deleteDTO.getReviewId());
		this.getListComment(commentlist);
		return "Delete successfuly";
	}

	@MessageMapping("/admin/deleteComment")
	public String delteCommentByIdAdmin(@Payload CommentDeleteDTO deleteDTO, SimpMessageHeaderAccessor headerAccessor) {
		commentService.deleteComment(deleteDTO.getId(), headerAccessor);
		CommentListDTO commentlist = new CommentListDTO();
		this.getListComment(commentlist);
		return "Delete successfuly";
	}

	@MessageExceptionHandler
	public void handleException(Throwable exception, SimpMessageSendingOperations messagingTemplate,
			SimpMessageHeaderAccessor headerAccessor) {
		// Tạo thông điệp lỗi
		String errorMessage = "Error: " + exception.getMessage();

		// Xác định sessionId của người dùng hiện tại
		String sessionId = headerAccessor.getSessionId();

		// Gửi thông điệp lỗi chỉ tới người dùng này
		messagingTemplate.convertAndSendToUser(sessionId, "/queue/errors", errorMessage);
		logger.info("RUNNN");
	}
}
