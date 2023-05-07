package com.turkey.walkingwith7puppy.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

	private final ErrorCode errorCode;
}
