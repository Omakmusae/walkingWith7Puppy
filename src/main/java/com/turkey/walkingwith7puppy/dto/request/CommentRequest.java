package com.turkey.walkingwith7puppy.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class CommentRequest {

    @NotNull
    @Max(value = 30)
    private String content;
}
