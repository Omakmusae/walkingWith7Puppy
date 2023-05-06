package com.turkey.walkingwith7puppy.dto.request;

import com.turkey.walkingwith7puppy.entity.Member;
import lombok.Getter;

@Getter
public class MemberSignupRequest {

    private String username;
    private String password;
    private String email;

    public static Member toEntity(MemberSignupRequest memberSignupRequest) {
        return Member.of(
                memberSignupRequest.getUsername(),
                memberSignupRequest.getPassword(),
                memberSignupRequest.getEmail()
        );
    }

}
