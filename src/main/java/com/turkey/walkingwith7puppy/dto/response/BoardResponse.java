package com.turkey.walkingwith7puppy.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.turkey.walkingwith7puppy.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardResponse {

	private Long id;
	private String title;
	private String content;
	private String username;
	private String address;
	private String img;

	private List<CommentResponse> comments = new ArrayList<>();

	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public static BoardResponse from(Board entity) {

		return new BoardResponse(
			entity.getId(),
			entity.getTitle(),
			entity.getContent(),
			entity.getMember().getUsername(),
			entity.getAddress(),
			entity.getImg(),
			entity.getComments()
				.stream().map(CommentResponse::from)
				.toList(),
			entity.getCreatedAt(),
			entity.getModifiedAt()
		);
	}
}
