package com.turkey.walkingwith7puppy.dto.response;

import java.time.LocalDateTime;

import com.turkey.walkingwith7puppy.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardListResponse {

	private Long id;
	private String title;
	private String content;
	private String username;
	private String address;
	private String img;

	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public static BoardListResponse from(Board entity) {

		return new BoardListResponse(
			entity.getId(),
			entity.getTitle(),
			entity.getContent(),
			entity.getMember().getUsername(),
			entity.getAddress(),
			entity.getImg(),
			entity.getCreatedAt(),
			entity.getModifiedAt()
		);
	}
}
