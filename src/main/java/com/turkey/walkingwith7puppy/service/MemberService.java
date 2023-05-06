package com.turkey.walkingwith7puppy.service;

import com.turkey.walkingwith7puppy.dto.request.MemberLoginRequest;
import com.turkey.walkingwith7puppy.dto.request.MemberSignupRequest;
import com.turkey.walkingwith7puppy.entity.Member;

import com.turkey.walkingwith7puppy.jwt.JwtUtil;
import com.turkey.walkingwith7puppy.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final JwtUtil jwtUtil;

	@Transactional
	public void signup(MemberSignupRequest memberSignupRequest) {
		Optional<Member> searchedMember = memberRepository.findByUsername(memberSignupRequest.getUsername());

		if (searchedMember.isPresent()) {
			throw new IllegalArgumentException("중복된 아이디가 있습니다.");
		}

		Member member = MemberSignupRequest.toEntity(memberSignupRequest);
		memberRepository.save(member);
	}

	public void login(MemberLoginRequest memberLoginRequest, HttpServletResponse response) {
		String username = memberLoginRequest.getUsername();
		String password = memberLoginRequest.getPassword();

		Member searchedMember = memberRepository.findByUsername(username).orElseThrow(
			() -> new IllegalArgumentException("등록된 사용자가 없습니다.")
		);

		if (!searchedMember.getPassword().equals(password)) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(searchedMember.getUsername()));
	}

}
