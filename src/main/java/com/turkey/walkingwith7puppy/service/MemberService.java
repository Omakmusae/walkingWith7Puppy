package com.turkey.walkingwith7puppy.service;

import com.turkey.walkingwith7puppy.dto.TokenDto;
import com.turkey.walkingwith7puppy.dto.request.MemberLoginRequest;
import com.turkey.walkingwith7puppy.dto.request.MemberSignupRequest;
import com.turkey.walkingwith7puppy.entity.Member;

import com.turkey.walkingwith7puppy.entity.RefreshToken;
import com.turkey.walkingwith7puppy.jwt.JwtUtil;
import com.turkey.walkingwith7puppy.repository.MemberRepository;
import com.turkey.walkingwith7puppy.repository.RefreshTokenRepository;

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
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signup(MemberSignupRequest memberSignupRequest) {
        Optional<Member> searchedMember = memberRepository.findByUsername(memberSignupRequest.getUsername());
        String password = passwordEncoder.encode(memberSignupRequest.getPassword());

        if (searchedMember.isPresent()) {
           throw new IllegalArgumentException("중복된 아이디가 있습니다.");
        }

        Member member = MemberSignupRequest.toEntity(memberSignupRequest, password);
        memberRepository.save(member);
    }

    @Transactional
    public void login(MemberLoginRequest memberLoginRequest, HttpServletResponse response) {

        String username = memberLoginRequest.getUsername();
        String password = memberLoginRequest.getPassword();

        Member searchedMember = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if(!passwordEncoder.matches(password, searchedMember.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(username);
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUsername(username);

        if(refreshToken.isPresent()) {
            RefreshToken updateToken = refreshToken.get().updateToken(tokenDto.getRefreshToken().substring(7));
            refreshTokenRepository.save(updateToken);
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken().substring(7), memberLoginRequest.getUsername());
            refreshTokenRepository.save(newToken);
        }

        response.addHeader(jwtUtil.ACCESS_KEY, tokenDto.getAccessToken());
        response.addHeader(jwtUtil.REFRESH_KEY, tokenDto.getRefreshToken());
    }
}
