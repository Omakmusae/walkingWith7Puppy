package com.turkey.walkingwith7puppy.dto;

import com.turkey.walkingwith7puppy.dto.request.BoardRequest;
import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class BoardDto {

	private String title;
	private String content;
	private String address;
	@Setter private String img;
	@Setter private Member member;

	private BoardDto(String title, String content, String address) {
		this.title = title;
		this.content = content;
		this.address = address;
	}

	public static BoardDto from(BoardRequest request) {

		return new BoardDto(
			request.getTitle(),
			request.getContent(),
			request.getAddress()
		);
	}

	public static Board toEntity (BoardDto boardDto) {

		return Board.of(
			boardDto.getTitle(),
			boardDto.getContent(),
			boardDto.getAddress(),
			boardDto.getImg(),
			boardDto.getMember()
		);
	}
}
