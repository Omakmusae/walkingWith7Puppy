package com.turkey.walkingwith7puppy.jwt;

import com.turkey.walkingwith7puppy.entity.Member;
import com.turkey.walkingwith7puppy.exception.RestApiException;
import com.turkey.walkingwith7puppy.exception.TokenErrorCode;
import com.turkey.walkingwith7puppy.repository.MemberRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String access_token = jwtUtil.resolveToken(request, jwtUtil.ACCESS_KEY);
        String refresh_token = jwtUtil.resolveToken(request, jwtUtil.REFRESH_KEY);


        if(access_token != null) {
            if(jwtUtil.validateToken(access_token)) {
                setAuthentication(jwtUtil.getUserInfoFromToken(access_token));
            } else if(refresh_token != null && jwtUtil.refreshTokenValidation(refresh_token)) {
                String username = jwtUtil.getUserInfoFromToken(refresh_token);
                Member member = memberRepository.findByUsername(username).get();
                String newAccessToken = jwtUtil.createToken(username, "Access");
                jwtUtil.setHeaderAccessToken(response, newAccessToken);
                setAuthentication(username);
            } else if(refresh_token == null) {
                throw new RestApiException(TokenErrorCode.EXPIRED_ACCESS_TOKEN);
            } else {
                throw new RestApiException(TokenErrorCode.EXPIRED_REFRESH_TOKEN);
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String username) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
    //
    //     response.setStatus(statusCode);
    //     response.setContentType("application/json");
    //     try {
    //         String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
    //         response.getWriter().write(json);
    //     } catch (Exception e) {
    //         log.error(e.getMessage());
    //     }
    // }

}
