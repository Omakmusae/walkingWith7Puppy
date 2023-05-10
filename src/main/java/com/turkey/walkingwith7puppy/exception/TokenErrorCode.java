package com.turkey.walkingwith7puppy.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {

	EXPIRED_TOKEN (HttpStatus.BAD_REQUEST, "만료된 JWT token 입니다"),
	INVALID_TOKEN (HttpStatus.BAD_REQUEST, "유효하지 않는 JWT 서명 입니다"),
	UNSUPPORTED_TOKEN (HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 토큰 입니다"),
	EMPTY_TOKEN (HttpStatus.BAD_REQUEST, "잘못된 JWT 토큰 입니다")
	;

	private final HttpStatus httpStatus;
	private final String message;
}
