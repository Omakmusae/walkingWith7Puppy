package com.turkey.walkingwith7puppy.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import lombok.Getter;

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
}
