package com.turkey.walkingwith7puppy.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class BoardRequest {

	@NotNull
	private String title;

	@NotNull
	private String content;

	@NotNull
	private String address;
}
