package com.turkey.walkingwith7puppy.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

	INACTIVE_MEMBER(HttpStatus.FORBIDDEN, "Member is inactive"),
	DUPLICATED_MEMBER(HttpStatus.BAD_REQUEST, "Username is duplicated"),
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
	;

	private final HttpStatus httpStatus;
	private final String message;
}
