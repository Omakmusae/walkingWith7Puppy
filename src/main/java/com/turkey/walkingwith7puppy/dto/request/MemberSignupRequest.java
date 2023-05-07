package com.turkey.walkingwith7puppy.dto.request;

import com.turkey.walkingwith7puppy.annotation.Email;
import com.turkey.walkingwith7puppy.annotation.Password;
import com.turkey.walkingwith7puppy.annotation.Username;
import com.turkey.walkingwith7puppy.entity.Member;
import lombok.Getter;

@Getter
public class MemberSignupRequest {

    @Username
    private String username;

    @Password
    private String password;

    @Email
    private String email;

    public static Member toEntity(MemberSignupRequest memberSignupRequest) {
        return Member.of(
                memberSignupRequest.getUsername(),
                memberSignupRequest.getPassword(),
                memberSignupRequest.getEmail()
        );
    }

}
