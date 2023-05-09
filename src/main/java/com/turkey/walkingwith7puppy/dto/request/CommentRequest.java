package com.turkey.walkingwith7puppy.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class CommentRequest {

    @NotNull
    private String content;
}
