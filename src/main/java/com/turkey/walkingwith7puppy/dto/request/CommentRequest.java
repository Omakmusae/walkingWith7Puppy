package com.turkey.walkingwith7puppy.dto.request;

import com.turkey.walkingwith7puppy.entity.Board;
import com.turkey.walkingwith7puppy.entity.Comment;
import com.turkey.walkingwith7puppy.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CommentRequest {

    private String content;
    @Setter private Member member;
    @Setter private Board board;

    public static Comment toEntity(CommentRequest commentRequest) {
        return Comment.of(
            commentRequest.getContent(),
            commentRequest.getMember(),
            commentRequest.getBoard()
        );
    }
}
