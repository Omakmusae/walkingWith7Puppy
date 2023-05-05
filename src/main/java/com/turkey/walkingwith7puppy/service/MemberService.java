package com.turkey.walkingwith7puppy.service;

import com.turkey.walkingwith7puppy.dto.MemberLoginRequest;
import com.turkey.walkingwith7puppy.dto.MemberSignupRequest;
import com.turkey.walkingwith7puppy.entity.Member;

import com.turkey.walkingwith7puppy.jwt.JwtUtil;
import com.turkey.walkingwith7puppy.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";



    @Transactional
    public void signup(MemberSignupRequest memberSignupRequest) {


        // 회원 중복 확인
        Optional<Member> found = memberRepository.findByUsername(memberSignupRequest.getUsername());
        if (found.isPresent()) {
           throw new IllegalArgumentException("중복된 아이디가 있습니다.");
        }
//        UserRoleEnum role = UserRoleEnum.USER;
//        if(userRequestDto.isAdmin()){
//            if(!userRequestDto.getAdminToken().equals(ADMIN_TOKEN)){
//                throw new IllegalArgumentException("관리자 암호가 틀립니다");
//            }
//            role = UserRoleEnum.ADMIN;
//        }


        Member member = MemberSignupRequest.toEntity(memberSignupRequest);
        memberRepository.save(member);

    }

    @Transactional
    public void login(MemberLoginRequest memberLoginRequestDto, HttpServletResponse response) {
        String username = memberLoginRequestDto.getUsername();
        String password = memberLoginRequestDto.getPassword();

        // 사용자 확인
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!member.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //http header에 jwt 토큰 발급
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername()));


    }
}