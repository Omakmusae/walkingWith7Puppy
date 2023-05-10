package com.turkey.walkingwith7puppy.controller;

import com.turkey.walkingwith7puppy.dto.request.MemberLoginRequest;
import com.turkey.walkingwith7puppy.dto.request.MemberSignupRequest;
import com.turkey.walkingwith7puppy.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @Operation(summary = "post user/signup", description = "회원가입 하기")
    @PostMapping("/user/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid final MemberSignupRequest memberSignupRequest) {

        memberService.signup(memberSignupRequest);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Operation(summary = "post user/login", description = "로그인하기")
    @PostMapping("/user/login")
    public ResponseEntity<Void> login(
        @RequestBody final MemberLoginRequest memberLoginRequest,
        final HttpServletResponse response) {

        memberService.login(memberLoginRequest, response);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
