package com.turkey.walkingwith7puppy.dto;

import com.turkey.walkingwith7puppy.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {

	private Long id;
	private String content;

	public static CommentDto from(Comment entity) {
		return new CommentDto(
			entity.getId(),
			entity.getContent()
		);
	}
}
