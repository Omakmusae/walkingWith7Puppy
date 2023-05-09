package com.turkey.walkingwith7puppy.dto;

import com.turkey.walkingwith7puppy.dto.request.CommentRequest;
import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Comment;
import com.turkey.walkingwith7puppy.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CommentDto {

	private String content;

	@Setter	private Member member;
	@Setter private Board board;

	private CommentDto(String content) {
		this.content = content;
	}

	public static CommentDto from (CommentRequest request) {

		return new CommentDto(
			request.getContent()
		);
	}

	public static Comment toEntity(CommentDto commentDto) {

		return Comment.of(
			commentDto.getContent(),
			commentDto.getMember(),
			commentDto.getBoard()
		);
	}
}
