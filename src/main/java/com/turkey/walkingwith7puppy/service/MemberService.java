package com.turkey.walkingwith7puppy.service;

import com.turkey.walkingwith7puppy.dto.request.MemberLoginRequest;
import com.turkey.walkingwith7puppy.dto.request.MemberSignupRequest;
import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Member;

import com.turkey.walkingwith7puppy.exception.MemberErrorCode;
import com.turkey.walkingwith7puppy.exception.RestApiException;
import com.turkey.walkingwith7puppy.jwt.JwtUtil;
import com.turkey.walkingwith7puppy.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(MemberSignupRequest memberSignupRequest) {
        Optional<Member> searchedMember = memberRepository.findByUsername(memberSignupRequest.getUsername());
        String password = passwordEncoder.encode(memberSignupRequest.getPassword());

        throwIfExistOwner(memberSignupRequest.getUsername());

        Member member = MemberSignupRequest.toEntity(memberSignupRequest, password);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public void login(MemberLoginRequest memberLoginRequest, HttpServletResponse response) {
        String username = memberLoginRequest.getUsername();
        String password = memberLoginRequest.getPassword();

        Member searchedMember = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if(!passwordEncoder.matches(password, searchedMember.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(searchedMember.getUsername()));
    }

    private void throwIfExistOwner(String loginUsername) {

        Optional<Member> searchedMember = memberRepository.findByUsername(loginUsername);

        if (searchedMember.isPresent()) {
            throw new RestApiException(MemberErrorCode.DUPLICATED_MEMBER);
        }
    }
}
