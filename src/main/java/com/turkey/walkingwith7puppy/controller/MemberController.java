package com.turkey.walkingwith7puppy.controller;

import com.turkey.walkingwith7puppy.dto.MemberLoginRequest;
import com.turkey.walkingwith7puppy.dto.MemberSignupRequest;
import com.turkey.walkingwith7puppy.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid MemberSignupRequest memberSignupRequest) {
        memberService.signup(memberSignupRequest);
    }


    @PostMapping("/login")
    public void login(@RequestBody MemberLoginRequest memberRequsetDto, HttpServletResponse response) {

        memberService.login(memberRequsetDto,response);

    }
}
