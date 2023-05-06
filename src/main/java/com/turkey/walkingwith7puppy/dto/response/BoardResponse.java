package com.turkey.walkingwith7puppy.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.turkey.walkingwith7puppy.dto.CommentDto;
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
	private List<CommentDto> comments = new ArrayList<>();

	public static BoardResponse from(Board entity) {

		return new BoardResponse(
			entity.getId(),
			entity.getTitle(),
			entity.getContent(),
			entity.getMember().getUsername(),
			entity.getAddress(),
			entity.getComments()
				.stream().map(CommentDto::from)
				.toList()
		);
	}
}
