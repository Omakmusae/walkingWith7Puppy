package com.turkey.walkingwith7puppy.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BoardRequest {

	@NotNull
	@Max(value = 20)
	private String title;

	@NotNull
	@Max(value = 50)
	private String content;

	@NotNull
	private String address;

	@Setter	private String img;
	@Setter	private Member member;

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
