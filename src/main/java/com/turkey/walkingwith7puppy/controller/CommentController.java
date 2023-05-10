package com.turkey.walkingwith7puppy.controller;

import javax.validation.Valid;

import com.turkey.walkingwith7puppy.dto.CommentDto;
import com.turkey.walkingwith7puppy.dto.request.CommentRequest;
import com.turkey.walkingwith7puppy.security.UserDetailsImpl;
import com.turkey.walkingwith7puppy.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "post boards/id", description = "댓글 작성하기")
    @PostMapping("/boards/{boardId}/comments")
    public ResponseEntity<Void> createComment(
        @PathVariable final Long boardId,
        @RequestBody @Valid final CommentRequest commentRequest,
        @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        commentService.createComment(boardId, CommentDto.from(commentRequest), userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Operation(summary = "put boards/boardid/comments/commentid", description = "댓글 수정하기")
    @PutMapping("/boards/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> updateComment(
        @PathVariable final Long boardId,
        @PathVariable final Long commentId,
        @RequestBody @Valid final CommentRequest commentRequest,
        @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        commentService.updateComment(boardId, commentId, CommentDto.from(commentRequest), userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    @Operation(summary = "delete boards/boardid/comments/commentid", description = "댓글 삭제하기")
    @DeleteMapping("/boards/{boardId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @PathVariable final Long boardId,
        @PathVariable final Long commentId,
        @AuthenticationPrincipal final UserDetailsImpl userDetails) {

        commentService.deleteComment(boardId, commentId, userDetails.getMember());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
