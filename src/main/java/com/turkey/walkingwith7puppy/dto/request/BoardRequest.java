package com.turkey.walkingwith7puppy.dto.request;

import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BoardRequest {

	private String title;
	private String content;
	private String address;
	@Setter private String img;
	@Setter private Member member;

	public static Board toEntity(BoardRequest boardRequest) {

		return Board.of(
			boardRequest.getTitle(),
			boardRequest.getContent(),
			boardRequest.getAddress(),
			boardRequest.getImg(),
			boardRequest.getMember()
		);
	}
}
