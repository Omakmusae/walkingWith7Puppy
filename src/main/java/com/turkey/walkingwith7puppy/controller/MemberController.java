package com.turkey.walkingwith7puppy.controller;

import com.turkey.walkingwith7puppy.dto.request.MemberLoginRequest;
import com.turkey.walkingwith7puppy.dto.request.MemberSignupRequest;
import com.turkey.walkingwith7puppy.service.MemberService;
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

    @PostMapping("/user/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid final MemberSignupRequest memberSignupRequest) {

        memberService.signup(memberSignupRequest);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Void> login(
        @RequestBody final MemberLoginRequest memberLoginRequest,
        final HttpServletResponse response) {

        memberService.login(memberLoginRequest, response);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
