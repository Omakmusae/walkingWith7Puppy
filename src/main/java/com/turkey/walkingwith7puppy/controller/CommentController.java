package com.turkey.walkingwith7puppy.controller;

import com.turkey.walkingwith7puppy.dto.request.CommentRequest;
import com.turkey.walkingwith7puppy.security.UserDetailsImpl;
import com.turkey.walkingwith7puppy.service.CommentService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/boards/{boardId}/comments")
    public void createComment(
        @PathVariable final Long boardId,
        @RequestBody final CommentRequest commentRequest,
        @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        commentService.createComment(boardId, commentRequest, userDetails.getMember());
    }

    @PutMapping("/boards/{boardId}/comments/{commentId}")
    public void updateComment(
        @PathVariable final Long boardId,
        @PathVariable final Long commentId,
        @RequestBody final CommentRequest commentRequest,
        @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        commentService.updateComment(boardId, commentId, commentRequest, userDetails.getMember());
    }

    @DeleteMapping("/boards/{boardId}/comments/{commentId}")
    public void deleteComment(
        @PathVariable final Long boardId,
        @PathVariable final Long commentId,
        @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        commentService.deleteComment(boardId, commentId, userDetails.getMember());
    }
}
