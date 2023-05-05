package com.turkey.walkingwith7puppy.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Message signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        //String password = signupRequestDto.getPassword();
        // 비밀번호 암호화 해서 저장
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);
        return new Message("회원가입 성공", 200);
    }

    @Transactional(readOnly = true)
    public Message login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

//        // 비밀번호 확인
//        if(!user.getPassword().equals(password)){
//            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
        // 비밀번호 확인. (사용자가 입력한 비밀번호, 저장된 비밀번호)
        // 사용자가 입력한 비밀번호를 암호화해서 DB에 저장된 비밀번호와 비교하여 인증
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw  new CustomException(CANNOT_FOUND_USER);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

        return new Message("로그인 성공", 200);
    }
}