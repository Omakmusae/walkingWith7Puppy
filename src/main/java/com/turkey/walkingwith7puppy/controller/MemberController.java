package com.turkey.walkingwith7puppy.controller;

import com.turkey.walkingwith7puppy.dto.request.MemberLoginRequest;
import com.turkey.walkingwith7puppy.dto.request.MemberSignupRequest;
import com.turkey.walkingwith7puppy.service.MemberService;
import lombok.RequiredArgsConstructor;


import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/user/signup")
    public void signup(@RequestBody MemberSignupRequest memberSignupRequest) {
        memberService.signup(memberSignupRequest);
    }

    @PostMapping("/user/login")
    public void login(@RequestBody MemberLoginRequest memberLoginRequest, HttpServletResponse response) {
        memberService.login(memberLoginRequest, response);
    }

}
