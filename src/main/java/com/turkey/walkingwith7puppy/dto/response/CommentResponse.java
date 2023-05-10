package com.turkey.walkingwith7puppy.dto.response;

import java.time.LocalDateTime;

import com.turkey.walkingwith7puppy.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {

	private Long id;
	private String content;
	private String username;

	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public static CommentResponse from(Comment entity) {

		return new CommentResponse(
			entity.getId(),
			entity.getContent(),
			entity.getMember().getUsername(),
			entity.getCreatedAt(),
			entity.getModifiedAt()
		);
	}
}
