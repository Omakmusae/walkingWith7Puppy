package com.turkey.walkingwith7puppy.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	String name();
	HttpStatus getHttpStatus();
	String getMessage();
}
