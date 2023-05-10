package com.turkey.walkingwith7puppy.service;

import com.turkey.walkingwith7puppy.dto.TokenDto;
import com.turkey.walkingwith7puppy.dto.request.MemberLoginRequest;
import com.turkey.walkingwith7puppy.dto.request.MemberSignupRequest;

import com.turkey.walkingwith7puppy.entity.Member;
import com.turkey.walkingwith7puppy.entity.RefreshToken;

import com.turkey.walkingwith7puppy.exception.MemberErrorCode;
import com.turkey.walkingwith7puppy.exception.RestApiException;

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
    public void signup(final MemberSignupRequest memberSignupRequest) {

        throwIfExistOwner(memberSignupRequest.getUsername());
        String password = passwordEncoder.encode(memberSignupRequest.getPassword());
        Member member = MemberSignupRequest.toEntity(memberSignupRequest, password);
        memberRepository.save(member);
    }

    @Transactional
    public void login(final MemberLoginRequest memberLoginRequest, final HttpServletResponse response) {

        String username = memberLoginRequest.getUsername();
        String password = memberLoginRequest.getPassword();
        Member searchedMember = memberRepository.findByUsername(username).orElseThrow(
            () -> new RestApiException(MemberErrorCode.MEMBER_NOT_FOUND)
        );

        if(!passwordEncoder.matches(password, searchedMember.getPassword())){
            throw new RestApiException(MemberErrorCode.INVALID_PASSWORD);
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

    private void throwIfExistOwner(final String loginUsername) {

        Optional<Member> searchedMember = memberRepository.findByUsername(loginUsername);

        if (searchedMember.isPresent()) {
            throw new RestApiException(MemberErrorCode.DUPLICATED_MEMBER);
        }
    }
}
