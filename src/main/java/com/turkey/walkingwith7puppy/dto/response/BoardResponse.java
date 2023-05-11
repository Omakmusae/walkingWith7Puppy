package com.turkey.walkingwith7puppy.dto.response;

import java.time.LocalDateTime;

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

	// private List<CommentResponse> comments = new ArrayList<>();

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
			// entity.getComments()
			// 	.stream()
			// 	.map(CommentResponse::from)
			// 	.sorted(Comparator.comparing(CommentResponse::getCreatedAt))
			// 	.toList(),
			entity.getCreatedAt(),
			entity.getModifiedAt()
		);
	}
}
